package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;

public interface AccountDao {

    public BigDecimal getAccountBalance(int id);
    public Account getAccountByUserId(int id);
    public Account getAccountByAccountId(int id);
    public Account getAccountBalanceByUserName(String username);
    public void updateBalances(int account_id, BigDecimal amount);
    public BigDecimal getAccountBalanceByAccountId(int id);



}
