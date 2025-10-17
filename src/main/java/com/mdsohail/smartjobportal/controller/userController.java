package com.mdsohail.smartjobportal.controller;

import com.mdsohail.smartjobportal.dto.updateUserDto;
import com.mdsohail.smartjobportal.entity.job;
import com.mdsohail.smartjobportal.entity.user;
import com.mdsohail.smartjobportal.service.EmailService;
import com.mdsohail.smartjobportal.service.userService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
@Tag(name = "User Controller", description = "APIs for user operations")
public class userController {

    @Autowired
    private userService US;

    @Autowired
    private EmailService emailService;

    @Operation(summary = "Delete user", description = "Deletes the currently logged-in user")
    @DeleteMapping("/delete-user")
    public ResponseEntity<?> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Boolean isdeleted = US.deleteUserByUserName(userName);
        if (isdeleted)
            return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
        else
            return new ResponseEntity<>("Not Deleted", HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Update user", description = "Updates the profile of the logged-in user")
    @PutMapping("update-user")
    public ResponseEntity<?> updateUser(@RequestBody updateUserDto newData) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        user oldData = US.findUserByUserName(userName);
        if (oldData != null) {
            oldData.setUserName(newData.getUserName() != null && !newData.getUserName().equals("") ? newData.getUserName() : oldData.getUserName());
            oldData.setPassword(newData.getPassword() != null && !newData.getPassword().equals("") ? newData.getPassword() : oldData.getPassword());
            oldData.setEmail(newData.getEmail() != null && !newData.getEmail().equals("") ? newData.getEmail() : oldData.getEmail());
            US.addNewUser(oldData);
            return new ResponseEntity<>(oldData, HttpStatus.OK);
        }
        return new ResponseEntity<>("Not Updated", HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "See all jobs", description = "Returns the list of available jobs")
    @GetMapping("/see-job")
    public ResponseEntity<?> seeJob() {
        List<job> j = US.viewJob();
        return new ResponseEntity<>(j, HttpStatus.OK);
    }

    @Operation(summary = "Apply for a job by title", description = "Apply for a job using its title")
    @GetMapping("apply-job-byTitle")
    public ResponseEntity<?> applyJobByTitle(@RequestParam String title) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        user u = US.findUserByUserName(userName);

        if (u != null) {
            String jobTitleCompanyName = US.appliedJobByTitle(title);
            List<String> appliedJOB = u.getAppliedJob();
            if (appliedJOB == null) {
                appliedJOB = new ArrayList<>();
            }

            if (!appliedJOB.contains(jobTitleCompanyName)) {
                appliedJOB.add(jobTitleCompanyName);
                u.setAppliedJob(appliedJOB);
                US.saveUser(u);

                // Send email
                String subject = "Job Application Successful - Smart Job Portal";
                String body = "Dear " + u.getUserName() + ",\n\n" +
                        "Congratulations! You have successfully applied for the job: " + jobTitleCompanyName + ".\n\n" +
                        "Our team will review your application and contact you if shortlisted.\n\n" +
                        "Best regards,\nSmart Job Portal";
                emailService.sendEmail(u.getEmail(), subject, body);

                return new ResponseEntity<>(u.getAppliedJob(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("You have already applied for this job.", HttpStatus.CONFLICT);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "See user profile", description = "View profile details of the logged-in user")
    @GetMapping("see-profile")
    public ResponseEntity<?> seeProfileDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        user u = US.findUserByUserName(name);
        return new ResponseEntity<>(u, HttpStatus.OK);
    }
}
