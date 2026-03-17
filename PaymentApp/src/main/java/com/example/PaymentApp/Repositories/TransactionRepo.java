package com.example.PaymentApp.Repositories;

import com.example.PaymentApp.Entity.Transactions;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepo extends MongoRepository<Transactions, ObjectId> {
    
}
