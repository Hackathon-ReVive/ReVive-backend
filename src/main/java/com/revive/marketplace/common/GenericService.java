package com.revive.marketplace.common;

import java.util.List;
import java.util.Optional;

public interface GenericService<T, ID> {
    T getById(ID id);
    List<T> getAll();
    T save(T entity);
    void deleteById(ID id);
    Optional<T> getByField(String fieldName, Object value);
}

