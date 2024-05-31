package com.rishitha.jobms.job.JobImpl;


import com.rishitha.jobms.job.Job;
import com.rishitha.jobms.job.dto.JobDTO;

import java.util.List;


public interface JobServiceImpl {
    List<JobDTO> findAll();

    void createJob(Job job);

    JobDTO getJobById(Long id);

    boolean deleteJobById(Long id);

    boolean upadateJobById(Long id, Job updatedJob);
}
