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
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnEdit;

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
    //--PACKAGES
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

    //--PRODUCTS
    @FXML
    private TableColumn<Product, Integer> prodId;

    @FXML
    private TableColumn<Product, String> prodName;

    //--SUPPLIERS
    @FXML
    private TableColumn<Supplier, Integer> supId;

    @FXML
    private TableColumn<Supplier, String> supName;
    //--PRODUCTS_SUPPLIERS
    @FXML
    private TableColumn<Product_Supplier, Integer> prodSupProdId;

    @FXML
    private TableColumn<Product_Supplier, Integer> prodSupSupId;

    @FXML
    private TableColumn<Product_Supplier, Integer> prodSupplierId;

    //--PACKAGES_PRODUCTS_SUPPLIERS
    @FXML
    private TableColumn<Package_Product_Supplier, Integer> ppsPackageId;

    @FXML
    private TableColumn<Package_Product_Supplier, Integer> ppsProductSupplierId;

    //--TABS
    @FXML
    private Tab tbPackagesProductsSuppliers;

    @FXML
    private Tab tbProductsSuppliers;

    @FXML
    private Tab tbProducts;

    @FXML // fx:id="tbSuppliers"
    private Tab tbSuppliers; // Value injected by FXMLLoader

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

    //--TableViews
    @FXML
    private TableView<Package_Product_Supplier> tvPackagesProductsSuppliers;

    @FXML
    private TableView<Product_Supplier> tvProductsSuppliers;

    @FXML // fx:id="tvSuppliers"
    private TableView<Supplier> tvSuppliers; // Value injected by FXMLLoader

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

    //fulfill Table-Views with Observable Lists from objects
    ObservableList<Agent> data= FXCollections.observableArrayList();
    ObservableList<Booking> dataBooking = FXCollections.observableArrayList();
    ObservableList<Customer> dataCustomer = FXCollections.observableArrayList();
    ObservableList<Package> dataPackage = FXCollections.observableArrayList();
    ObservableList<Product> dataProduct = FXCollections.observableArrayList();
    ObservableList<Supplier> dataSupplier = FXCollections.observableArrayList();
    ObservableList<Product_Supplier> dataProductSupplier = FXCollections.observableArrayList();
    ObservableList<Package_Product_Supplier> dataPackageProductSupplier = FXCollections.observableArrayList();
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
//SUPPLIERS
        supId.setCellValueFactory(new PropertyValueFactory<Supplier,Integer>("SupplierId"));
        supName.setCellValueFactory(new PropertyValueFactory<Supplier,String>("SupName"));
//PRODUCTS_SUPPLIERS
        prodSupplierId.setCellValueFactory(new PropertyValueFactory<Product_Supplier,Integer>("productSupplierId"));
        prodSupProdId.setCellValueFactory(new PropertyValueFactory<Product_Supplier,Integer>("productId"));
        prodSupSupId.setCellValueFactory(new PropertyValueFactory<Product_Supplier,Integer>("supplierId"));
//PACKAGES_PRODUCTS_SUPPLIERS
        ppsPackageId.setCellValueFactory(new PropertyValueFactory<Package_Product_Supplier,Integer>("ppsPackageId"));
        ppsProductSupplierId.setCellValueFactory(new PropertyValueFactory<Package_Product_Supplier,Integer>("ppsProductSupplierId"));
//Assign Observable Lists into the table-views
        tvAgents.setItems(data);
        tvBookings.setItems(dataBooking);
        tvCustomers.setItems(dataCustomer);
        tvPackages.setItems(dataPackage);
        tvProducts.setItems(dataProduct);
        tvSuppliers.setItems(dataSupplier);
        tvProductsSuppliers.setItems(dataProductSupplier);
        tvPackagesProductsSuppliers.setItems(dataPackageProductSupplier);
