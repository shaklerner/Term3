package com.example.travelexpertsadministrator;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddEditBookingController {

    public enum Mode {
        ADD, EDIT
    }

    @FXML
    private DatePicker dpBookingDate;
    @FXML
    private TextField tfBookingNo, tfTravelerCounter;
    @FXML
    private ComboBox<Integer> cbCustomerId, cbPackageId;
    @FXML
    private ComboBox<String> cbTripTypeId;

    private Mode currentMode;
    private Booking currentBooking;

    public void setMainController(MainController mainController) {
        // Intentionally left empty if not used elsewhere.
    }

    public void setModeAndData(Mode mode, Booking booking) {
        this.currentMode = mode;
        this.currentBooking = booking;

        // Populate ComboBoxes
        cbCustomerId.setItems(FXCollections.observableArrayList(BookingDAO.fetchCustomerIds()));
        cbTripTypeId.setItems(FXCollections.observableArrayList(BookingDAO.fetchTripTypeIds()));
        cbPackageId.setItems(FXCollections.observableArrayList(BookingDAO.fetchPackageIds()));

        if (mode == Mode.EDIT && booking != null) {
            populateFields(booking);
        }
    }

    private void populateFields(Booking booking) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        dpBookingDate.setValue(LocalDate.parse(booking.getBookingDate(), formatter));
        tfBookingNo.setText(booking.getBookingNo());
        tfTravelerCounter.setText(String.valueOf(booking.getTravelerCounter()));
        cbCustomerId.getSelectionModel().select(booking.getCustomerId());
        cbTripTypeId.getSelectionModel().select(booking.getTripTypeId());
        cbPackageId.getSelectionModel().select(booking.getPackageId());
    }

    @FXML
    private void btnAddEditBookingClicked() {
        if (!validateInput()) return;

        if (showConfirmation("Confirmation", "Are you sure you want to add/update this booking?")) {
            Booking bookingToSave = (currentMode == Mode.EDIT && currentBooking != null) ? currentBooking : new Booking(-1,"","",-1,-1,"",-1);
            populateBookingFromFields(bookingToSave);
            if (currentMode == Mode.ADD) BookingDAO.insertBooking(bookingToSave);
            else BookingDAO.updateBooking(bookingToSave);
            closeWindow();
        }
    }

    @FXML
    private void btnCancelClicked() {
        if (showConfirmation("Confirmation", "Are you sure you want to cancel?")) closeWindow();
    }

    private void populateBookingFromFields(Booking booking) {

        booking.setBookingDate(dpBookingDate.getValue()+"");
        booking.setBookingNo(tfBookingNo.getText());
        booking.setTravelerCounter(Integer.parseInt(tfTravelerCounter.getText()));
        booking.setCustomerId(cbCustomerId.getSelectionModel().getSelectedItem());
        booking.setTripTypeId(cbTripTypeId.getSelectionModel().getSelectedItem());
        booking.setPackageId(cbPackageId.getSelectionModel().getSelectedItem());
    }

    private boolean validateInput() {
        // Check Booking Number
        if (ValidationUtils.isEmptyOrNull(tfBookingNo.getText())) {
            showAlert("Validation Error", "Booking Number cannot be empty!");
            return false;
        }

        // Check Booking Date
        if (dpBookingDate.getValue() == null) {
            showAlert("Validation Error", "Booking Date cannot be empty!");
            return false;
        }

        // Check Traveler Counter
        if (ValidationUtils.isEmptyOrNull(tfTravelerCounter.getText())) {
            showAlert("Validation Error", "Traveler Counter cannot be empty!");
            return false;
        }

        // Note: Assuming that Traveler Counter is a number. You might want to add additional validation for this.

        // Check Customer ID
        if (cbCustomerId.getSelectionModel().getSelectedItem() == null) {
            showAlert("Validation Error", "Customer ID cannot be empty!");
            return false;
        }

        // Check Trip Type ID
        if (cbTripTypeId.getSelectionModel().getSelectedItem() == null) {
            showAlert("Validation Error", "Trip Type ID cannot be empty!");
            return false;
        }

        // Check Package ID
        if (cbPackageId.getSelectionModel().getSelectedItem() == null) {
            showAlert("Validation Error", "Package ID cannot be empty!");
            return false;
        }

        // You can add additional validation as necessary for other fields or specific data requirements.

        return true;
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
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

    private void closeWindow() {
        ((Stage) dpBookingDate.getScene().getWindow()).close();
    }
}
