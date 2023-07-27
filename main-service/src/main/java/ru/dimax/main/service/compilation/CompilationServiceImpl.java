package ru.dimax.main.service.compilation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.dimax.main.exception.EntityNotFoundException;
import ru.dimax.main.mapper.compilation.CompilationUpdateMapper;
import ru.dimax.main.model.Compilation;
import ru.dimax.main.model.Event;
import ru.dimax.main.model.dtos.compilation.CompilationDto;
import ru.dimax.main.model.dtos.compilation.NewCompilationDto;
import ru.dimax.main.model.dtos.compilation.UpdateCompilationRequest;
import ru.dimax.main.repository.CompilationRepository;
import ru.dimax.main.repository.EventRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.dimax.main.mapper.compilation.CompilationMapper.*;

@Service
@Slf4j
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    public CompilationServiceImpl(CompilationRepository compilationRepository, EventRepository eventRepository) {
        this.compilationRepository = compilationRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public CompilationDto addCompilation(NewCompilationDto newCompilation) {

        Compilation compilationToSave = newCompilationToModel(newCompilation);

        List<Long> eventIds = newCompilation.getEvents();

        List<Event> events = new ArrayList<>();

        if (eventIds != null) {
            events = eventRepository.findAllById(eventIds);
        }

        compilationToSave.setEvents(events);

        Compilation compilationSaved = compilationRepository.save(compilationToSave);

        return modelToDto(compilationSaved);
    }

    @Override
    public void deleteCompilation(Long compId) {

        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Compilation {} not found", compId)));

        compilationRepository.deleteById(compId);

    }

    @Override
    public CompilationDto updateCompilation(Long compId, UpdateCompilationRequest request) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Compilation {} not found", compId)));

        CompilationUpdateMapper.INSTANCE.updateCompilation(request, compilation);

        if (request.getEvents() != null) {
            List<Event> events = eventRepository.findAllById(request.getEvents());
            compilation.setEvents(events);
        }

        Compilation compilationUPD = compilationRepository.save(compilation);
        return modelToDto(compilationUPD);
    }

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {

        int page = from/size;
        Pageable pageable = PageRequest.of(page, size);

        List<Compilation> compilation = compilationRepository.findAll(pageable).getContent();

        if (pinned != null) {
            compilation  = compilationRepository.findAllByPinned(pinned, pageable);
        }


        return compilation.stream()
                .map(x -> modelToDto(x))
                .collect(Collectors.toList());
    }

    @Override
    public CompilationDto getCompilationById(Long compId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Compilation {} not found", compId)));

        return modelToDto(compilation);
    }
}
