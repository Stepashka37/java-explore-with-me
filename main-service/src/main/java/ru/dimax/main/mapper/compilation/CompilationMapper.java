package ru.dimax.main.mapper.compilation;

import lombok.experimental.UtilityClass;
import ru.dimax.main.model.Compilation;
import ru.dimax.main.model.dtos.compilation.CompilationDto;
import ru.dimax.main.model.dtos.event.EventShortDto;
import ru.dimax.main.model.dtos.compilation.NewCompilationDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.dimax.main.mapper.event.EventMapper.*;

@UtilityClass
public class CompilationMapper {

    public Compilation newCompilationToModel(NewCompilationDto dto) {
        return Compilation.builder()
                .id(0L)
                .title(dto.getTitle())
                .pinned(dto.getPinned())
                .events(new ArrayList<>())
                .build();
    }

    public CompilationDto modelToDto(Compilation compilation) {

        List<EventShortDto> events = compilation.getEvents().stream()
                .map(x -> eventToShortDto(x))
                .collect(Collectors.toList());

        return CompilationDto.builder()
                .id(compilation.getId())
                .pinned(compilation.getPinned())
                .title(compilation.getTitle())
                .events(events)
                .build();
    }
}
