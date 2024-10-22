package com.pariyajafari.example.wallet.Service;

import com.pariyajafari.example.wallet.Model.Account;
import com.pariyajafari.example.wallet.Repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;


@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    //method to get all accounts from repository
    public List<Account> getAllAccounts() {
        return (List<Account>) accountRepository.findAll();
    }
    //method to get account from repository by account id
    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));
    }
    //method to create an account in account repository
    public Account createAccount(Account account) {
        if (account.getBalance() < 10000) {
            throw new IllegalArgumentException("Minimum balance should be 10000.");
        }
        account.setCreatedAt(LocalDate.now());
        return accountRepository.save(account);
    }
    //method to update an account in account repository
    public Account updateAccount(Long id, Account updatedAccount) {
        Account existingAccount = getAccountById(id);
        existingAccount.setAccountNumber(updatedAccount.getAccountNumber());
        existingAccount.setBalance(updatedAccount.getBalance());
        existingAccount.setIban(updatedAccount.getIban());
        return accountRepository.save(existingAccount);
    }
}
