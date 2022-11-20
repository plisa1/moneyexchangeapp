package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class RestTransferService {

    private static final String API_BASE_URL = "http://localhost:8080/transfer";
    private final RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser currentUser;

    private String authToken = null;

    public RestTransferService() {

    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
     public void setUser(AuthenticatedUser someUser) {
        this.currentUser = someUser;
     }

    public RestTransferService(AuthenticatedUser currentUser, String authToken) {
        this.currentUser = currentUser;
        this.authToken = authToken;
    }

    public Transfer[] getTransferHistory() {
        Transfer[] arrayTransfer = null;
        try {
            ResponseEntity <Transfer[]> response =
                    restTemplate.exchange(API_BASE_URL + "/alltransfers", HttpMethod.GET, makeAuthEntity(), Transfer[].class);
            for(int i = 0; i < response.getBody().length; i++){
                System.out.println(response.getBody()[i]);
            }
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        } catch (NullPointerException e){
            BasicLogger.log("Response body is null");
        }

       return arrayTransfer;

    }



    public ResponseEntity<Transfer> sendMoney(Transfer newTransfer){

//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<Transfer> entity = new HttpEntity<>(newTransfer, headers);
//        headers.setBearerAuth(authToken);

//        HttpEntity<Transfer> transferHttpEntity = makeTransferEntity(newTransfer);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setBearerAuth(authToken);

        ResponseEntity<Transfer> returnedTransfer = null;
        try {
          //  returnedTransfer  = restTemplate.postForObject(API_BASE_URL + "/create", makeTransferEntity(newTransfer),  Transfer.class);
            returnedTransfer = restTemplate.exchange(API_BASE_URL + "/create", HttpMethod.POST, makeTransferEntity(newTransfer), Transfer.class);
        } catch (RestClientResponseException | ResourceAccessException | NullPointerException e) {
            BasicLogger.log(e.getMessage());
        }
        return returnedTransfer;

    }



    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
        return entity;
    }

//    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setBearerAuth(authToken);
//        return new HttpEntity<>(transfer, headers);
//    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }


}
