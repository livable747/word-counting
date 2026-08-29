package edu.stoffers.model;

public class WordFrequencyModel implements WordFrequency {

    private final String word;
    private final int frequency;

    public WordFrequencyModel(String word, int frequency) {
        this.word = word;
        this.frequency = frequency;
    }

    @Override
    public String getWord() {
        return word;
    }

    @Override
    public int getFrequency() {
        return frequency;
    }
}
