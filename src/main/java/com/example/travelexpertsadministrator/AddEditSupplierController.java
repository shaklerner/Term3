/**
 * Sample Skeleton for 'addEdit_supplier_view.fxml' Controller Class
 */

package com.example.travelexpertsadministrator;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AddEditSupplierController {

    public enum Mode {
        ADD, EDIT
    }

    private AddEditSupplierController.Mode currentMode;
    private Supplier currentSupplier;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAdd"
    private Button btnAdd; // Value injected by FXMLLoader

    @FXML // fx:id="btnCancel"
    private Button btnCancel; // Value injected by FXMLLoader

    @FXML // fx:id="tfSupId"
    private TextField tfSupId; // Value injected by FXMLLoader

    @FXML // fx:id="tfSupName"
    private TextField tfSupName; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'addEdit_supplier_view.fxml'.";
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'addEdit_supplier_view.fxml'.";
        assert tfSupId != null : "fx:id=\"tfSupId\" was not injected: check your FXML file 'addEdit_supplier_view.fxml'.";
        assert tfSupName != null : "fx:id=\"tfSupName\" was not injected: check your FXML file 'addEdit_supplier_view.fxml'.";

        tfSupId.setDisable(true);
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

    }

    public void setMainController(MainController mainController) {
        // Intentionally left empty if not used elsewhere.
    }

    public void setModeAndData(AddEditSupplierController.Mode mode, Supplier supplier) {
        this.currentMode = mode;
        this.currentSupplier = supplier;

        if (mode == AddEditSupplierController.Mode.EDIT && supplier != null) {
            btnAdd.setText("Edit");
            populateFields(supplier);
        }
    }

    private void populateFields(Supplier supplier) {
        tfSupId.setText(supplier.getSupplierId()+"");
        tfSupName.setText(supplier.getSupName());

    }



    private void btnCancelClicked() {
        closeWindow();
    }

    private void btnAddClicked() {
        if (!validateInput()) return;//complete validate later

        if (currentSupplier == null) {
            Supplier newSupplier = new Supplier(-1, tfSupName.getText());
            if (SupplierDAO.insertSupplier(newSupplier)) {
                showAlert("Success", "Supplier added successfully.");
                closeWindow();
            } else {
                showAlert("Error", "Could not add the supplier. Try again later.");
            }
        } else {
            currentSupplier.setSupName(tfSupName.getText());

            if (SupplierDAO.updateSupplier(currentSupplier)) {
                showAlert("Success", "Supplier updated successfully.");
                closeWindow();
            } else {
                showAlert("Error", "Could not update the supplier. Try again later.");
            }
        }
    }

    private boolean validateInput() {
        if (tfSupName.getText().isEmpty()) {
            showAlert("Error", "Supplier name cannot be empty.");
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
        ((Stage) tfSupName.getScene().getWindow()).close();
    }


}
