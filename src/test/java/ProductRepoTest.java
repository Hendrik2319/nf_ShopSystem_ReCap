import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepoTest {

    @Test
    void getProducts() {
        //GIVEN
        ProductRepo repo = new ProductRepo();
        repo.getProducts().put("1", new Product("1", "Apfel"));

        //WHEN
        Map<String, Product> actual = repo.getProducts();

        //THEN
        Map<String,Product> expected = new HashMap<>();
        expected.put("1", new Product("1", "Apfel"));
        assertEquals(actual, expected);
    }

    @Test
    void getProductById() {
        //GIVEN
        ProductRepo repo = new ProductRepo();
        repo.getProducts().put("1", new Product("1", "Apfel"));

        //WHEN
        Optional<Product> actual = repo.getProductById("1");

        //THEN
        Product expected = new Product("1", "Apfel");
        assertNotNull(actual);
        assertTrue(actual.isPresent());
        assertEquals(actual.get(), expected);
    }

    @Test
    void addProduct() {
        //GIVEN
        ProductRepo repo = new ProductRepo();
        repo.getProducts().put("1", new Product("1", "Apfel"));
        Product newProduct = new Product("2", "Banane");

        //WHEN
        try {
            repo.addProduct(newProduct);
        } catch (ProductIdAlreadyExistsException e) {
            //THEN
            fail();
        }

        //THEN
        Product expected = new Product("2", "Banane");
        Optional<Product> product = repo.getProductById("2");
        assertNotNull(product);
        assertTrue(product.isPresent());
        assertEquals(product.get(), expected);
    }

    @Test
    void removeProduct() {
        //GIVEN
        ProductRepo repo = new ProductRepo();
        repo.getProducts().put("1", new Product("1", "Apfel"));

        //WHEN
        repo.removeProduct("1");

        //THEN
        Optional<Product> product = repo.getProductById("1");
        assertNotNull(product);
        assertTrue(product.isEmpty());
    }
}
