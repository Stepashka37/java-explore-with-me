package ru.dimax.main.service.compilation;

import ru.dimax.main.model.dtos.compilation.CompilationDto;
import ru.dimax.main.model.dtos.compilation.NewCompilationDto;
import ru.dimax.main.model.dtos.compilation.UpdateCompilationRequest;

import java.util.List;

public interface CompilationService {

    CompilationDto addCompilation(NewCompilationDto newCompilation);

    void deleteCompilation(Long compId);

    CompilationDto updateCompilation(Long compId, UpdateCompilationRequest request);

    List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size);

    CompilationDto getCompilationById(Long compId);
}
