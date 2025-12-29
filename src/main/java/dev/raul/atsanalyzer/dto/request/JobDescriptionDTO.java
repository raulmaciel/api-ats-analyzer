package dev.raul.atsanalyzer.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record JobDescriptionDTO(@NotEmpty List<@NotBlank String> descriptions) {
}
