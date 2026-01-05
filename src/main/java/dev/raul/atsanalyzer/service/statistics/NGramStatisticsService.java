package dev.raul.atsanalyzer.service.statistics;

import dev.raul.atsanalyzer.dto.response.TermStatDTO;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NGramStatisticsService {
    public List<TermStatDTO> computeUnigrams(List<List<String>> tokenizedText) {
        if (tokenizedText == null) return List.of();

        List<String> allTokens = tokenizedText.stream()
                .flatMap(List::stream)
                .toList();

        long totalTokensCount = allTokens.size();
        if (totalTokensCount == 0) return List.of();

        Map<String, Long> counts = allTokens.stream()
                .collect(Collectors.groupingBy(term -> term, Collectors.counting()));


        return counts.entrySet().stream()
                .map(e -> new TermStatDTO(
                        e.getKey(),
                        e.getValue(),
                        (double) e.getValue() / totalTokensCount
                ))
                .sorted(Comparator
                        .comparing(TermStatDTO::count).reversed()
                        .thenComparing(TermStatDTO::term))
                .toList();
    }
}
