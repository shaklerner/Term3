package com.example.travelexpertsadministrator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    // Database connection parameters. Replace these with your actual parameters.
    private static final String DB_URL = "jdbc:mysql://localhost:3306/travelexperts";
    private static final String USER = "mac";
    private static final String PASSWORD = "password";

    // SQL statements for the bookings table
    private static final String INSERT_BOOKING_SQL = "INSERT INTO bookings (BookingDate, BookingNo, TravelerCount, CustomerId, TripTypeId, PackageId) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_BOOKING_SQL = "UPDATE bookings SET BookingDate=?, BookingNo=?, TravelerCount=?, CustomerId=?, TripTypeId=?, PackageId=? WHERE BookingId=?";
    private static final String DELETE_BOOKING_SQL = "DELETE FROM bookings WHERE BookingId=?";
    // Add similar statements for update and delete operations if needed

    public static boolean insertBooking(Booking booking) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BOOKING_SQL)) {
            preparedStatement.setString(1, booking.getBookingDate());
            preparedStatement.setString(2, booking.getBookingNo());
            preparedStatement.setInt(3, booking.getTravelerCounter());
            preparedStatement.setInt(4, booking.getCustomerId());
            preparedStatement.setString(5, booking.getTripTypeId());
            preparedStatement.setInt(6, booking.getPackageId());

            int result = preparedStatement.executeUpdate();
            return result == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean updateBooking(Booking booking) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BOOKING_SQL)) {

            preparedStatement.setString(1, booking.getBookingDate());
            preparedStatement.setString(2, booking.getBookingNo());
            preparedStatement.setInt(3, booking.getTravelerCounter());
            preparedStatement.setInt(4, booking.getCustomerId());
            preparedStatement.setString(5, booking.getTripTypeId());
            preparedStatement.setInt(6, booking.getPackageId());
            preparedStatement.setInt(7, booking.getBookingId());

            int result = preparedStatement.executeUpdate();
            return result == 1;  // Returns true if one record was updated, otherwise false

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean deleteBooking(Booking booking) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BOOKING_SQL)) {

            preparedStatement.setInt(1, booking.getBookingId());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<String> fetchTripTypeIds() {
        List<String> tripTypeIds = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT TripTypeId FROM triptypes");

            while (rs.next()) {
                tripTypeIds.add(rs.getString(1));
            }

            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tripTypeIds;
    }
    public static List<Integer> fetchCustomerIds() {
        List<Integer> customerIds = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT CustomerId FROM customers")) {

            while (rs.next()) {
                customerIds.add(rs.getInt("CustomerId"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customerIds;
    }
    public static List<Integer> fetchPackageIds() {
        List<Integer> packageIds = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT PackageId FROM packages");

            while (rs.next()) {
                packageIds.add(rs.getInt(1));
            }

            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return packageIds;
    }
}
