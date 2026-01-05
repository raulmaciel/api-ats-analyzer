package dev.raul.atsanalyzer.service.preprocessing;

import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Service
public class NormalizationService {
    private static final Map<String, String> SEMANTIC_MAP = Map.of(
            "spring-boot","springboot",
            "spring boot","springboot",
            "microservicos","microservices",
            "micro servicos","microservices",
            "micro-serviços","microservices",
            "microsservicos","microservices",
            "restful","rest",
            "rest api","api rest"
    );

    public List<String> fullNormalization(List<String> descriptions) {
        if (descriptions == null) return List.of();

        return descriptions.stream()
                .map(NormalizationService::basicTextNormalizer)
                .map(this::semanticNormalizer)
                .collect(Collectors.toList());
    }


    private String semanticNormalizer(String text){
        String result = text;

        for (Map.Entry<String, String> entry : SEMANTIC_MAP.entrySet()){
            String regex = "\\b" + Pattern.quote(entry.getKey()) + "\\b";
            result = result.replaceAll(regex, entry.getValue());
        }

        return result.replaceAll("\\s+", " ").trim();
    }


    private static String basicTextNormalizer(String text) {
        if (text == null) return "";

        String t = text.toLowerCase();
        t = t.replace("c#", "csharp");
        t = t.replace(".net", "dotnet");

        String normalizeText = Normalizer.normalize(t, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .replaceAll("\\p{Punct}", " ")
                .replaceAll("\\s+", " ").trim();


        return normalizeText;
    }
}
