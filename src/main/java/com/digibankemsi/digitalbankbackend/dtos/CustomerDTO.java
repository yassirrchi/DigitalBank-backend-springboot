package com.digibankemsi.digitalbankbackend.dtos;

import com.digibankemsi.digitalbankbackend.entities.BankAccount;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Data
public class CustomerDTO {

    private String id;
    private String name;
    private String email;

}
