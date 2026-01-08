package dev.raul.atsanalyzer.service.analysis;

import dev.raul.atsanalyzer.dto.response.TermStatDTO;
import dev.raul.atsanalyzer.entity.AnalyzesRun;
import dev.raul.atsanalyzer.entity.TermStat;
import dev.raul.atsanalyzer.repository.TermStatRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
public class TermStatPersistenceService {
    private final TermStatRepository termStatRepository;

    public void saveBatch(AnalyzesRun savedAnalysis, int nGrams, List<TermStatDTO> computedNgrams, Set<String> highlightedTerms){
        if(computedNgrams == null || computedNgrams.isEmpty()) return;

        List<TermStat> entities = computedNgrams.stream()
                .map(dto -> TermStat.builder()
                        .analyzesRun(savedAnalysis)
                        .nGram(nGrams)
                        .term(dto.term())
                        .count(dto.count())
                        .frequencyPercent(dto.relativeFrequency() * 100)
                        .highlighted(highlightedTerms.contains(dto.term()))
                        .build()
                ).toList();


        termStatRepository.saveAll(entities);
    }
}
