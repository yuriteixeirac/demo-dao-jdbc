package model.dao.impl;

import model.dao.DataAccessObject;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SellerDaoJDBC implements DataAccessObject<Seller> {
    private Connection conn;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller seller) {
        PreparedStatement statement;

        try {
            statement = conn.prepareStatement(
                    "INSERT INTO seller (name, email, birthdate, salary, department_id) VALUES (?, ?, ?, ?, ?)"
            );
            statement.setString(1, seller.getName());
            statement.setString(2, seller.getEmail());
            statement.setDate(3, new Date(new java.util.Date(
                    dateFormat.format(seller.getBirthDate()))
                    .getTime()));
            statement.setDouble(4, seller.getSalary());
            statement.setInt(5, seller.getDepartment().getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Seller seller) {
        PreparedStatement statement;

        try {
            statement = conn.prepareStatement(
                    "UPDATE seller SET name = ?, email = ?, birthdate = ?, salary = ?, department_id = ? WHERE id = ?"
            );

            statement.setString(1, seller.getName());
            statement.setString(2, seller.getEmail());
            statement.setDate(3, new Date(new java.util.Date(dateFormat.format(seller.getBirthDate())).getTime()));
            statement.setDouble(4, seller.getSalary());
            statement.setInt(5, seller.getDepartment().getId());
            statement.setInt(6, seller.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id) {
        PreparedStatement statement;

        try {
            statement = conn.prepareStatement(
                    "DELETE FROM seller WHERE id = ?"
            );
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Seller findById(int id) {
        PreparedStatement statement;
        Seller seller = null;
        try {
            statement = conn.prepareStatement(
                    "SELECT seller.*, department.name AS department_name FROM seller INNER JOIN department WHERE department.id = seller.department_id AND id = ?;"
            );
            
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                seller = new Seller(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getTime("birthdate"),
                        resultSet.getDouble("salary"),
                        new Department(
                                resultSet.getInt("department_id"),
                                resultSet.getString("department_name")
                        )
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return seller;
    }

    @Override
    public List<Seller> findAll() {
        List<Seller> sellers = new ArrayList<>();
        PreparedStatement statement;

        try {
            statement = conn.prepareStatement("SELECT seller.*, department.name AS department_name FROM seller INNER JOIN department WHERE department.id = seller.department_id");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                sellers.add(new Seller(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getDate("birthdate"),
                        resultSet.getDouble("salary"),
                        new Department(
                                resultSet.getInt("department_id"),
                                resultSet.getString("department_name")
                        )
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sellers;
    }
}
