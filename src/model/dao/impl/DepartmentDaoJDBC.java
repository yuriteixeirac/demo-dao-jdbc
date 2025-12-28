package model.dao.impl;

import database.Database;
import database.DatabaseException;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {
    private final Connection conn;

    public DepartmentDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Department department) {
        PreparedStatement statement = null;

        try {
            statement = conn.prepareStatement("INSERT INTO department (name) VALUES (?)");
            statement.setString(1, department.getName());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            Database.closeStatement(statement);
        }
    }

    @Override
    public void update(Department department) {
        PreparedStatement statement = null;

        try {
            statement = conn.prepareStatement("UPDATE department SET name = ? WHERE id = ?");
            statement.setString(1, department.getName());
            statement.setInt(2, department.getId());

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
            statement = conn.prepareStatement("DELETE FROM department WHERE id = ?");
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            Database.closeStatement(statement);
        }
    }

    @Override
    public Department findById(int id) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = conn.prepareStatement("SELECT * FROM department WHERE id = ?");
            statement.setInt(1, id);

            resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            return instantiateDepartment(resultSet);
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            Database.closeStatement(statement);

            return null;
        }

    }

    @Override
    public List<Department> findAll() {
        List<Department> departments = new ArrayList<>();
        Statement statement = null;

        try {
            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM department");

            while (resultSet.next()) {
                departments.add(
                        instantiateDepartment(resultSet)
                );
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            Database.closeStatement(statement);
        }

        return departments;
    }

    public Department instantiateDepartment(ResultSet resultSet) throws SQLException {
        return new Department(
                resultSet.getInt("id"),
                resultSet.getString("name")
        );
    }
}
