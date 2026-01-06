package dev.raul.atsanalyzer.service.statistics;

import dev.raul.atsanalyzer.dto.response.TermStatDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TermHighlightRulesService {
    public List<TermStatDTO> highlightTerms(List<TermStatDTO> termStatList, int minCount){
        return termStatList.stream()
                .filter(term -> term.count() >= minCount)
                .toList();
    }


}
