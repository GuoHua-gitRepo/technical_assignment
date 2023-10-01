package com.example.technical_test.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    String userId;
    Float bidPrice;
    Float askPrice;
    Float askQty;
    Float bidQty;
    String symbol;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Float getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(Float bidPrice) {
        this.bidPrice = bidPrice;
    }

    public Float getAskPrice() {
        return askPrice;
    }

    public void setAskPrice(Float askPrice) {
        this.askPrice = askPrice;
    }

    public Float getAskQty() {
        return askQty;
    }

    public void setAskQty(Float askQty) {
        this.askQty = askQty;
    }

    public Float getBidQty() {
        return bidQty;
    }

    public void setBidQty(Float bidQty) {
        this.bidQty = bidQty;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", bidPrice=" + bidPrice +
                ", askPrice=" + askPrice +
                ", askQty=" + askQty +
                ", bidQty=" + bidQty +
                ", symbol='" + symbol + '\'' +
                '}';
    }
}
