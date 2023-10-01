/**
 * Sample Skeleton for 'main-view.fxml' Controller Class
 */

package com.example.travelexpertsadministrator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import java.sql.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
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

    //---BOOKINGS TABLE
    @FXML // fx:id="bkDate"
    private TableColumn<Booking, String> bkDate; // Value injected by FXMLLoader

    @FXML // fx:id="bkId"
    private TableColumn<Booking, Integer> bkId; // Value injected by FXMLLoader

    @FXML // fx:id="bkNo"
    private TableColumn<Booking, Integer> bkNo; // Value injected by FXMLLoader

    @FXML // fx:id="custId"
    private TableColumn<Booking, Integer> custId; // Value injected by FXMLLoader

    @FXML // fx:id="pkgId"
    private TableColumn<Booking, Integer> pkgId; // Value injected by FXMLLoader

    @FXML // fx:id="travelNo"
    private TableColumn<Booking, Integer> travelNo; // Value injected by FXMLLoader

    @FXML // fx:id="trptypId"
    private TableColumn<Booking, String> trptypId; // Value injected by FXMLLoader


    //---CUSTOMERS TABLE
    @FXML
    private TableColumn<Customer, String> custAddress;

    @FXML
    private TableColumn<Customer, Integer> custAgtId;

    @FXML
    private TableColumn<Customer, String> custBPhone;

    @FXML
    private TableColumn<Customer, String> custCity;

    @FXML
    private TableColumn<Customer, String> custCountry;

    @FXML
    private TableColumn<Customer, String> custEmail;

    @FXML
    private TableColumn<Customer, String> custFName;

    @FXML
    private TableColumn<Customer, String> custHPhone;

    @FXML
    private TableColumn<Customer, Integer> customerId;

    @FXML
    private TableColumn<Customer, String> custLName;

    @FXML
    private TableColumn<Customer, String> custPost;

    @FXML
    private TableColumn<Customer, String> custProv;
    @FXML
    private TableColumn<Package, Double> pkgAgncCom;

    @FXML
    private TableColumn<Package, Double> pkgBasePrice;

    @FXML
    private TableColumn<Package, String> pkgPkgDesc;

    @FXML
    private TableColumn<Package, String> pkgPkgEndDate;

    @FXML
    private TableColumn<Package, Integer> pkgPkgId;

    @FXML
    private TableColumn<Package, String> pkgPkgName;

    @FXML
    private TableColumn<Package, String> pkgPkgStartDate;

    @FXML
    private TableColumn<Product, Integer> prodId;

    @FXML
    private TableColumn<Product, String> prodName;


    @FXML
    private Tab tbPackages;

    @FXML // fx:id="tbBookings"
    private Tab tbBookings; // Value injected by FXMLLoader

    @FXML // fx:id="tbAbout"
    private Tab tbAbout; // Value injected by FXMLLoader

    @FXML // fx:id="tbAgent"
    private Tab tbAgent; // Value injected by FXMLLoader

    @FXML
    private Tab tbCustomers;

    @FXML // fx:id="tpMain"
    private TabPane tpMain; // Value injected by FXMLLoader

    @FXML
    private Tab tbProducts;

    @FXML
    private TableView<Product> tvProducts;

    @FXML
    private TableView<Package> tvPackages;

    @FXML // fx:id="tvAgents"
    private TableView<Agent> tvAgents; // Value injected by FXMLLoader

    @FXML // fx:id="tvBookings"
    private TableView<Booking> tvBookings; // Value injected by FXMLLoader

    @FXML
    private TableView<Customer> tvCustomers;



    @FXML
    private Label lbConnection; // Reference to the ToggleButton


    private String mode; // add|edit

    private String sqlString="";
    private final ScheduledExecutorService connectionCheckScheduler = Executors.newSingleThreadScheduledExecutor();


    ObservableList<Agent> data= FXCollections.observableArrayList();
    ObservableList<Booking> dataBooking = FXCollections.observableArrayList();
    ObservableList<Customer> dataCustomer = FXCollections.observableArrayList();
    ObservableList<Package> dataPackage = FXCollections.observableArrayList();

    ObservableList<Product> dataProduct = FXCollections.observableArrayList();
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

        // Schedule the connection check task to run every 5 seconds (adjust as needed)
        connectionCheckScheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                updateConnectionStatus();
            }
        }, 0, 5, TimeUnit.SECONDS);

        assert agcId != null : "fx:id=\"agcId\" was not injected: check your FXML file 'main-view.fxml'.";
        assert agtEmail != null : "fx:id=\"agtEmail\" was not injected: check your FXML file 'main-view.fxml'.";
        assert agtFName != null : "fx:id=\"agtFName\" was not injected: check your FXML file 'main-view.fxml'.";
        assert agtId != null : "fx:id=\"agtId\" was not injected: check your FXML file 'main-view.fxml'.";
        assert agtLName != null : "fx:id=\"agtLName\" was not injected: check your FXML file 'main-view.fxml'.";
        assert agtMidInit != null : "fx:id=\"agtMidInit\" was not injected: check your FXML file 'main-view.fxml'.";
        assert agtPhone != null : "fx:id=\"agtPhone\" was not injected: check your FXML file 'main-view.fxml'.";
        assert agtPosition != null : "fx:id=\"agtPosition\" was not injected: check your FXML file 'main-view.fxml'.";
        assert tbAbout != null : "fx:id=\"tbAbout\" was not injected: check your FXML file 'main-view.fxml'.";
        assert tbAgent != null : "fx:id=\"tbAgent\" was not injected: check your FXML file 'main-view.fxml'.";
        assert tpMain != null : "fx:id=\"tpMain\" was not injected: check your FXML file 'main-view.fxml'.";
        assert tvAgents != null : "fx:id=\"tvAgents\" was not injected: check your FXML file 'main-view.fxml'.";
