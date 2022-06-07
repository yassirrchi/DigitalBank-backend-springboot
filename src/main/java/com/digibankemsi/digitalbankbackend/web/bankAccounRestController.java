package com.digibankemsi.digitalbankbackend.web;

import com.digibankemsi.digitalbankbackend.dtos.AccountHistoryDTO;
import com.digibankemsi.digitalbankbackend.dtos.AccountOperationDTO;
import com.digibankemsi.digitalbankbackend.dtos.BankAccountDTO;
import com.digibankemsi.digitalbankbackend.exceptions.BankAccountNotFoundException;
import com.digibankemsi.digitalbankbackend.services.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
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
public List<AccountHistoryDTO> getbankAccountHistory(@PathVariable  String id, @RequestParam(name = "page",defaultValue = "0") int page, @RequestParam(name = "size",defaultValue = "5") int size) throws BankAccountNotFoundException {

    return bankAccountService.getBankAccountHistory(id,page,size);
}

}
