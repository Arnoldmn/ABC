package com.arnold.mna.abcinsurance.model;

public class User {

    public User() {
    }

    String firstName, midName, lName, email, phone, medicalCon, img;

    public User(String fname,
                String midName,
                String lName,
                String email,
                String phone,
                String medicalCon,

                String img) {
        this.firstName = fname;
        this.midName = midName;
        this.lName = lName;
        this.email = email;
        this.phone = phone;
        this.medicalCon = medicalCon;
        this.img = img;


    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMidName() {
        return midName;
    }

    public void setMidName(String midName) {
        this.midName = midName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMedicalCon() {
        return medicalCon;
    }

    public void setMedicalCon(String medicalCon) {
        this.medicalCon = medicalCon;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