//AGENTS
        agtId.setCellValueFactory(new PropertyValueFactory<Agent,Integer>("agentId"));
        agtFName.setCellValueFactory(new PropertyValueFactory<Agent,String>("agtFName"));
        agtMidInit.setCellValueFactory(new PropertyValueFactory<Agent,String>("agtMidInit"));
        agtLName.setCellValueFactory(new PropertyValueFactory<Agent,String>("agtLName"));
        agtPhone.setCellValueFactory(new PropertyValueFactory<Agent,String>("agtPhone"));
        agtEmail.setCellValueFactory(new PropertyValueFactory<Agent,String>("agtEmail"));
        agtPosition.setCellValueFactory(new PropertyValueFactory<Agent,String>("agtPosition"));
        agcId.setCellValueFactory(new PropertyValueFactory<Agent,Integer>("agencyId"));
//BOOKINGS
        bkDate.setCellValueFactory(new PropertyValueFactory<Booking,String>("bookingDate"));
        bkId.setCellValueFactory(new PropertyValueFactory<Booking,Integer>("bookingId"));
        bkNo.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("bookingNo"));
        travelNo.setCellValueFactory(new PropertyValueFactory<Booking,Integer>("travelerCounter"));
        custId.setCellValueFactory(new PropertyValueFactory<Booking,Integer>("customerId"));
        trptypId.setCellValueFactory(new PropertyValueFactory<Booking,String>("tripTypeId"));
        pkgId.setCellValueFactory(new PropertyValueFactory<Booking,Integer>("PackageId"));
//CUSTOMERS
        customerId.setCellValueFactory(new PropertyValueFactory<Customer,Integer>("CustomerId"));
        custFName.setCellValueFactory(new PropertyValueFactory<Customer,String>("CustFName"));
        custLName.setCellValueFactory(new PropertyValueFactory<Customer,String>("CustLName"));
        custAddress.setCellValueFactory(new PropertyValueFactory<Customer,String>("CustAddress"));
        custCity.setCellValueFactory(new PropertyValueFactory<Customer,String>("CustCity"));
        custProv.setCellValueFactory(new PropertyValueFactory<Customer,String>("CustProv"));
        custPost.setCellValueFactory(new PropertyValueFactory<Customer,String>("CustPostal"));
        custCountry.setCellValueFactory(new PropertyValueFactory<Customer,String>("CustCountry"));
        custHPhone.setCellValueFactory(new PropertyValueFactory<Customer,String>("CustHomePhone"));
        custBPhone.setCellValueFactory(new PropertyValueFactory<Customer,String>("CustBusPhone"));
        custEmail.setCellValueFactory(new PropertyValueFactory<Customer,String>("CustEmail"));
        custAgtId.setCellValueFactory(new PropertyValueFactory<Customer,Integer>("AgentId"));
//PACKAGES
        pkgPkgId.setCellValueFactory(new PropertyValueFactory<Package,Integer>("PackageId"));
        pkgPkgName.setCellValueFactory(new PropertyValueFactory<Package,String>("PkgName"));
        pkgPkgStartDate.setCellValueFactory(new PropertyValueFactory<Package,String>("PkgStartDate"));
        pkgPkgEndDate.setCellValueFactory(new PropertyValueFactory<Package,String>("PkgEndDate"));
        pkgPkgDesc.setCellValueFactory(new PropertyValueFactory<Package,String>("PkgDesc"));
        pkgBasePrice.setCellValueFactory(new PropertyValueFactory<Package,Double>("PkgBasePrice"));
        pkgAgncCom.setCellValueFactory(new PropertyValueFactory<Package,Double>("PkgAgencyCommission"));
