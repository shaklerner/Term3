/**
 * Sample Skeleton for 'Booking-view.fxml' Controller Class
 */

package com.example.travelexpertsadministrator;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
public class AddEditBookingController {

    //initialize Package
    public enum Mode {
        ADD, EDIT
    }

    private AddEditBookingController.Mode currentMode;
    private Booking currentBooking;


        @FXML // ResourceBundle that was given to the FXMLLoader
        private ResourceBundle resources;

        @FXML // URL location of the FXML file that was given to the FXMLLoader
        private URL location;

        @FXML // fx:id="btnAdd"
        private Button btnAdd; // Value injected by FXMLLoader

        @FXML // fx:id="btnCancel"
        private Button btnCancel; // Value injected by FXMLLoader

        @FXML // fx:id="cbCustomerID"
        private ChoiceBox<?> cbCustomerID; // Value injected by FXMLLoader

        @FXML // fx:id="cbPackageId"
        private ComboBox<?> cbPackageId; // Value injected by FXMLLoader

        @FXML // fx:id="cbTavelType"
        private ComboBox<?> cbTavelType; // Value injected by FXMLLoader

        @FXML // fx:id="dpBookingDate"
        private DatePicker dpBookingDate; // Value injected by FXMLLoader

        @FXML // fx:id="tfBookingCount"
        private TextField tfBookingCount; // Value injected by FXMLLoader

        @FXML // fx:id="tfBookingId"
        private TextField tfBookingId; // Value injected by FXMLLoader

        @FXML // fx:id="tfBookingNo"
        private TextField tfBookingNo; // Value injected by FXMLLoader

        @FXML
        void btnAddBookingClicked(ActionEvent event) {

        }

        @FXML
        void btnCancelBookingCilcked(ActionEvent event) {

        }

        @FXML // This method is called by the FXMLLoader when initialization is complete
        void initialize() {
            assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'Booking-view.fxml'.";
            assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'Booking-view.fxml'.";
            assert cbCustomerID != null : "fx:id=\"cbCustomerID\" was not injected: check your FXML file 'Booking-view.fxml'.";
            assert cbPackageId != null : "fx:id=\"cbPackageId\" was not injected: check your FXML file 'Booking-view.fxml'.";
            assert cbTavelType != null : "fx:id=\"cbTavelType\" was not injected: check your FXML file 'Booking-view.fxml'.";
            assert dpBookingDate != null : "fx:id=\"dpBookingDate\" was not injected: check your FXML file 'Booking-view.fxml'.";
            assert tfBookingCount != null : "fx:id=\"tfBookingCount\" was not injected: check your FXML file 'Booking-view.fxml'.";
            assert tfBookingId != null : "fx:id=\"tfBookingId\" was not injected: check your FXML file 'Booking-view.fxml'.";
            assert tfBookingNo != null : "fx:id=\"tfBookingNo\" was not injected: check your FXML file 'Booking-view.fxml'.";

        }

    public void setMainController(MainController mainController) {
        // Intentionally left empty if not used elsewhere.
    }

    public void setModeAndData(AddEditBookingController.Mode mode, Booking booking) {
        this.currentMode = mode;
        this.currentBooking = booking;

        if (mode == AddEditBookingController.Mode.EDIT && booking != null) {
            btnAdd.setText("Edit");
            populateFields(booking);
        }
    }

    private void populateFields(Booking booking) {
        tfBookingId.setEditable(false);
        tfBookingId.setText(booking.getBookingId()+"");

    }


}
