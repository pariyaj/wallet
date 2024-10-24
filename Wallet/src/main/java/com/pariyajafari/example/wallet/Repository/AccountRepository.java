package com.pariyajafari.example.wallet.Repository;

import com.pariyajafari.example.wallet.Model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    List<Account> findByAccountNumber(String accountNumber);
    List<Account> findByIban(String iban);

}
