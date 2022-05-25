package com.digibankemsi.digitalbankbackend.repositories;

import com.digibankemsi.digitalbankbackend.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepo extends JpaRepository<BankAccount,String> {
}
