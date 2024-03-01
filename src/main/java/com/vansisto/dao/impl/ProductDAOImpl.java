package com.vansisto.dao.impl;

import com.vansisto.config.MySQLConnector;
import com.vansisto.dao.ProductDAO;
import com.vansisto.model.Product;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {
    @Override
    public int create(Product product) {
        int status = 400;
        String SQL = "INSERT INTO product(name, price) VALUES (?, ?)";

        try (
                Connection connection = MySQLConnector.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
                ){
            preparedStatement.setString(1, product.getName());
            preparedStatement.setBigDecimal(2, product.getPrice());

            preparedStatement.execute();
            status = 201;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
    }

    @Override
    public Product getById(long id) {
        Product product = null;
        String SQL = "SELECT * FROM product WHERE id = ?";

        try (
                Connection connection = MySQLConnector.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                BigDecimal price = resultSet.getBigDecimal("price");

                product = new Product(id, name, price);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return product;
    }

    @Override
    public int update(Product product) {
        int status = 400;
        String SQL = "UPDATE product SET name = ?, price = ? WHERE id = ?";

        try (
                Connection connection = MySQLConnector.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setBigDecimal(2, product.getPrice());
            preparedStatement.setLong(3, product.getId());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                status = 200;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return status;
    }

    @Override
    public int deleteById(long id) {
        int status = 400;
        String SQL = "DELETE FROM product WHERE id = ?";

        try (
                Connection connection = MySQLConnector.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ) {
            preparedStatement.setLong(1, id);

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                status = 200;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return status;
    }

    @Override
    public List<Product> getAll() {
        List<Product> productList = new ArrayList<>();

        try (
                Connection connection = MySQLConnector.getConnection();
                Statement statement = connection.createStatement();
                ) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM product");
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                BigDecimal price = resultSet.getBigDecimal("price");

                Product product = new Product(id, name, price);
                productList.add(product);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productList;
    }
}
