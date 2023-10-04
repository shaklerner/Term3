package com.example.travelexpertsadministrator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductDAO {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/travelexperts";
    private static final String USER = "mac";
    private static final String PASSWORD = "password";

    private static final String INSERT_PRODUCT_SQL = "INSERT INTO products (ProdName) VALUES (?)";
    private static final String UPDATE_PRODUCT_SQL = "UPDATE products SET ProdName=? WHERE ProductId=?";
    private static final String DELETE_PRODUCT_SQL = "DELETE FROM products WHERE ProductId = ?";

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
