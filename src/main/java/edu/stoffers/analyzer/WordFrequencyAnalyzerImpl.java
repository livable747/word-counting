package edu.stoffers.analyzer;

import edu.stoffers.model.WordFrequencyModel;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class WordFrequencyAnalyzerImpl implements WordFrequencyAnalyzer {

    private List<WordFrequencyModel> extractWordFrequencies(String text) {
        return Arrays.stream(text.split("[^\\p{L}]+"))
                .filter(Predicate.not(String::isEmpty))
                .map(String::toLowerCase)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(w -> 1)))
                .entrySet()
                .stream()
                .map(e -> new WordFrequencyModel(e.getKey(), e.getValue()))
                .toList();
    }

    @Override
    public int calculateHighestFrequency(String text) {
        return extractWordFrequencies(text).stream()
                .mapToInt(WordFrequencyModel::getFrequency)
                .max()
                .orElse(0);
    }

    @Override
    public int calculateFrequencyForWord(String text, String word) {
        return extractWordFrequencies(text).stream()
                .filter(wf -> wf.getWord().equalsIgnoreCase(word))
                .mapToInt(WordFrequencyModel::getFrequency)
                .findFirst()
                .orElse(0);
    }

    @Override
    public List<WordFrequencyModel> calculateMostFrequentNWords(String text, int n) {
        return extractWordFrequencies(text).stream()
                .sorted(Comparator.comparingInt(WordFrequencyModel::getFrequency)
                        .reversed()
                        .thenComparing(WordFrequencyModel::getWord)
                )
                .limit(n)
                .toList();
    }
}
