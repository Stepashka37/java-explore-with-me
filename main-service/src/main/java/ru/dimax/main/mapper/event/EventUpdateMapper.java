package ru.dimax.main.mapper.event;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import ru.dimax.main.model.Event;
import ru.dimax.main.model.dtos.event.UpdateEventAdminRequest;
import ru.dimax.main.model.dtos.event.UpdateEventUserRequest;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EventUpdateMapper {
    EventUpdateMapper INSTANCE = Mappers.getMapper(EventUpdateMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "requests", ignore = true)
    @Mapping(target = "initiator", ignore = true)
    @Mapping(target = "published", ignore = true)
    @Mapping(target = "views", ignore = true)
    @Mapping(target = "category", ignore = true)// ignore other fields not updated
    void updateEventFromDtoByUser(UpdateEventUserRequest dto, @MappingTarget Event event);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "requests", ignore = true)
    @Mapping(target = "initiator", ignore = true)
    @Mapping(target = "published", ignore = true)
    @Mapping(target = "views", ignore = true)
    @Mapping(target = "category", ignore = true)// ignore other fields not updated
    void updateEventFromDtoByAdmin(UpdateEventAdminRequest dto, @MappingTarget Event event);
}
