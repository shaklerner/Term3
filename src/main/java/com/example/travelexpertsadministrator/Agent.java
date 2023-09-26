package com.example.travelexpertsadministrator;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Agent {

    private SimpleIntegerProperty agentId;
    private SimpleStringProperty agtFName;
    private SimpleStringProperty agtMidInit;
    private SimpleStringProperty agtLName;
    private SimpleStringProperty agtPhone;
    private SimpleStringProperty agtEmail;
    private SimpleStringProperty agtPosition;
    private SimpleIntegerProperty agencyId;


    public int getAgentId() {
        return agentId.get();
    }

    public SimpleIntegerProperty agentIdProperty() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId.set(agentId);
    }

    public String getAgtFName() {
        return agtFName.get();
    }

    public SimpleStringProperty agtFNameProperty() {
        return agtFName;
    }

    public void setAgtFName(String agtFName) {
        this.agtFName.set(agtFName);
    }

    public String getAgtMidInit() {
        return agtMidInit.get();
    }

    public SimpleStringProperty agtMidInitProperty() {
        return agtMidInit;
    }

    public void setAgtMidInit(String agtMidInit) {
        this.agtMidInit.set(agtMidInit);
    }

    public String getAgtLName() {
        return agtLName.get();
    }

    public SimpleStringProperty agtLNameProperty() {
        return agtLName;
    }

    public void setAgtLName(String agtLName) {
        this.agtLName.set(agtLName);
    }

    public String getAgtPhone() {
        return agtPhone.get();
    }

    public SimpleStringProperty agtPhoneProperty() {
        return agtPhone;
    }

    public void setAgtPhone(String agtPhone) {
        this.agtPhone.set(agtPhone);
    }

    public String getAgtEmail() {
        return agtEmail.get();
    }

    public SimpleStringProperty agtEmailProperty() {
        return agtEmail;
    }

    public void setAgtEmail(String agtEmail) {
        this.agtEmail.set(agtEmail);
    }

    public String getAgtPosition() {
        return agtPosition.get();
    }

    public SimpleStringProperty agtPositionProperty() {
        return agtPosition;
    }

    public void setAgtPosition(String agtPosition) {
        this.agtPosition.set(agtPosition);
    }

    public int getAgencyId() {
        return agencyId.get();
    }

    public SimpleIntegerProperty agencyIdProperty() {
        return agencyId;
    }

    public void setAgencyId(int agencyId) {
        this.agencyId.set(agencyId);
    }

    public Agent(int agentId, String agtFName, String agtMidInit, String agtLName,
                 String agtPhone, String agtEmail, String agtPosition, int agencyId) {
        this.agentId = new SimpleIntegerProperty(agentId);
        this.agtFName = new SimpleStringProperty(agtFName);
        this.agtMidInit = new SimpleStringProperty(agtMidInit);
        this.agtLName = new SimpleStringProperty(agtLName);
        this.agtPhone = new SimpleStringProperty(agtPhone);
        this.agtEmail = new SimpleStringProperty(agtEmail);
        this.agtPosition = new SimpleStringProperty(agtPosition);
        this.agencyId = new SimpleIntegerProperty(agencyId);
    }


}//Agent Class
