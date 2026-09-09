package edu.stoffers.controller;

import edu.stoffers.analyzer.WordFrequencyAnalyzer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class WordFrequencyControllerTest {

    private MockMvc mockMvc;

    @MockitoSpyBean
    private WordFrequencyAnalyzer analyzer;

    @BeforeEach
    void setUp(WebApplicationContext context) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @DisplayName("returns the highest frequency")
    void testHighestFrequency() throws Exception {
        mockMvc.perform(get("/api/v1/words/frequency/highest")
                        .param("text", "the sun shines over the lake"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(2));
    }

    @Test
    @DisplayName("returns 400 for empty text")
    void testHighestFrequencyEmptyText() throws Exception {
        mockMvc.perform(get("/api/v1/words/frequency/highest")
                .param("text", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("returns 400 when text parameter is missing")
    void testHighestFrequencyMissingText() throws Exception {
        mockMvc.perform(get("/api/v1/words/frequency/highest"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("returns 200 for single word")
    void testHighestFrequencySingleWord() throws Exception {
        mockMvc.perform(get("/api/v1/words/frequency/highest")
                        .param("text", "hello"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1));
    }

    @Test
    @DisplayName("returns the frequency for a specific word")
    void testFrequencyForWord() throws Exception {
        mockMvc.perform(get("/api/v1/words/frequency")
                        .param("text", "the sun shines over the lake")
                        .param("word", "the"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(2));
    }

    @Test
    @DisplayName("case insensitive matching - word param uppercase")
    void testFrequencyForWordCaseInsensitive() throws Exception {
        mockMvc.perform(get("/api/v1/words/frequency")
                        .param("text", "The sun shines over the lake")
                        .param("word", "THE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(2));
    }

    @Test
    @DisplayName("returns 0 when word is not in text")
    void testFrequencyForWordNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/words/frequency")
                .param("text", "the sun shines over the lake")
                .param("word", "rain"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(0));
    }

    @Test
    @DisplayName("returns 400 when text parameter is missing")
    void testFrequencyForWordMissingText() throws Exception {
        mockMvc.perform(get("/api/v1/words/frequency")
                .param("word", "the"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("returns 400 when word parameter is missing")
    void testFrequencyForWordMissingWord() throws Exception {
        mockMvc.perform(get("/api/v1/words/frequency")
                .param("text", "hello world"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("returns n most frequent words sorted by frequency desc then alphabetically")
    void testMostFrequentNWords() throws Exception {
        mockMvc.perform(get("/api/v1/words/frequency/most")
                        .param("text", "the sun shines over the lake")
                        .param("n", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].word").value("the"))
                .andExpect(jsonPath("$[0].frequency").value(2))
                .andExpect(jsonPath("$[1].word").value("lake"))
                .andExpect(jsonPath("$[1].frequency").value(1))
                .andExpect(jsonPath("$[2].word").value("over"))
                .andExpect(jsonPath("$[2].frequency").value(1));
    }

    @Test
    @DisplayName("returns words in lowercase")
    void testMostFrequentAllWordsLowercase() throws Exception {
        mockMvc.perform(get("/api/v1/words/frequency/most")
                        .param("text", "Hello HELLO hello")
                        .param("n", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].word").value("hello"))
                .andExpect(jsonPath("$[0].frequency").value(3));
    }

    @Test
    @DisplayName("returns all words when n exceeds word count")
    void testMostFrequentNExceedsWordCount() throws Exception {
        mockMvc.perform(get("/api/v1/words/frequency/most")
                .param("text", "hello world")
                .param("n", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("returns 400 for empty text")
    void testMostFrequentEmptyText() throws Exception {
        mockMvc.perform(get("/api/v1/words/frequency/most")
                .param("text", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("returns 400 when text parameter is missing")
    void testMostFrequentMissingText() throws Exception {
        mockMvc.perform(get("/api/v1/words/frequency/most"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("returns 400 when n is less than 1")
    void testMostFrequentInvalidN() throws Exception {
        mockMvc.perform(get("/api/v1/words/frequency/most")
                .param("text", "hello world")
                .param("n", "0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("returns 400 when analyzer throws ArithmeticException on overflow")
    void testHighestFrequencyOverflow() throws Exception {
        doThrow(new ArithmeticException("integer overflow"))
                .when(analyzer)
                .calculateHighestFrequency(anyString());

        mockMvc.perform(get("/api/v1/words/frequency/highest")
                        .param("text", "overflowtest"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("returns 400 when analyzer throws ArithmeticException on word frequency overflow")
    void testFrequencyForWordOverflow() throws Exception {
        doThrow(new ArithmeticException("integer overflow"))
                .when(analyzer)
                .calculateFrequencyForWord(anyString(), anyString());

        mockMvc.perform(get("/api/v1/words/frequency")
                        .param("text", "overflowtest")
                        .param("word", "test"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("returns 400 when analyzer throws ArithmeticException on most frequent overflow")
    void testMostFrequentNWordsOverflow() throws Exception {
        doThrow(new ArithmeticException("integer overflow"))
                .when(analyzer)
                .calculateMostFrequentNWords(anyString(), anyInt());

        mockMvc.perform(get("/api/v1/words/frequency/most")
                        .param("text", "overflowtest")
                        .param("n", "10"))
                .andExpect(status().isBadRequest());
    }
}
