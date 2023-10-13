/**
 * Sample Skeleton for 'addEdit_package_view.fxml' Controller Class
 */

package com.example.travelexpertsadministrator;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AddEditPackageController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnPkgAdd"
    private Button btnPkgAdd; // Value injected by FXMLLoader

    @FXML // fx:id="btnPkgCancel"
    private Button btnPkgCancel; // Value injected by FXMLLoader

    @FXML // fx:id="dpPkgEndDate"
    private DatePicker dpPkgEndDate; // Value injected by FXMLLoader

    @FXML // fx:id="dpPkgStartDate"
    private DatePicker dpPkgStartDate; // Value injected by FXMLLoader

    @FXML // fx:id="taPkgDesc"
    private TextArea taPkgDesc; // Value injected by FXMLLoader

    @FXML // fx:id="tfPkgAgencyCommission"
    private TextField tfPkgAgencyCommission; // Value injected by FXMLLoader

    @FXML // fx:id="tfPkgBasePrice"
    private TextField tfPkgBasePrice; // Value injected by FXMLLoader

    @FXML // fx:id="tfPkgId"
    private TextField tfPkgId; // Value injected by FXMLLoader

    @FXML // fx:id="tfPkgName"
    private TextField tfPkgName; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnPkgAdd != null : "fx:id=\"btnPkgAdd\" was not injected: check your FXML file 'addEdit_package_view.fxml'.";
        assert btnPkgCancel != null : "fx:id=\"btnPkgCancel\" was not injected: check your FXML file 'addEdit_package_view.fxml'.";
        assert dpPkgEndDate != null : "fx:id=\"dpPkgEndDate\" was not injected: check your FXML file 'addEdit_package_view.fxml'.";
        assert dpPkgStartDate != null : "fx:id=\"dpPkgStartDate\" was not injected: check your FXML file 'addEdit_package_view.fxml'.";
        assert taPkgDesc != null : "fx:id=\"taPkgDesc\" was not injected: check your FXML file 'addEdit_package_view.fxml'.";
        assert tfPkgAgencyCommission != null : "fx:id=\"tfPkgAgencyCommission\" was not injected: check your FXML file 'addEdit_package_view.fxml'.";
        assert tfPkgBasePrice != null : "fx:id=\"tfPkgBasePrice\" was not injected: check your FXML file 'addEdit_package_view.fxml'.";
        assert tfPkgId != null : "fx:id=\"tfPkgId\" was not injected: check your FXML file 'addEdit_package_view.fxml'.";
        assert tfPkgName != null : "fx:id=\"tfPkgName\" was not injected: check your FXML file 'addEdit_package_view.fxml'.";
        tfPkgId.setDisable(true);
        btnPkgAdd.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                btnAddClicked();
            }
        });

        btnPkgCancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                btnCancelClicked();
            }
        });


    }



    //initialize Package
    public enum Mode {
        ADD, EDIT
    }
    private Mode currentMode;
    private Package currentPackage;

    public void setMainController(MainController mainController) {
        // Intentionally left empty if not used elsewhere.
    }
    public void setModeAndData(AddEditPackageController.Mode mode, Package mypackage) {
        this.currentMode = mode;
        this.currentPackage = mypackage;

        if (mode == Mode.EDIT && mypackage != null) {
            btnPkgAdd.setText("Edit");
            populateFields(mypackage);
        }
    }

    private void populateFields(Package mypackage) {
            tfPkgId.setText(mypackage.getPackageId()+"");
            tfPkgId.setDisable(true);
            tfPkgName.setText(mypackage.getPkgName());
        // Create a DateTimeFormatter to specify the format of your date string
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00"); // Change the pattern as per your date format
            LocalDate localDate1 = LocalDate.parse((mypackage.getPkgStartDate()), dateFormatter);
            dpPkgStartDate.setValue(localDate1);
            LocalDate localDate2 = LocalDate.parse((mypackage.getPkgEndDate()), dateFormatter);
            dpPkgEndDate.setValue(localDate2);
            tfPkgBasePrice.setText(mypackage.getPkgBasePrice()+"");
            tfPkgAgencyCommission.setText(mypackage.getPkgAgencyCommission()+"");
            taPkgDesc.setText(mypackage.getPkgDesc());

    }


    private void btnAddClicked() {
        if (!true) return;//add validator later

        if (currentPackage == null) {
            Package newPackage = new Package(-1, tfPkgName.getText(),dpPkgStartDate.getValue()+"",dpPkgEndDate.getValue()+"",taPkgDesc.getText(),Double.parseDouble(tfPkgBasePrice.getText()),Double.parseDouble(tfPkgAgencyCommission.getText()));
            if (PackageDAO.insertPackage(newPackage)) {
                showAlert("Success", "Package added successfully.");
                closeWindow();
            } else {
                showAlert("Error", "Could not add the product. Try again later.");
            }
        } else {
            currentPackage.setPkgName(tfPkgName.getText());
            currentPackage.setPkgStartDate(dpPkgStartDate.getValue()+"");
            currentPackage.setPkgEndDate(dpPkgEndDate.getValue()+"");
            currentPackage.setPkgDesc(taPkgDesc.getText());
            currentPackage.setPkgBasePrice(Double.parseDouble(tfPkgBasePrice.getText()));
            currentPackage.setPkgAgencyCommission(Double.parseDouble(tfPkgAgencyCommission.getText()));
            if (PackageDAO.updatePackage(currentPackage)) {
                showAlert("Success", "Package updated successfully.");
                closeWindow();
            } else {
                showAlert("Error", "Could not update the package. Try again later.");
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

    private void closeWindow() {
        ((Stage) tfPkgName.getScene().getWindow()).close();
    }


}
