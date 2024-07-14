package org.example.financemanagment.domain.dao;


import org.example.financemanagment.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findByUserName(String userName);

    void deleteById(Long id);

    List<User> findAll();
}
