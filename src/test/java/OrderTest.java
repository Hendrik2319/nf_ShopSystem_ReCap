import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("DataFlowIssue")
class OrderTest {

    @Test
    void throwsException_whenConstructor_getsANullValue() {
        // Given
        // When
        // Then
        assertThrows(NullPointerException.class, () -> new Order(null, List.of(), ZonedDateTime.now()), "[ id == null ]");
        assertThrows(NullPointerException.class, () -> new Order("id", null, ZonedDateTime.now()), "[ products == null ]");
        assertThrows(NullPointerException.class, () -> new Order(null, List.of(), null), "[ orderDate == null ]");
        assertThrows(NullPointerException.class, () -> new Order(null, List.of(), null, ZonedDateTime.now()), "[ orderState == null ]");
    }

    @Test
    void throwsException_whenWithId_getsNull() {
        // Given
        Order order = new Order("id", List.of(), ZonedDateTime.now());
        // When
        // Then
        assertThrows(NullPointerException.class, () -> order.withId(null), "[ withId(null) ]");
    }

    @Test
    void throwsException_whenWithProducts_getsNull() {
        // Given
        Order order = new Order("id", List.of(), ZonedDateTime.now());
        // When
        // Then
        assertThrows(NullPointerException.class, () -> order.withProducts(null), "[ withProducts(null) ]");
    }

    @Test
    void throwsException_whenWithOrderState_getsNull() {
        // Given
        Order order = new Order("id", List.of(), ZonedDateTime.now());
        // When
        // Then
        assertThrows(NullPointerException.class, () -> order.withOrderState(null), "[ withOrderState(null) ]");
    }

    @Test
    void throwsException_whenWithOrderDate_getsNull() {
        // Given
        Order order = new Order("id", List.of(), ZonedDateTime.now());
        // When
        // Then
        assertThrows(NullPointerException.class, () -> order.withOrderDate(null), "[ withOrderDate(null) ]");
    }
}