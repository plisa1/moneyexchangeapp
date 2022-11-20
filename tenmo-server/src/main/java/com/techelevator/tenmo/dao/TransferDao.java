package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {

    List<Transfer> findAll();

    void create(Transfer transfer);

    Transfer getTransferById(int id);

    Transfer getTransferByUserId(int id);
//
//    Transfer getTransferByType(String typeDescription);
//
//    Transfer getTransferByStatus(String statusDescription);


}
