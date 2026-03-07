package com.belak.timetable.professor.dto;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Builder
@Getter
@Setter
public class ProfessorDto {
    @NotBlank
    private String userId ;
    @NotBlank
    private String firstName ;
    @NotBlank
    private String lastName ;
    @Email
    private String email ;
}
