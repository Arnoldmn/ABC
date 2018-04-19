package com.arnold.mna.abcinsurance.model;

public class Policies {

    public  Policies(){

    }

    String  policy_number,
            policy_down_payment,
            policy_deductible,
            policy_details,
            policy_effe_date,
            policy_freq,
            policy_length,
            policy_premium,
            policy_summary,
            policy_exp_date;

    public Policies(
                    String policy_number,
                    String policy_down_payment,
                    String policy_deductible,
                    String policy_details,
                    String policy_effe_date,
                    String policy_freq,
                    String policy_length,
                    String policy_premium,
                    String policy_summary,
                    String policy_exp_date) {
        this.policy_down_payment = policy_down_payment;
        this.policy_deductible = policy_deductible;
        this.policy_details = policy_details;
        this.policy_effe_date = policy_effe_date;
        this.policy_freq = policy_freq;
        this.policy_length = policy_length;
        this.policy_premium = policy_premium;
        this.policy_summary = policy_summary;
        this.policy_exp_date = policy_exp_date;
    }


    public String getPolicy_number() {
        return policy_number;
    }

    public void setPolicy_number(String policy_number) {
        this.policy_number = policy_number;
    }

    public String getPolicy_down_payment() {
        return policy_down_payment;
    }

    public void setPolicy_down_payment(String policy_down_payment) {
        this.policy_down_payment = policy_down_payment;
    }

    public String getPolicy_deductible() {
        return policy_deductible;
    }

    public void setPolicy_deductible(String policy_deductible) {
        this.policy_deductible = policy_deductible;
    }

    public String getPolicy_details() {
        return policy_details;
    }

    public void setPolicy_details(String policy_details) {
        this.policy_details = policy_details;
    }

    public String getPolicy_effe_date() {
        return policy_effe_date;
    }

    public void setPolicy_effe_date(String policy_effe_date) {
        this.policy_effe_date = policy_effe_date;
    }

    public String getPolicy_freq() {
        return policy_freq;
    }

    public void setPolicy_freq(String policy_freq) {
        this.policy_freq = policy_freq;
    }

    public String getPolicy_length() {
        return policy_length;
    }

    public void setPolicy_length(String policy_length) {
        this.policy_length = policy_length;
    }

    public String getPolicy_premium() {
        return policy_premium;
    }

    public void setPolicy_premium(String policy_premium) {
        this.policy_premium = policy_premium;
    }

    public String getPolicy_summary() {
        return policy_summary;
    }

    public void setPolicy_summary(String policy_summary) {
        this.policy_summary = policy_summary;
    }

    public String getPolicy_exp_date() {
        return policy_exp_date;
    }

    public void setPolicy_exp_date(String policy_exp_date) {
        this.policy_exp_date = policy_exp_date;
    }
}
