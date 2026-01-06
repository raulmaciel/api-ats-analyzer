package dev.raul.atsanalyzer.service.statistics;

import dev.raul.atsanalyzer.dto.response.TermStatDTO;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NGramStatisticsService {
    public List<TermStatDTO> computeUnigrams(List<List<String>> tokenizedText) {
        if (tokenizedText == null) return List.of();

        List<String> allTokens = getFlattenedList(tokenizedText);

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

    public List<TermStatDTO> computeBigrams(List<List<String>> tokenizedText){
        if (tokenizedText == null) return List.of();

        List<String> wordsList = getFlattenedList(tokenizedText);
        List<String> bigrams = new ArrayList<>();

        for(int i = 0; i < wordsList.size() - 1; i++){
            String bigram = wordsList.get(i) + " " + wordsList.get(i + 1);
            bigrams.add(bigram);
        }

        Map<String, Long> counts = bigrams.stream()
                .collect(Collectors.groupingBy(term -> term, Collectors.counting()));

        return counts.entrySet().stream()
                .map(e -> new TermStatDTO(
                        e.getKey(),
                        e.getValue(),
                        (double) e.getValue() / bigrams.size()
                ))
                .sorted(Comparator
                        .comparing(TermStatDTO::count).reversed()
                        .thenComparing(TermStatDTO::term))
                .toList();
    }

    private static List<String> getFlattenedList(List<List<String>> tokenizedText) {
        List<String> allTokens = tokenizedText.stream()
                .flatMap(List::stream)
                .toList();
        return allTokens;
    }


}
