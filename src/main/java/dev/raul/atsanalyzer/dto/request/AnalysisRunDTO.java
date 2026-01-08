package dev.raul.atsanalyzer.dto.request;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AnalysisRunDTO(Long totalDescriptions, Long totalTokens) {
}
