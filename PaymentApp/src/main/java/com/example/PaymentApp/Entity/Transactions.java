package com.example.PaymentApp.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "transactions")
public class Transactions {
    @Id
    private ObjectId transactionID;

    private LocalDateTime datetimeattransaction;

    private String recieverID;

    private String transactionType;

    private float amountsent;

}
