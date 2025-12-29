package dev.raul.atsanalyzer.service;

import dev.raul.atsanalyzer.dto.request.JobDescriptionDTO;
import dev.raul.atsanalyzer.dto.response.SendJobDescriptionResponseDTO;
import dev.raul.atsanalyzer.repository.AnalysisRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AnalysisService {
    private final AnalysisRepository analysisRepository;

    @Autowired
    public AnalysisService(AnalysisRepository analysisRepository) {
        this.analysisRepository = analysisRepository;
    }

    @Transactional
    public SendJobDescriptionResponseDTO createJobAnalysis(JobDescriptionDTO request){
        return SendJobDescriptionResponseDTO.builder()
                .descriptionCount(request.descriptions().size())
                .build();
    }
}
