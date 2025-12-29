package dev.raul.atsanalyzer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
public record SendJobDescriptionResponseDTO(int descriptionCount) {
}
