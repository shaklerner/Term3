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
        }
    }

    private void populateFields(Agent agent) {
        tfFirstName.setText(agent.getAgtFName());
        tfMiddleInit.setText(agent.getAgtMidInit());
        tfLastName.setText(agent.getAgtLName());
        tfPhone.setText(agent.getAgtPhone());
        tfEmail.setText(agent.getAgtEmail());
        tfPosition.setText(agent.getAgtPosition());
        cbAgencyId.getSelectionModel().select(agent.getAgencyId());
    }

    @FXML
    private void btnAddAgentClicked() {
        if (!validateInput()) return;
        if (cbAgencyId.getSelectionModel().getSelectedItem() == null) {
            showAlert("Error", "Please select an agency.");
            return;
        }
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

        Integer selectedAgencyId = cbAgencyId.getSelectionModel().getSelectedItem();
        if (selectedAgencyId == null) {
            // handle the case, maybe set to a default value or show an error
            agent.setAgencyId(-1);
        } else {
            agent.setAgencyId(selectedAgencyId);
        }
    }

    private boolean validateInput() {
        // Checking for null or empty fields
        if (ValidationUtils.isEmptyOrNull(tfFirstName.getText())) {
            showAlert("Error", "First Name cannot be empty.");
            return false;
        }
        if (ValidationUtils.isEmptyOrNull(tfLastName.getText())) {
            showAlert("Error", "Last Name cannot be empty.");
            return false;
        }
        if (ValidationUtils.exceedsMaxLength(tfFirstName.getText(), 20)) {
            showAlert("Error", "First Name cannot be longer than 20 characters");
            return false;
        }
        if (ValidationUtils.exceedsMaxLength(tfMiddleInit.getText(), 1)) {
            showAlert("Error", "Middle Initial cannot be longer than 1 character");
            return false;
        }
        if (ValidationUtils.exceedsMaxLength(tfLastName.getText(), 20)) {
            showAlert("Error", "Last Name cannot be longer than 20 characters");
            return false;
        }
        if (!ValidationUtils.isValidEmail(tfEmail.getText())) {
            showAlert("Error", "Email is not valid. Please enter a correct email address.");
            return false;
        }
        if (ValidationUtils.exceedsMaxLength(tfPosition.getText(), 50)) {
            showAlert("Error", "Position cannot be longer than 50 characters");
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
