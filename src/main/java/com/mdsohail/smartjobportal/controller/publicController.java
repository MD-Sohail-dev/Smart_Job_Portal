package com.mdsohail.smartjobportal.controller;

import com.mdsohail.smartjobportal.Utils.JwtUtils;
import com.mdsohail.smartjobportal.dto.loginDto;
import com.mdsohail.smartjobportal.dto.signupDto;
import com.mdsohail.smartjobportal.entity.user;
import com.mdsohail.smartjobportal.service.userDetailsServiceImpl;
import com.mdsohail.smartjobportal.service.userService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/public")
@Tag(name = "Public Controller", description = "APIs for signup and login")
public class publicController {

    @Autowired
    private userService US;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private userDetailsServiceImpl userDetailsServImp;

    @Autowired
    private JwtUtils jwtUtils;

    @Operation(summary = "Signup a new user", description = "Registers a new user account")
    @PostMapping("/signup")
    public ResponseEntity<?> signupAccount(@RequestBody signupDto newUser) {
        try {
            user u = new user();
            u.setUserName(newUser.getUserName());
            u.setPassword(newUser.getPassword());
            u.setEmail(newUser.getEmail());
            US.addNewUser(u);
            return new ResponseEntity<>(u, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Not register due to " + e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Login user", description = "Authenticates user and returns JWT token")
    @PostMapping("/login")
    public ResponseEntity<?> loginAccount(@RequestBody loginDto loginUser) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUser.getUserName(), loginUser.getPassword())
            );
            UserDetails userDetails = userDetailsServImp.loadUserByUsername(loginUser.getUserName());
            String jwtToken = jwtUtils.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwtToken, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred while createAuthenticationToken ", e);
            return new ResponseEntity<>("Incorrect userName and Password", HttpStatus.BAD_REQUEST);
        }
    }
}
