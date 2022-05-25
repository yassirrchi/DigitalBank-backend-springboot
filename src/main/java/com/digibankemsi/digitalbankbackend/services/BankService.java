package com.digibankemsi.digitalbankbackend.services;

import com.digibankemsi.digitalbankbackend.entities.BankAccount;
import com.digibankemsi.digitalbankbackend.entities.CurrentAccount;
import com.digibankemsi.digitalbankbackend.entities.SavingAccount;
import com.digibankemsi.digitalbankbackend.repositories.BankAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BankService {
    @Autowired
    private BankAccountRepo bankAccountRepo;
    public void consulter(){

        BankAccount bankAccount = bankAccountRepo.findById("4028828180fca8730180fca8780f0003").orElse(null);
        if (bankAccount != null) {
            System.out.println("===============bank acc======================");
            System.out.println(bankAccount.getId());
            System.out.println(bankAccount.getBalance());
            System.out.println(bankAccount.getCreatedAt());
            System.out.println(bankAccount.getCustomer());
            System.out.println(bankAccount.getCustomer().getName());
            if (bankAccount instanceof CurrentAccount) {
                System.out.println("over draft=>" + ((CurrentAccount) (CurrentAccount) bankAccount).getOverDraft());
            } else {
                System.out.println("Interest rate=>" + ((SavingAccount) bankAccount).getInterestRate());
            }
            bankAccount.getAccountOperations().forEach(op -> {
                System.out.println("=========op==========");
                System.out.println(op.getOperationDate());
                System.out.println(op.getBankAccount());
                System.out.println(op.getAmount());
                System.out.println(op.getType());
                System.out.println("=========================");
            });
        }
    }
}
