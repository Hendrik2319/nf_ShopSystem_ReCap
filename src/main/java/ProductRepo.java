import lombok.Getter;
import lombok.NonNull;

import java.util.*;

@Getter
public class ProductRepo {
    private final Map<String,Product> products;

    public ProductRepo() {
        products = new HashMap<>();
    }

    public Optional<Product> getProductById(String id) {
        return Optional.ofNullable(products.get(id));
    }

    public void addProduct(@NonNull Product newProduct) throws ProductIdAlreadyExistsException {
        if (products.containsKey(newProduct.id()))
            throw new ProductIdAlreadyExistsException("Can't add product: A product with id \"%s\" already exists.", newProduct.id());
        products.put(newProduct.id(), newProduct);
    }

    @SuppressWarnings("UnusedReturnValue")
    public Product removeProduct(String id) {
        return products.remove(id);
    }
}
