package org.example.financemanagment.domain.dao;

import org.example.financemanagment.domain.entity.SubscriptionEntity;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class SubscriptionDAO implements Dao<SubscriptionEntity, Long> {

    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String JDBC_USERNAME = "postgres";
    private static final String JDBC_PASSWORD = "Aba32835";

    private static final String INSERT_SQL = "INSERT INTO subscriptions (name, price, start_date, end_date, active) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM subscriptions";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM subscriptions WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM subscriptions WHERE id = ?";
    private static final String UPDATE_SQL = "UPDATE subscriptions SET name = ?, price = ?, start_date = ?, end_date = ?, active = ? WHERE id = ?";
    private static final String PARTIAL_UPDATE_SQL = "UPDATE subscriptions SET name = COALESCE(?, name), price = COALESCE(?, price), start_date = COALESCE(?, start_date), end_date = COALESCE(?, end_date), active = COALESCE(?, active) WHERE id = ?";
    private static final String TOTAL_PRICE_SQL = "SELECT SUM(price) AS total_price FROM subscriptions";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
    }

    @Override
    public SubscriptionEntity save(SubscriptionEntity entity) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, entity.getName());
            stmt.setDouble(2, entity.getPrice());
            stmt.setObject(3, entity.getStartDate());
            stmt.setObject(4, entity.getEndDate());
            stmt.setBoolean(5, entity.isActive());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating subscription failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating subscription failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle exception or rethrow as a custom runtime exception
            throw new RuntimeException("Failed to save subscription", ex);
        }
        return entity;
    }

    public double getTotalPriceOfSubscriptions() {
        double totalPrice = 0.0;
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(TOTAL_PRICE_SQL)) {

            if (rs.next()) {
                totalPrice = rs.getDouble("total_price");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle exception or rethrow as a custom runtime exception
            throw new RuntimeException("Failed to calculate total price of subscriptions", ex);
        }
        return totalPrice;
    }

    @Override
    public List<SubscriptionEntity> findAll() {
        List<SubscriptionEntity> subscriptions = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_SQL)) {

            while (rs.next()) {
                SubscriptionEntity subscription = extractSubscriptionFromResultSet(rs);
                subscriptions.add(subscription);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle exception or rethrow as a custom runtime exception
            throw new RuntimeException("Failed to fetch all subscriptions", ex);
        }
        return subscriptions;
    }

    @Override
    public Optional<SubscriptionEntity> findById(Long id) {
        SubscriptionEntity subscription = null;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    subscription = extractSubscriptionFromResultSet(rs);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle exception or rethrow as a custom runtime exception
            throw new RuntimeException("Failed to find subscription by id: " + id, ex);
        }
        return Optional.ofNullable(subscription);
    }

    @Override
    public void deleteById(Long id) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_SQL)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle exception or rethrow as a custom runtime exception
            throw new RuntimeException("Failed to delete subscription with id: " + id, ex);
        }
    }

    // Helper method to extract SubscriptionEntity from ResultSet
    private SubscriptionEntity extractSubscriptionFromResultSet(ResultSet rs) throws SQLException {
        return new SubscriptionEntity(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getDouble("price"),
                rs.getObject("start_date", LocalDate.class),
                rs.getObject("end_date", LocalDate.class),
                rs.getBoolean("active")
        );
    }
}