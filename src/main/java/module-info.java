module com.example.travelexpertsadministrator {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.travelexpertsadministrator to javafx.fxml;
    exports com.example.travelexpertsadministrator;
}