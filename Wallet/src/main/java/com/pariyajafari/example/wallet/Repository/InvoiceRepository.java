package com.pariyajafari.example.wallet.Repository;

import com.pariyajafari.example.wallet.Model.Invoice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends CrudRepository<Invoice, Long> {

}

