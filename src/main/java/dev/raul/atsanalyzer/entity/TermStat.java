package dev.raul.atsanalyzer.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TermStat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "analyzes_run_id", nullable = false)
    private AnalyzesRun analyzesRun;

    @Column(nullable = false, length = 255)
    private String term;

    @Column(nullable = false)
    private Integer nGram;

    @Column(nullable = false)
    private Long count;

    @Column(name = "relative_frequency", nullable = false)
    private Double frequencyPercent;

    @Column(nullable = false)
    private boolean highlighted;

}
