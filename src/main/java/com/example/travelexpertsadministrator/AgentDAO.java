package com.example.travelexpertsadministrator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgentDAO {

    // Database connection parameters. Replace these with your actual parameters.
    private static final String DB_URL = "jdbc:mysql://localhost:3306/travelexperts";
    private static final String USER = "mac";
    private static final String PASSWORD = "password";

    // SQL statements
    private static final String INSERT_AGENT_SQL = "INSERT INTO agents (AgtFirstName, AgtMiddleInitial, AgtLastName, AgtBusPhone, AgtEmail, AgtPosition, AgencyId) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_AGENT_SQL = "UPDATE agents SET AgtFirstName=?, AgtMiddleInitial=?, AgtLastName=?, AgtBusPhone=?, AgtEmail=?, AgtPosition=?, AgencyId=? WHERE agentId=?";
    private static final String DELETE_AGENT_SQL = "DELETE FROM agents WHERE AgentId = ?";
    public static boolean insertAgent(Agent agent) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_AGENT_SQL)) {

            preparedStatement.setString(1, agent.getAgtFName());
            preparedStatement.setString(2, agent.getAgtMidInit());
            preparedStatement.setString(3, agent.getAgtLName());
            preparedStatement.setString(4, agent.getAgtPhone());
            preparedStatement.setString(5, agent.getAgtEmail());
            preparedStatement.setString(6, agent.getAgtPosition());
            preparedStatement.setInt(7, agent.getAgencyId());

            int result = preparedStatement.executeUpdate();
            return result == 1;  // Returns true if one record was inserted, otherwise false

        } catch (SQLException e) {
            // Handle database errors or log them
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateAgent(Agent agent) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_AGENT_SQL)) {

            preparedStatement.setString(1, agent.getAgtFName());
            preparedStatement.setString(2, agent.getAgtMidInit());
            preparedStatement.setString(3, agent.getAgtLName());
            preparedStatement.setString(4, agent.getAgtPhone());
            preparedStatement.setString(5, agent.getAgtEmail());
            preparedStatement.setString(6, agent.getAgtPosition());
            preparedStatement.setInt(7, agent.getAgencyId());
            preparedStatement.setInt(8, agent.getAgentId());  // Assuming Agent has getAgentId() method

            int result = preparedStatement.executeUpdate();
            return result == 1;  // Returns true if one record was updated, otherwise false

        } catch (SQLException e) {
            // Handle database errors or log them
            e.printStackTrace();
            return false;
        }
    }
    public static boolean deleteAgent(Agent agent) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_AGENT_SQL)) {

            preparedStatement.setInt(1, agent.getAgentId());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Integer> fetchAgencyIds() {
        List<Integer> agencyIds = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT AgencyId FROM agencies");

            while (rs.next()) {
                agencyIds.add(rs.getInt(1));
            }

            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return agencyIds;
    }
    public static boolean canDeleteAgent(int agentId) {
        // Query to check if there are customers with the given AgentId
        String query = "SELECT COUNT(*) FROM customers WHERE AgentId = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, agentId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                return false; // There are customers associated with this agent
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;  // Agent can be deleted
    }
}
