package com.mdsohail.smartjobportal.service;

import com.mdsohail.smartjobportal.entity.job;
import com.mdsohail.smartjobportal.entity.user;
import com.mdsohail.smartjobportal.repository.jobRepository;
import com.mdsohail.smartjobportal.repository.userRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class userService {

    @Autowired
    private jobRepository JR;

    @Autowired
    private jobService JS;

    @Autowired
    private userRepository UR;



    private static final PasswordEncoder passEnc = new BCryptPasswordEncoder();
    public void addNewUser(user u){
        u.setPassword(passEnc.encode(u.getPassword()));
        u.setRole("USER");
        UR.save(u);
    }
 public void saveUser(user u){
        UR.save(u);
 }

public List<job> viewJob(){
     return JR.findAll();
}

    public Boolean deleteUserByUserName(String name){
        Boolean delete = false;
        user u = UR.findByUserName(name);
        if(u!=null){
            UR.deleteByUserName(name);
            delete=true;
        }
        return  delete;
    }

    public user findUserByUserName(String userName){
       return UR.findByUserName(userName);
    }



    public String appliedJobByTitle(String title){
        try{
            job j = JS.findJobByTitle(title);
            return j.getTitle()+" at "+j.getCompanyName();
        }catch (Exception e){
          log.error("This job is not available "+e);
          return "null";
        }

    }

    public List<user> getAll(){
        return UR.findAll();
    }
}
