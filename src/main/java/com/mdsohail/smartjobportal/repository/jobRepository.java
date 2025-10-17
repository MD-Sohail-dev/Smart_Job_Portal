package com.mdsohail.smartjobportal.repository;

import com.mdsohail.smartjobportal.entity.job;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface jobRepository extends MongoRepository<job, ObjectId> {
    job findByTitle(String title);
}
