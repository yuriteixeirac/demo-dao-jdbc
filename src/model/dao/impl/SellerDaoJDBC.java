package model.dao.impl;

import database.Database;
import database.DatabaseException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

public class SellerDaoJDBC implements SellerDao {
    private final Connection conn;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller seller) {
        PreparedStatement statement = null;

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
            throw new DatabaseException(e.getMessage());
        } finally {
            Database.closeStatement(statement);
        }
    }

    @Override
    public void update(Seller seller) {
        PreparedStatement statement = null;

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
            throw new DatabaseException(e.getMessage());
        } finally {
            Database.closeStatement(statement);
        }
    }

    @Override
    public void deleteById(int id) {
        PreparedStatement statement = null;

        try {
            statement = conn.prepareStatement(
                    "DELETE FROM seller WHERE id = ?"
            );
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            Database.closeStatement(statement);
        }
    }

    @Override
    public Seller findById(int id) {
        PreparedStatement statement = null;

        try {
            statement = conn.prepareStatement(
                    "SELECT seller.*, department.name AS department_name FROM seller " +
                        "INNER JOIN department WHERE department.id = seller.department_id AND seller.id = ?"
            );
            
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Department department = instantiateDepartment(resultSet);
                return instantiateSeller(resultSet, department);
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            Database.closeStatement(statement);
        }

        return null;
    }

    @Override
    public List<Seller> findAll() {
        List<Seller> sellers = new ArrayList<>();
        Map<Integer, Department> departments = new HashMap<>();

        PreparedStatement statement = null;

        try {
            statement = conn.prepareStatement(
                    "SELECT seller.*, department.name AS department_name FROM seller"
                        + " INNER JOIN department ON department.id = seller.department_id"
            );

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Integer departmentId = resultSet.getInt("department_id");
                if (!departments.containsKey(departmentId)) {
                    departments.put(departmentId, instantiateDepartment(resultSet));
                }

                sellers.add(
                        instantiateSeller(resultSet, departments.get(departmentId))
                );
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            Database.closeStatement(statement);
        }

        return sellers;
    }

    public Seller instantiateSeller(ResultSet resultSet, Department department) throws SQLException {
        Seller seller = new Seller();

        seller.setId(resultSet.getInt("id"));
        seller.setName(resultSet.getString("name"));
        seller.setEmail(resultSet.getString("email"));
        seller.setBirthDate(resultSet.getDate("birthdate"));
        seller.setSalary(resultSet.getDouble("salary"));

        seller.setDepartment(instantiateDepartment(resultSet));

        return seller;
    }

    public Department instantiateDepartment(ResultSet resultSet) throws SQLException{
        return new Department(
                resultSet.getInt("department_id"),
                resultSet.getString("department_name")
        );
    }

    public List<Seller> findByDepartment(Department department) {
        List<Seller> sellers = new ArrayList<>();

        PreparedStatement statement = null;

        try {
            statement = conn.prepareStatement(
                    "SELECT seller.*, department.name as department_name" +
                        "  FROM seller INNER JOIN department WHERE seller.department_id = ? " +
                        "AND department.id = ?;"
            );
            statement.setInt(1, department.getId());
            statement.setInt(2, department.getId());

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                sellers.add(instantiateSeller(resultSet, department));
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            Database.closeStatement(statement);
        }

        return sellers;
    }
}
