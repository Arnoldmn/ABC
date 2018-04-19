package com.arnold.mna.abcinsurance.model;

public class Claim {

    public void Claim(){

    }

    String claimDate,
            claimAmount,
            amountPaid,
            claimStatus,
            fullfilDate,
            notes;

    public Claim(String claimDate,
                 String claimAmount,
                 String amountPaid,
                 String claimStatus,
                 String fullfilDate,
                 String notes) {

        this.claimDate = claimDate;
        this.claimAmount = claimAmount;
        this.amountPaid = amountPaid;
        this.claimStatus = claimStatus;
        this.fullfilDate = fullfilDate;
        this.notes = notes;
    }

    public String getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(String claimDate) {
        this.claimDate = claimDate;
    }

    public String getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(String claimAmount) {
        this.claimAmount = claimAmount;
    }

    public String getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(String amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getClaimStatus() {
        return claimStatus;
    }

    public void setClaimStatus(String claimStatus) {
        this.claimStatus = claimStatus;
    }

    public String getFullfilDate() {
        return fullfilDate;
    }

    public void setFullfilDate(String fullfilDate) {
        this.fullfilDate = fullfilDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
