package edu.stoffers.service;

import edu.stoffers.analyzer.WordFrequencyAnalyzer;
import edu.stoffers.model.WordFrequencyModel;
import edu.stoffers.validation.ValidateWordNotBlank;
import edu.stoffers.validation.ValidateTextNotBlank;
import edu.stoffers.validation.ValidatePositive;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WordFrequencyServiceImpl implements WordFrequencyService {

    private final WordFrequencyAnalyzer analyzer;
    private final ValidateTextNotBlank validateTextNotBlank;
    private final ValidateWordNotBlank validateWordNotBlank;
    private final ValidatePositive validatePositive;

    @Override
    public int calculateHighestFrequency(String text) {
        validateTextNotBlank.validate(text);
        return analyzer.calculateHighestFrequency(text);
    }

    @Override
    public int calculateFrequencyForWord(String text, String word) {
        validateTextNotBlank.validate(text);
        validateWordNotBlank.validate(word);
        return analyzer.calculateFrequencyForWord(text, word);
    }

    @Override
    public List<WordFrequencyModel> calculateMostFrequentNWords(String text, int n) {
        validateTextNotBlank.validate(text);
        validatePositive.validate(n);
        return analyzer.calculateMostFrequentNWords(text, n);
    }
}
