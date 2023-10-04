package com.example.travelexpertsadministrator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/travelexperts";
    private static final String USER = "mac";
    private static final String PASSWORD = "password";

    private static final String INSERT_CUSTOMER_SQL = "INSERT INTO customers (CustFirstName, CustLastName, CustAddress, CustCity, CustProv, CustPostal, CustCountry, CustHomePhone, CustBusPhone, CustEmail, AgentId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_CUSTOMER_SQL = "UPDATE customers SET CustFirstName=?, CustLastName=?, CustAddress=?, CustCity=?, CustProv=?, CustPostal=?, CustCountry=?, CustHomePhone=?, CustBusPhone=?, CustEmail=?, AgentId=? WHERE CustomerId=?";
    private static final String DELETE_CUSTOMER_SQL = "DELETE FROM customers WHERE CustomerId = ?";

    public static boolean insertCustomer(Customer customer) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CUSTOMER_SQL)) {

            preparedStatement.setString(1, customer.getCustFName());
            preparedStatement.setString(2, customer.getCustLName());
            preparedStatement.setString(3, customer.getCustAddress());
            preparedStatement.setString(4, customer.getCustCity());
            preparedStatement.setString(5, customer.getCustProv());
            preparedStatement.setString(6, customer.getCustPostal());
            preparedStatement.setString(7, customer.getCustCountry());
            preparedStatement.setString(8, customer.getCustHomePhone());
            preparedStatement.setString(9, customer.getCustBusPhone());
            preparedStatement.setString(10, customer.getCustEmail());
            preparedStatement.setInt(11, customer.getAgentId());

            int result = preparedStatement.executeUpdate();
            return result == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateCustomer(Customer customer) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CUSTOMER_SQL)) {

            preparedStatement.setString(1, customer.getCustFName());
            preparedStatement.setString(2, customer.getCustLName());
            preparedStatement.setString(3, customer.getCustAddress());
            preparedStatement.setString(4, customer.getCustCity());
            preparedStatement.setString(5, customer.getCustProv());
            preparedStatement.setString(6, customer.getCustPostal());
            preparedStatement.setString(7, customer.getCustCountry());
            preparedStatement.setString(8, customer.getCustHomePhone());
            preparedStatement.setString(9, customer.getCustBusPhone());
            preparedStatement.setString(10, customer.getCustEmail());
            preparedStatement.setInt(11, customer.getAgentId());
            preparedStatement.setInt(12, customer.getCustomerId());

            int result = preparedStatement.executeUpdate();
            return result == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteCustomer(Customer customer) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CUSTOMER_SQL)) {

            preparedStatement.setInt(1, customer.getCustomerId());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Integer> fetchAgentIds() {
        List<Integer> agentIds = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT AgentId FROM agents");

            while (rs.next()) {
                agentIds.add(rs.getInt(1));
            }

            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return agentIds;
    }
}
