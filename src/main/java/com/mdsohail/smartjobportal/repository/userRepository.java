package com.mdsohail.smartjobportal.repository;

import com.mdsohail.smartjobportal.entity.user;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface userRepository extends MongoRepository<user, ObjectId> {
    user findByUserName(String userName);
    void deleteByUserName(String usernName);
}
