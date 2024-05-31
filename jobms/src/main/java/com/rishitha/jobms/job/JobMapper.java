package com.rishitha.jobms.job;

import com.rishitha.jobms.job.dto.JobDTO;
import com.rishitha.jobms.job.external.Company;
import com.rishitha.jobms.job.external.Review;

import java.util.List;

public class JobMapper {
    public static JobDTO jobWithCompanyDTO(
            Job job,
            Company company,
            List<Review> reviews
    ){
        JobDTO jobDTO = new JobDTO();
        jobDTO.setId(job.getId());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setMaxSalary(job.getMaxSalary());
        jobDTO.setMinSalary(job.getMinSalary());
        jobDTO.setSalary(job.getSalary());
        jobDTO.setCompany(company);
        jobDTO.setReview(reviews);
        return jobDTO;
    }
}
