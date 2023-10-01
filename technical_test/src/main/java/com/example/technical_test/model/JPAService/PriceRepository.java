package com.example.technical_test.model.JPAService;

import com.example.technical_test.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  PriceRepository extends JpaRepository<Price,Long> {
    Price findBySymbol(String symbol);
}
