package com.example.technical_test.model.JPAService;


import com.example.technical_test.model.Transaction;
import com.example.technical_test.model.UserCoin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findByUserId(String userId);
}
