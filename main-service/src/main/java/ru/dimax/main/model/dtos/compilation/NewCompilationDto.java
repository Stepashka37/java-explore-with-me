package ru.dimax.main.model.dtos.compilation;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NewCompilationDto {

    private List<Long> events;

    @Builder.Default
    private Boolean pinned = false;

    @Size(min = 1, max = 50)
    @NotBlank
    private String title;

}
