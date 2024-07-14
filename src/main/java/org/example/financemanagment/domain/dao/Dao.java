package org.example.financemanagment.domain.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    T save(T entity);

    List<T> findAll();

    Optional<T> findById(Long id);

    void deleteById(Long id);
}
