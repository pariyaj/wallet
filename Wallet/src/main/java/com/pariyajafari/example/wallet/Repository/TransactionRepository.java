package com.pariyajafari.example.wallet.Repository;

import com.pariyajafari.example.wallet.Model.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    List<Transaction> findByAccountId(Long accountId);
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t " +
            "WHERE t.account.id = :accountId " +
            "AND t.transactionType = 'Withdraw' " +
            "AND t.date BETWEEN :startOfDay AND :endOfDay")
    Double findDailyWithdrawals(@Param("accountId") Long accountId,
                                @Param("startOfDay") LocalDateTime startOfDay,
                                @Param("endOfDay") LocalDateTime endOfDay);
}