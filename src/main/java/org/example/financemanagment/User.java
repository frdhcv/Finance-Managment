package org.example.financemanagment;

import java.util.List;

public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String phoneNumber;
    private String password;
    private List<Subscription> subscriptions;
    private List<Loan> loans;

    // Constructors, getters, and setters

    public User() {}

    public User(Long id, String firstName, String lastName, String userName, String email, String phoneNumber, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    // Getters and Setters
}
