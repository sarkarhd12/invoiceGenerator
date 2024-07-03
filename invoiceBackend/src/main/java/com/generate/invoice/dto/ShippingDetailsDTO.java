package com.generate.invoice.dto;

public class ShippingDetailsDTO {
    private String name;
    private String address;
    private String city;
    private String state;
    private String pincode;
    private String stateUTCode;

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

    public String getStateUTCode() {
        return stateUTCode;
    }

    public void setStateUTCode(String stateUTCode) {
        this.stateUTCode = stateUTCode;
    }

    @Override
    public String toString() {
        return "ShippingDetailsDTO{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", pincode='" + pincode + '\'' +
                ", stateUTCode='" + stateUTCode + '\'' +
                '}';
    }
}