//Setting-up buttons in About page as disabled
        btnAdd.setDisable(true);
        btnEdit.setDisable(true);
        btnDelete.setDisable(true);
        LoadData();
        tpMain.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observableValue, Tab tab, Tab t1) {LoadData();}});
// ---------------------------------------------------------------------------------------------------------Buttons Functionality---------------------------------------------------------------------------------------------------------

    }
    @FXML
    private void handleAddButtonClick() {
        Tab currentTab = tpMain.getSelectionModel().getSelectedItem();
        if (currentTab == tbAgent) {
            showModalAgent(null);
        } else if (currentTab == tbProducts) {
            showModalProduct(null);
        } else if (currentTab == tbSuppliers) {
            showModalSupplier(null);
        } else if (currentTab == tbProductsSuppliers) {
            showModalProductSupplier(null);
        } else if (currentTab == tbPackagesProductsSuppliers) {
            showModalPackageProductSupplier(null);
        } else if (currentTab == tbCustomers) {
            showModalCustomer(null);
        } else if (currentTab == tbPackages) {
            showModalPackage(null);
        }else if(currentTab == tbBookings){
            showModalBooking(null);
        }
        else {
            System.out.println("Under Construction");
        }
    }
    @FXML
    private void handleEditButtonClick() {
        Tab currentTab = tpMain.getSelectionModel().getSelectedItem();

        if (currentTab == tbAgent) {
            Agent selectedAgent = tvAgents.getSelectionModel().getSelectedItem();
            if (selectedAgent != null) {
                showModalAgent(selectedAgent);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Selection Required");
                alert.setHeaderText(null);
                alert.setContentText("Please select an agent from the table to edit.");
                alert.showAndWait();
            }
        }
        else if (currentTab == tbProducts) {
            Product selectedProduct = tvProducts.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                showModalProduct(selectedProduct);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Selection Required");
                alert.setHeaderText(null);
                alert.setContentText("Please select a product from the table to edit.");
                alert.showAndWait();
            }
        } else if (currentTab == tbBookings) {
            Booking selectedBooking = tvBookings.getSelectionModel().getSelectedItem();
            if (selectedBooking != null) {
                showModalBooking(selectedBooking);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Selection Required");
                alert.setHeaderText(null);
                alert.setContentText("Please select a booking from the table to edit.");
                alert.showAndWait();
            }
        }else if (currentTab == tbSuppliers) {
            Supplier selectedSupplier = tvSuppliers.getSelectionModel().getSelectedItem();
            if (selectedSupplier != null) {
                showModalSupplier(selectedSupplier);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Selection Required");
                alert.setHeaderText(null);
                alert.setContentText("Please select a supplier from the table to edit.");
                alert.showAndWait();
            }
        } else if (currentTab == tbProductsSuppliers) {
            Product_Supplier selectedProductSupplier = tvProductsSuppliers.getSelectionModel().getSelectedItem();
            if (selectedProductSupplier != null) {
                showModalProductSupplier(selectedProductSupplier);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Selection Required");
                alert.setHeaderText(null);
                alert.setContentText("Please select a product_supplier from the table to edit.");
                alert.showAndWait();
            }
        } else if (currentTab == tbPackagesProductsSuppliers) {
            Package_Product_Supplier selectedPackageProductSupplier = tvPackagesProductsSuppliers.getSelectionModel().getSelectedItem();
            if (selectedPackageProductSupplier != null) {
                showModalPackageProductSupplier(selectedPackageProductSupplier);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Selection Required");
                alert.setHeaderText(null);
                alert.setContentText("Please select a package_product_supplier from the table to edit.");
                alert.showAndWait();
            }
        } else if (currentTab == tbCustomers) {
            Customer selectedCustomer = tvCustomers.getSelectionModel().getSelectedItem(); // Assuming you have tvCustomers as TableView<Customer>
            if (selectedCustomer != null) {
                showModalCustomer(selectedCustomer);
            } else {
                showAlert("Selection Required", "Please select a customer from the table to edit.");
            }
        } else if (currentTab == tbPackages) {
            Package selectedPackage = tvPackages.getSelectionModel().getSelectedItem();
            if (selectedPackage != null) {
                showModalPackage(selectedPackage);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Selection Required");
                alert.setHeaderText(null);
                alert.setContentText("Please select a product from the table to edit.");
                alert.showAndWait();
            }
        }
        else {
            System.out.println("Under Construction");
        }
    }
    @FXML
    private void handleDeleteButtonClick() {
        Tab currentTab = tpMain.getSelectionModel().getSelectedItem();
        if (currentTab == tbAgent) {
            Agent selectedAgent = tvAgents.getSelectionModel().getSelectedItem();

            if (selectedAgent == null) {
                showAlert("Selection Required", "Please select an agent to delete.");
                return;
            }
            if (!AgentDAO.canDeleteAgent(selectedAgent.getAgentId())) {
                showAlert("Error", "Cannot delete this agent as there are customers associated with it. Please reassign or remove the customers first.");
                return;
            }
            if (showConfirmation("Delete Confirmation", "Do you really want to delete this agent?")) {
                if (AgentDAO.deleteAgent(selectedAgent)) {  // assuming deleteAgent returns boolean for success/failure
                    tvAgents.getItems().remove(selectedAgent);
                    showAlert("Success", "Agent deleted successfully.");
                } else {
                    showAlert("Failure", "Could not delete the agent. Please try again later.");
                }
            }
        }
        else if (currentTab == tbProducts) {
            Product selectedProduct = tvProducts.getSelectionModel().getSelectedItem();

            if (selectedProduct == null) {
                showAlert("Selection Required", "Please select a product to delete.");
                return;
            }

            if (showConfirmation("Delete Confirmation", "Do you really want to delete this product?")) {
                if (ProductDAO.deleteProduct(selectedProduct)) {
                    tvProducts.getItems().remove(selectedProduct);
                    showAlert("Success", "Product deleted successfully.");
                } else {
                    showAlert("Failure", "Could not delete the product. Please try again later.");
                }
            }
        } else if (currentTab == tbSuppliers) {
            Supplier selectedSupplier = tvSuppliers.getSelectionModel().getSelectedItem();

            if (selectedSupplier == null) {
                showAlert("Selection Required", "Please select a supplier to delete.");
                return;
            }

            if (showConfirmation("Delete Confirmation", "Do you really want to delete this supplier?")) {
                if (SupplierDAO.deleteSupplier(selectedSupplier)) {
                    tvSuppliers.getItems().remove(selectedSupplier);
                    showAlert("Success", "Supplier deleted successfully.");
                } else {
                    showAlert("Failure", "Could not delete the supplier. Please try again later.");
                }
            }
        }  else if (currentTab == tbProductsSuppliers) {
            Product_Supplier selectedProductSupplier = tvProductsSuppliers.getSelectionModel().getSelectedItem();

            if (selectedProductSupplier == null) {
                showAlert("Selection Required", "Please select a product_supplier to delete.");
                return;
            }

            if (showConfirmation("Delete Confirmation", "Do you really want to delete this product_supplier?")) {
                if (ProductSupplierDAO.deleteProductSupplier(selectedProductSupplier)) {
                    tvProductsSuppliers.getItems().remove(selectedProductSupplier);
                    showAlert("Success", "Product_Supplier deleted successfully.");
                } else {
                    showAlert("Failure", "Could not delete the product_supplier. Please try again later.");
                }
            }
        }  else if (currentTab == tbPackagesProductsSuppliers) {
            Package_Product_Supplier selectedPackageProductSupplier = tvPackagesProductsSuppliers.getSelectionModel().getSelectedItem();

            if (selectedPackageProductSupplier == null) {
                showAlert("Selection Required", "Please select a package_product_supplier to delete.");
                return;
            }

            if (showConfirmation("Delete Confirmation", "Do you really want to delete this package_product_supplier?")) {
                if (PackageProductSupplierDAO.deletePackageProductSupplier(selectedPackageProductSupplier)) {
                    tvPackagesProductsSuppliers.getItems().remove(selectedPackageProductSupplier);
                    showAlert("Success", "Package_Product_Supplier deleted successfully.");
                } else {
                    showAlert("Failure", "Could not delete the package_product_supplier. Please try again later.");
                }
            }
        } else if (currentTab == tbCustomers) {
            Customer selectedCustomer = tvCustomers.getSelectionModel().getSelectedItem();
            if (selectedCustomer == null) {
                showAlert("Selection Required", "Please select a customer to delete.");
                return;
            }

            if (showConfirmation("Delete Confirmation", "Do you really want to delete this customer?")) {
                if (CustomerDAO.deleteCustomer(selectedCustomer)) {  // Using the deleteCustomer method
                    tvCustomers.getItems().remove(selectedCustomer);
                    showAlert("Success", "Customer deleted successfully.");
                } else {
                    showAlert("Failure", "Could not delete the customer. Please try again later.");
                }
            }
        } else if (currentTab == tbPackages) {
            Package selectedPackage = tvPackages.getSelectionModel().getSelectedItem();
            if (selectedPackage == null) {
                showAlert("Selection Required", "Please select a package to delete.");
                return;
            }

            if (showConfirmation("Delete Confirmation", "Do you really want to delete this package?")) {
                if (PackageDAO.deletePackage(selectedPackage)) {  // Using the deletePackage method
                    tvPackages.getItems().remove(selectedPackage);
                    showAlert("Success", "Package deleted successfully.");
                } else {
                    showAlert("Failure", "Could not delete the package. Please try again later.");
                }
            }
        } else if (currentTab == tbBookings){
            Booking selectedBooking = tvBookings.getSelectionModel().getSelectedItem();
            if (selectedBooking == null) {
                showAlert("Selection Required", "Please select a package to delete.");
                return;
            }

            if (showConfirmation("Delete Confirmation", "Do you really want to delete this Booking?")) {
                if (BookingDAO.deleteBooking(selectedBooking)) {  // Using the deletePackage method
                    tvPackages.getItems().remove(selectedBooking);
                    showAlert("Success", "Booking deleted successfully.");

                } else {
                    showAlert("Failure", "Could not delete the booking. Please try again later.");
                }
            }
        }
        refreshTable();
    }
