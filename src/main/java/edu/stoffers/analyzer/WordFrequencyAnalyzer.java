package edu.stoffers.analyzer;

import edu.stoffers.model.WordFrequencyModel;

import java.util.List;

public interface WordFrequencyAnalyzer {
    int calculateHighestFrequency(String text);
    int calculateFrequencyForWord(String text, String word);
    List<WordFrequencyModel> calculateMostFrequentNWords(String text, int n);
}
