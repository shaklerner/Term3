package com.example.travelexpertsadministrator;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.collections.FXCollections;

public class AddEditCustomerController {

    @FXML
    private TextField tfCustFName, tfCustLName, tfCustAddress, tfCustCity, tfCustProv, tfCustPostal, tfCustCountry, tfCustHomePhone, tfCustBusPhone, tfCustEmail;
    @FXML
    private ComboBox<Integer> cbAgentId;

    private Customer currentCustomer;

    @FXML
    public void initialize() {
        // populate the ComboBox with agent IDs
        cbAgentId.setItems(FXCollections.observableArrayList(CustomerDAO.fetchAgentIds()));
    }

    public void setCustomer(Customer customer) {
        this.currentCustomer = customer;
        if (customer != null) {
            populateFields(customer);
        }
    }

    private void populateFields(Customer customer) {
        tfCustFName.setText(customer.getCustFName());
        tfCustLName.setText(customer.getCustLName());
        tfCustAddress.setText(customer.getCustAddress());
        tfCustCity.setText(customer.getCustCity());
        tfCustProv.setText(customer.getCustProv());
        tfCustPostal.setText(customer.getCustPostal());
        tfCustCountry.setText(customer.getCustCountry());
        tfCustHomePhone.setText(customer.getCustHomePhone());
        tfCustBusPhone.setText(customer.getCustBusPhone());
        tfCustEmail.setText(customer.getCustEmail());
        cbAgentId.setValue(customer.getAgentId());
    }

    @FXML
    private void btnAddCustomerClicked() {
        if(!validateInput()) return;
        if (currentCustomer == null) {
            currentCustomer = new Customer(-1, "", "", "", "", "", "", "", "", "", "", -1);  // Default empty customer
        }

        updateCustomerFromFields(currentCustomer);

        if (CustomerDAO.insertCustomer(currentCustomer)) {
            showAlert("Success", "Customer saved successfully.");
            closeWindow();
        } else {
            showAlert("Error", "There was an error saving the customer.");
        }
    }

    @FXML
    private void btnCancelClicked() {
        if (showConfirmation("Confirmation", "Are you sure you want to cancel?")) {
            closeWindow();
        }
    }

    private void updateCustomerFromFields(Customer customer) {
        customer.setCustFName(tfCustFName.getText());
        customer.setCustLName(tfCustLName.getText());
        customer.setCustAddress(tfCustAddress.getText());
        customer.setCustCity(tfCustCity.getText());
        customer.setCustProv(tfCustProv.getText());
        customer.setCustPostal(tfCustPostal.getText());
        customer.setCustCountry(tfCustCountry.getText());
        customer.setCustHomePhone(tfCustHomePhone.getText());
        customer.setCustBusPhone(tfCustBusPhone.getText());
        customer.setCustEmail(tfCustEmail.getText());
        customer.setAgentId(cbAgentId.getValue());
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    private boolean showConfirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
        alert.setTitle(title);
        alert.setHeaderText(null);
        return alert.showAndWait().get() == ButtonType.YES;
    }
    private boolean validateInput() {

        if (ValidationUtils.isEmptyOrNull(tfCustFName.getText()) ||
                ValidationUtils.exceedsMaxLength(tfCustFName.getText(), 20)) {
            showAlert("Error", "First Name cannot be empty and should not exceed 20 characters.");
            return false;
        }

        if (ValidationUtils.isEmptyOrNull(tfCustLName.getText()) ||
                ValidationUtils.exceedsMaxLength(tfCustLName.getText(), 20)) {
            showAlert("Error", "Last Name cannot be empty and should not exceed 20 characters.");
            return false;
        }

        if (!ValidationUtils.isValidEmail(tfCustEmail.getText())) {
            showAlert("Error", "Invalid email format.");
            return false;
        }

        if (ValidationUtils.isEmptyOrNull(tfCustHomePhone.getText())) {
            showAlert("Error", "Home Phone cannot be empty.");
            return false;
        }

        if (ValidationUtils.isEmptyOrNull(tfCustBusPhone.getText())) {
            showAlert("Error", "Business Phone cannot be empty.");
            return false;
        }


        return true;
    }

    private void closeWindow() {
        ((Stage) tfCustFName.getScene().getWindow()).close();
    }
}
