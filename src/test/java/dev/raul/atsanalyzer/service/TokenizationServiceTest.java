package dev.raul.atsanalyzer.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TokenizationServiceTest {

    @Test
    @DisplayName("Should split each text into tokens by whitespace")
    void tokenizeTexts() {
        List<String> input = List.of("java  spring boot",
                "JAVA e frameworks Spring",
                "Testes unitários integração");

        NormalizationService normalizationService = new NormalizationService();
        List<String> nomalizedInput = normalizationService.fullNormalization(input);

        TextPreprocessingService tokenizationService = new TextPreprocessingService();
        List<List<String>> tokenizedTexts = tokenizationService.tokenizeTexts(nomalizedInput);

        assertEquals(3, tokenizedTexts.size());

        assertEquals(List.of("java", "springboot"), tokenizedTexts.get(0));
        assertEquals(List.of("java", "e", "frameworks", "spring"), tokenizedTexts.get(1));
        assertEquals(List.of("testes", "unitarios", "integracao"), tokenizedTexts.get(2));

    }
}