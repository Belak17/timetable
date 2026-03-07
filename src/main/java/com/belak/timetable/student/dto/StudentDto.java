package com.belak.timetable.student.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StudentDto {
    @NotBlank
    private String userId ;
    @NotBlank
    private String firstName ;
    @NotBlank
    private String lastName ;
    @Email
    private String email ;
}
