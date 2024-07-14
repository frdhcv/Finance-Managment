package org.example.financemanagment.domain.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T, ID> {
    T save(T entity);
    List<T> findAll();
    Optional<T> findById(ID id);
    void deleteById(ID id);
}