package com.digibankemsi.digitalbankbackend.dtos;

import com.digibankemsi.digitalbankbackend.entities.AccountOperation;
import com.digibankemsi.digitalbankbackend.entities.BankAccount;
import com.digibankemsi.digitalbankbackend.entities.Customer;
import com.digibankemsi.digitalbankbackend.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Data
public   class savingBankAccountDTO extends BankAccountDTO {

    private String id;
    private double balance;
    private Date createdAt;

    private AccountStatus status;

    private CustomerDTO customer;
    private double interestRate;


}
