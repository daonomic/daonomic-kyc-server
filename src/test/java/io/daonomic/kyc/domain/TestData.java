package io.daonomic.kyc.domain;

import java.util.List;


public class TestData {
    private String country;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String phone;
    private String post;
    private boolean terms;
    private List<String> passport;
    private List<String> selfie;
    private List<String> accreditation;
    private String address;

    public TestData(String country, String firstName, String lastName, String birthDate, String phone, String post, boolean terms, List<String> passport, List<String> selfie, List<String> accreditation, String address) {
        this.country = country;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phone = phone;
        this.post = post;
        this.terms = terms;
        this.passport = passport;
        this.selfie = selfie;
        this.accreditation = accreditation;
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public boolean isTerms() {
        return terms;
    }

    public void setTerms(boolean terms) {
        this.terms = terms;
    }

    public List<String> getPassport() {
        return passport;
    }

    public void setPassport(List<String> passport) {
        this.passport = passport;
    }

    public List<String> getSelfie() {
        return selfie;
    }

    public void setSelfie(List<String> selfie) {
        this.selfie = selfie;
    }

    public List<String> getAccreditation() {
        return accreditation;
    }

    public void setAccreditation(List<String> accreditation) {
        this.accreditation = accreditation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
