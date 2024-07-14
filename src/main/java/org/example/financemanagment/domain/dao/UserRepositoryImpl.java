package org.example.financemanagment.domain.dao;

import org.example.financemanagment.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public User save(User user) {
        String sql = "INSERT INTO users (first_name, last_name, user_name, email, phone_number, password) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getFirstName(), user.getLastName(), user.getUserName(), user.getEmail(), user.getPhoneNumber(), user.getPassword());
        return user;
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        String sql = "SELECT * FROM users WHERE user_name = ?";
        User user = jdbcTemplate.queryForObject(sql, new Object[]{userName}, new UserRowMapper());
        return Optional.ofNullable(user);
    }

    @Override
    public Long getUserId(String userName, String password) {
        String sql = "SELECT id FROM users WHERE user_name = ? AND password = ?";
        try {
            Long userId = jdbcTemplate.queryForObject(sql, new Object[]{userName, password}, Long.class);
            return userId;
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    private static final class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("user_name"),
                    rs.getString("email"),
                    rs.getString("phone_number"),
                    rs.getString("password")
            );
        }
    }


}
