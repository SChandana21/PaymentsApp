package com.example.PaymentApp.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class TransactionResponseDTO {

    private float amounttoLookup;

    private int daystolookupto;

    private String transactionType;
}
