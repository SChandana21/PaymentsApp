package com.example.PaymentApp.Repositories;

import com.example.PaymentApp.Entity.Transactions;

import com.example.PaymentApp.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;

@Repository
public class UserQueryGetter {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserRepo userRepo;
    public List<Transactions> QueryGetter(String transactionType) {
        Query query = new Query();
        //transaction Criteria
        Criteria transactionCriteria = Criteria.where("transactionType").is(transactionType);
        query.addCriteria(transactionCriteria);
        List <Transactions> transactions = mongoTemplate.find(query, Transactions.class);
        return transactions;
    }

    public List<Transactions> FindByDate(int days) {
        Query query = new Query();
        Criteria dateCriteria = Criteria.where("datetimeattransaction").in(LocalDateTime.now().minusDays(days));
        query.addCriteria(dateCriteria);
        List<Transactions> transactions = mongoTemplate.find(query, Transactions.class);
        return transactions;
    }

    public List<Transactions> Findbyamount(float amount) {
        Query amountquery = new Query();
        String credentials = SecurityContextHolder.getContext().getAuthentication().getName();
        User byuserName = userRepo.findByuserName(credentials);
        String userEmail = byuserName.getUserEmail();
        Criteria userCriteria = Criteria.where("senderEmail").is(userEmail);
        Criteria AmountCriteria = Criteria.where("amountsent").gte(amount);
        amountquery.addCriteria(new Criteria().andOperator(userCriteria, AmountCriteria));
        List<Transactions> transactions = mongoTemplate.find(amountquery, Transactions.class);
        return transactions;
    }


}
