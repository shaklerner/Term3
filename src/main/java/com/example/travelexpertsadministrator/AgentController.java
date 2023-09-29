/**
 * Sample Skeleton for 'agent-view.fxml' Controller Class
 */

package com.example.travelexpertsadministrator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AgentController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="agcId"
    private TableColumn<Agent, Integer> agcId; // Value injected by FXMLLoader

    @FXML // fx:id="agtEmail"
    private TableColumn<Agent, String> agtEmail; // Value injected by FXMLLoader

    @FXML // fx:id="agtFName"
    private TableColumn<Agent, String> agtFName; // Value injected by FXMLLoader

    @FXML // fx:id="agtId"
    private TableColumn<Agent, Integer> agtId; // Value injected by FXMLLoader

    @FXML // fx:id="agtLName"
    private TableColumn<Agent, String> agtLName; // Value injected by FXMLLoader

    @FXML // fx:id="agtMidInit"
    private TableColumn<Agent, String> agtMidInit; // Value injected by FXMLLoader

    @FXML // fx:id="agtPhone"
    private TableColumn<Agent, String> agtPhone; // Value injected by FXMLLoader

    @FXML // fx:id="agtPosition"
    private TableColumn<Agent, String> agtPosition; // Value injected by FXMLLoader

    @FXML // fx:id="tbAbout"
    private Tab tbAbout; // Value injected by FXMLLoader

    @FXML // fx:id="tbAgent"
    private Tab tbAgent; // Value injected by FXMLLoader

    @FXML // fx:id="tpMain"
    private TabPane tpMain; // Value injected by FXMLLoader

    @FXML // fx:id="tvAgents"
    private TableView<Agent> tvAgents; // Value injected by FXMLLoader

    private String mode; // add|edit

    ObservableList<Agent> data= FXCollections.observableArrayList();
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert agcId != null : "fx:id=\"agcId\" was not injected: check your FXML file 'agent-view.fxml'.";
        assert agtEmail != null : "fx:id=\"agtEmail\" was not injected: check your FXML file 'agent-view.fxml'.";
        assert agtFName != null : "fx:id=\"agtFName\" was not injected: check your FXML file 'agent-view.fxml'.";
        assert agtId != null : "fx:id=\"agtId\" was not injected: check your FXML file 'agent-view.fxml'.";
        assert agtLName != null : "fx:id=\"agtLName\" was not injected: check your FXML file 'agent-view.fxml'.";
        assert agtMidInit != null : "fx:id=\"agtMidInit\" was not injected: check your FXML file 'agent-view.fxml'.";
        assert agtPhone != null : "fx:id=\"agtPhone\" was not injected: check your FXML file 'agent-view.fxml'.";
        assert agtPosition != null : "fx:id=\"agtPosition\" was not injected: check your FXML file 'agent-view.fxml'.";
        assert tbAbout != null : "fx:id=\"tbAbout\" was not injected: check your FXML file 'agent-view.fxml'.";
        assert tbAgent != null : "fx:id=\"tbAgent\" was not injected: check your FXML file 'agent-view.fxml'.";
        assert tpMain != null : "fx:id=\"tpMain\" was not injected: check your FXML file 'agent-view.fxml'.";
        assert tvAgents != null : "fx:id=\"tvAgents\" was not injected: check your FXML file 'agent-view.fxml'.";

        agtId.setCellValueFactory(new PropertyValueFactory<Agent,Integer>("agentId"));
        agtFName.setCellValueFactory(new PropertyValueFactory<Agent,String>("agtFName"));
        agtMidInit.setCellValueFactory(new PropertyValueFactory<Agent,String>("agtMidInit"));
        agtLName.setCellValueFactory(new PropertyValueFactory<Agent,String>("agtLName"));
        agtPhone.setCellValueFactory(new PropertyValueFactory<Agent,String>("agtPhone"));
        agtEmail.setCellValueFactory(new PropertyValueFactory<Agent,String>("agtEmail"));
        agtPosition.setCellValueFactory(new PropertyValueFactory<Agent,String>("agtPosition"));
        agcId.setCellValueFactory(new PropertyValueFactory<Agent,Integer>("agencyId"));

        tvAgents.setItems(data);

        getAgents();


    }
    @FXML
    private void handleAddButtonClick() {
        showModal(null);
    }
    @FXML
    private void handleEditButtonClick() {
        Agent selectedAgent = tvAgents.getSelectionModel().getSelectedItem();
        if (selectedAgent != null) {
            showModal(selectedAgent);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Selection Required");
            alert.setHeaderText(null);
            alert.setContentText("Please select an agent from the table to edit.");
            alert.showAndWait();
        }
    }
    public void refreshTable() {
        getAgents();
    }
    private void showModal(Agent agent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addEdit_agent_view.fxml"));
            Parent root = loader.load();

            AddEditAgentController addEditAgentController = loader.getController();
            addEditAgentController.setMainController(this);

            if (agent == null) {
                addEditAgentController.setModeAndData(AddEditAgentController.Mode.ADD, null);
            } else {
                addEditAgentController.setModeAndData(AddEditAgentController.Mode.EDIT, agent);
            }

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(agent == null ? "Add New Agent" : "Edit Agent");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            refreshTable(); // Refresh the table data after subview closes
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getAgents() {
        data.clear();

        String url="";
        String user="";
        String pass="";

        try {
            FileInputStream fis=new FileInputStream("connection.properties");
            Properties p=new Properties();
            p.load(fis);
            url=(String) p.get("url");
            user=(String) p.get("user");
            pass=(String) p.get("password");
            Connection conn=DriverManager.getConnection(url,user,pass);
            Statement stmt= conn.createStatement();
            ResultSet rs=stmt.executeQuery("Select * from agents");
            ResultSetMetaData rsmd=rs.getMetaData();

            while(rs.next())
            {
                data.add(new Agent
                        (rs.getInt(1),rs.getString(2),
                                rs.getString(3),rs.getString(4),
                                rs.getString(5),rs.getString(6),
                                rs.getString(7),rs.getInt(8)
                        )
                );
            }

            conn.close();


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

}