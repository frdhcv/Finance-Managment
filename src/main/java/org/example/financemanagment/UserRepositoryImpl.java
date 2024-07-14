package org.example.financemanagment;

import com.example.financemanagement.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
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
    public void deleteById(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static final class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(
                    rs.getLong("id"),
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
