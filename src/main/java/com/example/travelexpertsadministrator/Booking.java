package com.example.travelexpertsadministrator;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Booking {
    private SimpleIntegerProperty bookingId;
    private SimpleStringProperty bookingDate;
    private SimpleStringProperty bookingNo;
    private SimpleIntegerProperty travelerCounter;
    private SimpleIntegerProperty customerId;

    private SimpleStringProperty tripTypeId;
    private SimpleIntegerProperty packageId;

    public int getBookingId() {
        return bookingId.get();
    }

    public SimpleIntegerProperty bookingIdProperty() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId.set(bookingId);
    }

    public String getBookingDate() {
        return bookingDate.get();
    }

    public SimpleStringProperty bookingDateProperty() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate.set(bookingDate);
    }

    public String getBookingNo() {
        return bookingNo.get();
    }

    public SimpleStringProperty bookingNoProperty() {
        return bookingNo;
    }

    public void setBookingNo(String bookingNo) {
        this.bookingNo.set(bookingNo);
    }

    public int getTravelerCounter() {
        return travelerCounter.get();
    }

    public SimpleIntegerProperty travelerCounterProperty() {
        return travelerCounter;
    }

    public void setTravelerCounter(int travelerCounter) {
        this.travelerCounter.set(travelerCounter);
    }

    public int getCustomerId() {
        return customerId.get();
    }

    public SimpleIntegerProperty customerIdProperty() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId.set(customerId);
    }

    public String getTripTypeId() {
        return tripTypeId.get();
    }

    public SimpleStringProperty tripTypeIdProperty() {
        return tripTypeId;
    }

    public void setTripTypeId(String tripTypeId) {
        this.tripTypeId.set(tripTypeId);
    }

    public int getPackageId() {
        return packageId.get();
    }

    public SimpleIntegerProperty packageIdProperty() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId.set(packageId);
    }


    public Booking(int bookingId, String bookingDate,
                   String bookingNo, int travelerCounter,
                   int customerId, String tripTypeId,
                   int packageId) {
        this.bookingId = new SimpleIntegerProperty(bookingId);
        this.bookingDate = new SimpleStringProperty(bookingDate);
        this.bookingNo = new SimpleStringProperty(bookingNo);
        this.travelerCounter = new SimpleIntegerProperty(travelerCounter);
        this.customerId = new SimpleIntegerProperty(customerId);
        this.tripTypeId = new SimpleStringProperty(tripTypeId);
        this.packageId = new SimpleIntegerProperty(packageId);
    }
}
