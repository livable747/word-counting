package edu.stoffers.validation;

import org.springframework.stereotype.Component;

@Component
public class ValidateWordNotBlank {

    public void validate(String word) {
        if (word == null || word.isBlank()) {
            throw new IllegalArgumentException("word must not be null or blank");
        }
    }
}
