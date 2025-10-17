package com.mdsohail.smartjobportal.controller;

import com.mdsohail.smartjobportal.entity.job;
import com.mdsohail.smartjobportal.entity.user;
import com.mdsohail.smartjobportal.service.jobService;
import com.mdsohail.smartjobportal.service.userService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin")
@Tag(name = "Admin Controller", description = "APIs for admin operations")
public class adminController {

    @Autowired
    private userService US;

    @Autowired
    private jobService JS;

    @Operation(
            summary = "Post a new job",
            description = "Allows an admin to post a new job",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Job posted successfully"),
                    @ApiResponse(responseCode = "204", description = "Job not posted")
            }
    )
    @PostMapping("post-job")
    public ResponseEntity<?> postJob(@RequestBody job j) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String adminName = authentication.getName();
        user adm = US.findUserByUserName(adminName);
        if (adm != null) {
            JS.postJob(j);
            return new ResponseEntity<>(j, HttpStatus.OK);
        }
        return new ResponseEntity<>("Not posted ", HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Get all users",
            description = "Returns list of all registered users"
    )
    @GetMapping("see-all-user")
    public List<user> showAlluser() {
        return US.getAll();
    }
}
