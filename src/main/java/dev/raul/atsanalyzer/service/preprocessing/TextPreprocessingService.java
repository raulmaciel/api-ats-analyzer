package dev.raul.atsanalyzer.service.preprocessing;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TextPreprocessingService {
    private final Set<String> STOP_WORDS = new HashSet<>(List.of(
            // Diversos
            "responsabilidades", "atribuicoes", "requisitos", "qualificacoes",
            "nivel", "superior", "boa",
            // Artigos / determinantes
            "a", "o", "as", "os", "um", "uma", "uns", "umas",

            // Contrações e combinações comuns
            "ao", "aos", "da", "das", "do", "dos",
            "na", "nas", "no", "nos",
            "pela", "pelas", "pelo", "pelos",
            "dum", "duma", "duns", "dumas",
            "num", "numa", "nuns", "numas",

            // Preposições
            "de", "em", "por", "para", "com", "sem", "sob", "sobre",
            "entre", "ate", "desde", "contra", "perante", "atraves",
            "alem", "dentro", "fora", "perto", "longe", "durante",

            // Conjunções / conectivos
            "e", "ou", "mas", "porque", "pois", "que", "se",
            "como", "quando", "embora", "porem", "todavia", "contudo",
            "entao", "tambem", "apesar", "caso", "portanto", "logo",

            // Pronomes pessoais
            "eu", "tu", "ele", "ela", "nos", "vos", "eles", "elas",

            // Pronomes oblíquos / reflexivos
            "me", "te", "se", "lhe", "lhes", "nos", "vos",
            "mim", "ti", "si", "comigo", "contigo", "consigo",

            // Pronomes possessivos
            "meu", "minha", "meus", "minhas",
            "teu", "tua", "teus", "tuas",
            "seu", "sua", "seus", "suas",
            "nosso", "nossa", "nossos", "nossas",
            "vosso", "vossa", "vossos", "vossas",

            // Demonstrativos
            "este", "esta", "estes", "estas",
            "esse", "essa", "esses", "essas",
            "isto", "isso",
            "aquele", "aquela", "aqueles", "aquelas",
            "aquilo",

            // Relativos / interrogativos comuns
            "quem", "qual", "quais", "onde", "aonde",

            // Advérbios muito comuns que costumam só “encher”
            "aqui", "ali", "la",

            // Verbos auxiliares e muito frequentes
            "ser", "estar", "ter", "haver", "ir", "vir", "fazer", "poder",
            "dever", "querer", "saber",

            // Formas flexionadas comuns
            "e", "eh", "sao", "esta", "estao", "foi", "eram",
            "tem", "tenho", "tinha", "tiveram", "vai", "vao",

            // Contrações coloquiais
            "pra", "pro", "pras", "pros"));


    public List<List<String>> processPipeline(List<String> in) {
        List<List<String>> tokenizedTexts = tokenizeTexts(in);

        return tokenizedTexts.stream()
                .map(this::removeStopWords)
                .collect(Collectors.toList());
    }

    public List<List<String>> tokenizeTexts(List<String> in) {
        if (in == null) return List.of();

        return in.stream().map(s -> {
            if (s == null || s.isBlank()) {
                return Collections.<String>emptyList();
            }

            return Arrays.asList(s.trim().split("\\s+"));
        }).collect(Collectors.toList());
    }

    public List<String> removeStopWords(List<String> tokenizedTexts) {
        return tokenizedTexts.stream().filter(word -> !STOP_WORDS.contains(word)).collect(Collectors.toList());
    }
}
