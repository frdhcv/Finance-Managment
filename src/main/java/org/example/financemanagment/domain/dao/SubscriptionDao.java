package org.example.financemanagment.domain.dao;

import java.util.List;
import java.util.Optional;

public class SubscriptionDao implements Dao {
    @Override
    public Object save(Object entity) {
        return null;
    }

    @Override
    public List findAll() {
        return List.of();
    }

    @Override
    public Optional findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {

    }
}
