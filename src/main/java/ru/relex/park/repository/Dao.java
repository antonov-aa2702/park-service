package ru.relex.park.repository;

import java.util.List;
import java.util.Optional;

public interface Dao<K, E> {
    K save(E entity);

    Optional<E> findById(K id);

    List<E> findAll();

    void update(E entity);

    boolean delete(K id);
}