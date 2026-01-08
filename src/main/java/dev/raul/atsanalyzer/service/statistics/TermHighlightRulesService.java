package dev.raul.atsanalyzer.service.statistics;

import dev.raul.atsanalyzer.dto.response.TermStatDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TermHighlightRulesService {

    public Set<String> highlightTerms(List<TermStatDTO> stats, int minCount) {
        return stats.stream()
                .filter(s -> s.count() >= minCount)
                .map(TermStatDTO::term)
                .collect(Collectors.toSet());
    }


    public List<TermStatDTO> highlightTopNTerms(List<TermStatDTO> computedNGrams, int topN) {
        if (topN > computedNGrams.size()) {
            return computedNGrams.stream()
                    .limit(computedNGrams.size())
                    .toList();
        }

        return computedNGrams.stream()
                .limit(topN)
                .toList();
    }


}
