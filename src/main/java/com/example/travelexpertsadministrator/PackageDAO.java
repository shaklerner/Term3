package com.example.travelexpertsadministrator;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class PackageDAO {

    private static final String INSERT_PACKAGE_SQL = "INSERT INTO packages (PkgName, PkgStartDate, PkgEndDate, PkgDesc, PkgBasePrice, PkgAgencyCommission) VALUES (?,?,?,?,?,?)";
    private static final String UPDATE_PACKAGE_SQL = "UPDATE packages SET PkgName=?,PkgStartDate=?,PkgEndDate=?,PkgDesc=?,PkgBasePrice=?,PkgAgencyCommission=? WHERE PackageId=?";
    private static final String DELETE_PACKAGE_SQL = "DELETE FROM packages WHERE PackageId=?";

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


    public static boolean insertPackage(Package mypackage) {

        Properties p= getConnectionProperties();


        try (Connection connection = DriverManager.getConnection((String) p.get("url"), (String) p.get("user"), (String) p.get("password"));
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PACKAGE_SQL)) {

            preparedStatement.setString(1, mypackage.getPkgName());
            preparedStatement.setString(2, mypackage.getPkgStartDate());
            preparedStatement.setString(3, mypackage.getPkgEndDate());
            preparedStatement.setString(4, mypackage.getPkgDesc());
            preparedStatement.setDouble(5, mypackage.getPkgBasePrice());
            preparedStatement.setDouble(6, mypackage.getPkgAgencyCommission());




            int result = preparedStatement.executeUpdate();
            return result == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updatePackage(Package mypackage) {
        Properties p= getConnectionProperties();
        try (Connection connection = DriverManager.getConnection((String) p.get("url"), (String) p.get("user"), (String) p.get("password"));
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PACKAGE_SQL)) {

            preparedStatement.setString(1, mypackage.getPkgName());
            preparedStatement.setString(2, mypackage.getPkgStartDate());
            preparedStatement.setString(3, mypackage.getPkgEndDate());
            preparedStatement.setString(4, mypackage.getPkgDesc());
            preparedStatement.setDouble(5, mypackage.getPkgBasePrice());
            preparedStatement.setDouble(6, mypackage.getPkgAgencyCommission());
            preparedStatement.setInt(7,mypackage.getPackageId());

            int result = preparedStatement.executeUpdate();
            return result == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean deletePackage(Package mypackage) {
        Properties p= getConnectionProperties();
        try (Connection connection = DriverManager.getConnection((String) p.get("url"), (String) p.get("user"), (String) p.get("password"));
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PACKAGE_SQL)) {

            preparedStatement.setInt(1, mypackage.getPackageId());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }




}
