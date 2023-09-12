import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void addOrderTest() {
        //GIVEN
        ProductRepo productRepo = new ProductRepo();
        productRepo.getProducts().put("1", new Product("1", "Apfel", 10.5));

        ShopService shopService = new ShopService(productRepo, new OrderMapRepo(), new IdService());
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
        Order expected = new Order("-1", List.of(new Product("1", "Apfel", 1)), ZonedDateTime.now());
        assertEquals(expected.products(), actual.products());
        assertNotNull(expected.id());
    }

    @Test
    void addOrderTest_whenNeedOverStorageAmount_expectException() {
        //GIVEN
        ProductRepo productRepo = new ProductRepo();
        productRepo.getProducts().put("1", new Product("1", "Apfel", 10.5));

        ShopService shopService = new ShopService(productRepo, new OrderMapRepo(), new IdService());
        List<Need> needs = Need.builder().add("1", 12).getList();

        //WHEN

        //THEN
        assertThrows(NotEnoughAmountException.class, () -> shopService.addOrder(needs));
    }

    @Test
    void addOrderTest_whenInvalidProductId_expectException() {
        //GIVEN
        ProductRepo productRepo = new ProductRepo();
        productRepo.getProducts().put("1", new Product("1", "Apfel", 10.5));

        ShopService shopService = new ShopService(productRepo, new OrderMapRepo(), new IdService());
        List<Need> needs = Need.builder().add("1", 1).add("2", 1).getList();

        //WHEN

        //THEN
        assertThrows(ProductNotFoundException.class, () -> shopService.addOrder(needs));
    }
}
