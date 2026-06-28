package com.example.jobtracker.service;

import com.example.jobtracker.dto.*;
import com.example.jobtracker.entity.*;
import com.example.jobtracker.exception.ResourceNotFoundException;
import com.example.jobtracker.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class JobApplicationService {
    private final JobApplicationRepository jobRepository;
    private final UserRepository userRepository;

    public JobApplicationService(JobApplicationRepository jobRepository, UserRepository userRepository) {
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    public List<JobApplicationResponse> findAll(Long userId) {
        return jobRepository.findByUserIdOrderByAppliedDateDesc(userId).stream().map(this::toResponse).toList();
    }

    public JobApplicationResponse create(Long userId, JobApplicationRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        JobApplication job = JobApplication.builder()
                .companyName(request.companyName())
                .jobTitle(request.jobTitle())
                .location(request.location())
                .status(request.status())
                .appliedDate(request.appliedDate() == null ? LocalDate.now() : request.appliedDate())
                .followUpDate(request.followUpDate())
                .jobLink(request.jobLink())
                .notes(request.notes())
                .user(user)
                .build();
        return toResponse(jobRepository.save(job));
    }

    public JobApplicationResponse update(Long userId, Long id, JobApplicationRequest request) {
        JobApplication job = getOwnedJob(userId, id);
        job.setCompanyName(request.companyName());
        job.setJobTitle(request.jobTitle());
        job.setLocation(request.location());
        job.setStatus(request.status());
        job.setAppliedDate(request.appliedDate());
        job.setFollowUpDate(request.followUpDate());
        job.setJobLink(request.jobLink());
        job.setNotes(request.notes());
        return toResponse(jobRepository.save(job));
    }

    public void delete(Long userId, Long id) {
        jobRepository.delete(getOwnedJob(userId, id));
    }

    public List<JobApplicationResponse> filterByStatus(Long userId, ApplicationStatus status) {
        return jobRepository.findByUserIdAndStatusOrderByAppliedDateDesc(userId, status).stream().map(this::toResponse).toList();
    }

    public List<JobApplicationResponse> search(Long userId, String keyword) {
        return jobRepository.findByUserIdAndCompanyNameContainingIgnoreCaseOrUserIdAndJobTitleContainingIgnoreCase(
                userId, keyword, userId, keyword
        ).stream().map(this::toResponse).toList();
    }

    public DashboardResponse dashboard(Long userId) {
        List<JobApplication> jobs = jobRepository.findByUserIdOrderByAppliedDateDesc(userId);
        return new DashboardResponse(
                jobs.size(),
                jobs.stream().filter(j -> j.getStatus() == ApplicationStatus.APPLIED).count(),
                jobs.stream().filter(j -> j.getStatus() == ApplicationStatus.INTERVIEW).count(),
                jobs.stream().filter(j -> j.getStatus() == ApplicationStatus.OFFER).count(),
                jobs.stream().filter(j -> j.getStatus() == ApplicationStatus.REJECTED).count()
        );
    }

    private JobApplication getOwnedJob(Long userId, Long id) {
        JobApplication job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));
        if (!job.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Application not found");
        }
        return job;
    }

    private JobApplicationResponse toResponse(JobApplication job) {
        return new JobApplicationResponse(
                job.getId(), job.getCompanyName(), job.getJobTitle(), job.getLocation(), job.getStatus(),
                job.getAppliedDate(), job.getFollowUpDate(), job.getJobLink(), job.getNotes()
        );
    }
}
