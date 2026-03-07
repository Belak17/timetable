package com.belak.timetable.professortimetable.service;

import com.belak.timetable.exception.*;
import com.belak.timetable.professor.entity.ProfessorEntity;
import com.belak.timetable.professor.repository.ProfessorRepository;
import com.belak.timetable.professortimetable.entity.ProfessorTimetableEntity;
import com.belak.timetable.professortimetable.repository.ProfessorTimetableRepository;
import com.spire.xls.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.rendering.PDFRenderer;
//import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class ProfessorTimetableService {
    private final ProfessorTimetableRepository professorTimetableRepository;
    private final ProfessorRepository professorRepository;
    ProfessorTimetableService(ProfessorTimetableRepository professorTimetableRepository, ProfessorRepository professorRepository)
    {
        this.professorTimetableRepository=professorTimetableRepository;
        this.professorRepository=professorRepository;
    }

    public void sendManyProfessorTimetable(MultipartFile file) throws IOException, InterruptedException {

        if (file.isEmpty()) {
            throw new EmptyExcelFileException("Le fichier Excel est vide");
        }

        Workbook originalWorkbook = new Workbook();
        try {
            originalWorkbook.loadFromStream(file.getInputStream());

            int sheetNumbers = originalWorkbook.getWorksheets().getCount();
            String tempDir = System.getProperty("java.io.tmpdir");
            String outputDir = System.getProperty("user.dir") + "/output";
            Files.createDirectories(Paths.get(outputDir));

            for (int position = 0; position < sheetNumbers; position++) {

                Workbook singleWorkbook = new Workbook();
                try {
                    Worksheet sourceSheet = originalWorkbook.getWorksheets().get(position);
                    singleWorkbook.getWorksheets().clear();
                    singleWorkbook.getWorksheets().addCopy(sourceSheet);
                    Worksheet dataSheet = singleWorkbook.getWorksheets().get(0);

                    // Lecture des données
                    String fullname = dataSheet.getCellRange(4, 9).getValue().trim();
                    String grade = dataSheet.getCellRange(8, 7).getValue().trim();
                    String statut = dataSheet.getCellRange(10, 13).getValue().trim();
                    String speciality = dataSheet.getCellRange(11, 20).getValue().trim();

                    if (fullname == null || !fullname.contains(" ")) {
                        throw new InvalidExcelFormatException("Nom complet manquant ou mal formaté à la sheet " + position);
                    }

                    String[] parts = fullname.trim().split("\\s+", 2);

                    String firstname = parts[0].trim();
                    String lastname = parts.length > 1 ? parts[1].trim() : "";

                    // Ajustement hauteur lignes
                    for (int i = 14; i <= 21; i++) dataSheet.setRowHeight(i, 40);
                    dataSheet.getPageSetup().setFitToPagesWide(1);

                    // Sauvegarde temporaire Excel
                    File tempExcel = new File(tempDir, "Timetable_" + position + ".xlsx");
                    singleWorkbook.saveToFile(tempExcel.getAbsolutePath(), ExcelVersion.Version2013);

                    // Conversion PDF
                    String libreOfficePath = "C:\\Program Files\\LibreOffice\\program\\soffice.exe";
                    ProcessBuilder pb = new ProcessBuilder(
                            libreOfficePath, "--headless", "--convert-to", "pdf",
                            "--outdir", outputDir, tempExcel.getAbsolutePath()
                    );
                    Process process = pb.start();
                    int exitCode = process.waitFor();
                    if (exitCode != 0) {
                        throw new LibreOfficeConversionException("Erreur conversion PDF à la sheet " + position);
                    }

                    String pdfPath = outputDir + "/Timetable_" + position + ".pdf";
                    byte[] pdfBytes = Files.readAllBytes(Paths.get(pdfPath));



                    Optional<ProfessorEntity> optionalProfessor = professorRepository.findByNameNormalized(firstname, lastname);
                    int finalPosition = position;
                    optionalProfessor.ifPresent(professorEntity ->
                    { ProfessorTimetableEntity timetableEntity = ProfessorTimetableEntity.builder()
                            .grade(grade) .statut(statut)
                            .speciality(speciality) .position(finalPosition)
                            .filename("Timetable_" + finalPosition + ".pdf")
                            .contentType("application/pdf") .fileData(pdfBytes) .build();
                        // Lier les deux côtés
                        professorEntity.setTimetable(timetableEntity);
                        timetableEntity.setProfessor(professorEntity);
                        // Sauvegarde propriétaire
                        professorRepository.save(professorEntity);
                         });

                    // Nettoyage
                    Files.deleteIfExists(Paths.get(pdfPath));
                    Files.deleteIfExists(tempExcel.toPath());

                } finally {
                    singleWorkbook.dispose(); // toujours fermer
                }
            }

        } catch (IOException | InterruptedException e) {
            throw new FileProcessingException("Erreur lors du traitement du fichier Excel.");
        } finally {
            originalWorkbook.dispose(); // toujours fermer
        }
    }
    public ProfessorTimetableEntity getTimetableEntity(Long id)
    {
        return professorTimetableRepository
                .findById(id)
                .orElseThrow();
    }
    public ResponseEntity<byte[]> getTimetableFileTest(String userId) {

        if (professorRepository.findByUserId(userId).isPresent()) {
            ProfessorTimetableEntity professorTimetableEntity = professorRepository
                    .findByUserId(userId).get().getTimetable();

            // On renvoie directement le tableau de bytes avec les headers
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(professorTimetableEntity.getFileData()); // Pas de header "attachment", pour que le navigateur l'affiche
        }
        return null ;
    }

    public byte[] convertFirstPageToImage(byte[] pdfBytes) throws IOException {

        try (PDDocument document = PDDocument.load(pdfBytes)) {

            PDFRenderer pdfRenderer = new PDFRenderer(document);
            PDPage page = document.getPage(0);
            PDRectangle mediaBox = page.getMediaBox();

            float pdfHeight = mediaBox.getHeight();
            float targetHeight = 1000f; // plus grand que nécessaire
            float scale = targetHeight / pdfHeight;
            //BufferedImage image = pdfRenderer.renderImage(0, scale);
            BufferedImage image = pdfRenderer.renderImageWithDPI(0, 150);
            // 0 = première page

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);

            return baos.toByteArray();
        }
    }
    public Page<ProfessorTimetableEntity> getProfessorTimetables(int page , int size)
    {
        Pageable pageable = PageRequest.of(page, size);
        return professorTimetableRepository.findAll(pageable);
    }
    @Transactional(readOnly = true)
    public byte[] getProfessorPreview(String userId) throws IOException {

        ProfessorEntity professor = professorRepository
                .findByUserIdWithTimetable(userId)
                .orElseThrow(() ->
                        new RuntimeException("Professor non trouvé pour userId : " + userId)
                );

        if (professor.getTimetable() == null) {
            throw new RuntimeException("Aucun emploi du temps associé au professeur");
        }

        byte[] pdfBytes = professor.getTimetable().getFileData();

        if (pdfBytes == null || pdfBytes.length == 0) {
            throw new RuntimeException("Le fichier PDF est vide");
        }

        return convertFirstPageToImage(pdfBytes);
    }

    @Transactional(readOnly = true)
    public byte[] getProfessorTimetablePreview(Long id) throws IOException {

        ProfessorTimetableEntity professorTimetable = professorTimetableRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Professor non trouvé pour userId : " + id)
                );


        byte[] pdfBytes = professorTimetable.getFileData();

        if (pdfBytes == null || pdfBytes.length == 0) {
            throw new RuntimeException("Le fichier PDF est vide");
        }

        return convertFirstPageToImage(pdfBytes);
    }
}
