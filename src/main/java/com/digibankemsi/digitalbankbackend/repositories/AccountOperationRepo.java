package com.digibankemsi.digitalbankbackend.repositories;

import com.digibankemsi.digitalbankbackend.entities.AccountOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepo extends JpaRepository<AccountOperation,String> {

    public List<AccountOperation> findByBankAccountId(String id);
    public Page<AccountOperation> findByBankAccountId(String id, Pageable pageable);
}
