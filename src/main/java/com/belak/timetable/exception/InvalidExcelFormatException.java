package com.belak.timetable.exception;

// mauvais format Excel
public class InvalidExcelFormatException extends RuntimeException {
    public InvalidExcelFormatException(String message) { super(message); }
}
