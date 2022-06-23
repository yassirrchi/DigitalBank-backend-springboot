package com.digibankemsi.digitalbankbackend.web;

import com.digibankemsi.digitalbankbackend.dtos.CustomerDTO;
import com.digibankemsi.digitalbankbackend.entities.Customer;
import com.digibankemsi.digitalbankbackend.exceptions.CustomerNotFoundException;
import com.digibankemsi.digitalbankbackend.repositories.BankAccountRepo;
import com.digibankemsi.digitalbankbackend.services.BankAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class customerRestController {
    private BankAccountService bankAccountService;

    @GetMapping("/customers")
    public List<CustomerDTO> customers(){
        return bankAccountService.listCustmers();
    }
    @GetMapping("/customers/search")
    public List<CustomerDTO> Searchcustomers(@RequestParam(name="keyword",defaultValue = "") String keyword){
        return bankAccountService.searchCustomer("%"+keyword+"%");
    }
    @GetMapping("/customers/{id}")
public CustomerDTO getCustomer( @PathVariable(name="id") String customerID) throws CustomerNotFoundException {
        return bankAccountService.getCustomerDTO(customerID);
    }
    @PostMapping("/customers")
    public CustomerDTO saveCust(@RequestBody CustomerDTO customerDTO){
        return bankAccountService.saveCustomer(customerDTO);

    }
    @PutMapping("/customers/{id}")
    public CustomerDTO update(@PathVariable(name = "id") String custId,@RequestBody CustomerDTO customerDTO){
        customerDTO.setId(custId);
return bankAccountService.updateCustomer(customerDTO);

    }
    @DeleteMapping("/customers/{id}")
    public void deleteCust(@PathVariable(name = "id") String id){
        bankAccountService.deleteCustomer(id);
    }
}
