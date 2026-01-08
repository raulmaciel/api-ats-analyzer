package dev.raul.atsanalyzer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Builder
public record SendJobDescriptionResponseDTO(UUID savedAnalysisId, int descriptionCount) {
}
