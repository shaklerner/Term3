package com.example.travelexpertsadministrator;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class PackageProductSupplierDAO {
    private static final String INSERT_PACKAGEPRODUCTSUPPLIER_SQL = "INSERT INTO packages_products_suppliers ( PackageId, ProductSupplierId) VALUES (?,?)";
    private static final String UPDATE_PACKAGEPRODUCTSUPPLIER_SQL = "UPDATE packages_products_suppliers SET ProductSupplierId=? WHERE PackageId=? AND ProductSupplierId=?";
    private static final String DELETE_PACKAGEPRODUCTSUPPLIER_SQL = "DELETE FROM packages_products_suppliers WHERE PackageId=?";

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

    public static boolean insertPackageProductSupplier(Package_Product_Supplier packageProductSupplier) {

        Properties p= getConnectionProperties();


        try (Connection connection = DriverManager.getConnection((String) p.get("url"), (String) p.get("user"), (String) p.get("password"));
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PACKAGEPRODUCTSUPPLIER_SQL)) {

            preparedStatement.setInt(1, packageProductSupplier.getPpsPackageId());
            preparedStatement.setInt(2, packageProductSupplier.getPpsProductSupplierId());

            int result = preparedStatement.executeUpdate();
            return result == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updatePachakeProductSupplier(Package_Product_Supplier packageProductSupplier) {
        Properties p= getConnectionProperties();
        try (Connection connection = DriverManager.getConnection((String) p.get("url"), (String) p.get("user"), (String) p.get("password"));
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PACKAGEPRODUCTSUPPLIER_SQL)) {

            preparedStatement.setInt(1, packageProductSupplier.getPpsProductSupplierId());
            preparedStatement.setInt(2, packageProductSupplier.getPpsPackageId());
            preparedStatement.setInt(3, packageProductSupplier.getPpsProductSupplierIdOld());

            //System.out.println("SuppID: "+productSupplier.getSupplierId());
            //System.out.println("ProdID: "+productSupplier.getProductId());
            //System.out.println(preparedStatement.toString());
            int result = preparedStatement.executeUpdate();
            return result == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deletePackageProductSupplier(Package_Product_Supplier packageProductSupplier) {
        Properties p= getConnectionProperties();
        try (Connection connection = DriverManager.getConnection((String) p.get("url"), (String) p.get("user"), (String) p.get("password"));
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PACKAGEPRODUCTSUPPLIER_SQL)) {

            preparedStatement.setInt(1, packageProductSupplier.getPpsPackageId());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String fetchProductSupplierNamesFromDatabase(int Id) {

        // Assuming you have a database connection and a statement, you can retrieve the product ID like this:
        String selectSQL = "SELECT distinct PkgName FROM products_suppliers WHERE ProductSupplierId=?";
        String packageName="";
        Properties p= getConnectionProperties();
        try (Connection connection = DriverManager.getConnection((String) p.get("url"), (String) p.get("user"), (String) p.get("password"));
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, Id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    packageName = resultSet.getString("ProductId");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return packageName;
    }

}
