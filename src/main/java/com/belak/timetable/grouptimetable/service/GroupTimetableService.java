package com.belak.timetable.grouptimetable.service;

import com.belak.timetable.exception.EmptyExcelFileException;
import com.belak.timetable.exception.FileProcessingException;
import com.belak.timetable.exception.InvalidExcelFormatException;
import com.belak.timetable.exception.LibreOfficeConversionException;
import com.belak.timetable.grouptimetable.entity.GroupTimetableEntity;
import com.belak.timetable.grouptimetable.repository.GroupTimetableRepository;
import com.belak.timetable.professor.entity.ProfessorEntity;
import com.belak.timetable.professortimetable.entity.ProfessorTimetableEntity;
import com.belak.timetable.student.entity.StudentEntity;
import com.belak.timetable.student.repository.StudentRepository;
import com.spire.xls.ExcelVersion;
import com.spire.xls.Workbook;
import com.spire.xls.Worksheet;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;

@Service
public class GroupTimetableService {
    private final GroupTimetableRepository groupTimetableRepository ;
    private final StudentRepository studentRepository ;
    GroupTimetableService(GroupTimetableRepository groupTimetableRepository , StudentRepository studentRepository)
    {
        this.groupTimetableRepository=groupTimetableRepository;
        this.studentRepository=studentRepository;
    }

