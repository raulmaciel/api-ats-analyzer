package dev.raul.atsanalyzer.service;

import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Service
public class TextNormalizationService {
    public List<String> minimumNormalization(List<String> descriptions){
        if (descriptions == null) return List.of();

        return descriptions.stream()
                .map(TextNormalizationService::textNormalizer)
                .collect(Collectors.toList());
    }

    private static String textNormalizer(String text){
        if (text == null) return "";

        String normalizeText = Normalizer.normalize(text, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase()
                .replaceAll("\\p{Punct}", " ")
                .replaceAll("\\s+", " ").trim();


        return normalizeText;
    }
}
