package com.example.travelexpertsadministrator;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Properties;

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
    @FXML
    private Button btnSendInvoice;

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
        cbCustomerId.setValue(booking.getCustomerId());
        cbTripTypeId.getSelectionModel().select(booking.getTripTypeId());
        cbPackageId.setValue(booking.getPackageId());
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
    public class EmailConfig {
        public static final String HOST = "smtp.gmail.com";
        public static final int PORT = 587;
        public static final String USERNAME = "xiangshuo9988@gmail.com";
        public static final String PASSWORD = "ihax pkfl jafv eqnl";
    }

    private void sendEmail(String to, String subject, String content) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", EmailConfig.HOST);
        properties.put("mail.smtp.port", EmailConfig.PORT);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EmailConfig.USERNAME, EmailConfig.PASSWORD);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(EmailConfig.USERNAME));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(content);

        Transport.send(message);
    }

    @FXML
    public void generateInvoice() {
        Booking currentBooking = this.currentBooking;
        if (currentBooking == null || currentBooking.getCustomerId() == 0) {
            return;
        }
        Customer customer = BookingDAO.getCustomerByBooking(currentBooking);

        if (customer == null || customer.getCustEmail() == null || customer.getCustEmail().isEmpty()) {
            return;
        }
        String invoice = createInvoice(currentBooking, customer);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, invoice);
        alert.setHeaderText("Generated Invoice");
        ButtonType sendButtonType = new ButtonType("Send Invoice");
        alert.getButtonTypes().add(sendButtonType);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == sendButtonType) {
            try {
                sendEmail(customer.getCustEmail(), "Your Invoice", invoice);
                showAlert("Success", "Invoice sent successfully!");
            } catch (MessagingException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to send email. Check the logs.");
            }
        }
    }
    private String createInvoice(Booking booking, Customer customer) {
        StringBuilder invoiceSB = new StringBuilder();

        // Header
        invoiceSB.append("INVOICE\n\n");

        // Customer details
        invoiceSB.append("Customer: ").append(customer.getCustFName()).append(" ").append(customer.getCustLName()).append("\n");
        invoiceSB.append("Address: ").append(customer.getCustAddress()).append(", ").append(customer.getCustCity()).append(", ").append(customer.getCustProv()).append("\n");
        invoiceSB.append("Postal Code: ").append(customer.getCustPostal()).append("\n");
        invoiceSB.append("Country: ").append(customer.getCustCountry()).append("\n");
        invoiceSB.append("Home Phone: ").append(customer.getCustHomePhone()).append("\n");
        invoiceSB.append("Business Phone: ").append(customer.getCustBusPhone()).append("\n\n");

        // Booking details
        invoiceSB.append("Booking ID: ").append(booking.getBookingId()).append("\n");
        invoiceSB.append("Booking Date: ").append(booking.getBookingDate()).append("\n");
        invoiceSB.append("Booking No: ").append(booking.getBookingNo()).append("\n");
        invoiceSB.append("Traveler Count: ").append(booking.getTravelerCounter()).append("\n");
        invoiceSB.append("Trip Type ID: ").append(booking.getTripTypeId()).append("\n");
        invoiceSB.append("Package ID: ").append(booking.getPackageId()).append("\n");

        return invoiceSB.toString();
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
