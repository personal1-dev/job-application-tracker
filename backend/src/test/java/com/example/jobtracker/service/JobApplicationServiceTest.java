package com.example.jobtracker.service;

import com.example.jobtracker.dto.JobApplicationRequest;
import com.example.jobtracker.entity.ApplicationStatus;
import com.example.jobtracker.entity.User;
import com.example.jobtracker.repository.JobApplicationRepository;
import com.example.jobtracker.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class JobApplicationServiceTest {

    @Test
    void createApplicationShouldReturnSavedApplication() {
        JobApplicationRepository jobRepository = Mockito.mock(JobApplicationRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        JobApplicationService service = new JobApplicationService(jobRepository, userRepository);

        User user = User.builder().id(1L).name("Test User").email("test@email.com").password("pass").build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(jobRepository.save(any())).thenAnswer(invocation -> {
            var job = (com.example.jobtracker.entity.JobApplication) invocation.getArgument(0);
            job.setId(100L);
            return job;
        });

        var request = new JobApplicationRequest(
                "Google", "Java Developer", "Boston", ApplicationStatus.APPLIED,
                LocalDate.now(), null, "https://example.com", "Applied online"
        );

        var response = service.create(1L, request);

        assertEquals(100L, response.id());
        assertEquals("Google", response.companyName());
        assertEquals(ApplicationStatus.APPLIED, response.status());
    }
}
