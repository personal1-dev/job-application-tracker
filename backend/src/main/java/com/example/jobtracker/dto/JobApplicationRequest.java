package com.example.jobtracker.dto;

import com.example.jobtracker.entity.ApplicationStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record JobApplicationRequest(
        @NotBlank String companyName,
        @NotBlank String jobTitle,
        String location,
        @NotNull ApplicationStatus status,
        LocalDate appliedDate,
        LocalDate followUpDate,
        String jobLink,
        String notes
) {}
