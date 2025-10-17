package com.mdsohail.smartjobportal.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "jobs")
@Data
public class job {
    @Id
    private ObjectId id;


    private String title;
    private String description;
    private String location;
    private String skills;
    private String companyName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date lastDate;

}
