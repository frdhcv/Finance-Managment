package org.example.financemanagment;

import com.example.financemanagement.model.User;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findByUserName(String userName);
    void deleteById(Long id);
}
