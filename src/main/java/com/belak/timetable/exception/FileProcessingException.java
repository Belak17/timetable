package com.belak.timetable.exception;

// erreur traitement Excel
public class FileProcessingException extends RuntimeException {
    public FileProcessingException(String message) {
        super(message);
    }
}
