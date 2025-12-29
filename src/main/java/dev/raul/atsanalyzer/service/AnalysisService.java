package dev.raul.atsanalyzer.service;

import dev.raul.atsanalyzer.dto.request.JobDescriptionDTO;
import dev.raul.atsanalyzer.dto.response.SendJobDescriptionResponseDTO;
import dev.raul.atsanalyzer.repository.AnalysisRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor_ = @__(@Autowired)) @Slf4j
public class AnalysisService {
    private final AnalysisRepository analysisRepository;
    private final TextNormalizationService textNormalizationService;


    public SendJobDescriptionResponseDTO createJobAnalysis(JobDescriptionDTO request){
        List<String> minimumNormalization = textNormalizationService.minimumNormalization(request.descriptions());
        for (String text : minimumNormalization){
            log.info(text);
        }


        return SendJobDescriptionResponseDTO.builder()
                .descriptionCount(request.descriptions().size())
                .build();
    }
}
