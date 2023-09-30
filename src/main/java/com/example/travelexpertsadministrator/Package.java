package com.example.travelexpertsadministrator;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Package {
    private SimpleIntegerProperty PackageId;
    private SimpleStringProperty PkgName;
    private SimpleStringProperty PkgStartDate;
    private SimpleStringProperty PkgEndDate;
    private SimpleStringProperty PkgDesc;
    private SimpleDoubleProperty PkgBasePrice;
    private SimpleDoubleProperty PkgAgencyCommission;

    public int getPackageId() {
        return PackageId.get();
    }

    public SimpleIntegerProperty packageIdProperty() {
        return PackageId;
    }

    public void setPackageId(int packageId) {
        this.PackageId.set(packageId);
    }

    public String getPkgName() {
        return PkgName.get();
    }

    public SimpleStringProperty pkgNameProperty() {
        return PkgName;
    }

    public void setPkgName(String pkgName) {
        this.PkgName.set(pkgName);
    }

    public String getPkgStartDate() {
        return PkgStartDate.get();
    }

    public SimpleStringProperty pkgStartDateProperty() {
        return PkgStartDate;
    }

    public void setPkgStartDate(String pkgStartDate) {
        this.PkgStartDate.set(pkgStartDate);
    }

    public String getPkgEndDate() {
        return PkgEndDate.get();
    }

    public SimpleStringProperty pkgEndDateProperty() {
        return PkgEndDate;
    }

    public void setPkgEndDate(String pkgEndDate) {
        this.PkgEndDate.set(pkgEndDate);
    }

    public String getPkgDesc() {
        return PkgDesc.get();
    }

    public SimpleStringProperty pkgDescProperty() {
        return PkgDesc;
    }

    public void setPkgDesc(String pkgDesc) {
        this.PkgDesc.set(pkgDesc);
    }

    public double getPkgBasePrice() {
        return PkgBasePrice.get();
    }

    public SimpleDoubleProperty pkgBasePriceProperty() {
        return PkgBasePrice;
    }

    public void setPkgBasePrice(double pkgBasePrice) {
        this.PkgBasePrice.set(pkgBasePrice);
    }

    public double getPkgAgencyCommission() {
        return PkgAgencyCommission.get();
    }

    public SimpleDoubleProperty pkgAgencyCommissionProperty() {
        return PkgAgencyCommission;
    }

    public void setPkgAgencyCommission(double pkgAgencyCommission) {
        this.PkgAgencyCommission.set(pkgAgencyCommission);
    }


    public Package(int packageId, String pkgName,
                   String pkgStartDate, String pkgEndDate,
                   String pkgDesc, Double pkgBasePrice,
                   double pkgAgencyCommission) {
        this.PackageId = new SimpleIntegerProperty(packageId);
        this.PkgName = new SimpleStringProperty(pkgName);
        this.PkgStartDate = new SimpleStringProperty(pkgStartDate);
        this.PkgEndDate = new SimpleStringProperty(pkgEndDate);
        this.PkgDesc = new SimpleStringProperty(pkgDesc);
        this.PkgBasePrice = new SimpleDoubleProperty(pkgBasePrice);
        this.PkgAgencyCommission = new SimpleDoubleProperty(pkgAgencyCommission);
    }



}//Class
