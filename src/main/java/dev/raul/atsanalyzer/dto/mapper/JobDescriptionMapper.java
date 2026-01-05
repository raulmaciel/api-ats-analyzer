package dev.raul.atsanalyzer.dto.mapper;

import dev.raul.atsanalyzer.dto.request.JobDescriptionDTO;
import dev.raul.atsanalyzer.entity.JobDescription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface JobDescriptionMapper {
    JobDescriptionMapper INSTANCE = Mappers.getMapper(JobDescriptionMapper.class);

    JobDescription toModel(JobDescriptionDTO jobDescriptionDTO);
    JobDescriptionDTO toDTO(JobDescription jobDescription);


}
