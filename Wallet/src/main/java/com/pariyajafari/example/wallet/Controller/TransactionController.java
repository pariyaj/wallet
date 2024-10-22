package com.pariyajafari.example.wallet.Controller;

import com.pariyajafari.example.wallet.DTO.TransactionDTO;
import com.pariyajafari.example.wallet.Model.Account;
import com.pariyajafari.example.wallet.Model.Transaction;
import com.pariyajafari.example.wallet.Service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    //create transaction request
    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
        // تبدیل DTO به موجودیت Transaction
        Transaction transaction = convertToEntity(transactionDTO);
        Transaction savedTransaction = transactionService.createTransaction(transaction);

        // تبدیل موجودیت Transaction به DTO
        TransactionDTO savedTransactionDTO = convertToDTO(savedTransaction);
        return ResponseEntity.ok(savedTransactionDTO);
    }
    //get transactions by transaction id request
    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable Long id) {
        Transaction transaction = transactionService.getTransactionById(id);
        TransactionDTO transactionDTO = convertToDTO(transaction);
        return ResponseEntity.ok(transactionDTO);
    }
    //get transactions by account id request
    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByAccountId(@PathVariable Long accountId) {
        List<Transaction> transactions = transactionService.getTransactionsByAccountId(accountId);
        List<TransactionDTO> transactionDTOs = transactions.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(transactionDTOs);
    }
    //deposit request
    @PostMapping("/deposit")
    public ResponseEntity<TransactionDTO> deposit( @Valid @RequestBody TransactionDTO transactionDTO) {
        // واریز به حساب
        Transaction transaction = transactionService.deposit(transactionDTO.getAccountId(), transactionDTO.getAmount());

        // تبدیل موجودیت Transaction به DTO و بازگشت به کلاینت
        TransactionDTO responseDTO = convertToDTO(transaction);
        return ResponseEntity.ok(responseDTO);
    }
    //withdraw request
    @PostMapping("/withdraw")
    public ResponseEntity<TransactionDTO> withdraw( @Valid @RequestBody TransactionDTO transactionDTO) {
        // برداشت از حساب
        Transaction transaction = transactionService.withdraw(transactionDTO.getAccountId(), transactionDTO.getAmount());

        // تبدیل موجودیت Transaction به DTO و بازگشت به کلاینت
        TransactionDTO responseDTO = convertToDTO(transaction);
        return ResponseEntity.ok(responseDTO);
    }

    //method for converting transaction dto to transaction entity
    private Transaction convertToEntity(TransactionDTO dto) {
        Transaction transaction = new Transaction();
        Account account = new Account();
        account.setId(dto.getAccountId());
        transaction.setAccount(account);
        transaction.setAmount(dto.getAmount());
        transaction.setTransactionType(dto.getTransactionType());
        return transaction;
    }
    //method for converting transaction entity to transaction dto
    private TransactionDTO convertToDTO(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setAccountId(transaction.getAccount().getId());
        dto.setAmount(transaction.getAmount());
        dto.setDate(transaction.getDate());
        dto.setTransactionType(transaction.getTransactionType());
        return dto;
    }
}

