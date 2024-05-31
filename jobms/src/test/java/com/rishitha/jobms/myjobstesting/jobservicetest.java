package com.rishitha.jobms.myjobstesting;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.rishitha.jobms.job.Job;
import com.rishitha.jobms.job.JobRepository;
import com.rishitha.jobms.job.Jobservice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class jobservicetest {
    @Mock
    private JobRepository jobRepository;

    @Captor
    ArgumentCaptor<Job> jobArgumentCaptor;

    @InjectMocks
    private Jobservice undertest;

    @BeforeEach
    void setUp() {
        undertest = new Jobservice(jobRepository);
    }

    @Test
    void canGetAllJobs() {
        // when
        undertest.findAll();
        // then
        verify(jobRepository).findAll();
    }

    @Test
    void canCreateCustomer(){
        Job job = new Job(
                1L,
                "Test desctiption",
                "30000",
                "40000",
                "35000",
                "50000",
                "Australia"
        );
        undertest.createJob(job);
        verify(jobRepository).save(jobArgumentCaptor.capture());
        Job jobCaptured = jobArgumentCaptor.getValue();
        assertThat(jobCaptured.getTitle()).isEqualTo(job.getTitle());
        assertThat(jobCaptured.getDescription()).isEqualTo(job.getDescription());
        assertThat(jobCaptured.getLocation()).isEqualTo(job.getLocation());
        assertThat(jobCaptured.getMaxSalary()).isEqualTo(job.getMaxSalary());
        assertThat(jobCaptured.getMinSalary()).isEqualTo(job.getMinSalary());
    }

    @Test
    void canUpdateCustomer(){

        Long id = 5L;
        Job job = new Job(
                id,
                "Test desctiption",
                "30000",
                "40000",
                "35000",
                "50000",
                "Australia"
        );

        Job newjob = new Job(
                id,
                "Test desctiption2",
                "20000",
                "10000",
                "65000",
                "10000",
                "Sri Lanka"
        );


        when(jobRepository.findById(id)).thenReturn(Optional.of(job));
        boolean result = undertest.upadateJobById(5L, newjob);
        assertTrue(result);
        verify(jobRepository).save(jobArgumentCaptor.capture());
        Job jobCaptured = jobArgumentCaptor.getValue();

        assertThat(jobCaptured.getTitle()).isEqualTo(newjob.getTitle());
        assertThat(jobCaptured.getDescription()).isEqualTo(newjob.getDescription());
        assertThat(jobCaptured.getLocation()).isEqualTo(newjob.getLocation());
        assertThat(jobCaptured.getMaxSalary()).isEqualTo(newjob.getMaxSalary());
        assertThat(jobCaptured.getMinSalary()).isEqualTo(newjob.getMinSalary());
    }

    @Test
    void shouldAssertFalseGivenIdDoesNotExistWhileUpdateJob() {
        //given
        long id = 5L;

        Job job = new Job(
                id,
                "Test desctiption",
                "30000",
                "40000",
                "35000",
                "50000",
                "Australia"
        );
        Job newjob = new Job(
                id,
                "Test desctiption2",
                "20000",
                "10000",
                "65000",
                "10000",
                "Sri Lanka"
        );

        when(jobRepository.findById(id))
                .thenReturn(Optional.empty());
        boolean result = undertest.upadateJobById(5L, newjob);

        assertFalse(result);
        verify(jobRepository, never()).save(any());

    }


    @Test
    void shouldDeleteCustomer() {
        //given
        long id = 5L;
//        when(jobRepository.existsById(id))
//                .thenReturn(true);
        //when
        undertest.deleteJobById(id);
        //then
        verify(jobRepository).deleteById(id);

    }

    @Test
    void  shouldGetJobById(){
        long id = 5L;
        undertest.getJobById(id);
        verify(jobRepository).findById(id);
    }


}
