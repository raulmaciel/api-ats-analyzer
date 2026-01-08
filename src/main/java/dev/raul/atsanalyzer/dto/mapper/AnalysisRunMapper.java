package dev.raul.atsanalyzer.dto.mapper;

import dev.raul.atsanalyzer.dto.request.AnalysisRunDTO;
import dev.raul.atsanalyzer.dto.request.JobDescriptionDTO;
import dev.raul.atsanalyzer.entity.AnalyzesRun;
import dev.raul.atsanalyzer.entity.JobDescription;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AnalysisRunMapper {
    AnalysisRunMapper INSTANCE = Mappers.getMapper(AnalysisRunMapper.class);

    AnalyzesRun toModel(AnalysisRunDTO analysisRunDTO);
    AnalysisRunDTO toDTO(AnalyzesRun analyzesRun);
}
