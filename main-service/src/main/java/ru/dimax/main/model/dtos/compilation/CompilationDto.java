package ru.dimax.main.model.dtos.compilation;

import lombok.Builder;
import lombok.Data;
import ru.dimax.main.model.dtos.event.EventShortDto;

import java.util.List;

@Data
@Builder
public class CompilationDto {

    private List<EventShortDto> events;

    private Long id;

    private Boolean pinned;

    private String title;

}
