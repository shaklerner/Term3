package com.example.travelexpertsadministrator;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SupplierDAO {

    private static final String INSERT_SUPPLIER_SQL = "INSERT INTO suppliers (SupplierId, SupName)\n" +
            "SELECT MAX(SupplierId) + 1, ? \n" +
            "FROM suppliers;\n";
    private static final String UPDATE_SUPPLIER_SQL = "UPDATE suppliers SET SupName=? WHERE SupplierId=?";
    private static final String DELETE_SUPPLIER_SQL = "DELETE FROM suppliers WHERE SupplierId=?";

    private static Properties getConnectionProperties() {
        String currentDirectory = System.getProperty("user.dir");
        FileInputStream fis=null;
        try {

            fis = new FileInputStream(currentDirectory.replaceAll("\\\\", "\\\\\\\\") + "\\connection.properties");
            Properties p = new Properties();

            //System.out.println("[x] read -- connection.properties -- ");
            p.load(fis);
            return p;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static boolean insertSupplier(Supplier supplier) {

        Properties p= getConnectionProperties();


        try (Connection connection = DriverManager.getConnection((String) p.get("url"), (String) p.get("user"), (String) p.get("password"));
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SUPPLIER_SQL)) {

            preparedStatement.setString(1, supplier.getSupName());


            int result = preparedStatement.executeUpdate();
            return result == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateSupplier(Supplier supplier) {
        Properties p= getConnectionProperties();
        try (Connection connection = DriverManager.getConnection((String) p.get("url"), (String) p.get("user"), (String) p.get("password"));
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SUPPLIER_SQL)) {

            preparedStatement.setString(1, supplier.getSupName());
            preparedStatement.setInt(2, supplier.getSupplierId());

            int result = preparedStatement.executeUpdate();
            return result == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteSupplier(Supplier supplier) {
        Properties p= getConnectionProperties();
        try (Connection connection = DriverManager.getConnection((String) p.get("url"), (String) p.get("user"), (String) p.get("password"));
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SUPPLIER_SQL)) {

            preparedStatement.setInt(1, supplier.getSupplierId());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<String> fetchSupplierNamesFromDatabase() {
        List<String> supplierNames = new ArrayList<>();

        // Assuming you have a database connection and a statement, you can retrieve supplier names like this:
        String selectSQL = "SELECT SupName FROM suppliers";
        Properties p= getConnectionProperties();
        try (Connection connection = DriverManager.getConnection((String) p.get("url"), (String) p.get("user"), (String) p.get("password"));
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String supplierName = resultSet.getString("SupName");
                supplierNames.add(supplierName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return supplierNames;
    }

    public static int fetchSupplierIdFromDatabase(String supplierName) {
        int supplierId = -1;  // Initialize to a default value or handle not found case
        Properties p= getConnectionProperties();
        String sql = "SELECT SupplierId FROM suppliers WHERE SupName = ?";


            try (Connection conn = DriverManager.getConnection((String) p.get("url"), (String) p.get("user"), (String) p.get("password"));
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, supplierName);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    supplierId = rs.getInt("SupplierId");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }


        return supplierId;
    }

}