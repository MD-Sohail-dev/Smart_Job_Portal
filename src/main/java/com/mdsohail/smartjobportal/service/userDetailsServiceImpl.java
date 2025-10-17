package com.mdsohail.smartjobportal.service;

import com.mdsohail.smartjobportal.entity.user;
import com.mdsohail.smartjobportal.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class userDetailsServiceImpl implements UserDetailsService {



    @Autowired
    private userRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        user u = userRepo.findByUserName(username);
        if (u != null) {
            UserDetails UD = org.springframework.security.core.userdetails.User.builder()
                    .username(u.getUserName())
                    .password(u.getPassword())
                    .roles(u.getRole())
                    .build();
            return  UD;
        }

        throw new UsernameNotFoundException("Username not found in DB: " + username);
    }
}
