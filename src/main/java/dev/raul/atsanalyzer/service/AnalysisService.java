package dev.raul.atsanalyzer.service;

import dev.raul.atsanalyzer.dto.request.JobDescriptionDTO;
import dev.raul.atsanalyzer.dto.response.SendJobDescriptionResponseDTO;
import dev.raul.atsanalyzer.repository.AnalysisRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor_ = @__(@Autowired)) @Slf4j
public class AnalysisService {
    private final AnalysisRepository analysisRepository;
    private final NormalizationService textNormalizationService;
    private final TokenizationService tokenizationService;


    public SendJobDescriptionResponseDTO createJobAnalysis(JobDescriptionDTO request){
        List<String> fullNormalization = textNormalizationService.fullNormalization(request.descriptions());

        List<List<String>> tokenizedTexts = tokenizationService.tokenizeTexts(fullNormalization);

        log.info("Descriptions: {}", fullNormalization.size());
        for (List<String> tokenizedText : tokenizedTexts) {
            for (String s : tokenizedText) {

                log.info("Tokenized word: {}", s);
            }

        }





        return SendJobDescriptionResponseDTO.builder()
                .descriptionCount(request.descriptions().size())
                .build();
    }
}
