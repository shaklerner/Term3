package com.example.travelexpertsadministrator;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Supplier {
    private SimpleIntegerProperty SupplierId;
    private SimpleStringProperty SupName;

    public int getSupplierId() {
        return SupplierId.get();
    }

    public SimpleIntegerProperty supplierIdProperty() {
        return SupplierId;
    }

    public void setSupplierId(int supplierId) {
        this.SupplierId.set(supplierId);
    }

    public String getSupName() {
        return SupName.get();
    }

    public SimpleStringProperty supNameProperty() {
        return SupName;
    }

    public void setSupName(String supName) {
        this.SupName.set(supName);
    }

    public Supplier(int supplierId, String supName) {
        SupplierId = new SimpleIntegerProperty(supplierId);
        SupName = new SimpleStringProperty(supName);
    }
}
