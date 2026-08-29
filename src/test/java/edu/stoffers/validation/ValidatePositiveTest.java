package edu.stoffers.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidatePositiveTest {

    private final ValidatePositive validator = new ValidatePositive();

    @Test
    @DisplayName("Do not throw exception if input is >= 1")
    void testInputPositiveInteger() {
        assertDoesNotThrow(() -> validator.validate(1));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    @DisplayName("Throw exception if input is < 1")
    void testInputZeroOrNegativeInteger(int input) {
        assertThrows(IllegalArgumentException.class, () -> validator.validate(input));
    }
}
