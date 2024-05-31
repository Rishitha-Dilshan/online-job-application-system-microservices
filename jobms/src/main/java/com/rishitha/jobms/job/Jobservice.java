package com.rishitha.jobms.job;

import com.rishitha.jobms.job.JobImpl.JobServiceImpl;
import com.rishitha.jobms.job.dto.JobDTO;
import com.rishitha.jobms.job.external.Company;
import com.rishitha.jobms.job.external.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class Jobservice implements JobServiceImpl {

    public Job getJOBById;
    //private List<Job> jobs = new ArrayList<>();
    JobRepository jobRepository;

    @Autowired
    RestTemplate restTemplate;

    public Jobservice(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }



    @Override
    public List<JobDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();
        List<JobDTO> jobDTOS = new ArrayList<>();
        return jobs.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // get Company details of particular job
    private JobDTO convertToDto(Job job){
        //RestTemplate restTemplate = new RestTemplate();

        //jobWithCompanyDTO.setJob(job);
        Company company = restTemplate.getForObject("http://COMPANY-SERVICE:8081/companies/" + job.getCompanyId(), Company.class);

        ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange("http://REVIEW-SERVICE:8081/reviews?companyId=" + job.getCompanyId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Review>>() {
                });

        List<Review> reviews = reviewResponse.getBody();

        JobDTO jobDTO = JobMapper.jobWithCompanyDTO(job,company,reviews);
        return jobDTO;
    }


    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public JobDTO getJobById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        return convertToDto(job);
    }

    @Override
    public boolean deleteJobById(Long id) {
        try{
            jobRepository.deleteById(id);
            return true;
        } catch (Exception e){
            return false;
        }

    }

    @Override
    public boolean upadateJobById(Long id, Job updatedJob) {
        Optional<Job> jobOptional = jobRepository.findById(id);

           if(jobOptional.isPresent()){
               Job job = jobOptional.get();
               job.setTitle(updatedJob.getTitle());
               job.setDescription(updatedJob.getDescription());
               job.setLocation(updatedJob.getLocation());
               job.setSalary(updatedJob.getSalary());
               job.setMaxSalary(updatedJob.getMaxSalary());
               job.setMinSalary(updatedJob.getMinSalary());
               jobRepository.save(job);
               return true;
           }

       return false;
    }


}
