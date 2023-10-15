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
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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

    private Product_Supplier currentProductSupplier;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'addEdit_product_supplier_view.fxml'.";
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'addEdit_product_supplier_view.fxml'.";
        assert dbPrdName != null : "fx:id=\"dbPrdName\" was not injected: check your FXML file 'addEdit_product_supplier_view.fxml'.";
        assert dbSupName != null : "fx:id=\"dbSupName\" was not injected: check your FXML file 'addEdit_product_supplier_view.fxml'.";
        assert tfPrdSupId != null : "fx:id=\"tfPrdSupId\" was not injected: check your FXML file 'addEdit_product_supplier_view.fxml'.";
        tfPrdSupId.setDisable(true);
/*After SupplierDAO need to release this part
        // Define data structures for mapping names to IDs
        Map<String, Integer> productMap = new HashMap<>();
        Map<String, Integer> supplierMap = new HashMap<>();

// Fetch and populate product and supplier ComboBox controls and their mappings
        private void populateProductAndSupplierComboBoxes() {
            // Fetch product and supplier names from the database
            List<String> productNames = ProductDAO.fetchProductNamesFromDatabase();
            List<String> supplierNames = fetchSupplierNamesFromDatabase();

            // Populate ComboBox controls
            dbPrdName.setItems(FXCollections.observableArrayList(productNames));
            dbSupName.setItems(FXCollections.observableArrayList(supplierNames));

            // Create mappings from names to IDs
            for (String productName : productNames) {
                int productId = ProductDAO.fetchProductIdFromDatabase(productName);
                productMap.put(productName, productId);
            }

            for (String supplierName : supplierNames) {
                int supplierId = fetchSupplierIdFromDatabase(supplierName);
                supplierMap.put(supplierName, supplierId);
            }
        }

*/
    }





}
