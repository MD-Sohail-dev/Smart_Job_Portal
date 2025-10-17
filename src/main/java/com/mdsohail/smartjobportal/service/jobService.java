package com.mdsohail.smartjobportal.service;

import com.mdsohail.smartjobportal.entity.job;
import com.mdsohail.smartjobportal.repository.jobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class jobService {
    @Autowired
    private jobRepository JR;

    public void postJob(job j){
        JR.save(j);
    }
    public job findJobByTitle(String title){
        return JR.findByTitle(title);
    }
}
