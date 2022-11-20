package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Objects;

public class Transfer {
    //@JsonProperty("transfer_id")
    private int transfer_id;
    //@JsonProperty("transfer_type_id")
    private int type_id;
    //@JsonProperty("transfer_status_id")
    private int status_id;
    //@JsonProperty("account_from")
    private int acct_from;
    //@JsonProperty("account_to")
    private int acct_to;
    private BigDecimal amount;

    public Transfer(int transfer_id, int type_id, int status_id, int acct_from, int acct_to, BigDecimal amount) {
        this.transfer_id = transfer_id;
        this.type_id = type_id;
        this.status_id = status_id;
        this.acct_from = acct_from;
        this.acct_to = acct_to;
        this.amount = amount;
    }

    public Transfer() {

    }

    public void setTransfer_id(int transfer_id) {
        this.transfer_id = transfer_id;
    }

    public int getTransfer_id() {return transfer_id; }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public int getAcct_from() {
        return acct_from;
    }

    public void setAcct_from(int acct_from) {
        this.acct_from = acct_from;
    }

    public int getAcct_to() {
        return acct_to;
    }

    public void setAcct_to(int acct_to) {
        this.acct_to = acct_to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transfer transfer = (Transfer) o;
        return getTransfer_id() == transfer.getTransfer_id() && getType_id() == transfer.getType_id() && getStatus_id() == transfer.getStatus_id() && getAcct_from() == transfer.getAcct_from() && getAcct_to() == transfer.getAcct_to() && getAmount().equals(transfer.getAmount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTransfer_id(), getType_id(), getStatus_id(), getAcct_from(), getAcct_to(), getAmount());
    }
}
