package com.rishitha.jobms.job;

import com.rishitha.jobms.job.JobImpl.JobServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private JobServiceImpl JobServiceImpl;

    public JobController(JobServiceImpl JobServiceImpl) {
        this.JobServiceImpl = JobServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<Job>> findall(){
        return ResponseEntity.ok(JobServiceImpl.findAll()) ;
    }

    @PostMapping
    public ResponseEntity<String> createjobs(@RequestBody Job job){
        JobServiceImpl.createJob(job);
        return new ResponseEntity<>("Job is successefully created",HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id){


        Job job = JobServiceImpl.getJobById(id);
        if(job!=null)
            return new ResponseEntity<>(job,HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJobById(@PathVariable Long id){
        boolean deleted = JobServiceImpl.deleteJobById(id);
        if(deleted)
            return new ResponseEntity<>("Job deleted Successfully",HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    // @RequestMapping(value ="/jobs/{id}", method = RequestMethod.PUT )
    public ResponseEntity<String> updateJobById(@PathVariable Long id,
                                                @RequestBody Job updatedJob ){
        boolean updated = JobServiceImpl.upadateJobById(id,updatedJob);
        if(updated)
            return new ResponseEntity<>("Job Updated Successfully",HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
