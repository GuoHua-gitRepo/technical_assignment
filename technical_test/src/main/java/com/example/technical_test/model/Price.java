package com.example.technical_test.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_price")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    String symbol;
    Float bidPrice;
    Float askPrice;
    Float askQty;
    Float bidQty;

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
        return "Price{" +
                "id=" + id +
                ", symbol='" + symbol + '\'' +
                ", bidPrice=" + bidPrice +
                ", askPrice=" + askPrice +
                ", askQty=" + askQty +
                ", bidQty=" + bidQty +
                '}';
    }
}
