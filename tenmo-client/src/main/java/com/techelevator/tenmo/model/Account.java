package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Account {

    private int acccount_id;
    private int user_id;
    private BigDecimal balance;

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

    @Override
    public String toString() {
        return "Account{" +
                "account_id=" + acccount_id +
                "user_id=" + user_id +
                ", balance=" + balance +
                '}';
    }
}
