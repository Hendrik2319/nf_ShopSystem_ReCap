import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderListRepoTest {

    @Test
    void getOrders() {
        //GIVEN
        OrderListRepo repo = new OrderListRepo();

        ZonedDateTime orderDate = ZonedDateTime.of(2023, 9, 12, 12, 0, 0, 0, ZoneId.systemDefault());
        Product product = new Product("1", "Apfel");
        Order newOrder = new Order("1", List.of(product), orderDate);
        repo.addOrder(newOrder);

        //WHEN
        List<Order> actual = repo.getOrders();

        //THEN
        List<Order> expected = new ArrayList<>();
        ZonedDateTime expOrderDate = ZonedDateTime.of(2023, 9, 12, 12, 0, 0, 0, ZoneId.systemDefault());
        Product product1 = new Product("1", "Apfel");
        expected.add(new Order("1", List.of(product1), expOrderDate));

        assertEquals(actual, expected);
    }

    @Test
    void getOrderById() {
        //GIVEN
        OrderListRepo repo = new OrderListRepo();

        ZonedDateTime orderDate = ZonedDateTime.of(2023, 9, 12, 12, 0, 0, 0, ZoneId.systemDefault());
        Product product = new Product("1", "Apfel");
        Order newOrder = new Order("1", List.of(product), orderDate);
        repo.addOrder(newOrder);

        //WHEN
        Order actual = repo.getOrderById("1");

        //THEN
        ZonedDateTime expOrderDate = ZonedDateTime.of(2023, 9, 12, 12, 0, 0, 0, ZoneId.systemDefault());
        Product product1 = new Product("1", "Apfel");
        Order expected = new Order("1", List.of(product1), expOrderDate);

        assertEquals(actual, expected);
    }

    @Test
    void addOrder() {
        //GIVEN
        OrderListRepo repo = new OrderListRepo();
        ZonedDateTime orderDate = ZonedDateTime.of(2023, 9, 12, 12, 0, 0, 0, ZoneId.systemDefault());
        Product product = new Product("1", "Apfel");
        Order newOrder = new Order("1", List.of(product), orderDate);

        //WHEN
        Order actual = repo.addOrder(newOrder);

        //THEN
        ZonedDateTime expOrderDate = ZonedDateTime.of(2023, 9, 12, 12, 0, 0, 0, ZoneId.systemDefault());
        Product product1 = new Product("1", "Apfel");
        Order expected = new Order("1", List.of(product1), expOrderDate);
        assertEquals(actual, expected);
        assertEquals(repo.getOrderById("1"), expected);
    }

    @Test
    void removeOrder() {
        //GIVEN
        OrderListRepo repo = new OrderListRepo();

        //WHEN
        repo.removeOrder("1");

        //THEN
        assertNull(repo.getOrderById("1"));
    }
}
