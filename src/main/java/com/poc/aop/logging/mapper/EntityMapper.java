package com.poc.aop.logging.mapper;

import com.poc.aop.logging.dto.EntityDto;
import com.poc.aop.logging.entity.Entity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EntityMapper {

    EntityDto toDto(Entity entity);

    Entity toEntity(EntityDto dto);

    List<EntityDto> toDtoList(List<Entity> source);

    List<Entity> toEntityList(List<EntityDto> source);
}
