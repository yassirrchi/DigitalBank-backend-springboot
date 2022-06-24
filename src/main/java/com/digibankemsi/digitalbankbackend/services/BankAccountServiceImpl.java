package com.digibankemsi.digitalbankbackend.services;

import com.digibankemsi.digitalbankbackend.dtos.*;
import com.digibankemsi.digitalbankbackend.entities.*;
import com.digibankemsi.digitalbankbackend.enums.OperationType;
import com.digibankemsi.digitalbankbackend.exceptions.BalanceNotSufficentException;
import com.digibankemsi.digitalbankbackend.exceptions.BankAccountNotFoundException;
import com.digibankemsi.digitalbankbackend.exceptions.CustomerNotFoundException;
import com.digibankemsi.digitalbankbackend.mappers.BankAccountMapper;
import com.digibankemsi.digitalbankbackend.repositories.AccountOperationRepo;
import com.digibankemsi.digitalbankbackend.repositories.BankAccountRepo;
import com.digibankemsi.digitalbankbackend.repositories.CustomerRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor //better practice than Autowired
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {
    private CustomerRepo customerRepo;
    private BankAccountRepo bankAccountRepo;
    private AccountOperationRepo accountOperationRepo;
    private BankAccountMapper bankAccountMapper;

    @Override
    public void transfer(String senderAccountId, String recieverAccountId, double amount,String description) throws BankAccountNotFoundException, BalanceNotSufficentException {
debit(senderAccountId,amount,"transer to "+recieverAccountId);
credit(recieverAccountId,amount,"Transfer from "+senderAccountId);
    }

    @Override
    public void debit(String accountId, double amount, String motif) throws BankAccountNotFoundException, BalanceNotSufficentException {

        BankAccount bankAccount= bankAccountRepo.findById( accountId).orElseThrow(()-> new BankAccountNotFoundException("no"));
        if(bankAccount.getBalance()<amount)
            throw new BalanceNotSufficentException("balance not sufficient");

        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setAmount(amount);
        accountOperation.setMotif(motif);
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepo.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepo.save(bankAccount);

    }

    @Override
    public void credit(String accountId, double amount, String motif) throws BankAccountNotFoundException {
        BankAccount bankAccount= bankAccountRepo.findById( accountId).orElseThrow(()-> new BankAccountNotFoundException("no"));


        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setAmount(amount);
        accountOperation.setMotif(motif);
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepo.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepo.save(bankAccount);
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("saving a new customer");
Customer customer =bankAccountMapper.fromCustomerDTO(customerDTO);
Customer savedCustomer =customerRepo.save(customer);
   return bankAccountMapper.fromCustomer(savedCustomer);
    }

    @Override
    public currentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, String customerId) throws CustomerNotFoundException {
     Customer customer=customerRepo.findById(customerId).orElse(null);
     if(customer==null)
         throw new CustomerNotFoundException("client not found");
        CurrentAccount currentAccount=new CurrentAccount();;
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreatedAt(new Date());
        currentAccount.setBalance(initialBalance);
        currentAccount.setOverDraft(overDraft);
        currentAccount.setCustomer(customer);
        CurrentAccount savedCurrentAcc=bankAccountRepo.save(currentAccount);


        return bankAccountMapper.fromCurrentAcc(savedCurrentAcc);
    }

    @Override
    public savingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, String customerId) throws CustomerNotFoundException {
        Customer customer=customerRepo.findById(customerId).orElse(null);
        if(customer==null)
            throw new CustomerNotFoundException("client not found");
        SavingAccount savingAccount=new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(new Date());
        savingAccount.setBalance(initialBalance);
        savingAccount.setInterestRate(interestRate);
        savingAccount.setCustomer(customer);
        SavingAccount savedSavingAcc=bankAccountRepo.save(savingAccount);

        return bankAccountMapper.fromSavingAcc(savedSavingAcc);
    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
       BankAccount bankAccount= bankAccountRepo.findById( accountId).orElseThrow(()-> new BankAccountNotFoundException("no"));
       if(bankAccount instanceof SavingAccount){
           SavingAccount savingAccount=(SavingAccount) bankAccount;
           return bankAccountMapper.fromSavingAcc(savingAccount);
       }
       else{
           CurrentAccount currentAccount=(CurrentAccount) bankAccount;
           return bankAccountMapper.fromCurrentAcc(currentAccount);
       }

    }

    @Override
    public List<CustomerDTO> listCustmers() {
       List<Customer> customers= customerRepo.findAll();

       List<CustomerDTO> customerDTOS=customers.stream().map(customer->
           bankAccountMapper.fromCustomer(customer)
      ).collect(Collectors.toList());
        return customerDTOS;
    }

    @Override
    public List<BankAccountDTO> BankAccountList() {
        List<BankAccount> bankAccountList= bankAccountRepo.findAll();
        List<BankAccountDTO> bankAccountDTOS=bankAccountList.stream().map(bankAccount -> {
            if(bankAccount instanceof SavingAccount){
                SavingAccount savingAccount=(SavingAccount) bankAccount;
                return bankAccountMapper.fromSavingAcc(savingAccount);
            }
            else{
                CurrentAccount currentAccount=(CurrentAccount) bankAccount;
                return bankAccountMapper.fromCurrentAcc(currentAccount);
            }
        }).collect(Collectors.toList());
        return bankAccountDTOS;
    }
    @Override
    public CustomerDTO getCustomerDTO(String id) throws CustomerNotFoundException {

        Customer customer=customerRepo.findById(id).orElseThrow(()->new CustomerNotFoundException(" customer not found"));
     return bankAccountMapper.fromCustomer(customer);
    }
    @Override
    public void deleteCustomer(String custId){
        customerRepo.deleteById(custId);

    }
    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        log.info("updating a customer !");
        Customer customer = bankAccountMapper.fromCustomerDTO(customerDTO);
        Customer saved = customerRepo.save(customer);
        return bankAccountMapper.fromCustomer(saved);
    }
    @Override
    public List<AccountOperationDTO> operationsHistory(String id){
        List<AccountOperation>  OperationsList= accountOperationRepo.findByBankAccountId(id);
       List<AccountOperationDTO> accountOperationDTOS= OperationsList.stream().map(op->bankAccountMapper.fromAccountOperation(op)).collect(Collectors.toList());
       return accountOperationDTOS;

    }

    @Override
    public AccountHistoryDTO getBankAccountHistory(String id, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepo.findById(id).orElse(null);
        if(bankAccount==null)
            throw new BankAccountNotFoundException("bank acc not found");
        Page<AccountOperation> accountOperations=accountOperationRepo.findByBankAccountId(id, PageRequest.of(page,size));
        AccountHistoryDTO accountHistoryDTO=new AccountHistoryDTO();

        List<AccountOperationDTO> accountOperationDTOList=accountOperations.getContent().stream().map(op->bankAccountMapper.fromAccountOperation(op)).collect(Collectors.toList());
        accountHistoryDTO.setAccountOperationDTOList(accountOperationDTOList);
        accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());


        return accountHistoryDTO;
    }

    @Override
    public List<CustomerDTO> searchCustomer(String keyword) {
        List<Customer> customers=customerRepo.searchCustomer(keyword);
        List<CustomerDTO> customerDTOS=customers.stream().map(cust->bankAccountMapper.fromCustomer(cust)).collect(Collectors.toList());
        return customerDTOS;
    }
}
