package com.digibankemsi.digitalbankbackend;

import com.digibankemsi.digitalbankbackend.dtos.BankAccountDTO;
import com.digibankemsi.digitalbankbackend.dtos.CustomerDTO;
import com.digibankemsi.digitalbankbackend.dtos.currentBankAccountDTO;
import com.digibankemsi.digitalbankbackend.dtos.savingBankAccountDTO;
import com.digibankemsi.digitalbankbackend.entities.*;
import com.digibankemsi.digitalbankbackend.enums.AccountStatus;
import com.digibankemsi.digitalbankbackend.enums.OperationType;
import com.digibankemsi.digitalbankbackend.exceptions.BalanceNotSufficentException;
import com.digibankemsi.digitalbankbackend.exceptions.BankAccountNotFoundException;
import com.digibankemsi.digitalbankbackend.exceptions.CustomerNotFoundException;
import com.digibankemsi.digitalbankbackend.repositories.AccountOperationRepo;
import com.digibankemsi.digitalbankbackend.repositories.BankAccountRepo;
import com.digibankemsi.digitalbankbackend.repositories.CustomerRepo;
import com.digibankemsi.digitalbankbackend.services.BankAccountService;
import com.digibankemsi.digitalbankbackend.services.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class DigitalBankBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalBankBackendApplication.class, args);
    }




    CommandLineRunner start(CustomerRepo customerRepo, BankAccountRepo bankAccountRepo, AccountOperationRepo accountOperationRepo) {
       return args -> {
           Stream.of("Hassan", "Yassine", "Aicha").forEach(
                   name -> {
                       Customer customer = new Customer();
                       customer.setName(name);
                       customer.setEmail(name + "@mail.xt");
                       customerRepo.save(customer);
                   }
           );

           customerRepo.findAll().forEach(cust -> {
               CurrentAccount currentAccount = new CurrentAccount();
               currentAccount.setBalance(Math.random() * 90000);
               currentAccount.setCreatedAt(new Date());
               currentAccount.setStatus(AccountStatus.CREATED);
               currentAccount.setCustomer(cust);
               currentAccount.setOverDraft(80000);
               bankAccountRepo.save(currentAccount);
               SavingAccount savingAccount = new SavingAccount();
               savingAccount.setBalance(Math.random() * 90000);
               savingAccount.setCreatedAt(new Date());
               savingAccount.setStatus(AccountStatus.CREATED);
               savingAccount.setCustomer(cust);
               savingAccount.setInterestRate(5.5);
               bankAccountRepo.save(savingAccount);
               bankAccountRepo.findAll().forEach(account -> {

                   for (int i = 0; i < 10; i++) {
                       AccountOperation accountOperation = new AccountOperation();
                       accountOperation.setOperationDate(new Date());
                       accountOperation.setAmount(Math.random() * 12000);
                       accountOperation.setType(Math.random() > 0.5 ? OperationType.DEBIT : OperationType.CREDIT);
                       accountOperation.setBankAccount(account);
                       accountOperationRepo.save(accountOperation);

                   }


               });


           });


       };
   }
   @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService) {
        return args->{

            Stream.of("Yassir","Hasbulla","Imane").forEach(name->{
                CustomerDTO customer=new CustomerDTO();
                customer.setName(name);
                customer.setEmail(name+"@mail.ext");

                bankAccountService.saveCustomer(customer);

                bankAccountService.listCustmers().forEach(custmer->{
                    try {
                        bankAccountService.saveCurrentBankAccount(Math.random()*90000,9000, custmer.getId());
                        bankAccountService.saveSavingBankAccount(Math.random()*90000,5.5, custmer.getId());


                    } catch (CustomerNotFoundException e) {
                       throw new RuntimeException(e);
                    }
                });
                List<BankAccountDTO> bankAccountList=bankAccountService.BankAccountList();
                for(BankAccountDTO bankAccount:bankAccountList){
                    for(int i=0;i<10;i++){
                        String accId;
                        if( bankAccount instanceof savingBankAccountDTO)
                        {
                            accId=((savingBankAccountDTO) bankAccount).getId();
                        }
                        else{
                            accId=((currentBankAccountDTO) bankAccount).getId();

                        }
                        try {
                            bankAccountService.credit(accId, 10000+Math.random()*120000,"Credit");
                        } catch (BankAccountNotFoundException e) {
                            e.printStackTrace();
                        }
                        try {
                            bankAccountService.debit(accId, 1000+Math.random()*9000,"Debit");
                        } catch (BankAccountNotFoundException e) {
                            e.printStackTrace();
                        } catch (BalanceNotSufficentException e) {
                            e.printStackTrace();
                        }

                    }
                }

            });


        };

    }
    @Bean
    CommandLineRunner bo(BankService bankService) {
        return args->{


            bankService.consulter();

        };

    }
}

