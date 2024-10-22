package com.pariyajafari.example.wallet.Service;

import com.pariyajafari.example.wallet.Model.Account;
import com.pariyajafari.example.wallet.Model.Transaction;
import com.pariyajafari.example.wallet.Repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;

    private static final Double MIN_ACCOUNT_BALANCE = 10000.0;

    private static final Double DAILY_WITHDRAWAL_LIMIT = 1000000.0;

    //method to create a transaction in transaction repository
    public Transaction createTransaction(Transaction transaction) {
        transaction.setDate(LocalDateTime.now());
        return transactionRepository.save(transaction);
    }

    //method to find a transaction in transaction repository
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found"));
    }

    //method to find all transactions from transaction repository
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        transactionRepository.findAll().forEach(transactions::add);
        return transactions;
    }

    //method to find one specific transaction from transaction repository
    public List<Transaction> getTransactionsByAccountId(Long accountId) {
        return transactionRepository.findByAccountId(accountId);
    }

    //method for creating a deposit transaction for specific account in transaction repository
    public Transaction deposit(Long accountId, Double amount) {
        //find account
        Account account = accountService.getAccountById(accountId);

        //define deposit amount
        account.setBalance(account.getBalance() + amount);
        accountService.updateAccount(accountId, account);

        //creating transaction
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setTransactionType("Deposit");
        transaction.setDate(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }
    //method for creating a withdrawal transaction for specific account in transaction repository
    public Transaction withdraw(Long accountId, Double amount) {
        //find account
        Account account = accountService.getAccountById(accountId);

        //check account balance
        if (account.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance for withdrawal.");
        }

        //set date and time of the current day
        LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        LocalDateTime endOfDay = LocalDateTime.now();

        //checking the daily withdrawals
        double dailyWithdrawals = transactionRepository.findDailyWithdrawals(accountId, startOfDay, endOfDay);

        //checking the daily withdrawals limit
        if (dailyWithdrawals + amount > DAILY_WITHDRAWAL_LIMIT) {
            throw new IllegalArgumentException("Daily withdrawal limit exceeded.");
        }

        //decreasing the balance amount
        account.setBalance(account.getBalance() - amount);
        accountService.updateAccount(accountId, account);

        //checking minimum balance limit for account balance
        if ( account.getBalance() < MIN_ACCOUNT_BALANCE ){
            throw new IllegalArgumentException("Account balance cannot be less than 10000.");
        }

        accountService.updateAccount(accountId, account);

        //creating the withdrawal transaction
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setTransactionType("Withdraw");
        transaction.setDate(LocalDateTime.now()); // تاریخ کنونی

        return transactionRepository.save(transaction);
    }

}