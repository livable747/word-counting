package edu.stoffers.service;

import edu.stoffers.analyzer.WordFrequencyAnalyzer;
import edu.stoffers.validation.ValidatePositive;
import edu.stoffers.validation.ValidateTextNotBlank;
import edu.stoffers.validation.ValidateWordNotBlank;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WordFrequencyServiceImplTest {

    @Mock
    private WordFrequencyAnalyzer analyzer;
    @Mock
    private ValidateTextNotBlank validateTextNotBlank;
    @Mock
    private ValidateWordNotBlank validateWordNotBlank;
    @Mock
    private ValidatePositive validatePositive;

    @InjectMocks
    private WordFrequencyServiceImpl service;

    @Test
    @DisplayName("verify calculateHighestFrequency input validation before analyzing")
    void testCalculateHighestFrequencyCallsValidations() {
        String text = "hello world";
        when(analyzer.calculateHighestFrequency(text)).thenReturn(1);

        service.calculateHighestFrequency(text);

        verify(validateTextNotBlank).validate(text);
        verify(analyzer).calculateHighestFrequency(text);
    }

    @Test
    @DisplayName("verify calculateFrequencyForWord input validation before analyzing")
    void testCalculateFrequencyForWordCallsValidations() {
        String text = "hello world";
        String word = "hello";
        when(analyzer.calculateFrequencyForWord(text, word)).thenReturn(1);

        service.calculateFrequencyForWord(text, word);

        verify(validateTextNotBlank).validate(text);
        verify(validateWordNotBlank).validate(word);
        verify(analyzer).calculateFrequencyForWord(text, word);
    }

    @Test
    @DisplayName("verify calculateMostFrequentNWords input validation before analyzing")
    void testCalculateMostFrequentNWordsCallsValidations() {
        String text = "hello world";
        int n = 3;
        when(analyzer.calculateMostFrequentNWords(text, n)).thenReturn(Collections.emptyList());

        service.calculateMostFrequentNWords(text, n);

        verify(validateTextNotBlank).validate(text);
        verify(validatePositive).validate(n);
        verify(analyzer).calculateMostFrequentNWords(text, n);
    }
}
