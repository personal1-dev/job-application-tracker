package com.example.jobtracker.controller;

import com.example.jobtracker.dto.*;
import com.example.jobtracker.entity.ApplicationStatus;
import com.example.jobtracker.service.JobApplicationService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {
    private final JobApplicationService service;

    public JobApplicationController(JobApplicationService service) {
        this.service = service;
    }

    @GetMapping
    public List<JobApplicationResponse> all(Authentication authentication) {
        return service.findAll(currentUserId(authentication));
    }

    @PostMapping
    public JobApplicationResponse create(Authentication authentication, @Valid @RequestBody JobApplicationRequest request) {
        return service.create(currentUserId(authentication), request);
    }

    @PutMapping("/{id}")
    public JobApplicationResponse update(Authentication authentication, @PathVariable Long id,
                                         @Valid @RequestBody JobApplicationRequest request) {
        return service.update(currentUserId(authentication), id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(Authentication authentication, @PathVariable Long id) {
        service.delete(currentUserId(authentication), id);
    }

    @GetMapping("/status/{status}")
    public List<JobApplicationResponse> byStatus(Authentication authentication, @PathVariable ApplicationStatus status) {
        return service.filterByStatus(currentUserId(authentication), status);
    }

    @GetMapping("/search")
    public List<JobApplicationResponse> search(Authentication authentication, @RequestParam String keyword) {
        return service.search(currentUserId(authentication), keyword);
    }

    @GetMapping("/dashboard")
    public DashboardResponse dashboard(Authentication authentication) {
        return service.dashboard(currentUserId(authentication));
    }

    private Long currentUserId(Authentication authentication) {
        return (Long) authentication.getPrincipal();
    }
}
