package com.digibankemsi.digitalbankbackend.repositories;

import com.digibankemsi.digitalbankbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer,Long> {

}