// ---------------------------------------------------------------------------------------------------------Modal Display---------------------------------------------------------------------------------------------------------

    private void showModalAgent(Agent agent) {
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
            data.clear();
            LoadData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showModalBooking(Booking booking) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addEdit_Booking_view.fxml"));
            Parent root = loader.load();

            AddEditBookingController addEditBookingController = loader.getController();
            addEditBookingController.setMainController(this);

            if (booking == null) {
                addEditBookingController.setModeAndData(AddEditBookingController.Mode.ADD, null);
            } else {
                addEditBookingController.setModeAndData(AddEditBookingController.Mode.EDIT, booking);
            }

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(booking == null ? "Add New Booking" : "Edit Booking");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            refreshTable();
            data.clear();
            LoadData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void showModalProduct(Product product) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addEdit_product_view.fxml"));
            Parent root = loader.load();

            AddEditProductController controller = loader.getController();
            controller.setProduct(product);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(product == null ? "Add New Product" : "Edit Product");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            refreshTable();
            dataProduct.clear();
            LoadData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showModalSupplier(Supplier supplier) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addEdit_supplier_view.fxml"));
            Parent root = loader.load();

            AddEditSupplierController controller = loader.getController();
            controller.setMainController(this);

            if (supplier == null) {

                controller.setModeAndData(AddEditSupplierController.Mode.ADD, null);
            } else {
                controller.setModeAndData(AddEditSupplierController.Mode.EDIT, supplier);
            }

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(supplier == null ? "Add New Supplier" : "Edit Supplier");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Here, you can refresh the product table data after subview closes
            // for instance: refreshSupplierTable();
            refreshTable();
            dataSupplier.clear();
            LoadData(); // Load the updated data into the table view
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showModalProductSupplier(Product_Supplier productSupplier) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addEdit_product_supplier_view.fxml"));
            Parent root = loader.load();

            AddEditProductSupplierController controller = loader.getController();
            controller.setMainController(this);

            if (productSupplier == null) {

                controller.setModeAndData(AddEditProductSupplierController.Mode.ADD, null);
            } else {
                controller.setModeAndData(AddEditProductSupplierController.Mode.EDIT, productSupplier);
            }

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(productSupplier == null ? "Add New Product_Supplier" : "Edit Product_Supplier");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Here, you can refresh the product table data after subview closes
            // for instance: refreshSupplierTable();
            refreshTable();
            dataProductSupplier.clear();
            LoadData(); // Load the updated data into the table view
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showModalPackageProductSupplier(Package_Product_Supplier packageProductSupplier) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addEdit_package_product_supplier_view.fxml"));
            Parent root = loader.load();

            AddEditPackageProductSupplierController controller = loader.getController();
            controller.setMainController(this);

            if (packageProductSupplier == null) {

                controller.setModeAndData(AddEditPackageProductSupplierController.Mode.ADD, null);
            } else {
                controller.setModeAndData(AddEditPackageProductSupplierController.Mode.EDIT, packageProductSupplier);
            }

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(packageProductSupplier == null ? "Add New Package_Product_Supplier" : "Edit Package_Product_Supplier");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Here, you can refresh the product table data after subview closes
            // for instance: refreshSupplierTable();
            refreshTable();
            dataPackageProductSupplier.clear();
            LoadData(); // Load the updated data into the table view
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showModalCustomer(Customer customer) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addEdit_customer_view.fxml"));
            Parent root = loader.load();
            AddEditCustomerController controller = loader.getController();

            controller.setCustomer(customer); // Assuming you have this method in AddEditCustomerController

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(customer == null ? "Add Customer" : "Edit Customer");
            stage.showAndWait();

            // Refresh the customer table view after modal closes, if needed
            // for example: loadCustomersToTable();
            refreshTable();
            dataCustomer.clear();
            LoadData(); // Load the updated data into the table view
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showModalPackage(Package mypackage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addEdit_package_view.fxml"));
            Parent root = loader.load();

            AddEditPackageController controller = loader.getController();
            controller.setMainController(this);

            if (mypackage == null) {

                controller.setModeAndData(AddEditPackageController.Mode.ADD, null);
            } else {
                controller.setModeAndData(AddEditPackageController.Mode.EDIT, mypackage);
            }

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(mypackage == null ? "Add New Package" : "Edit Package");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Here, you can refresh the product table data after subview closes
            // for instance: refreshProductTable();
            dataPackage.clear();
            LoadData(); // Load the updated data into the table view
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


// ---------------------------------------------------------------------------------------------------------Utility and Helper Function---------------------------------------------------------------------------------------------------------


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


    private void LoadData() {

        setSQL("SELECT * FROM agents");
        Tab currentTab= tpMain.getSelectionModel().getSelectedItem();
        if (currentTab!=tbAbout)
        {
            btnDelete.setDisable(false);
            btnEdit.setDisable(false);
            btnAdd.setDisable(false);
        }
        else
        {
            btnDelete.setDisable(true);
            btnEdit.setDisable(true);
            btnAdd.setDisable(true);
        }
        if (currentTab==tbAgent) {

            setSQL("Select * from agents");
        } else if (currentTab==tbBookings) {
            setSQL("Select * from bookings");
        } else if (currentTab==tbCustomers){
            setSQL("Select * from Customers");
        } else if (currentTab==tbPackages){
            setSQL("Select * from Packages");
        } else if (currentTab==tbProducts){
            setSQL("Select * from Products");
        } else if (currentTab==tbSuppliers){
            setSQL("Select * from Suppliers");
        } else if (currentTab==tbProductsSuppliers){
            setSQL("Select * from products_suppliers");
        } else if (currentTab==tbPackagesProductsSuppliers){
            setSQL("Select * from packages_products_suppliers");
        }

                data.clear();
                dataBooking.clear();
                dataCustomer.clear();
                dataPackage.clear();
                dataProduct.clear();
                dataSupplier.clear();
                dataProductSupplier.clear();
                dataPackageProductSupplier.clear();

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

                        if (currentTab==tbAgent) {
                            data.add(new Agent
                                    (rs.getInt(1), rs.getString(2),
                                            rs.getString(3), rs.getString(4),
                                            rs.getString(5), rs.getString(6),
                                            rs.getString(7), rs.getInt(8)
                                    )
                            );
                        } else if (currentTab==tbBookings) {
                            dataBooking.add(new Booking
                                    (rs.getInt(1), rs.getString(2),
                                            rs.getString(3), rs.getInt(4),
                                            rs.getInt(5), rs.getString(6),
                                            rs.getInt(7)
                                    )
                            );
                        } else if (currentTab==tbCustomers) {
                            dataCustomer.add(new Customer
                                    (rs.getInt(1), rs.getString(2),
                                            rs.getString(3), rs.getString(4),
                                            rs.getString(5), rs.getString(6),
                                            rs.getString(7),rs.getString(8),
                                            rs.getString(9),rs.getString(10),
                                            rs.getString(11),rs.getInt(12)
                                    )
                            );
                        } else if (currentTab==tbPackages) {
                            dataPackage.add(new Package
                                    (rs.getInt(1), rs.getString(2),
                                            rs.getString(3), rs.getString(4),
                                            rs.getString(5), rs.getDouble(6),
                                            rs.getDouble(7)
                                    )
                            );
                        } else if (currentTab==tbProducts) {
                            dataProduct.add(new Product
                                    (rs.getInt(1), rs.getString(2)
                                    )
                            );
                        } else if (currentTab==tbSuppliers) {
                            dataSupplier.add(new Supplier
                                    (rs.getInt(1), rs.getString(2)
                                    )
                            );
                        } else if (currentTab==tbProductsSuppliers) {
                            dataProductSupplier.add(new Product_Supplier
                                    (rs.getInt(1), rs.getInt(2), rs.getInt(3)
                                    )
                            );
                        } else if (currentTab==tbPackagesProductsSuppliers) {
                            dataPackageProductSupplier.add(new Package_Product_Supplier
                                    (rs.getInt(1), rs.getInt(2),rs.getInt(2)
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
        //});




    //}
    public void refreshTable() {
        LoadData();
    }

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
                tbAgent.setDisable(false);
                tbBookings.setDisable(false);
                tbCustomers.setDisable(false);
                tbPackages.setDisable(false);
                tbProducts.setDisable(false);
                tbProductsSuppliers.setDisable(false);
                tbSuppliers.setDisable(false);
                tbPackagesProductsSuppliers.setDisable(false);
                return true;  // Connection successful
            } catch (SQLException e) {
                tbAgent.setDisable(true);
                tbBookings.setDisable(true);
                tbCustomers.setDisable(true);
                tbPackages.setDisable(true);
                tbProducts.setDisable(true);
                tbProductsSuppliers.setDisable(true);
                tbSuppliers.setDisable(true);
                tbPackagesProductsSuppliers.setDisable(true);
                //e.printStackTrace();
            }
        } catch (IOException e) {


            //e.printStackTrace();
        }

        return false;  // Connection failed
    }

    // Method to update the connection status property
    private void updateConnectionStatus() {
        boolean isConnected = isDatabaseConnected();
//System.out.println("Connection check...");
        if (isConnected) {
//System.out.println("[x] Connected");

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