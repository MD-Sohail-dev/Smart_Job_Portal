package com.mdsohail.smartjobportal.controller;

import com.mdsohail.smartjobportal.Utils.JwtUtils;
import com.mdsohail.smartjobportal.entity.user;
import com.mdsohail.smartjobportal.repository.userRepository;
import com.mdsohail.smartjobportal.service.userDetailsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/auth/google")
@Slf4j
@Tag(name = "Google Authentication Controller", description = "Google OAuth2 authentication APIs")
public class GoogleAuthController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private userDetailsServiceImpl userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private userRepository userRepository;

    @Autowired
    private JwtUtils jwtUtil;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

    @Operation(
            summary = "Google OAuth2 Callback",
            description = "Exchanges authorization code for tokens, creates a user if not found, and returns a JWT token",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Authentication successful, JWT token returned"),
                    @ApiResponse(responseCode = "401", description = "Invalid or expired authorization code"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/callback")
    public ResponseEntity<?> handleGoogleCallBack(@RequestParam String code) {
        try {
            String tokenEndpoint = "https://oauth2.googleapis.com/token";

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("code", code);
            params.add("client_id", clientId);
            params.add("client_secret", clientSecret);
            params.add("redirect_uri", redirectUri);
            params.add("grant_type", "authorization_code");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

            // Exchange code for tokens
            ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(tokenEndpoint, request, Map.class);

            String idToken = (String) tokenResponse.getBody().get("id_token");

            String userInfoUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken;
            ResponseEntity<Map> userInfoResponse = restTemplate.getForEntity(userInfoUrl, Map.class);

            if (userInfoResponse.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> userInfo = userInfoResponse.getBody();
                String email = (String) userInfo.get("email");

                try {
                    userDetailsService.loadUserByUsername(email);
                } catch (Exception e) {
                    // Create new user if not found
                    user u = new user();
                    u.setEmail(email);
                    u.setUserName(email);
                    u.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
                    u.setRole("USER");
                    userRepository.save(u);
                }

                // Generate JWT token
                String jwtToken = jwtUtil.generateToken(email);
                return ResponseEntity.ok(Collections.singletonMap("token", jwtToken));
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            log.error("Exception occurred while handleGoogleCallback ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
