package com.generate.invoice.dto;

public class SellerDetailsDto {
    private String name;
    private String address;
    private String city;
    private String state;
    private String pincode;
    private String panNo;
    private String gstRegistrationNo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public String getGstRegistrationNo() {
        return gstRegistrationNo;
    }

    public void setGstRegistrationNo(String gstRegistrationNo) {
        this.gstRegistrationNo = gstRegistrationNo;
    }

    @Override
    public String toString() {
        return "SellerDetailsDTO{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", pincode='" + pincode + '\'' +
                ", panNo='" + panNo + '\'' +
                ", gstRegistrationNo='" + gstRegistrationNo + '\'' +
                '}';
    }
}
