package ru.dimax.main.model.dtos.request;

import lombok.Builder;
import lombok.Data;
import ru.dimax.main.model.State;

import java.util.List;

@Builder
@Data
public class EventRequestStatusUpdateRequest {

    private List<Long> requestIds;

    private State status;
}
