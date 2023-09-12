import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void addOrderTest() {
        //GIVEN
        ShopService shopService = new ShopService(new ProductRepo(), new OrderMapRepo(), new IdService());
        List<Need> needs = Need.builder().add("1", 1).getList();

        //WHEN
        Order actual = null;
        try {
            actual = shopService.addOrder(needs);
        } catch (ProductNotFoundException | NotEnoughAmountException e) {
            //THEN
            fail();
        }

        //THEN
        Order expected = new Order("-1", List.of(new Product("1", "Apfel")), ZonedDateTime.now());
        assertEquals(expected.products(), actual.products());
        assertNotNull(expected.id());
    }

    @Test
    void addOrderTest_whenInvalidProductId_expectNull() {
        //GIVEN
        ShopService shopService = new ShopService(new ProductRepo(), new OrderMapRepo(), new IdService());
        List<String> productsIds = List.of("1", "2");

        //WHEN

        //THEN
        assertThrows(ProductNotFoundException.class, () -> shopService.addOrder(productsIds));
    }
}
