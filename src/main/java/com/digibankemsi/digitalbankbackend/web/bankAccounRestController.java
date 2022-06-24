package com.digibankemsi.digitalbankbackend.web;

import com.digibankemsi.digitalbankbackend.dtos.*;
import com.digibankemsi.digitalbankbackend.exceptions.BalanceNotSufficentException;
import com.digibankemsi.digitalbankbackend.exceptions.BankAccountNotFoundException;
import com.digibankemsi.digitalbankbackend.services.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class bankAccounRestController {

    private BankAccountService bankAccountService;
@GetMapping("/accounts/{id}")
    public BankAccountDTO getBankAccount(@PathVariable String id) throws BankAccountNotFoundException {
    return bankAccountService.getBankAccount(id);

}
@GetMapping("/accounts")
    public List<BankAccountDTO> listAllAccounts(){
    return bankAccountService.BankAccountList();
}
@GetMapping("/accounts/{id}/operations")
public List<AccountOperationDTO> getHistory(@PathVariable  String id){

return bankAccountService.operationsHistory(id);
}
@GetMapping("/accounts/{id}/Pageoperations")
public AccountHistoryDTO getbankAccountHistory(@PathVariable  String id, @RequestParam(name = "page",defaultValue = "0") int page, @RequestParam(name = "size",defaultValue = "5") int size) throws BankAccountNotFoundException {

    return bankAccountService.getBankAccountHistory(id,page,size);
}
@PostMapping("/accounts/debit")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO ) throws BankAccountNotFoundException, BalanceNotSufficentException {
    System.out.println("thiiiiiiiiiiiiis"+debitDTO.getAccountId());
    this.bankAccountService.debit(debitDTO.getAccountId(),debitDTO.getAmount(),debitDTO.getDescription());
    return debitDTO;


}
    @PostMapping("/accounts/credit")
    public CreditDTO credit(@RequestBody CreditDTO creditDTO ) throws BankAccountNotFoundException, BalanceNotSufficentException {
        this.bankAccountService.credit(creditDTO.getAccountId(),creditDTO.getAmount(),creditDTO.getDescription());
        return creditDTO;


    }
    @PostMapping("/accounts/transfer")
    public void transfer(@RequestBody TransferRequestDTO transferRequestDTO ) throws BankAccountNotFoundException, BalanceNotSufficentException {
        this.bankAccountService.transfer(transferRequestDTO.getAccountSource(),transferRequestDTO.getAccountDestination(),transferRequestDTO.getAmount(),transferRequestDTO.getDescription());



    }
}
