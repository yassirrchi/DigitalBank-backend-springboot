package com.digibankemsi.digitalbankbackend.entities;

import com.digibankemsi.digitalbankbackend.enums.AccountStatus;
import com.digibankemsi.digitalbankbackend.entities.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
@Entity
@Inheritance(strategy =InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TYPE",length=4)
@Data @NoArgsConstructor @AllArgsConstructor
public abstract class BankAccount {
    @Id @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    @ManyToOne
    private Customer customer;
    @OneToMany(mappedBy = "bankAccount")
    private List<AccountOperation> accountOperations;

}
