package dev.raul.atsanalyzer.service.analysis;

import dev.raul.atsanalyzer.dto.request.JobDescriptionDTO;
import dev.raul.atsanalyzer.dto.response.SendJobDescriptionResponseDTO;
import dev.raul.atsanalyzer.dto.response.TermStatDTO;
import dev.raul.atsanalyzer.repository.AnalysisRepository;
import dev.raul.atsanalyzer.service.preprocessing.NormalizationService;
import dev.raul.atsanalyzer.service.preprocessing.TextPreprocessingService;
import dev.raul.atsanalyzer.service.statistics.NGramStatisticsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor(onConstructor_ = @__(@Autowired)) @Slf4j
public class AnalysisService {
    private final AnalysisRepository analysisRepository;
    private final NormalizationService textNormalizationService;
    private final TextPreprocessingService textPreprocessingService;
    private final NGramStatisticsService nGramStatisticsService;


    public SendJobDescriptionResponseDTO createJobAnalysis(JobDescriptionDTO request){
        List<String> fullNormalization = textNormalizationService.fullNormalization(request.descriptions());
        List<List<String>> lists = textPreprocessingService.processPipeline(fullNormalization);

        List<TermStatDTO> termStatDTOS = nGramStatisticsService.computeUnigrams(lists);

        for (List<String> list : lists) {
            log.info(String.valueOf(list));
        }

        termStatDTOS.stream().limit(60).forEach(s -> log.info("{} -> count={} freq={}", s.term(), s.count(), s.relativeFrequency()*100));


        return SendJobDescriptionResponseDTO.builder()
                .descriptionCount(request.descriptions().size())
                .build();
    }
}
