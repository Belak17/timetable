package com.belak.timetable.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDto {
    @NotBlank
    private String userId ;
    @NotBlank
    private String firstName ;
    @NotBlank
    private String lastName ;
    @Email
    private String email ;
}
