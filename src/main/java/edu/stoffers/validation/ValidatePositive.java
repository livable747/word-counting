package edu.stoffers.validation;

import org.springframework.stereotype.Component;

@Component
public class ValidatePositive {

    public void validate(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than 0");
        }
    }
}
