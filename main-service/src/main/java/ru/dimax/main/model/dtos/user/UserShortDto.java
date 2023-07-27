package ru.dimax.main.model.dtos.user;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserShortDto {
    private Long id;
    private String name;
}
