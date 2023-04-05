package com.poc.aop.logging.service;

import com.poc.aop.logging.entity.Entity;
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
public class EntityServiceImpl implements EntityService {
    EntityRepository repository;

    @Override
    @LogExecutionTime
    public List<Entity> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Entity> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public void persist(Entity entity) {
        repository.save(entity);
    }

    @Override
    public void update(Entity entity, Integer id) {
        repository.findById(id)
                .ifPresent(existing -> repository.save(entity));
    }

    @Override
    public void delete(Integer id) {
        repository.findById(id).ifPresentOrElse(repository::delete, this::throwEntityNotFoundException);
    }

}
