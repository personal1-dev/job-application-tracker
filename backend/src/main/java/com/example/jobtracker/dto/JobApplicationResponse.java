package com.example.jobtracker.dto;

import com.example.jobtracker.entity.ApplicationStatus;
import java.time.LocalDate;

public record JobApplicationResponse(
        Long id,
        String companyName,
        String jobTitle,
        String location,
        ApplicationStatus status,
        LocalDate appliedDate,
        LocalDate followUpDate,
        String jobLink,
        String notes
) {}
