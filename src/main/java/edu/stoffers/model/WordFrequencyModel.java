package edu.stoffers.model;

public record WordFrequencyModel(String word, int frequency) implements WordFrequency {

    @Override
    public String getWord() {
        return word;
    }

    @Override
    public int getFrequency() {
        return frequency;
    }
}
