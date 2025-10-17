package com.mdsohail.smartjobportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class loginDto {
    @NonNull
    private String userName;
    @NonNull
    private String password;
}
