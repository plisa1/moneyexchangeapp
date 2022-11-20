package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.security.jwt.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/account")
public class AccountController {
    private AccountDao accountDao;
    private UserDao userDao;
    private TokenProvider tokenProvider;

    //takes in a tokenprovider in the constructor - token given
    public AccountController(AccountDao accountDao, UserDao userDao, TokenProvider tokenProvider) {
        this.accountDao = accountDao;
        this.userDao = userDao;
        this.tokenProvider = tokenProvider;
    }

    //http reponse of type account, get balance and takes in string headers
    //token provider instance is used to obtain a token for the given audience
    @GetMapping()
    public ResponseEntity<Account> getBalance(@RequestHeader Map<String, String> headers) {
        System.out.println("After JWT Filter");
        System.out.println(headers.get("authorization"));
        String username = tokenProvider.getAuthentication(headers.get("authorization").split(" ")[1]).getName();
        System.out.println(username);
        Account acount = accountDao.getAccountBalanceByUserName(username);
        return new ResponseEntity<Account>(acount, HttpStatus.OK);
    }

    //List all users with their un and uid
    @RequestMapping(path="/listusers", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @RequestMapping(path="/user/{userid}", method = RequestMethod.GET)
    public User getUserByUserId(@PathVariable int userid) {
        User user = userDao.getUserById(userid);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found.");
        } else {
            return user;
        }
    }

    @RequestMapping(path= "/accountid/{userid}", method = RequestMethod.GET)
    public Integer getAccountIdByUserId(@PathVariable int userid){

       return accountDao.getAccountByUserId(userid).getAcccount_id();
    }


    //shouldn't be using this unless we have an admin role
    //@PreAuthorize("hasRole('Admin')")
    @RequestMapping(path =  "/{id}", method = RequestMethod.GET)
    public BigDecimal getAccountBalanceById(@PathVariable int id){
        BigDecimal accountBalance = accountDao.getAccountBalance(id);
            if (accountBalance == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.");
            }else{
                return accountBalance;
            }
    }

//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @RequestMapping(value = "/add/{id}", method = RequestMethod.PUT)
//    public void addBalanceToAccount(@Valid @RequestBody BigDecimal amountToAdd, @PathVariable int id){
//        accountDao.addBalance(amountToAdd, id);
//    }
//
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @RequestMapping(value = "/subtract/{id}", method = RequestMethod.PUT)
//    public void subtractBalanceFromAccount(@Valid @RequestBody BigDecimal amountToSubtract, @PathVariable int id){
//        accountDao.subtractBalance(amountToSubtract, id);
//    }




}
