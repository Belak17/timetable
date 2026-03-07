package com.belak.timetable.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFoundException(ResourceNotFoundException ex,Model model)
    {
        model.addAttribute("error", ex.getMessage());
        return "login/login";
    }
    @ExceptionHandler({FileEmptyException.class, InvalidFileFormatException.class})
    public String handleFileErrors(RuntimeException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/admin/timetable"; // page d’upload
    }

    @ExceptionHandler(FileProcessingException.class)
    public String handleProcessingError(FileProcessingException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "Erreur lors du traitement du fichier : " + ex.getMessage());
        return "redirect:/admin/timetable";
    }
    @ExceptionHandler(TimetableNotAttribuateException.class)
    public String handleProcessingError(TimetableNotAttribuateException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "Erreur lors du traitement du fichier : " + ex.getMessage());
        return "redirect:/admin/timetable";
    }
    @ExceptionHandler(EmptyExcelFileException.class)
    public String handleProcessingError(EmptyExcelFileException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "Erreur lors du traitement du fichier : " + ex.getMessage());
        return "redirect:/admin/timetable";
    }
    @ExceptionHandler(InvalidExcelFormatException.class)
    public String handleProcessingError(InvalidExcelFormatException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "Erreur lors du traitement du fichier : " + ex.getMessage());
        return "redirect:/admin/timetable";
    }
    @ExceptionHandler(LibreOfficeConversionException.class)
    public String handleProcessingError(LibreOfficeConversionException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "Erreur lors du traitement du fichier : " + ex.getMessage());
        return "redirect:/admin/timetable";
    }
}