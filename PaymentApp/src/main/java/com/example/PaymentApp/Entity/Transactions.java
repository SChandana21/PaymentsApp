package com.example.PaymentApp.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "transactions")
public class Transactions {
    @Id
    private ObjectId TransactionID;

    private Date DateofTransaction;

    private String TransactorID;

}
