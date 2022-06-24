package com.digibankemsi.digitalbankbackend.services;

import com.digibankemsi.digitalbankbackend.dtos.*;
import com.digibankemsi.digitalbankbackend.entities.BankAccount;
import com.digibankemsi.digitalbankbackend.entities.CurrentAccount;
import com.digibankemsi.digitalbankbackend.entities.Customer;
import com.digibankemsi.digitalbankbackend.entities.SavingAccount;
import com.digibankemsi.digitalbankbackend.exceptions.BalanceNotSufficentException;
import com.digibankemsi.digitalbankbackend.exceptions.BankAccountNotFoundException;
import com.digibankemsi.digitalbankbackend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {

    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    currentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, String customerId) throws CustomerNotFoundException;
    savingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, String customerId) throws CustomerNotFoundException;

    List<CustomerDTO> listCustmers();
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId, double amount,String motif ) throws BankAccountNotFoundException, BalanceNotSufficentException;
    void credit(String accountId, double amount,String motif ) throws BankAccountNotFoundException;
    void transfer(String senderAccountId,String recieverAccountId,double amount,String description) throws BankAccountNotFoundException, BalanceNotSufficentException;
    List<BankAccountDTO> BankAccountList();


    CustomerDTO getCustomerDTO(String id) throws CustomerNotFoundException;

    void deleteCustomer(String custId);

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    List<AccountOperationDTO> operationsHistory(String id);

    AccountHistoryDTO getBankAccountHistory(String id, int page, int size) throws BankAccountNotFoundException;

    List<CustomerDTO> searchCustomer(String keyword);
}
