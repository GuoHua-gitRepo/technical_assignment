package com.example.technical_test.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_user_coin")
public class UserCoin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String userId;
    private float quantity;
    private String symbol;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "UserCoin{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", quantity=" + quantity +
                ", symbol=" + symbol +
                '}';
    }
}
