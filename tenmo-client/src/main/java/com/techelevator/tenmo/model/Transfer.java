package com.techelevator.tenmo.model;

import com.techelevator.util.BasicLogger;

import java.math.BigDecimal;
import java.util.Objects;

public class Transfer {

    private int transfer_id;
    private int type_id;
    private int status_id;
    private int acct_from;
    private int acct_to;
    private BigDecimal amount;

    public int getTransfer_id() {
        return transfer_id;
    }

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

    public BigDecimal processMoney(String inputMoney) {
        Double newInput = Double.parseDouble(inputMoney);

        try {
            if (newInput > 0) {
                this.amount = BigDecimal.valueOf(newInput);
//                this.amount = amount.add(BigDecimal.valueOf(newInput));
                return amount;
            } else {
                this.amount = amount.add(BigDecimal.valueOf(0));
                System.out.println("Can't transfer zero or negative amount. Transfer did not process:");
            }
            return amount;
        } catch (NumberFormatException n) {
            BasicLogger.log(n.getMessage());
        }
        return amount;
    }

    public Transfer() {
        this.setAmount(BigDecimal.valueOf(0));
    }

    public Transfer(int accountFrom, int accountTo, BigDecimal amount) {
        Transfer transfer = new Transfer();
        transfer.setType_id(2);
        transfer.setStatus_id(2);
        transfer.setAcct_from(accountFrom);
        transfer.setAcct_to(accountTo);
        transfer.setAmount(amount);

    }


    @Override
    public String toString() {
        return "Transfer Receipt: " +  "\n" +
                "transfer_id: " + transfer_id + "\n" +
                "Type_id: " + type_id + "\n" +
                "Status_id: " + status_id + "\n" +
                "Acct_from: " + acct_from + "\n" +
                "Acct_to: " + acct_to + "\n" +
                "Amount: $" + amount + "\n" +
                "*****************************************";
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
