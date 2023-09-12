import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderMapRepoTest {

    @Test
    void getOrders() {
        //GIVEN
        OrderMapRepo repo = new OrderMapRepo();

        ZonedDateTime orderDate = ZonedDateTime.of(2023, 9, 12, 12, 0, 0, 0, ZoneId.systemDefault());
        Product product = new Product("1", "Apfel", 1);
        Order newOrder = new Order("1", List.of(product), orderDate);
        repo.addOrder(newOrder);

        //WHEN
        List<Order> actual = repo.getOrders();

        //THEN
        List<Order> expected = new ArrayList<>();
        ZonedDateTime expOrderDate = ZonedDateTime.of(2023, 9, 12, 12, 0, 0, 0, ZoneId.systemDefault());
        Product product1 = new Product("1", "Apfel", 1);
        expected.add(new Order("1", List.of(product1), expOrderDate));

        assertEquals(actual, expected);
    }

    @Test
    void getOrderById() {
        //GIVEN
        OrderMapRepo repo = new OrderMapRepo();

        ZonedDateTime orderDate = ZonedDateTime.of(2023, 9, 12, 12, 0, 0, 0, ZoneId.systemDefault());
        Product product = new Product("1", "Apfel", 1);
        Order newOrder = new Order("1", List.of(product), orderDate);
        repo.addOrder(newOrder);

        //WHEN
        Order actual = repo.getOrderById("1");

        //THEN
        ZonedDateTime expOrderDate = ZonedDateTime.of(2023, 9, 12, 12, 0, 0, 0, ZoneId.systemDefault());
        Product product1 = new Product("1", "Apfel", 1);
        Order expected = new Order("1", List.of(product1), expOrderDate);

        assertEquals(actual, expected);
    }

    @Test
    void addOrder() {
        //GIVEN
        OrderMapRepo repo = new OrderMapRepo();
        ZonedDateTime orderDate = ZonedDateTime.of(2023, 9, 12, 12, 0, 0, 0, ZoneId.systemDefault());
        Product product = new Product("1", "Apfel", 1);
        Order newOrder = new Order("1", List.of(product), orderDate);

        //WHEN
        Order actual = repo.addOrder(newOrder);

        //THEN
        ZonedDateTime expOrderDate = ZonedDateTime.of(2023, 9, 12, 12, 0, 0, 0, ZoneId.systemDefault());
        Product product1 = new Product("1", "Apfel", 1);
        Order expected = new Order("1", List.of(product1), expOrderDate);
        assertEquals(actual, expected);
        assertEquals(repo.getOrderById("1"), expected);
    }

    @Test
    void removeOrder() {
        //GIVEN
        OrderMapRepo repo = new OrderMapRepo();

        //WHEN
        repo.removeOrder("1");

        //THEN
        assertNull(repo.getOrderById("1"));
    }
}
