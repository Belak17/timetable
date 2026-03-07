package com.belak.timetable.grouptimetable.controller;

import com.belak.timetable.exception.EmptyExcelFileException;
import com.belak.timetable.exception.FileProcessingException;
import com.belak.timetable.exception.InvalidExcelFormatException;
import com.belak.timetable.exception.LibreOfficeConversionException;
import com.belak.timetable.grouptimetable.entity.GroupTimetableEntity;
import com.belak.timetable.grouptimetable.service.GroupTimetableService;
import com.belak.timetable.professortimetable.entity.ProfessorTimetableEntity;
import com.belak.timetable.professortimetable.service.ProfessorTimetableService;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class GroupTimetableController {
    private final GroupTimetableService groupTimetableService;
    GroupTimetableController(GroupTimetableService groupTimetableService)
    {
        this.groupTimetableService=groupTimetableService ;
    }
    @PostMapping("/upload/grouptimetable")
    public String upload(Authentication authentication, @RequestParam("excelFile") MultipartFile file , Model model , RedirectAttributes redirectAttributes)  {

        try {
            groupTimetableService.sendManyGroupTimetable(file);
            redirectAttributes.addFlashAttribute("success", "Upload terminé avec succès");
        } catch (EmptyExcelFileException e) {
            redirectAttributes.addFlashAttribute("error", "Le fichier est vide !");
        } catch (InvalidExcelFormatException e) {
            redirectAttributes.addFlashAttribute("error", "Erreur de format dans le fichier Excel : " + e.getMessage());
        } catch (LibreOfficeConversionException e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la conversion PDF : " + e.getMessage());
        } catch (FileProcessingException | IOException | InterruptedException e ) {
            redirectAttributes.addFlashAttribute("error", "Erreur inattendue lors du traitement du fichier : " + e.getMessage());
        }
        // Rediriger vers la page admin/timetable
        return "redirect:/admin/timetable";
    }
    @GetMapping("/group/timetables")
    public String showGroupTimetables(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {
        Page<GroupTimetableEntity> timetables =
                groupTimetableService.getGroupTimetables(page, size);
        model.addAttribute("GroupTimetableList", timetables);
        return "/admin/see-all-group-timetable";
    }

    @GetMapping("/group/preview/{id}")
    public ResponseEntity<byte[]> getPreview(Authentication authentication, @PathVariable Long id) throws IOException {

        byte[] image = groupTimetableService
                .getGroupTimetablePreview(id);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(image);
    }
}
