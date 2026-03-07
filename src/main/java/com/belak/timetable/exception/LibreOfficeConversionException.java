package com.belak.timetable.exception;

// erreur conversion PDF LibreOffice
public class LibreOfficeConversionException extends RuntimeException {
    public LibreOfficeConversionException(String message) { super(message); }
}