package edu.stoffers.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidateTextNotBlankTest {

    private final ValidateTextNotBlank validator = new ValidateTextNotBlank();;

    @Test
    @DisplayName("Do not throw exception if input has text")
    void testTextInput() {
        assertDoesNotThrow(() -> validator.validate("hello world"));
    }

    @Test
    @DisplayName("Do not throw exception if input has integers as text")
    void testTextIntegerInput() {
        assertDoesNotThrow(() -> validator.validate("12345"));
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    @ValueSource(strings = "  ")
    @DisplayName("Throw exception if input is null, empty or white spaces")
    void testEmptyInput(String text) {
        assertThrows(IllegalArgumentException.class, () -> validator.validate(text));
    }
}
