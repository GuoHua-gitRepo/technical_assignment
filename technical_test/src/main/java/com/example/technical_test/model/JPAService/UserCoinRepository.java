package com.example.technical_test.model.JPAService;

import com.example.technical_test.model.UserCoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserCoinRepository extends JpaRepository<UserCoin,Long> {
    @Query(value = "SELECT u FROM UserCoin u WHERE u.userId = :userId and u.symbol = :symbol")
    UserCoin findUserCoin(String userId, String symbol);

    List<UserCoin> findByUserId(String userId);
}
