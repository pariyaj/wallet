package com.pariyajafari.example.wallet.Controller;

import com.pariyajafari.example.wallet.DTO.AccountDTO;
import com.pariyajafari.example.wallet.Model.Account;
import com.pariyajafari.example.wallet.Model.Invoice;
import com.pariyajafari.example.wallet.Service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    //create an account request
    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@Valid @RequestBody AccountDTO accountDTO) {
        //convert account dto to account entity
        Account account = convertToEntity(accountDTO);
        Account savedAccount = accountService.createAccount(account);

        //convert account entity to account dto
        AccountDTO savedAccountDTO = convertToDTO(savedAccount);
        return ResponseEntity.ok(savedAccountDTO);
    }

    //get all accounts request
    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        List<AccountDTO> accountDTOs = accounts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(accountDTOs);
    }
    //get account by account id request
    @GetMapping("/{id}")
    public ResponseEntity <AccountDTO> getAccountById(@PathVariable Long id) {
        Account account = accountService.getAccountById(id);
        AccountDTO accountDTO = convertToDTO(account);
        return ResponseEntity.ok(accountDTO);
    }
    //update account request
    @PutMapping("/{id}")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable Long id, @Valid @RequestBody AccountDTO accountDTO) {
        Account account = convertToEntity(accountDTO);
        Account updatedAccount = accountService.updateAccount(id, account);
        AccountDTO updatedAccountDTO = convertToDTO(updatedAccount);
        return ResponseEntity.ok(updatedAccountDTO);
    }

    //get invoice for specific account
    @GetMapping("/{accountNumber}/invoice")
    public ResponseEntity<Invoice> getInvoiceForAccount(@PathVariable String accountNumber) throws Exception {
        Invoice invoice = accountService.getInvoiceForAccount(accountNumber);
        return ResponseEntity.ok(invoice);
    }

    //convert to entity method
    private Account convertToEntity(AccountDTO dto) {
        Account account = new Account();
        account.setAccountNumber(dto.getAccountNumber());
        account.setBalance(dto.getBalance());
        account.setIban(dto.getIban());
        return account;
    }
    // convert to dto method
    private AccountDTO convertToDTO(Account account) {
        AccountDTO dto = new AccountDTO();
        dto.setAccountNumber(account.getAccountNumber());
        dto.setBalance(account.getBalance());
        dto.setIban(account.getIban());
        return dto;
    }
}
