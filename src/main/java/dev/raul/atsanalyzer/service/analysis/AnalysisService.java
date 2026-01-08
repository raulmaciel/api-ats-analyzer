package dev.raul.atsanalyzer.service.analysis;

import dev.raul.atsanalyzer.dto.request.AnalysisRunDTO;
import dev.raul.atsanalyzer.dto.request.JobDescriptionDTO;
import dev.raul.atsanalyzer.dto.response.SendJobDescriptionResponseDTO;
import dev.raul.atsanalyzer.dto.response.TermStatDTO;
import dev.raul.atsanalyzer.entity.AnalyzesRun;
import dev.raul.atsanalyzer.service.preprocessing.NormalizationService;
import dev.raul.atsanalyzer.service.preprocessing.TextPreprocessingService;
import dev.raul.atsanalyzer.service.statistics.NGramStatisticsService;
import dev.raul.atsanalyzer.service.statistics.TermHighlightRulesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor(onConstructor_ = @__(@Autowired)) @Slf4j
public class AnalysisService {
    private final NormalizationService textNormalizationService;
    private final TextPreprocessingService textPreprocessingService;
    private final NGramStatisticsService nGramStatisticsService;
    private final TermHighlightRulesService termHighlightRulesService;
    private final AnalysisRunService analysisRunService;
    private final TermStatPersistenceService termStatPersistenceService;


    public SendJobDescriptionResponseDTO createJobAnalysis(JobDescriptionDTO jobDescriptionDTO){
        List<String> fullNormalization = textNormalizationService.fullNormalization(jobDescriptionDTO.descriptions());

        List<List<String>> listOfTokens = textPreprocessingService.processPipeline(fullNormalization);
        long totalTokens = listOfTokens.stream().mapToLong(List::size).sum();

        List<TermStatDTO> unigrams = nGramStatisticsService.computeUnigrams(listOfTokens);
        List<TermStatDTO> bigrams = nGramStatisticsService.computeBigrams(listOfTokens);
        List<TermStatDTO> trigrams = nGramStatisticsService.computeTrigrams(listOfTokens);


        Set<String> highlightedUni = termHighlightRulesService.highlightTerms(unigrams, 10);
        Set<String> highlightedBi = termHighlightRulesService.highlightTerms(bigrams, 5);
        Set<String> highlightedTri = termHighlightRulesService.highlightTerms(trigrams, 5);

        AnalysisRunDTO analysisToSave = AnalysisRunDTO.builder()
                .totalDescriptions((long) jobDescriptionDTO.descriptions().size())
                .totalTokens(totalTokens)
                .build();

        AnalyzesRun savedAnalyzesRun = analysisRunService.saveAnalysisRun(analysisToSave);

        termStatPersistenceService.saveBatch(savedAnalyzesRun, 1, unigrams, highlightedUni);
        termStatPersistenceService.saveBatch(savedAnalyzesRun, 2, bigrams, highlightedBi);
        termStatPersistenceService.saveBatch(savedAnalyzesRun, 3, trigrams, highlightedTri);


//        for (TermStatDTO highlightedTerm : highlightedUni) {
//            log.info("Highlighted: {} -> count={} freq={}% ",
//                    highlightedTerm.term(),
//                    highlightedTerm.count(),
//                    String.format(Locale.US, "%.2f", highlightedTerm.relativeFrequency()*100));
//        }

        return SendJobDescriptionResponseDTO.builder()
                .savedAnalysisId(savedAnalyzesRun.getId())
                .descriptionCount(jobDescriptionDTO.descriptions().size())
                .build();
    }
}
