package com.example.travelexpertsadministrator;

import javafx.beans.property.SimpleIntegerProperty;

public class Package_Product_Supplier {
    private SimpleIntegerProperty ppsPackageId;
    private SimpleIntegerProperty ppsProductSupplierId;
    private SimpleIntegerProperty ppsProductSupplierIdOld;

    public int getPpsPackageId() {
        return ppsPackageId.get();
    }

    public SimpleIntegerProperty ppsPackageIdProperty() {
        return ppsPackageId;
    }

    public void setPpsPackageId(int ppsPackageId) {
        this.ppsPackageId.set(ppsPackageId);
    }

    public int getPpsProductSupplierId() {
        return ppsProductSupplierId.get();
    }

    public SimpleIntegerProperty ppsProductSupplierIdProperty() {
        return ppsProductSupplierId;
    }

    public void setPpsProductSupplierId(int ppsProductSupplierId) {
        this.ppsProductSupplierId.set(ppsProductSupplierId);
    }

    public int getPpsProductSupplierIdOld() {
        return ppsProductSupplierIdOld.get();
    }

    public SimpleIntegerProperty ppsProductSupplierIdOldProperty() {
        return ppsProductSupplierIdOld;
    }

    public void setPpsProductSupplierIdOld(int ppsProductSupplierIdOld) {
        this.ppsProductSupplierIdOld.set(ppsProductSupplierIdOld);
    }

    public Package_Product_Supplier(int ppsPackageId, int ppsProductSupplierId, int ppsProductSupplierOld) {
        this.ppsPackageId = new SimpleIntegerProperty(ppsPackageId);
        this.ppsProductSupplierId = new SimpleIntegerProperty(ppsProductSupplierId);
        this.ppsProductSupplierIdOld=new SimpleIntegerProperty(ppsProductSupplierOld);
    }


}//Class
