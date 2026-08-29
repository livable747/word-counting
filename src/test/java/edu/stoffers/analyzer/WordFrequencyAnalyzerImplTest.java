package edu.stoffers.analyzer;

import edu.stoffers.model.WordFrequencyModel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class WordFrequencyAnalyzerImplTest {

    private final WordFrequencyAnalyzerImpl analyzer = new WordFrequencyAnalyzerImpl();

    @Test
    @DisplayName("calculateHighestFrequency returns 0 for empty string")
    void testEmptyString() {
        assertThat(analyzer.calculateHighestFrequency("")).isEqualTo(0);
    }

    @Test
    @DisplayName("calculateHighestFrequency returns 1 for single word")
    void testSingleWord() {
        assertThat(analyzer.calculateHighestFrequency("hello")).isEqualTo(1);
    }

    @Test
    @DisplayName("calculateHighestFrequency returns highest frequency when words repeat")
    void testMultipleWords() {
        assertThat(analyzer.calculateHighestFrequency("the sun shines over the lake")).isEqualTo(2);
    }

    @Test
    @DisplayName("calculateFrequencyForWord returns 0 for word not in text")
    void testWordNotFound() {
        assertThat(analyzer.calculateFrequencyForWord("the sun shines", "rain")).isEqualTo(0);
    }

    @Test
    @DisplayName("calculateFrequencyForWord numbers act as word separators")
    void testNumbersSplitWords() {
        assertThat(analyzer.calculateFrequencyForWord("hello1234world", "hello")).isEqualTo(1);
        assertThat(analyzer.calculateFrequencyForWord("hello1234world", "world")).isEqualTo(1);
    }


    @ParameterizedTest
    @ValueSource(strings = {"the", "THE", "tHe"})
    @DisplayName("calculateFrequencyForWord case insensitive matching")
    void testCaseInsensitiveMatching(String caseVariant) {
        assertThat(analyzer.calculateFrequencyForWord("The sun shines over the lake", caseVariant)).isEqualTo(2);
    }

    @ParameterizedTest
    @ValueSource(strings = {"café", "über", "niño", "naïve", "český"})
    @DisplayName("calculateFrequencyForWord accented words are not split by non-letter characters")
    void testAccentedWordsNotSplit(String word) {
        assertThat(analyzer.calculateFrequencyForWord(word, word.toLowerCase())).isEqualTo(1);
    }

    @Test
    @DisplayName("calculateFrequencyForWord accented words among regular words return correct frequency")
    void testAccentedWordsAmongRegularWords() {
        assertThat(analyzer.calculateFrequencyForWord("café over the café lake", "café")).isEqualTo(2);
    }

    @Test
    @DisplayName("calculateFrequencyForWord accent words distinct from regular words")
    void testAccentWordsDistinctFromRegularWords() {
        assertThat(analyzer.calculateFrequencyForWord("cafe over the cafe lake", "cafe")).isEqualTo(2);
        assertThat(analyzer.calculateFrequencyForWord("café over the café lake", "café")).isEqualTo(2);
        assertThat(analyzer.calculateFrequencyForWord("cafe over the café lake", "cafe")).isEqualTo(1);
        assertThat(analyzer.calculateFrequencyForWord("cafe over the café lake", "café")).isEqualTo(1);
    }

    @Test
    @DisplayName("calculateMostFrequentNWords alphabetical order for words with same frequency")
    void testAlphabeticalOrderForSameFrequency() {
        List<WordFrequencyModel> result = analyzer.calculateMostFrequentNWords("the sun shines over the lake", 3);

        assertThat(result).extracting(WordFrequencyModel::getWord)
                .containsExactly("the", "lake", "over");
        assertThat(result).extracting(WordFrequencyModel::getFrequency)
                .containsExactly(2, 1, 1);
    }

    @Test
    @DisplayName("calculateMostFrequentNWords returns empty list when text is empty")
    void testEmptyText() {
        assertThat(analyzer.calculateMostFrequentNWords("", 5)).isEmpty();
    }

    @Test
    @DisplayName("calculateMostFrequentNWords returns all words when n exceeds word count")
    void testNExceedsWordCount() {
        List<WordFrequencyModel> result = analyzer.calculateMostFrequentNWords("hello world", 10);

        assertThat(result).satisfies(wfs -> {
            assertThat(wfs.get(0)).satisfies(wf -> {
                assertThat(wf.getWord()).isEqualTo("hello");
                assertThat(wf.getFrequency()).isEqualTo(1);
            });
            assertThat(wfs.get(1)).satisfies(wf -> {
                assertThat(wf.getWord()).isEqualTo("world");
                assertThat(wf.getFrequency()).isEqualTo(1);
            });
        });
    }

    @Test
    @DisplayName("calculateMostFrequentNWords all returned words are lowercase")
    void testAllWordsLowercase() {
        List<WordFrequencyModel> result = analyzer.calculateMostFrequentNWords("Hello HELLO hello", 5);

        assertThat(result).extracting(WordFrequencyModel::getWord)
                .allSatisfy(word -> assertThat(word).isEqualTo(word.toLowerCase()));
        assertThat(result).extracting(WordFrequencyModel::getFrequency)
                .containsExactly(3);
    }

    @Test
    @DisplayName("calculateMostFrequentNWords most frequent n words with accented characters")
    void testMostFrequentAccentedWords() {
        List<WordFrequencyModel> result = analyzer.calculateMostFrequentNWords("café über café niño", 3);

        assertThat(result).hasSize(3);
        assertThat(result.get(0)).satisfies(wf -> {
            assertThat(wf.getWord()).isEqualTo("café");
            assertThat(wf.getFrequency()).isEqualTo(2);
        });
        assertThat(result.get(1)).satisfies(wf -> {
            assertThat(wf.getWord()).isEqualTo("niño");
            assertThat(wf.getFrequency()).isEqualTo(1);
        });
        assertThat(result.get(2)).satisfies(wf -> {
            assertThat(wf.getWord()).isEqualTo("über");
            assertThat(wf.getFrequency()).isEqualTo(1);
        });
    }
}
