package dev.raul.atsanalyzer.service.analysis;

import dev.raul.atsanalyzer.dto.mapper.AnalysisRunMapper;
import dev.raul.atsanalyzer.dto.request.AnalysisRunDTO;
import dev.raul.atsanalyzer.entity.AnalyzesRun;
import dev.raul.atsanalyzer.repository.AnalysisRunRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
public class AnalysisRunService {
    private final AnalysisRunRepository analysisRunRepository;

    public AnalyzesRun saveAnalysisRun(AnalysisRunDTO analysisRunDTO){
        return analysisRunRepository.save(AnalysisRunMapper.INSTANCE.toModel(analysisRunDTO));
    }
}
