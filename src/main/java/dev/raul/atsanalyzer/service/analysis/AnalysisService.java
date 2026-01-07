package dev.raul.atsanalyzer.service.analysis;

import dev.raul.atsanalyzer.dto.request.JobDescriptionDTO;
import dev.raul.atsanalyzer.dto.response.SendJobDescriptionResponseDTO;
import dev.raul.atsanalyzer.dto.response.TermStatDTO;
import dev.raul.atsanalyzer.repository.AnalysisRepository;
import dev.raul.atsanalyzer.service.preprocessing.NormalizationService;
import dev.raul.atsanalyzer.service.preprocessing.TextPreprocessingService;
import dev.raul.atsanalyzer.service.statistics.NGramStatisticsService;
import dev.raul.atsanalyzer.service.statistics.TermHighlightRulesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
@AllArgsConstructor(onConstructor_ = @__(@Autowired)) @Slf4j
public class AnalysisService {
    private final AnalysisRepository analysisRepository;
    private final NormalizationService textNormalizationService;
    private final TextPreprocessingService textPreprocessingService;
    private final NGramStatisticsService nGramStatisticsService;
    private final TermHighlightRulesService termHighlightRulesService;


    public SendJobDescriptionResponseDTO createJobAnalysis(JobDescriptionDTO request){
        List<String> fullNormalization = textNormalizationService.fullNormalization(request.descriptions());
        List<List<String>> lists = textPreprocessingService.processPipeline(fullNormalization);

        List<TermStatDTO> unigrams = nGramStatisticsService.computeUnigrams(lists);
        List<TermStatDTO> bigrams = nGramStatisticsService.computeBigrams(lists);
        List<TermStatDTO> trigrams = nGramStatisticsService.computeTrigrams(lists);

        List<TermStatDTO> highlightedUni = termHighlightRulesService.highlightTerms(unigrams, 10);
        List<TermStatDTO> highlightedBi = termHighlightRulesService.highlightTerms(bigrams, 5);
        List<TermStatDTO> highlightedTri = termHighlightRulesService.highlightTerms(trigrams, 5);

//        for(int i = 0; i<200; i++){
//            log.info("{} -> count={} freq={}%",
//                    termStatDTOS.get(i).term(),
//                    termStatDTOS.get(i).count(),
//                    String.format(java.util.Locale.US, "%.2f", termStatDTOS.get(i).relativeFrequency() * 100)
//            );
//        }

        for (TermStatDTO highlightedTerm : highlightedUni) {
            log.info("Highlighted: {} -> count={} freq={}% ",
                    highlightedTerm.term(),
                    highlightedTerm.count(),
                    String.format(Locale.US, "%.2f", highlightedTerm.relativeFrequency()*100));
        }




        return SendJobDescriptionResponseDTO.builder()
                .descriptionCount(request.descriptions().size())
                .build();
    }
}
