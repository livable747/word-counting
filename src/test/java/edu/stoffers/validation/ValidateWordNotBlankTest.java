package edu.stoffers.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidateWordNotBlankTest {

    private final ValidateWordNotBlank validator = new ValidateWordNotBlank();

    @Test
    @DisplayName("Do not throw exception if input has text")
    void testTextInput() {
        assertDoesNotThrow(() -> validator.validate("world"));
    }

    @Test
    @DisplayName("Do not throw exception if input has integers as text")
    void testTextIntegerInput() {
        assertDoesNotThrow(() -> validator.validate("12345"));
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    @ValueSource(strings = "   ")
    @DisplayName("throws for invalid word")
    void testEmptyInput(String word) {
        assertThrows(IllegalArgumentException.class, () -> validator.validate(word));
    }
}
