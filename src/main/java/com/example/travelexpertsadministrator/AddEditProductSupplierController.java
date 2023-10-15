/**
 * Sample Skeleton for 'addEdit_product_supplier_view.fxml' Controller Class
 */

package com.example.travelexpertsadministrator;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AddEditProductSupplierController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAdd"
    private Button btnAdd; // Value injected by FXMLLoader

    @FXML // fx:id="btnCancel"
    private Button btnCancel; // Value injected by FXMLLoader

    @FXML // fx:id="dbPrdName"
    private ComboBox<String> dbPrdName; // Value injected by FXMLLoader

    @FXML // fx:id="dbSupName"
    private ComboBox<String> dbSupName; // Value injected by FXMLLoader

    @FXML // fx:id="tfPrdSupId"
    private TextField tfPrdSupId; // Value injected by FXMLLoader

    public enum Mode {
        ADD, EDIT
    }
    private Product_Supplier currentProductSupplier;
    private AddEditProductSupplierController.Mode currentMode;

    // Fetch product and supplier names from the database
    private List<String> productNames = ProductDAO.fetchProductNamesFromDatabase();
    private List<String> supplierNames = SupplierDAO.fetchSupplierNamesFromDatabase();

    // Define data structures for mapping names to IDs
    Map<String, Integer> productMap = new HashMap<>();
    Map<String, Integer> supplierMap = new HashMap<>();

    Map<Integer,String> prodIDMap=new HashMap<>();
    Map<Integer,String> suppierIDMap=new HashMap<>();

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'addEdit_product_supplier_view.fxml'.";
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'addEdit_product_supplier_view.fxml'.";
        assert dbPrdName != null : "fx:id=\"dbPrdName\" was not injected: check your FXML file 'addEdit_product_supplier_view.fxml'.";
        assert dbSupName != null : "fx:id=\"dbSupName\" was not injected: check your FXML file 'addEdit_product_supplier_view.fxml'.";
        assert tfPrdSupId != null : "fx:id=\"tfPrdSupId\" was not injected: check your FXML file 'addEdit_product_supplier_view.fxml'.";
        tfPrdSupId.setDisable(true);
//After SupplierDAO need to release this part
        btnAdd.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                btnAddClicked();
            }
        });

        btnCancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                btnCancelClicked();
            }
        });

//NEW PARTS
    }

    public void setModeAndData(AddEditProductSupplierController.Mode mode, Product_Supplier productSupplier) {
        this.currentMode = mode;
        this.currentProductSupplier = productSupplier;

        if (mode == AddEditProductSupplierController.Mode.EDIT && productSupplier != null) {
            btnAdd.setText("Edit");
            populateFields(productSupplier);

            tfPrdSupId.setText(productSupplier.getProductSupplierId() + "");
            // Set the selected items in ComboBoxes based on the product and supplier names in currentProductSupplier
            dbPrdName.setValue(prodIDMap.get(productSupplier.getProductId()));
            dbSupName.setValue(suppierIDMap.get(productSupplier.getSupplierId()));

        }
        else
        {
            populateProductAndSupplierComboBoxes();
        }
    }

    private void populateFields(Product_Supplier productSupplier) {
        tfPrdSupId.setText(productSupplier.getProductSupplierId()+"");
        populateProductAndSupplierComboBoxes();
    }

    private void btnCancelClicked() {
            closeWindow();
    }

    private void btnAddClicked() {
        if (!validateInput()) return;//complete validate later

        if (currentProductSupplier == null) {
            Product_Supplier newProductSupplier = new Product_Supplier(-1, productMap.get(dbPrdName.getSelectionModel().getSelectedItem()),supplierMap.get(dbSupName.getSelectionModel().getSelectedItem()));
            if (ProductSupplierDAO.insertProductSupplier(newProductSupplier)) {
                showAlert("Success", "Product_Supplier added successfully.");
                closeWindow();

            } else {
                showAlert("Error", "Could not add the product_supplier. Try again later.");
            }
        } else {
            currentProductSupplier.setSupplierId(productMap.get(dbPrdName.getSelectionModel().getSelectedItem()));
            currentProductSupplier.setProductId(supplierMap.get(dbSupName.getSelectionModel().getSelectedItem()));

            if (ProductSupplierDAO.updateProductSupplier(currentProductSupplier)) {
                showAlert("Success", "Product_Supplier updated successfully.");
                closeWindow();
            } else {
                showAlert("Error", "Could not update the product_supplier. Try again later.");
            }
        }
    }

    private boolean validateInput() {
        if (dbSupName.getSelectionModel().isEmpty()) {
            showAlert("Error", "Supplier name cannot be empty.");
            return false;
        } else if (dbPrdName.getSelectionModel().isEmpty())
        {
            showAlert("Error", "Product name cannot be empty.");
            return false;
        }
        return true;
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
    private void closeWindow() {
        ((Stage) tfPrdSupId.getScene().getWindow()).close();
    }
    public void setMainController(MainController mainController) {
        // Intentionally left empty if not used elsewhere.
    }



    // Fetch and populate product and supplier ComboBox controls and their mappings
    private void populateProductAndSupplierComboBoxes()
    {

        // Populate ComboBox controls
        dbPrdName.setItems(FXCollections.observableArrayList(productNames));
        dbSupName.setItems(FXCollections.observableArrayList(supplierNames));


        // Create mappings from names to IDs and reverse
        for (String productName : productNames) {
            int productId = ProductDAO.fetchProductIdFromDatabase(productName);
            productMap.put(productName, productId);
            prodIDMap.put(productId,productName);
        }
        // Create mappings from names to IDs and reverse
        for (String supplierName : supplierNames) {
            int supplierId = SupplierDAO.fetchSupplierIdFromDatabase(supplierName);
            supplierMap.put(supplierName, supplierId);
            suppierIDMap.put(supplierId,supplierName);
        }
    }



}
