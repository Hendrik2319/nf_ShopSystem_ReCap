import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class MainTest {

    @Test
    void test_TransactionFileParser_getValues() {
        // Given
        String line = "pref   A B   C";
        String pref = "pref ";

        // When
        String[] actual = Main.TransactionFileParser.getValues(line, pref);

        // Then
        String[] expected = { "A", "B", "C" };
        assertArrayEquals(expected, actual);
    }
}