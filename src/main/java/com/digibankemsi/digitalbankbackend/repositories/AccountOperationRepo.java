package com.digibankemsi.digitalbankbackend.repositories;

import com.digibankemsi.digitalbankbackend.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepo extends JpaRepository<AccountOperation,String> {

}
