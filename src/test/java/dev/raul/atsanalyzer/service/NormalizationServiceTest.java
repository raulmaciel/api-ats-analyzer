package dev.raul.atsanalyzer.service;

import dev.raul.atsanalyzer.service.preprocessing.NormalizationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class NormalizationServiceTest {

    @Test
    @DisplayName("Should return full normalized to: lowercase/accent, pontuation, multiple spcaes, semantic, variations of microsservices")
    void fullNormalizationSuccessCase1() {
        List<String> nonNormalizedText = List.of("Ação",
                "Java;Spring",
                "java  spring",
                "spring boot",
                "spring-boot",
                "micro-serviços usam microserviços, que gostam de microsservicos",
                "C# e .NET",
                "RESTful");

        NormalizationService normalizationService = new NormalizationService();

        List<String> out = normalizationService.fullNormalization(nonNormalizedText);
        assertEquals(8, out.size());
        assertEquals("acao", out.get(0));
        assertEquals("java spring", out.get(1));
        assertEquals("java spring", out.get(2));
        assertEquals("springboot", out.get(3));
        assertEquals("springboot", out.get(4));
        assertEquals("microservices usam microservices que gostam de microservices", out.get(5));
        assertEquals("csharp e dotnet", out.get(6));
        assertEquals("rest", out.get(7));

    }


}