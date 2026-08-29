package edu.stoffers.validation;

import org.springframework.stereotype.Component;

@Component
public class ValidateTextNotBlank {

    public void validate(String text) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("text must not be null or blank");
        }
    }
}
