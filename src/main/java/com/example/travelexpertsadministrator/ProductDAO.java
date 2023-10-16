package com.example.travelexpertsadministrator;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ProductDAO {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/travelexperts";
    private static final String USER = "mac";
    private static final String PASSWORD = "password";

    private static final String INSERT_PRODUCT_SQL = "INSERT INTO products (ProdName) VALUES (?)";
    private static final String UPDATE_PRODUCT_SQL = "UPDATE products SET ProdName=? WHERE ProductId=?";
    private static final String DELETE_PRODUCT_SQL = "DELETE FROM products WHERE ProductId = ?";

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
    public static boolean insertProduct(Product product) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_SQL)) {

            preparedStatement.setString(1, product.getProductName());
            int result = preparedStatement.executeUpdate();
            return result == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int fetchProductIdFromDatabase(String productName) {
        int productId = -1; // Default value if the product is not found

        // Assuming you have a database connection and a statement, you can retrieve the product ID like this:
        String selectSQL = "SELECT ProductId FROM products WHERE ProdName = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, productName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    productId = resultSet.getInt("ProductId");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productId;
    }
    public static List<String> fetchProductNamesFromDatabase() {
        List<String> productNames = new ArrayList<>();

        // Assuming you have a database connection and a statement, you can retrieve product names like this:
        String selectSQL = "SELECT ProdName FROM products";
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSQL)) {

            while (resultSet.next()) {
                String productName = resultSet.getString("ProdName");
                productNames.add(productName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productNames;
    }

    public static String fetchProductNameWithId(int Id) {

        // Assuming you have a database connection and a statement, you can retrieve the product ID like this:
        String selectSQL = "SELECT distinct ProdName FROM products WHERE ProductId=?";
        String productName="";
        Properties p= getConnectionProperties();
        try (Connection connection = DriverManager.getConnection((String) p.get("url"), (String) p.get("user"), (String) p.get("password"));
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, Id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    productName = resultSet.getString("ProdName");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productName;
    }

    public static boolean updateProduct(Product product) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT_SQL)) {

            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setInt(2, product.getProductId());

            int result = preparedStatement.executeUpdate();
            return result == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean deleteProduct(Product product) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT_SQL)) {

            preparedStatement.setInt(1, product.getProductId());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
