package ru.dimax.main.mapper.compilation;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import ru.dimax.main.model.Compilation;
import ru.dimax.main.model.dtos.compilation.UpdateCompilationRequest;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CompilationUpdateMapper {
    CompilationUpdateMapper INSTANCE = Mappers.getMapper(CompilationUpdateMapper.class);

    @Mapping(target = "events", ignore = true)
    void updateCompilation(UpdateCompilationRequest dto, @MappingTarget Compilation compilation);
}
