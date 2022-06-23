package com.digibankemsi.digitalbankbackend.repositories;

import com.digibankemsi.digitalbankbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepo extends JpaRepository<Customer,String> {
    @Query("select c from Customer c where c.name like :kw  ")
    List<Customer> searchCustomer(@Param("kw") String keyword);

}
