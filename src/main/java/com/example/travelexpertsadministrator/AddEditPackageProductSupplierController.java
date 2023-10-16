/**
 * Sample Skeleton for 'addEdit_package_product_supplier_view.fxml' Controller Class
 */

package com.example.travelexpertsadministrator;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.xml.validation.Validator;

public class AddEditPackageProductSupplierController {

    public enum Mode {
        ADD, EDIT
    }
    private Package_Product_Supplier currentPackageProductSupplier;
    private AddEditPackageProductSupplierController.Mode currentMode;

    private List<Integer> packageId = PackageDAO.fetchAllPackageIdsFromDatabase();
    private List<Integer> productSupplierId = ProductSupplierDAO.fetchAllProductSupplierIdsFromDatabase();

    private int productSupplierOldValue=-1;
    Map<Integer,String > packageMap = new HashMap<>();

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCancel"
    private Button btnCancel; // Value injected by FXMLLoader

    @FXML // fx:id="btnSave"
    private Button btnSave; // Value injected by FXMLLoader

    @FXML // fx:id="chbPackageId"
    private ChoiceBox<Integer> chbPackageId; // Value injected by FXMLLoader

    @FXML // fx:id="chbProductSupplier"
    private ChoiceBox<Integer> chbProductSupplier; // Value injected by FXMLLoader

    @FXML // fx:id="tfPackageName"
    private TextField tfPackageName; // Value injected by FXMLLoader

    @FXML // fx:id="tfProductName"
    private TextField tfProductName; // Value injected by FXMLLoader

    @FXML // fx:id="tfSupplierName"
    private TextField tfSupplierName; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'addEdit_package_product_supplier_view.fxml'.";
        assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'addEdit_package_product_supplier_view.fxml'.";
        assert chbPackageId != null : "fx:id=\"chbPackageId\" was not injected: check your FXML file 'addEdit_package_product_supplier_view.fxml'.";
        assert chbProductSupplier != null : "fx:id=\"chbProductSupplier\" was not injected: check your FXML file 'addEdit_package_product_supplier_view.fxml'.";
        assert tfPackageName != null : "fx:id=\"tfPackageName\" was not injected: check your FXML file 'addEdit_package_product_supplier_view.fxml'.";
        assert tfProductName != null : "fx:id=\"tfProductName\" was not injected: check your FXML file 'addEdit_package_product_supplier_view.fxml'.";
        assert tfSupplierName != null : "fx:id=\"tfSupplierName\" was not injected: check your FXML file 'addEdit_package_product_supplier_view.fxml'.";
        tfSupplierName.setEditable(false);
        tfProductName.setEditable(false);
        tfPackageName.setEditable(false);

        fulfilHashMaps();
        chbPackageId.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                setPackageName();
                setProductSupplierInfo();
            }
        });

        chbProductSupplier.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                setProductSupplierInfo();
            }
        });

        btnSave.setOnMouseClicked(new EventHandler<MouseEvent>() {
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


    }

    private void setProductSupplierInfo() {
        if (!chbProductSupplier.getSelectionModel().isEmpty()) {
            int supId = ProductSupplierDAO.fetchSupplierIdFromProductSupplierId(chbProductSupplier.getValue());
            String supName = SupplierDAO.fetchSupplierNameWithId(supId);
            tfSupplierName.setText(supName);
            tfProductName.setText(ProductDAO.fetchProductNameWithId(ProductSupplierDAO.fetchProductIdFromProductSupplierId(chbProductSupplier.getValue())));
        }
    }

    private void setPackageName() {
        tfPackageName.setText(packageMap.get(chbPackageId.getValue()));
    }

    public void setMainController(MainController mainController) {
        // Intentionally left empty if not used elsewhere.
    }

    public void setModeAndData(AddEditPackageProductSupplierController.Mode mode, Package_Product_Supplier packageProductSupplier) {
        this.currentMode = mode;
        this.currentPackageProductSupplier = packageProductSupplier;

        if (mode == AddEditPackageProductSupplierController.Mode.EDIT && packageProductSupplier != null) {
            productSupplierOldValue=packageProductSupplier.getPpsProductSupplierIdOld();
            populateFields(packageProductSupplier);
            tfPackageName.setText(packageMap.get(packageProductSupplier.getPpsPackageId()));
            chbPackageId.setDisable(true);
            tfProductName.setText(ProductDAO.fetchProductNameWithId(ProductSupplierDAO.fetchProductIdFromProductSupplierId(packageProductSupplier.getPpsProductSupplierId())));
            tfSupplierName.setText(SupplierDAO.fetchSupplierNameWithId(ProductSupplierDAO.fetchSupplierIdFromProductSupplierId(packageProductSupplier.getPpsProductSupplierId())));
       }
        else
        {
            PopulateChooseBoxes();
        }

    }

    private void populateFields(Package_Product_Supplier packageProductSupplier) {
            chbPackageId.setValue(packageProductSupplier.getPpsPackageId());
            chbProductSupplier.setValue(packageProductSupplier.getPpsProductSupplierId());

            PopulateChooseBoxes();
    }


    private void fulfilHashMaps()
    {

        // Create mappings from names to IDs and reverse
        for (int thisPackageId : packageId) {
            String packageName = PackageDAO.fetchPackageNamesFromDatabase(thisPackageId);
            packageMap.put(thisPackageId,packageName);

        }

    }

    private void PopulateChooseBoxes() {
        chbPackageId.setItems(FXCollections.observableArrayList(packageId));
        chbProductSupplier.setItems(FXCollections.observableArrayList(productSupplierId));
    }

    private void btnAddClicked() {
        if (!validateInput()) return;//complete validate later

        if (currentPackageProductSupplier == null) {
            Package_Product_Supplier newPackageProductSupplier = new Package_Product_Supplier(chbPackageId.getValue(), chbProductSupplier.getValue(),-1);
            if (PackageProductSupplierDAO.insertPackageProductSupplier(newPackageProductSupplier)) {
                showAlert("Success", "Package_Product_Supplier added successfully.");
                closeWindow();

            } else {
                showAlert("Error", "Could not add the package_product_supplier. Try again later.");
            }
        } else {
            currentPackageProductSupplier.setPpsPackageId(chbPackageId.getValue());
            currentPackageProductSupplier.setPpsProductSupplierId(chbProductSupplier.getValue());
            currentPackageProductSupplier.setPpsProductSupplierIdOld(productSupplierOldValue);

            if (PackageProductSupplierDAO.updatePachakeProductSupplier(currentPackageProductSupplier)) {
                showAlert("Success", "Package_Product_Supplier updated successfully.");
                closeWindow();
            } else {
                showAlert("Error", "Could not update the package_product_supplier. Try again later.");
            }
        }
    }
    private void btnCancelClicked() {
        closeWindow();
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    private boolean validateInput() {
        if (chbPackageId.getSelectionModel().isEmpty()) {
            showAlert("Error", "Package cannot be empty.");
            return false;
        } else if (chbProductSupplier.getSelectionModel().isEmpty())
        {
            showAlert("Error", "Product_Supplier cannot be empty.");
            return false;
        }
        return true;
    }
    private void closeWindow() {
        ((Stage) tfPackageName.getScene().getWindow()).close();
    }



}
