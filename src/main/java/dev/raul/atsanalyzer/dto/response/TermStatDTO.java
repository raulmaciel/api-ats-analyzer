package dev.raul.atsanalyzer.dto.response;

import lombok.Builder;

@Builder
public record TermStatDTO(String term, long count, double relativeFrequency) {
}
