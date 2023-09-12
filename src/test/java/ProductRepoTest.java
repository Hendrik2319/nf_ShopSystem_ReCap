import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepoTest {

    @Test
    void getProducts() {
        //GIVEN
        ProductRepo repo = new ProductRepo();
        repo.getProducts().put("1", new Product("1", "Apfel", 10.5));

        //WHEN
        Map<String, Product> actual = repo.getProducts();

        //THEN
        Map<String,Product> expected = new HashMap<>();
        expected.put("1", new Product("1", "Apfel", 10.5));
        assertEquals(actual, expected);
    }

    @Test
    void getProductById() {
        //GIVEN
        ProductRepo repo = new ProductRepo();
        repo.getProducts().put("1", new Product("1", "Apfel", 10.5));

        //WHEN
        Optional<Product> actual = repo.getProductById("1");

        //THEN
        Product expected = new Product("1", "Apfel", 10.5);
        assertNotNull(actual);
        assertTrue(actual.isPresent());
        assertEquals(actual.get(), expected);
    }

    @Test
    void addProduct() {
        //GIVEN
        ProductRepo repo = new ProductRepo();
        repo.getProducts().put("1", new Product("1", "Apfel", 10.5));
        Product newProduct = new Product("2", "Banane", 8);

        //WHEN
        try {
            repo.addProduct(newProduct);
        } catch (ProductIdAlreadyExistsException e) {
            //THEN
            fail();
        }

        //THEN
        Product expected = new Product("2", "Banane", 8);
        Optional<Product> product = repo.getProductById("2");
        assertNotNull(product);
        assertTrue(product.isPresent());
        assertEquals(product.get(), expected);
    }

    @Test
    void addProduct_whenExistingID_expectException() {
        //GIVEN
        ProductRepo repo = new ProductRepo();
        repo.getProducts().put("1", new Product("1", "Apfel", 10.5));
        Product newProduct = new Product("1", "Banane", 8);

        //WHEN
        //THEN
        assertThrows(ProductIdAlreadyExistsException.class, () -> repo.addProduct(newProduct));
    }

    @Test
    void removeProduct() {
        //GIVEN
        ProductRepo repo = new ProductRepo();
        repo.getProducts().put("1", new Product("1", "Apfel", 10.5));

        //WHEN
        repo.removeProduct("1");

        //THEN
        Optional<Product> product = repo.getProductById("1");
        assertNotNull(product);
        assertTrue(product.isEmpty());
    }
}
