package dev.raul.atsanalyzer.repository;

import dev.raul.atsanalyzer.entity.AnalyzesRun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalysisRunRepository extends JpaRepository<AnalyzesRun, Long> {
}
