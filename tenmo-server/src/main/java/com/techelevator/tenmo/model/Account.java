package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class Account {

    @JsonProperty("account_id")
    private int acccount_id;
    @JsonProperty("user_id")
    private int user_id;
    @JsonProperty("balance")
    private BigDecimal balance;

    public Account(int acccount_id, int user_id, BigDecimal balance) {
        this.acccount_id = acccount_id;
        this.user_id = user_id;
        this.balance = balance;
    }

    public Account() {
    }

    public int getAcccount_id() {
        return acccount_id;
    }

    public void setAcccount_id(int acccount_id) {
        this.acccount_id = acccount_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }



}
