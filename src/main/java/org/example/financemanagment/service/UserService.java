package org.example.financemanagment.service;

import org.example.financemanagment.domain.dao.UserRepository;
import org.example.financemanagment.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User register(String firstName, String lastName, String userName, String email, String phoneNumber, String password) {
        User user = new User(firstName, lastName, userName, email, phoneNumber, password);
        return userRepository.save(user);
    }

    public boolean login(String userName, String password) {
        Optional<User> user = userRepository.findByUserName(userName);
        return user.isPresent() && user.get().getPassword().equals(password);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
