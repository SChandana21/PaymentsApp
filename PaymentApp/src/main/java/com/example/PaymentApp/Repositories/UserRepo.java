package com.example.PaymentApp.Repositories;

import com.example.PaymentApp.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository <User, ObjectId> {
    //Get by username..
    User findByuserName(String username);
}
