package com.mdsohail.smartjobportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class signupDto {
    @NonNull
    private String userName;
    @NonNull
    private String password;
    @NonNull
    private String email;
}
