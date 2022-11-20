package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.security.jwt.TokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/transfer")
public class TransferController {

    private TransferDao transferDao;
    private AccountDao accountDao;
    private TokenProvider tokenProvider;
    private UserDao userDao;

    public TransferController(TransferDao transferDao, AccountDao accountDao, TokenProvider tokenProvider) {
        this.transferDao = transferDao;
        this.accountDao = accountDao;
        this.tokenProvider = tokenProvider;
        this.userDao = userDao;
    }

    //??
    //wrote to get all transfers - check
    @RequestMapping(path="/alltransfers", method = RequestMethod.GET)
    public List<Transfer> getAllTransfers(@RequestHeader Map<String, String> headers) {
        System.out.println("After JWT Filter");
        System.out.println(headers.get("authorization"));
        String username = tokenProvider.getAuthentication(headers.get("authorization").split(" ")[1]).getName();
        System.out.println(username);
        return transferDao.findAll();
    }

    public boolean checkBalance(Account balanceAmount, BigDecimal transferAmount){
        if (balanceAmount.getBalance().compareTo(transferAmount) >= 0 ){
            return true;
        }else {
            return false;
        }

    }



    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/create", method = RequestMethod.POST)
        public Transfer createTransfer(@RequestBody Transfer transfer) {
            BigDecimal senderBalance = accountDao.getAccountBalanceByAccountId(transfer.getAcct_from()); // able to access the balance from the account_id in the account and transfer

            if (transfer.getAcct_from() != transfer.getAcct_to()) {
                if(senderBalance.compareTo(transfer.getAmount()) >= 0) { // this logic seemed to do the trick. Transfers initiated over an account balance don't go through
                    transferDao.create(transfer);
                    accountDao.updateBalances(transfer.getAcct_from(), transfer.getAmount().negate());
                    accountDao.updateBalances(transfer.getAcct_to(), (transfer.getAmount()));

                }else {
                    transfer.setStatus_id(3);

                }
            }
        return transfer;
    }


    @RequestMapping(path="/transferid/{id}", method = RequestMethod.GET)
    public Transfer getTransferByTransferId(@PathVariable int id) {
        Transfer transfer = transferDao.getTransferById(id);
        if (transfer == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.");
        }else{
            return transfer;
        }
    }

    @RequestMapping(path="/userid/{id}", method = RequestMethod.GET)
    public Transfer getTransferWithUserId(@PathVariable int id) {
        Transfer transfer = transferDao.getTransferByUserId(id);
        if (transfer == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.");
        }else{
            return transfer;
        }
    }

//    @RequestMapping(path="/type/{type}", method = RequestMethod.GET)
//    public Transfer getTransferWithType(@PathVariable String type) {
//        Transfer transfer = transferDao.getTransferByType(type);
//        if (transfer == null){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.");
//        }else{
//            return transfer;
//        }
//    }
//
//    @RequestMapping(path="/status/{status}", method = RequestMethod.GET)
//    public Transfer getTransferWithStatus(@PathVariable String status) {
//        Transfer transfer = transferDao.getTransferByStatus(status);
//        if (transfer == null){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.");
//        }else{
//            return transfer;
//        }
//    }

}