//PRODUCTS
        prodId.setCellValueFactory(new PropertyValueFactory<Product,Integer>("productId"));
        prodName.setCellValueFactory(new PropertyValueFactory<Product,String>("productName"));

        tvAgents.setItems(data);
        tvBookings.setItems(dataBooking);
        tvCustomers.setItems(dataCustomer);
        tvPackages.setItems(dataPackage);
        tvProducts.setItems(dataProduct);

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

        setSQL("SELECT * FROM agents");
        tpMain.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observableValue, Tab tab, Tab t1) {


                if (t1==tbAgent) {

                    setSQL("Select * from agents");
                } else if (t1==tbBookings) {

                    setSQL("Select * from bookings");
                } else if (t1==tbCustomers){
                    setSQL("Select * from Customers");
                } else if (t1==tbPackages){
                    setSQL("Select * from Packages");
                }
                else if (t1==tbProducts){
                    setSQL("Select * from Products");
                }


                data.clear();
                dataBooking.clear();
                dataCustomer.clear();
                dataPackage.clear();
                dataProduct.clear();

                String url = "";
                String user = "";
                String pass = "";

                try {
                    FileInputStream fis = new FileInputStream("connection.properties");
                    Properties p = new Properties();
                    p.load(fis);
                    url = (String) p.get("url");
                    user = (String) p.get("user");
                    pass = (String) p.get("password");
                    Connection conn = DriverManager.getConnection(url, user, pass);
                    Statement stmt = conn.createStatement();

                    ResultSet rs = stmt.executeQuery(getSQL());
                    ResultSetMetaData rsmd = rs.getMetaData();


                    while (rs.next()) {

                        if (t1==tbAgent) {
                            data.add(new Agent
                                    (rs.getInt(1), rs.getString(2),
                                            rs.getString(3), rs.getString(4),
                                            rs.getString(5), rs.getString(6),
                                            rs.getString(7), rs.getInt(8)
                                    )
                            );
                        } else if (t1==tbBookings) {
                            dataBooking.add(new Booking
                                    (rs.getInt(1), rs.getString(2),
                                            rs.getString(3), rs.getInt(4),
                                            rs.getInt(5), rs.getString(6),
                                            rs.getInt(7)
                                    )
                            );
                        } else if (t1==tbCustomers) {
                            dataCustomer.add(new Customer
                                    (rs.getInt(1), rs.getString(2),
                                            rs.getString(3), rs.getString(4),
                                            rs.getString(5), rs.getString(6),
                                            rs.getString(7),rs.getString(8),
                                            rs.getString(9),rs.getString(10),
                                            rs.getString(11),rs.getInt(12)
                                    )
                            );
                        } else if (t1==tbPackages) {
                            dataPackage.add(new Package
                                    (rs.getInt(1), rs.getString(2),
                                            rs.getString(3), rs.getString(4),
                                            rs.getString(5), rs.getDouble(6),
                                            rs.getDouble(7)
                                    )
                            );
                        } else if (t1==tbProducts) {
                            dataProduct.add(new Product
                                    (rs.getInt(1), rs.getString(2)
                                    )
                            );
                        }


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
        });




    }//getAgents

    private String getSQL () {
        return this.sqlString;
    }

    private void setSQL (String s){
        this.sqlString = s;
    }
    // Method to check the database connection status
    private boolean isDatabaseConnected() {
        String url = "";
        String user = "";
        String pass = "";

        try (FileInputStream fis = new FileInputStream("connection.properties")) {
            Properties p = new Properties();
            p.load(fis);
            url = p.getProperty("url");
            user = p.getProperty("user");
            pass = p.getProperty("password");

            // Attempt to establish a database connection
            try (Connection conn = DriverManager.getConnection(url, user, pass)) {
                return true;  // Connection successful
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;  // Connection failed
    }

    // Method to update the connection status property
    private void updateConnectionStatus() {
        boolean isConnected = isDatabaseConnected();
        System.out.println("Connection check...");
        if (isConnected) {
            System.out.println("[x] Connected");

            lbConnection.setTextFill(Color.GREEN);
        } else {
            lbConnection.setTextFill(Color.RED);
        }
    }

    // Cleanup method to stop the connection check scheduler when closing the application
    public void stop() {
        connectionCheckScheduler.shutdownNow();
    }



}//Class