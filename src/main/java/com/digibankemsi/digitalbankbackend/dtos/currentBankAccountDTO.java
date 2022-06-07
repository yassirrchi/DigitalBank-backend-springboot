package com.digibankemsi.digitalbankbackend.dtos;

import com.digibankemsi.digitalbankbackend.enums.AccountStatus;
import lombok.Data;

import java.util.Date;


@Data
public   class currentBankAccountDTO extends  BankAccountDTO {

    private String id;
    private double balance;
    private Date createdAt;

    private AccountStatus status;

    private CustomerDTO customer;
    private double overDraft;


}
