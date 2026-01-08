package dev.raul.atsanalyzer.service.analysis;

import dev.raul.atsanalyzer.dto.mapper.AnalysisRunMapper;
import dev.raul.atsanalyzer.dto.request.AnalysisRunDTO;
import dev.raul.atsanalyzer.dto.request.JobDescriptionDTO;
import dev.raul.atsanalyzer.dto.response.SendJobDescriptionResponseDTO;
import dev.raul.atsanalyzer.dto.response.TermStatDTO;
import dev.raul.atsanalyzer.entity.AnalyzesRun;
import dev.raul.atsanalyzer.repository.AnalysisRunRepository;
import dev.raul.atsanalyzer.service.preprocessing.NormalizationService;
import dev.raul.atsanalyzer.service.preprocessing.TextPreprocessingService;
import dev.raul.atsanalyzer.service.statistics.NGramStatisticsService;
import dev.raul.atsanalyzer.service.statistics.TermHighlightRulesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor(onConstructor_ = @__(@Autowired)) @Slf4j
public class AnalysisService {
    private final AnalysisRunRepository analysisRunRepository;
    private final NormalizationService textNormalizationService;
    private final TextPreprocessingService textPreprocessingService;
    private final NGramStatisticsService nGramStatisticsService;
    private final TermHighlightRulesService termHighlightRulesService;


    public SendJobDescriptionResponseDTO createJobAnalysis(JobDescriptionDTO jobDescriptionDTO){
        List<String> fullNormalization = textNormalizationService.fullNormalization(jobDescriptionDTO.descriptions());
        List<List<String>> listOfTokens = textPreprocessingService.processPipeline(fullNormalization);
        long totalTokens = listOfTokens.stream().mapToLong(List::size).sum();

        List<TermStatDTO> unigrams = nGramStatisticsService.computeUnigrams(listOfTokens);
        List<TermStatDTO> bigrams = nGramStatisticsService.computeBigrams(listOfTokens);
        List<TermStatDTO> trigrams = nGramStatisticsService.computeTrigrams(listOfTokens);

        List<TermStatDTO> highlightedUni = termHighlightRulesService.highlightTerms(unigrams, 10);
        List<TermStatDTO> highlightedBi = termHighlightRulesService.highlightTerms(bigrams, 5);
        List<TermStatDTO> highlightedTri = termHighlightRulesService.highlightTerms(trigrams, 5);

        AnalysisRunDTO analysisToSave = AnalysisRunDTO.builder()
                .totalDescriptions((long) jobDescriptionDTO.descriptions().size())
                .totalTokens(totalTokens)
                .build();

        AnalyzesRun savedAnalysis = analysisRunRepository.save(AnalysisRunMapper.INSTANCE.toModel(analysisToSave));

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
                .savedAnalysisId(savedAnalysis.getId())
                .descriptionCount(jobDescriptionDTO.descriptions().size())
                .build();
    }
}
