package com.rishitha.jobms.job.JobImpl;


import com.rishitha.jobms.job.Job;
import com.rishitha.jobms.job.dto.JobWithCompanyDTO;

import java.util.List;


public interface JobServiceImpl {
    List<JobWithCompanyDTO> findAll();

    void createJob(Job job);

    Job getJobById(Long id);

    boolean deleteJobById(Long id);

    boolean upadateJobById(Long id, Job updatedJob);
}
