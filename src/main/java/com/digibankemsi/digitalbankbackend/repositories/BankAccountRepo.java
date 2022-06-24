package com.digibankemsi.digitalbankbackend.repositories;

import com.digibankemsi.digitalbankbackend.dtos.BankAccountDTO;
import com.digibankemsi.digitalbankbackend.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankAccountRepo extends JpaRepository<BankAccount,String> {
    List<BankAccount> findBankAccountsByCustomerId(String id);
}
