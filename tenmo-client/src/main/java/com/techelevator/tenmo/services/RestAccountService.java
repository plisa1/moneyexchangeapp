package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RestAccountService {

    private static final String API_BASE_URL = "http://localhost:8080/account/";
    private final RestTemplate restTemplate = new RestTemplate();

    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }


    public Account getAccountBalance() {

        Account account = null;
        try {
            ResponseEntity<Account> response =
                    restTemplate.exchange(API_BASE_URL, HttpMethod.GET, makeAuthEntity(), Account.class);
            account = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return account;


    }

    public User[] getAllUsers() {
        User[] userList = null;
        try {
            userList = restTemplate.exchange(API_BASE_URL + "/listusers", HttpMethod.GET, makeAuthEntity(), User[].class).getBody();
        } catch (RestClientResponseException r) {
            System.out.println("Could not finish request");
        } catch (ResourceAccessException e) {
            System.out.println("Server issues");
        }
        return userList;

    }

    public User getUserById(int id) {
        User user = null;
        try {
            ResponseEntity<User> response =
                    restTemplate.exchange(API_BASE_URL + "/user/" + id, HttpMethod.GET, makeAuthEntity(), User.class);
            user = response.getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return user;
    }

    public Integer getAccountIdByUserId(int id){
        Integer accountId = 0;
        try {
            ResponseEntity<Integer> response =
                    restTemplate.exchange(API_BASE_URL + "/accountid/" + id, HttpMethod.GET, makeAuthEntity(), Integer.class);
            accountId = response.getBody();
        } catch (RestClientResponseException e){
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        }

        return accountId;

    }


//    public BigDecimal getAccountBalance(int id){
//
//        BigDecimal account = null;
//        try {
//            ResponseEntity<BigDecimal> response =
//                    restTemplate.exchange(API_BASE_URL + id, HttpMethod.GET, makeAuthEntity(), BigDecimal.class);
//            account = response.getBody();
//        } catch (RestClientResponseException | ResourceAccessException e) {
//            BasicLogger.log(e.getMessage());
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        return account;


//}

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

}
