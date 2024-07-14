package org.example.financemanagment.service;

import org.example.financemanagment.domain.entity.User;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String JDBC_USERNAME = "postgres";
    private static final String JDBC_PASSWORD = "Ferid100";

    private static final String INSERT_SQL = "INSERT INTO users (first_name, last_name, user_name, email, phone_number, password) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM users";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM users WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM users WHERE id = ?";
    private static final String UPDATE_SQL = "UPDATE users SET first_name = ?, last_name = ?, user_name = ?, email = ?, phone_number = ?, password = ? WHERE id = ?";
    private static final String PARTIAL_UPDATE_SQL = "UPDATE users SET first_name = COALESCE(?, first_name), last_name = COALESCE(?, last_name), user_name = COALESCE(?, user_name), email = COALESCE(?, email), phone_number = COALESCE(?, phone_number), password = COALESCE(?, password) WHERE id = ?";
    private static final String SELECT_BY_USERNAME_AND_PASSWORD_SQL = "SELECT * FROM users WHERE user_name = ? AND password = ?";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
    }

    public User register(String firstName, String lastName, String userName, String email, String phoneNumber, String password) {
        User user = new User();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, userName);
            stmt.setString(4, email);
            stmt.setString(5, phoneNumber);
            stmt.setString(6, password);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    user.setUserName(userName);
                    user.setEmail(email);
                    user.setPhoneNumber(phoneNumber);
                    user.setPassword(password);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to save user", ex);
        }
        return user;
    }

    public boolean login(String userName, String password) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_USERNAME_AND_PASSWORD_SQL)) {

            stmt.setString(1, userName);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to login user", ex);
        }
    }

    public void deleteUser(Long id) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_SQL)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to delete user with id: " + id, ex);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_SQL)) {

            while (rs.next()) {
                User user = extractUserFromResultSet(rs);
                users.add(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to fetch all users", ex);
        }
        return users;
    }

    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setUserName(rs.getString("user_name"));
        user.setEmail(rs.getString("email"));
        user.setPhoneNumber(rs.getString("phone_number"));
        user.setPassword(rs.getString("password"));
        return user;
    }
}
