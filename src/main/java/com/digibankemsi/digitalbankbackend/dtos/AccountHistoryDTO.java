package com.digibankemsi.digitalbankbackend.dtos;

import lombok.Data;

import java.util.List;
@Data
public class AccountHistoryDTO {
    private String accountId;
    private double balance;
    private  int currentPage;
    private int TotalPages;
    private  int pageSize;
    private String type;
    private List<AccountOperationDTO> accountOperationDTOList;
}
