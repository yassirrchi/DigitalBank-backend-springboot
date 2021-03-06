package com.digibankemsi.digitalbankbackend.entities;

import com.digibankemsi.digitalbankbackend.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class AccountOperation {
    @Id @GeneratedValue(strategy =GenerationType.IDENTITY )

    private Long id;
    private Date operationDate;
    private double amount;
    @Enumerated(EnumType.STRING)
    private OperationType type;
    @ManyToOne(cascade=CascadeType.REMOVE)
    private  BankAccount bankAccount;
    private String Motif;


}
