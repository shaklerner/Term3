package com.example.travelexpertsadministrator;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ProductSupplierDAO {
    private static final String INSERT_PRODUCTSUPPLIER_SQL = "INSERT INTO products_suppliers ( ProductId, SupplierId) VALUES (?,?)";
    private static final String UPDATE_PRODUCTSUPPLIER_SQL = "UPDATE products_suppliers SET ProductId=? , SupplierId=? WHERE ProductSupplierId=?";
    private static final String DELETE_PRODUCTSUPPLIER_SQL = "DELETE FROM products_suppliers WHERE ProductSupplierId=?";

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

    public static boolean insertProductSupplier(Product_Supplier productSupplier) {

        Properties p= getConnectionProperties();


        try (Connection connection = DriverManager.getConnection((String) p.get("url"), (String) p.get("user"), (String) p.get("password"));
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCTSUPPLIER_SQL)) {

            //preparedStatement.setInt(1, productSupplier.getProductSupplierId());
            preparedStatement.setInt(1, productSupplier.getProductId());
            preparedStatement.setInt(2, productSupplier.getSupplierId());

            int result = preparedStatement.executeUpdate();
            return result == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateProductSupplier(Product_Supplier productSupplier) {
        Properties p= getConnectionProperties();
        try (Connection connection = DriverManager.getConnection((String) p.get("url"), (String) p.get("user"), (String) p.get("password"));
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCTSUPPLIER_SQL)) {

            preparedStatement.setInt(1, productSupplier.getSupplierId());
            preparedStatement.setInt(2, productSupplier.getProductId());
            preparedStatement.setInt(3, productSupplier.getProductSupplierId());
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

    public static boolean deleteProductSupplier(Product_Supplier productSupplier) {
        Properties p= getConnectionProperties();
        try (Connection connection = DriverManager.getConnection((String) p.get("url"), (String) p.get("user"), (String) p.get("password"));
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCTSUPPLIER_SQL)) {

            preparedStatement.setInt(1, productSupplier.getProductSupplierId());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Integer> fetchAllProductSupplierIdsFromDatabase() {
        int thisProductSupplierId = -1; // Default value if the product_Supplier is not found

        // Assuming you have a database connection and a statement, you can retrieve the product ID like this:
        String selectSQL = "SELECT distinct ProductSupplierId FROM products_suppliers";
        List<Integer> productSupplierId = new ArrayList<>();
        Properties p= getConnectionProperties();
        try (Connection connection = DriverManager.getConnection((String) p.get("url"), (String) p.get("user"), (String) p.get("password"));
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    thisProductSupplierId = resultSet.getInt("ProductSupplierId");
                    productSupplierId.add(thisProductSupplierId);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productSupplierId;
    }

    public static int fetchProductIdFromProductSupplierId(int productSupplierId) {
        int productId = -1; // Default value if the product is not found

        // Assuming you have a database connection and a statement, you can retrieve the product ID like this:
        String selectSQL = "SELECT distinct ProductId FROM products_suppliers WHERE ProductSupplierId=?";

        Properties p= getConnectionProperties();
        try (Connection connection = DriverManager.getConnection((String) p.get("url"), (String) p.get("user"), (String) p.get("password"));
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1,productSupplierId);
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

    public static int fetchSupplierIdFromProductSupplierId(int productSupplierId) {
        int supplierId = -1; // Default value if the supplier is not found

        // Assuming you have a database connection and a statement, you can retrieve the product ID like this:
        String selectSQL = "SELECT distinct SupplierId FROM products_suppliers WHERE ProductSupplierId=?";

        Properties p= getConnectionProperties();
        try (Connection connection = DriverManager.getConnection((String) p.get("url"), (String) p.get("user"), (String) p.get("password"));
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1,productSupplierId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    supplierId = resultSet.getInt("SupplierId");
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return supplierId;
    }



}
