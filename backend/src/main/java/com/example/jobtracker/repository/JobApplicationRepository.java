package com.example.jobtracker.repository;

import com.example.jobtracker.entity.ApplicationStatus;
import com.example.jobtracker.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByUserIdOrderByAppliedDateDesc(Long userId);
    List<JobApplication> findByUserIdAndStatusOrderByAppliedDateDesc(Long userId, ApplicationStatus status);
    List<JobApplication> findByUserIdAndCompanyNameContainingIgnoreCaseOrUserIdAndJobTitleContainingIgnoreCase(
            Long userId1, String companyKeyword, Long userId2, String titleKeyword);
}
