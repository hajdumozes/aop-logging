package com.poc.aop.logging.service;

import com.poc.aop.logging.entity.Entity;
import com.poc.aop.logging.exception.EntityNotFoundException;
import com.poc.aop.logging.log.LogExecutionTime;
import com.poc.aop.logging.repository.EntityRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EntityService {
    EntityRepository repository;

    @LogExecutionTime
    public List<Entity> findAll() {
        return repository.findAll();
    }

    public Optional<Entity> findById(Integer id) {
        return repository.findById(id);
    }

    public void persist(Entity entity) {
        repository.save(entity);
    }

    public void update(Integer id, Entity entity) {
        repository.findById(id)
                .ifPresent(existing -> repository.save(entity));
    }

    public void delete(Integer id) {
        repository.findById(id).ifPresentOrElse(repository::delete, this::throwEntityNotFoundException);
    }

    private void throwEntityNotFoundException() {
        throw new EntityNotFoundException();
    }
}