    public void sendManyGroupTimetable( MultipartFile file) throws IOException , InterruptedException{

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

            Pattern pattern = Pattern.compile("[123]");

            for (int position = 0; position < sheetNumbers; position++) {

                Workbook singleWorkbook = new Workbook();

                try {

                    Worksheet sourceSheet = originalWorkbook.getWorksheets().get(position);
                    singleWorkbook.getWorksheets().clear();
                    singleWorkbook.getWorksheets().addCopy(sourceSheet);

                    Worksheet dataSheet = singleWorkbook.getWorksheets().get(0);

                    // =========================
                    // Lecture sécurisée données
                    // =========================

                    String department = dataSheet.getCellRange(1, 3).getValue();

                    if (department == null || !department.contains(" ")) {
                        throw new InvalidExcelFormatException("Department invalide sheet " + position);
                    }

                    department = department.trim();

                    String[] parts = department.split(" ", 2);

                    if (parts.length < 2) {
                        throw new InvalidExcelFormatException("Format department incorrect sheet " + position);
                    }

                    String depname = parts[1].trim();

                    String fullfield = dataSheet.getCellRange(6, 7).getValue();

                    if (fullfield == null) {
                        throw new InvalidExcelFormatException("Fullfield manquant sheet " + position);
                    }

                    fullfield = fullfield.trim();

                    Matcher matcher = pattern.matcher(fullfield);

                    if (!matcher.find()) {
                        throw new InvalidExcelFormatException("Année introuvable sheet " + position);
                    }

                    int year = Integer.parseInt(matcher.group());
                    int index = matcher.start();

                    String field = fullfield.substring(0, index).trim();
                    String group = fullfield.substring(index + 1).trim();

                    if (group.endsWith(" .")) {
                        group = group.substring(0, group.length() - 2).trim();
                        System.out.println(" . a ete retire ");
                    }
                    if (group.startsWith("-")) {
                        group = group.substring(1).trim();
                        System.out.println("- a ete retire");
                    }
                    // =========================
                    // Ajustement mise en page
                    // =========================

                    for (int i = 14; i <= 21; i++) {
                        dataSheet.setRowHeight(i, 40);
                    }

                    dataSheet.getPageSetup().setFitToPagesWide(1);

                    // =========================
                    // Sauvegarde Excel temporaire
                    // =========================

                    File tempExcel = new File(tempDir, "GroupTimetable_" + position + ".xlsx");

                    singleWorkbook.saveToFile(
                            tempExcel.getAbsolutePath(),
                            ExcelVersion.Version2013
                    );

                    // =========================
                    // Conversion PDF
                    // =========================

                    String libreOfficePath =
                            "C:\\Program Files\\LibreOffice\\program\\soffice.exe";

                    ProcessBuilder pb = new ProcessBuilder(
                            libreOfficePath,
                            "--headless",
                            "--convert-to",
                            "pdf",
                            "--outdir",
                            outputDir,
                            tempExcel.getAbsolutePath()
                    );

                    pb.redirectErrorStream(true); // IMPORTANT

                    Process process = pb.start();

                    int exitCode = process.waitFor();

                    if (exitCode != 0) {
                        throw new LibreOfficeConversionException(
                                "Erreur conversion PDF sheet " + position
                        );
                    }

                    // =========================
                    // Lecture du PDF généré
                    // =========================

                    Path pdfPath = Paths.get(
                            outputDir,
                            "GroupTimetable_" + position + ".pdf"
                    );

                    if (!Files.exists(pdfPath)) {
                        throw new LibreOfficeConversionException(
                                "PDF non généré sheet " + position
                        );
                    }

                    byte[] pdfBytes = Files.readAllBytes(pdfPath);

                    System.out.println(
                            "Saving: " + depname +
                                    ", year: " + year +
                                    ", field: " + field +
                                    ", group: " + group
                    );

                    System.out.println("File size: " + pdfBytes.length);

                    // =========================
                    // Sauvegarde BDD
                    // =========================

                    GroupTimetableEntity groupTimetableEntity =
                            GroupTimetableEntity.builder()
                                    .department(depname)
                                    .position(position)
                                    .filename("GroupTimetable_" + position + ".pdf")
                                    .contentType("application/pdf")
                                    .fileData(pdfBytes)
                                    .year(year)
                                    .field(field)
                                    .group(group)
                                    .build();
                    List<StudentEntity> students =
                            studentRepository.findByDepartmentAndFieldAndYearAndGroup(depname, field, year, group);

                    for (StudentEntity student : students) {
                        groupTimetableEntity.addStudent(student);
                    }

                    groupTimetableRepository.save(groupTimetableEntity);
                    // =========================
                    // Nettoyage
                    // =========================

                    Files.deleteIfExists(pdfPath);
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
    public ResponseEntity<byte[]> getTimetableFileTest(String userId) {

        if (studentRepository.findByUserId(userId).isPresent()) {
            GroupTimetableEntity groupTimetableEntity = studentRepository
                    .findByUserId(userId).get().getGroupTimetable();

            // On renvoie directement le tableau de bytes avec les headers
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(groupTimetableEntity.getFileData()); // Pas de header "attachment", pour que le navigateur l'affiche
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
    public Page<GroupTimetableEntity> getGroupTimetables(int page , int size)
    {
        Pageable pageable = PageRequest.of(page, size);
        return groupTimetableRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public byte[] getStudentPreview(String userId) throws IOException {

        StudentEntity student = studentRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new RuntimeException("Etudiant non trouvé pour userId : " + userId)
                );

        if (student.getGroupTimetable() == null) {
            throw new RuntimeException("Aucun emploi du temps associé a etudiant");
        }

        byte[] pdfBytes = student.getGroupTimetable().getFileData();

        if (pdfBytes == null || pdfBytes.length == 0) {
            throw new RuntimeException("Le fichier PDF est vide");
        }

        return convertFirstPageToImage(pdfBytes);
    }
    @Transactional(readOnly = true)
    public byte[] getGroupTimetablePreview(Long id) throws IOException {

        GroupTimetableEntity groupTimetable = groupTimetableRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Professor non trouvé pour userId : " + id)
                );


        byte[] pdfBytes = groupTimetable.getFileData();

        if (pdfBytes == null || pdfBytes.length == 0) {
            throw new RuntimeException("Le fichier PDF est vide");
        }

        return convertFirstPageToImage(pdfBytes);
    }
}
