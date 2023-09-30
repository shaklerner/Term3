package com.example.travelexpertsadministrator;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Customer {
    private SimpleIntegerProperty   CustomerId;
    private SimpleStringProperty    CustFName;
    private SimpleStringProperty    CustLName;
    private SimpleStringProperty    CustAddress;
    private SimpleStringProperty    CustCity;
    private SimpleStringProperty    CustProv;
    private SimpleStringProperty    CustPostal;
    private SimpleStringProperty    CustCountry;
    private SimpleStringProperty    CustHomePhone;
    private SimpleStringProperty    CustBusPhone;
    private SimpleStringProperty    CustEmail;
    private SimpleIntegerProperty   AgentId;

    public int getCustomerId() {
        return CustomerId.get();
    }

    public SimpleIntegerProperty customerIdProperty() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        this.CustomerId.set(customerId);
    }

    public String getCustFName() {
        return CustFName.get();
    }

    public SimpleStringProperty custFNameProperty() {
        return CustFName;
    }

    public void setCustFName(String custFName) {
        this.CustFName.set(custFName);
    }

    public String getCustLName() {
        return CustLName.get();
    }

    public SimpleStringProperty custLNameProperty() {
        return CustLName;
    }

    public void setCustLName(String custLName) {
        this.CustLName.set(custLName);
    }

    public String getCustAddress() {
        return CustAddress.get();
    }

    public SimpleStringProperty custAddressProperty() {
        return CustAddress;
    }

    public void setCustAddress(String custAddress) {
        this.CustAddress.set(custAddress);
    }

    public String getCustCity() {
        return CustCity.get();
    }

    public SimpleStringProperty custCityProperty() {
        return CustCity;
    }

    public void setCustCity(String custCity) {
        this.CustCity.set(custCity);
    }

    public String getCustProv() {
        return CustProv.get();
    }

    public SimpleStringProperty custProvProperty() {
        return CustProv;
    }

    public void setCustProv(String custProv) {
        this.CustProv.set(custProv);
    }

    public String getCustPostal() {
        return CustPostal.get();
    }

    public SimpleStringProperty custPostalProperty() {
        return CustPostal;
    }

    public void setCustPostal(String custPostal) {
        this.CustPostal.set(custPostal);
    }

    public String getCustCountry() {
        return CustCountry.get();
    }

    public SimpleStringProperty custCountryProperty() {
        return CustCountry;
    }

    public void setCustCountry(String custCountry) {
        this.CustCountry.set(custCountry);
    }

    public String getCustHomePhone() {
        return CustHomePhone.get();
    }

    public SimpleStringProperty custHomePhoneProperty() {
        return CustHomePhone;
    }

    public void setCustHomePhone(String custHomePhone) {
        this.CustHomePhone.set(custHomePhone);
    }

    public String getCustBusPhone() {
        return CustBusPhone.get();
    }

    public SimpleStringProperty custBusPhoneProperty() {
        return CustBusPhone;
    }

    public void setCustBusPhone(String custBusPhone) {
        this.CustBusPhone.set(custBusPhone);
    }

    public String getCustEmail() {
        return CustEmail.get();
    }

    public SimpleStringProperty custEmailProperty() {
        return CustEmail;
    }

    public void setCustEmail(String custEmail) {
        this.CustEmail.set(custEmail);
    }

    public int getAgentId() {
        return AgentId.get();
    }

    public SimpleIntegerProperty agentIdProperty() {
        return AgentId;
    }

    public void setAgentId(int agentId) {
        this.AgentId.set(agentId);
    }


    public Customer(int customerId,         String custFName,
                    String custLName,       String custAddress,
                    String custCity,        String custProv,
                    String custPostal,      String custCountry,
                    String custHomePhone,   String custBusPhone,
                    String custEmail,       int agentId)
    {
        this.CustomerId = new SimpleIntegerProperty(customerId);
        CustFName = new SimpleStringProperty(custFName);
        CustLName = new SimpleStringProperty(custLName);
        CustAddress = new SimpleStringProperty(custAddress);
        CustCity = new SimpleStringProperty(custCity);
        CustProv = new SimpleStringProperty(custProv);
        CustPostal = new SimpleStringProperty(custPostal);
        CustCountry = new SimpleStringProperty(custCountry);
        CustHomePhone = new SimpleStringProperty(custHomePhone);
        CustBusPhone = new SimpleStringProperty(custBusPhone);
        CustEmail = new SimpleStringProperty(custEmail);
        AgentId = new SimpleIntegerProperty(agentId);
    }
}//Class
