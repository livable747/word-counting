package edu.stoffers.service;

import edu.stoffers.model.WordFrequencyModel;

import java.util.List;

public interface WordFrequencyService {

    int calculateHighestFrequency(String text);

    int calculateFrequencyForWord(String text, String word);

    List<WordFrequencyModel> calculateMostFrequentNWords(String text, int n);
}
