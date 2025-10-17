package com.mdsohail.smartjobportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class updateUserDto {

    private String userName;
    private String password;
    private String email;

}
