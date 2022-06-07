package com.digibankemsi.digitalbankbackend.mappers;

import com.digibankemsi.digitalbankbackend.dtos.AccountOperationDTO;
import com.digibankemsi.digitalbankbackend.dtos.CustomerDTO;
import com.digibankemsi.digitalbankbackend.dtos.currentBankAccountDTO;
import com.digibankemsi.digitalbankbackend.dtos.savingBankAccountDTO;
import com.digibankemsi.digitalbankbackend.entities.AccountOperation;
import com.digibankemsi.digitalbankbackend.entities.CurrentAccount;
import com.digibankemsi.digitalbankbackend.entities.Customer;
import com.digibankemsi.digitalbankbackend.entities.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapper {
    public CustomerDTO fromCustomer(Customer customer ){
        CustomerDTO customerDTO=new CustomerDTO();
        //customerDTO.setId(customer.getId());
        //customerDTO.setName(customer.getName());
        //customerDTO.setEmail(customer.getEmail())
        BeanUtils.copyProperties(customer,customerDTO);

        return customerDTO;
    }
    public Customer  fromCustomerDTO(CustomerDTO customerDTO ){
        Customer  customer =new Customer();

        BeanUtils.copyProperties(customerDTO,customer);

        return customer;
    }



    public SavingAccount fromSavingAccDTO(savingBankAccountDTO savingBankAccountDTO){
        SavingAccount savingAccount=new SavingAccount();
        BeanUtils.copyProperties(savingBankAccountDTO,savingAccount);
        savingAccount.setCustomer(fromCustomerDTO(savingBankAccountDTO.getCustomer()));



        return savingAccount;
    }
    public savingBankAccountDTO fromSavingAcc(SavingAccount savingBankAccount){
        savingBankAccountDTO savingAccountDTO =new savingBankAccountDTO();
        BeanUtils.copyProperties(savingBankAccount,savingAccountDTO);
        savingAccountDTO.setCustomer(fromCustomer(savingBankAccount.getCustomer()));

         savingAccountDTO.setType(savingBankAccount.getClass().getSimpleName());
        return savingAccountDTO;
    }



    public CurrentAccount fromCurrentAccDTO(currentBankAccountDTO currentAccountDTO){
        CurrentAccount currentAccount=new CurrentAccount();
        BeanUtils.copyProperties(currentAccountDTO,currentAccount);
        currentAccount.setCustomer(fromCustomerDTO(currentAccountDTO.getCustomer()));




        return currentAccount;
    }
    public currentBankAccountDTO fromCurrentAcc(CurrentAccount currentAccount){
        currentBankAccountDTO  currentAccDTO =new currentBankAccountDTO();
        BeanUtils.copyProperties(currentAccount,currentAccDTO);
        currentAccDTO.setCustomer(fromCustomer(currentAccount.getCustomer()));
         currentAccDTO.setType(currentAccount.getClass().getSimpleName());


        return currentAccDTO;
    }
    public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation){
        AccountOperationDTO accountOperationDTO=new AccountOperationDTO();
        BeanUtils.copyProperties(accountOperation,accountOperationDTO);
        return accountOperationDTO;


    }

}
