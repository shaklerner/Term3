package com.example.travelexpertsadministrator;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.regex.Pattern;

public class AddEditAgentController {

    public enum Mode {
        ADD, EDIT
    }

    @FXML
    private TextField tfFirstName, tfMiddleInit, tfLastName, tfPhone, tfEmail, tfPosition;
    @FXML
    private ComboBox<Integer> cbAgencyId;

    private Mode currentMode;
    private Agent currentAgent;

    public void setMainController(MainController mainController) {
        // Intentionally left empty if not used elsewhere.
    }

    public void setModeAndData(Mode mode, Agent agent) {
        this.currentMode = mode;
        this.currentAgent = agent;
        cbAgencyId.setItems(FXCollections.observableArrayList(AgentDAO.fetchAgencyIds()));

        if (mode == Mode.EDIT && agent != null) {
            populateFields(agent);
            cbAgencyId.getSelectionModel().select(agent.getAgencyId());
        }
    }

    private void populateFields(Agent agent) {
        tfFirstName.setText(agent.getAgtFName());
        tfMiddleInit.setText(agent.getAgtMidInit());
        tfLastName.setText(agent.getAgtLName());
        tfPhone.setText(agent.getAgtPhone());
        tfEmail.setText(agent.getAgtEmail());
        tfPosition.setText(agent.getAgtPosition());
    }

    @FXML
    private void btnAddAgentClicked() {
        if (!validateInput()) return;

        if (showConfirmation("Confirmation", "Are you sure you want to add/update this agent?")) {
            Agent agentToSave = (currentMode == Mode.EDIT && currentAgent != null) ? currentAgent : new Agent(-1, "", "", "", "", "", "", -1);
            populateAgentFromFields(agentToSave);
            if (currentMode == Mode.ADD) AgentDAO.insertAgent(agentToSave);
            else AgentDAO.updateAgent(agentToSave);
            closeWindow();
        }
    }

    @FXML
    private void btnCancelClicked() {
        if (showConfirmation("Confirmation", "Are you sure you want to cancel?")) closeWindow();
    }

    private void populateAgentFromFields(Agent agent) {
        agent.setAgtFName(tfFirstName.getText());
        agent.setAgtMidInit(tfMiddleInit.getText());
        agent.setAgtLName(tfLastName.getText());
        agent.setAgtPhone(tfPhone.getText());
        agent.setAgtEmail(tfEmail.getText());
        agent.setAgtPosition(tfPosition.getText());
        agent.setAgencyId(cbAgencyId.getSelectionModel().getSelectedItem());
    }

    private boolean validateInput() {
        return !(isLongerThan(tfFirstName, 20, "First Name cannot be longer than 20 characters") ||
                isLongerThan(tfMiddleInit, 1, "Middle Initial cannot be longer than 1 character") ||
                isLongerThan(tfLastName, 20, "Last Name cannot be longer than 20 characters") ||
                !isValidEmail(tfEmail.getText(), "Email is not valid. Please enter a correct email address.") ||
                isLongerThan(tfPosition, 50, "Position cannot be longer than 50 characters"));
    }

    private boolean isLongerThan(TextField tf, int length, String message) {
        if (tf.getText().length() > length) {
            showAlert("Error", message);
            return true;
        }
        return false;
    }

    private boolean isValidEmail(String email, String errorMessage) {
        if (!Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$").matcher(email).matches()) {
            showAlert("Error", errorMessage);
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

    private boolean showConfirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
        alert.setTitle(title);
        alert.setHeaderText(null);
        return alert.showAndWait().get() == ButtonType.YES;
    }

    private void closeWindow() {
        ((Stage) tfFirstName.getScene().getWindow()).close();
    }
}
