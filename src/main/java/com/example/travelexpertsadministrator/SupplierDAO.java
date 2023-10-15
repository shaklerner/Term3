package com.example.travelexpertsadministrator;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

}