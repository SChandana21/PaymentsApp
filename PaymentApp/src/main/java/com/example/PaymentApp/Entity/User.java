package com.example.PaymentApp.Entity;

import com.fasterxml.jackson.annotation.JsonTypeId;
import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Document(collection = "users")
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class User {

    @Id
    private ObjectId ID;
    @Indexed(unique = true)
    @NonNull
    private String userName;

    @NonNull
    private String password;

    @Indexed(unique = true)
    private String userEmail;

    private Date currentDate;

    @DBRef
    private List<Wallet> wallet = new ArrayList<>();

    @DBRef
    private List<Transactions> Transactions = new ArrayList<>();

}
