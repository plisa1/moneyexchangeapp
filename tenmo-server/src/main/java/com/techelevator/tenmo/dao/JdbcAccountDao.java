package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    //gets the account balance by id ..
    @Override
    public BigDecimal getAccountBalance(int id) {
        //      BigDecimal accountBalance = new BigDecimal(0);

        String sql = "SELECT balance FROM account WHERE user_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);

        if (results.next()) {
            return results.getBigDecimal("balance");
        }

        return new BigDecimal(0);
    }

    //gets the account balance by the username
    @Override
    public Account getAccountBalanceByUserName(String username) {
        String sql = "SELECT tu.user_id, a.account_id, a.balance from tenmo_user tu " +
                "INNER JOIN account a ON tu.user_id = a.user_id " +
                "WHERE tu.username = ?";

        //String sql = "SELECT balance FROM account WHERE user_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);

        try {
            if (results.next()) {
                Account account = new Account();
                account.setUser_id(results.getInt("user_id"));
                account.setAcccount_id(results.getInt("account_id"));
                account.setBalance(results.getBigDecimal("balance"));
                return account;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        // Should throw an exception here
        return null;
    }

    @Override
    public void updateBalances(int account_id, BigDecimal amount) {
        String sql = "UPDATE account SET balance = (balance + ? ) WHERE account_id = ?; ";
        jdbcTemplate.update(sql, amount, account_id);

    }

    @Override
    public BigDecimal getAccountBalanceByAccountId(int id) {
        String sql = "SELECT balance FROM account WHERE account_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);

        if (results.next()) {
            return results.getBigDecimal("balance");
        }

        return new BigDecimal(0);
    }

    @Override
    public Account getAccountByUserId(int id) {
        Account account = null;
        String sql = "SELECT * FROM account WHERE user_id = ?; ";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);

        if (results.next()) {
            account = mapRowToAccount(results);
        }

        return account;
    }

    @Override
    public Account getAccountByAccountId(int id) {
        Account account = null;
        String sql = "SELECT * FROM account WHERE account_id = ?; ";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);

        if (results.next()) {
            account = mapRowToAccount(results);
        }

        return account;
    }


    private Account mapRowToAccount(SqlRowSet result) {
        Account accounts = new Account();
        accounts.setBalance(result.getBigDecimal("balance"));
        accounts.setAcccount_id(result.getInt("account_id"));
        accounts.setUser_id(result.getInt("user_id"));
        return accounts;
    }


}
