package com.rishitha.jobms.job;

import com.rishitha.jobms.job.JobImpl.JobServiceImpl;
import com.rishitha.jobms.job.dto.JobWithCompanyDTO;
import com.rishitha.jobms.job.external.Company;
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

    public Jobservice(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }



    @Override
    public List<JobWithCompanyDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();
        List<JobWithCompanyDTO> jobWithCompanyDTOS = new ArrayList<>();
        return jobs.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // get Company details of particular job
    private JobWithCompanyDTO convertToDto(Job job){
        RestTemplate restTemplate = new RestTemplate();
        JobWithCompanyDTO jobWithCompanyDTO = new JobWithCompanyDTO();
        jobWithCompanyDTO.setJob(job);
        Company company = restTemplate.getForObject("http://localhost:8081/companies/" + job.getCompanyId(), Company.class);
        jobWithCompanyDTO.setCompany(company);
        return  jobWithCompanyDTO;
    }


    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public Job getJobById(Long id) {
       return jobRepository.findById(id).orElse(null);
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
