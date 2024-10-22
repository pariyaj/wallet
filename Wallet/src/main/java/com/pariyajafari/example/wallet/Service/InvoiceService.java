package com.pariyajafari.example.wallet.Service;

import com.pariyajafari.example.wallet.Model.Invoice;
import com.pariyajafari.example.wallet.Model.Transaction;
import com.pariyajafari.example.wallet.Repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class InvoiceService {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private Transaction transaction;


    public Invoice createInvoice(String accountNumber) {

        Invoice invoice = new Invoice(new HashSet<>(transaction));
        return invoiceRepository.save(invoice);
    }
}

