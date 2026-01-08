package dev.raul.atsanalyzer.repository;

import dev.raul.atsanalyzer.entity.TermStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TermStatRepository extends JpaRepository<TermStat, Long> {
}
