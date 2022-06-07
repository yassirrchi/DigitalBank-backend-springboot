package com.digibankemsi.digitalbankbackend.dtos;

import com.digibankemsi.digitalbankbackend.entities.BankAccount;
import com.digibankemsi.digitalbankbackend.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Data
public class AccountOperationDTO {


    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType type;
    private String Motif;


}
