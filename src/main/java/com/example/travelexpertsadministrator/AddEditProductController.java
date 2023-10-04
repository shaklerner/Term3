package com.example.travelexpertsadministrator;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class AddEditProductController {

    @FXML
    private TextField tfProdName;

    private Product currentProduct;

    public void setProduct(Product product) {
        this.currentProduct = product;
        if (product != null) {
            tfProdName.setText(product.getProductName());
        }
    }

    @FXML
    private void handleSaveButtonClick() {
        if (!validateInput()) return;

        if (currentProduct == null) {
            Product newProduct = new Product(-1, tfProdName.getText());
            if (ProductDAO.insertProduct(newProduct)) {
                showAlert("Success", "Product added successfully.");
                closeWindow();
            } else {
                showAlert("Error", "Could not add the product. Try again later.");
            }
        } else {
            currentProduct.setProductName(tfProdName.getText());
            if (ProductDAO.updateProduct(currentProduct)) {
                showAlert("Success", "Product updated successfully.");
                closeWindow();
            } else {
                showAlert("Error", "Could not update the product. Try again later.");
            }
        }
    }

    @FXML
    private void handleCancelButtonClick() {
        closeWindow();
    }

    private boolean validateInput() {
        if (tfProdName.getText().isEmpty()) {
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
        ((Stage) tfProdName.getScene().getWindow()).close();
    }
}
