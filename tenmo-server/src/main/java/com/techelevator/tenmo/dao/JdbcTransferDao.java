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
public class JdbcTransferDao implements TransferDao {

    Account account;
    AccountDao currentBalance;
    private JdbcTemplate jdbcTemplate;
    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Transfer> findAll() {
        List<Transfer> allTransfers = new ArrayList<>();
        String sql = "SELECT * FROM transfer; ";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()){
            allTransfers.add(mapRowToTransfer(results));

        }
        return allTransfers;
    }

    //creates a transfer to send money of type transfer in DB(making and putting in all the transfer attributes)
    @Override
    public void create(Transfer transfer) {
        // TODO create the checks for moving money and create sql to move money

        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) VALUES (?, ?, ?, ?, ?);";
        jdbcTemplate.update(sql,
                transfer.getType_id(), transfer.getStatus_id(), transfer.getAcct_from(), transfer.getAcct_to(), transfer.getAmount());



//        int id = account.getUser_id();

//        if (transfer.getAmount() < currentBalance.getAccountBalance(transfer.getAcct_from(id))) {
//            transfer.getAcct_from(equals(BigDecimal.valueOf(currentBalance.getBalance())))
//        }

    }



    //get all the transfers by the transferid
    @Override
    public Transfer getTransferById(int id) {
        Transfer transfer = null;

        String sql = "SELECT * FROM transfer WHERE transfer_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);

        if (results.next()) {
            transfer = mapRowToTransfer(results);
        }

        return transfer;

    }

//    get transfers by the userid ..joining from account table
    @Override
    public Transfer getTransferByUserId(int id) {
        Transfer transfer = null;

        String sql = "SELECT * FROM transfer INNER JOIN account ON account.account_id = transfer.account_from WHERE account.user_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);

        if (results.next()) {
            transfer = mapRowToTransfer(results);
        }

        return transfer;
    }
//
//    //get transfers by the type of transfer it is
//    @Override
//    public Transfer getTransferByType(String typeDescription) {
//        Transfer transfer = null;
//        String sql = "SELECT * FROM transfer INNER JOIN transfer_type ON transfer_type.transfer_type_id = transfer.transfer_type_id WHERE transfer_type.transfer_type_desc = ?; ";
//
//        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, typeDescription);
//
//        if (results.next()){
//            transfer = mapRowToTransfer(results);
//        }
//
//        return transfer;
//    }
//
//    @Override
//    public Transfer getTransferByStatus(String statusDescription) {
//        Transfer transfer = null;
//        String sql = "SELECT * FROM transfer INNER JOIN transfer_status ON transfer_status.transfer_status_id = transfer.transfer_status_id WHERE transfer_status.transfer_status_desc = ?; ";
//
//        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, statusDescription);
//
//        if (results.next()){
//            transfer = mapRowToTransfer(results);
//        }
//
//        return transfer;
//    }

    private Transfer mapRowToTransfer(SqlRowSet result){
        Transfer transfers = new Transfer();
        transfers.setTransfer_id(result.getInt("transfer_id"));
        transfers.setAcct_from(result.getInt("account_from"));
        transfers.setAcct_to(result.getInt("account_to"));
        transfers.setAmount(result.getBigDecimal("amount"));
        transfers.setStatus_id(result.getInt("transfer_status_id"));
        transfers.setType_id(result.getInt("transfer_type_id"));
        return transfers;

    }


}
