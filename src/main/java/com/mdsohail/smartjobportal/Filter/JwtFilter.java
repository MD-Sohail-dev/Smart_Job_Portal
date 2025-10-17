package com.mdsohail.smartjobportal.Filter;


import com.mdsohail.smartjobportal.Utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component

public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;


    @Autowired
    private JwtUtils jwtUtil;

    // This method is executed for each request, before it reaches the controller
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Get the "Authorization" header from request (where token is usually sent)
        String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // Check if header is present and starts with "Bearer " (standard format for JWT tokens)
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Extract the token (removing "Bearer " part)
            jwt = authorizationHeader.substring(7);
            // Get username from token
            username = jwtUtil.extractUsername(jwt);
        }

        // If username is successfully extracted
        if (username != null) {
            // Load user details from database
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Check if token is valid
            if (jwtUtil.validateToken(jwt)) {
                // Create an authentication object with user details and roles
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // Attach request details (like IP address, session) to authentication object
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Store authentication info in SecurityContext (so Spring Security knows user is logged in)
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        // Continue the filter chain (let request move forward)
        chain.doFilter(request, response);
    }
}
