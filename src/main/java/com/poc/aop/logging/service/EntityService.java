package com.poc.aop.logging.service;

import com.poc.aop.logging.entity.Entity;
import com.poc.aop.logging.exception.EntityNotFoundException;
import com.poc.aop.logging.log.LogExecutionTime;

import java.util.List;
import java.util.Optional;

public interface EntityService {
    @LogExecutionTime
    List<Entity> findAll();

    Optional<Entity> findById(Integer id);

    void persist(Entity entity);

    void update(Entity entity, Integer id);

    void delete(Integer id);

    default void throwEntityNotFoundException() {
        throw new EntityNotFoundException();
    }
}
