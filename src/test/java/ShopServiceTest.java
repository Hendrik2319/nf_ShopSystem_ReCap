import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void addOrderTest() {
        //GIVEN
        ProductRepo productRepo = new ProductRepo();
        productRepo.getProducts().add(new Product("1", "Apfel"));

        ShopService shopService = new ShopService(productRepo, new OrderMapRepo(), new IdService());
        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = null;
        try {
            actual = shopService.addOrder(productsIds);
        } catch (ProductNotFoundException e) {
            //THEN
            fail();
        }

        //THEN
        Order expected = new Order("-1", List.of(new Product("1", "Apfel")), ZonedDateTime.now());
        assertEquals(expected.products(), actual.products());
        assertNotNull(expected.id());
    }

    @Test
    void addOrderTest_whenInvalidProductId_expectException() {
        //GIVEN
        ProductRepo productRepo = new ProductRepo();
        productRepo.getProducts().add(new Product("1", "Apfel"));

        ShopService shopService = new ShopService(productRepo, new OrderMapRepo(), new IdService());
        List<String> productsIds = List.of("1", "2");

        //WHEN

        //THEN
        assertThrows(ProductNotFoundException.class, () -> shopService.addOrder(productsIds));
    }
}
