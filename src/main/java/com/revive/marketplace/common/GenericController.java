package com.revive.marketplace.common;

import org.springframework.http.ResponseEntity;
import java.util.List;

public interface GenericController<T, ID> {
    ResponseEntity<T> getById(ID id);
    ResponseEntity<List<T>> getAll();
    ResponseEntity<T> save(T entity);
    ResponseEntity<Void> deleteById(ID id);
}

