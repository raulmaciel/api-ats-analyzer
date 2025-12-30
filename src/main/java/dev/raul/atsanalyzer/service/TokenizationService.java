package dev.raul.atsanalyzer.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TokenizationService {
    public List<List<String>> tokenizeTexts(List<String> in){
        if (in == null) return List.of();

        List<List<String>> out = new ArrayList<>();

        for (String s : in) {
            if (s == null || s.isBlank()){
                out.add(List.of());
                continue;
            }
            out.add(List.of(s.trim().split("\\s+")));
        }
        return out;
    }
}
