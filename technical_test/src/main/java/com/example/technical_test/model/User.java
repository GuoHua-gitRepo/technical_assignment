package com.example.technical_test.model;

import jakarta.persistence.*;
@Entity
@Table(name = "tbl_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    String userId;
    float USDTBalance;
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

    public float getUSDTBalance() {
        return USDTBalance;
    }

    public void setUSDTBalance(float USDTBalance) {
        this.USDTBalance = USDTBalance;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", USDTBalance=" + USDTBalance +
                '}';
    }
}
