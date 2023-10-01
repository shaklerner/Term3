package com.example.travelexpertsadministrator;

import javafx.beans.property.SimpleIntegerProperty;

public class Product_Supplier {
    private SimpleIntegerProperty productSupplierId;
    private SimpleIntegerProperty productId;
    private SimpleIntegerProperty supplierId;

    public int getProductSupplierId() {
        return productSupplierId.get();
    }

    public SimpleIntegerProperty productSupplierIdProperty() {
        return productSupplierId;
    }

    public void setProductSupplierId(int productSupplierId) {
        this.productSupplierId.set(productSupplierId);
    }

    public int getProductId() {
        return productId.get();
    }

    public SimpleIntegerProperty productIdProperty() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId.set(productId);
    }

    public int getSupplierId() {
        return supplierId.get();
    }

    public SimpleIntegerProperty supplierIdProperty() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId.set(supplierId);
    }

    public Product_Supplier(int productSupplierId,
                            int productId, int supplierId) {
        this.productSupplierId = new SimpleIntegerProperty(productSupplierId);
        this.productId = new SimpleIntegerProperty(productId);
        this.supplierId = new SimpleIntegerProperty(supplierId);
    }
}//Class
