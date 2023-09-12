import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepoTest {

    @Test
    void getProducts() {
        //GIVEN
        ProductRepo repo = new ProductRepo();
        repo.getProducts().add(new Product("1", "Apfel"));

        //WHEN
        List<Product> actual = repo.getProducts();

        //THEN
        List<Product> expected = new ArrayList<>();
        expected.add(new Product("1", "Apfel"));
        assertEquals(actual, expected);
    }

    @Test
    void getProductById() {
        //GIVEN
        ProductRepo repo = new ProductRepo();
        repo.getProducts().add(new Product("1", "Apfel"));

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
        repo.getProducts().add(new Product("1", "Apfel"));
        Product newProduct = new Product("2", "Banane");

        //WHEN
        Product actual = repo.addProduct(newProduct);

        //THEN
        Product expected = new Product("2", "Banane");
        assertEquals(actual, expected);
        Optional<Product> product = repo.getProductById("2");
        assertNotNull(product);
        assertTrue(product.isPresent());
        assertEquals(product.get(), expected);
    }

    @Test
    void removeProduct() {
        //GIVEN
        ProductRepo repo = new ProductRepo();
        repo.getProducts().add(new Product("1", "Apfel"));

        //WHEN
        repo.removeProduct("1");

        //THEN
        Optional<Product> product = repo.getProductById("1");
        assertNotNull(product);
        assertTrue(product.isEmpty());
    }
}
