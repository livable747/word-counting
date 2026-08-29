package edu.stoffers.controller;

import edu.stoffers.model.WordFrequencyModel;
import edu.stoffers.service.WordFrequencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/v1/words/frequency")
@RequiredArgsConstructor
public class WordFrequencyController {

    private final WordFrequencyService service;

    @GetMapping("/most")
    public ResponseEntity<List<WordFrequencyModel>> getMostFrequent(
            @RequestParam String text,
            @RequestParam int n) {
        return ResponseEntity.ok(service.calculateMostFrequentNWords(text, n));
    }

    @GetMapping
    public ResponseEntity<Integer> getFrequencyForWord(
            @RequestParam String text,
            @RequestParam String word) {
        return ResponseEntity.ok(service.calculateFrequencyForWord(text, word));
    }

    @GetMapping("/highest")
    public ResponseEntity<Integer> getHighestFrequency(
            @RequestParam String text) {
        return ResponseEntity.ok(service.calculateHighestFrequency(text));
    }
}
