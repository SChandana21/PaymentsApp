package com.example.PaymentApp.Repositories;

import com.example.PaymentApp.Entity.Wallet;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WalletRepo extends MongoRepository<Wallet, ObjectId> {

}
