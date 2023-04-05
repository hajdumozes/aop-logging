package com.poc.aop.logging.web;

import com.poc.aop.logging.dto.EntityDto;
import com.poc.aop.logging.entity.Entity;
import com.poc.aop.logging.mapper.EntityMapper;
import com.poc.aop.logging.service.EntityServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/entities")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EntityController {
    EntityServiceImpl service;
    EntityMapper entityMapper;

    @GetMapping
    public ResponseEntity<List<EntityDto>> findEntities() {
        List<Entity> entities = service.findAll();
        return ResponseEntity.ok(entityMapper.toDtoList(entities));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EntityDto> findEntity(@PathVariable Integer id) {
        return service.findById(id)
                .map(entityMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> persistEntity(@RequestBody EntityDto dto) {
        service.persist(entityMapper.toEntity(dto));
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody EntityDto dto) {
        service.update(entityMapper.toEntity(dto), id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteEntity(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
