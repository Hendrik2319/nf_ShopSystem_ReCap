import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class ProductRepo {
    private final List<Product> products;

    public ProductRepo() {
        products = new ArrayList<>();
    }

    public Optional<Product> getProductById(String id) {
        for (Product product : products) {
            if (product.id().equals(id)) {
                return Optional.of(product);
            }
        }
        return Optional.empty();
    }

    public Product addProduct(Product newProduct) {
        products.add(newProduct);
        return newProduct;
    }

    public void removeProduct(String id) {
        for (Product product : products) {
           if (product.id().equals(id)) {
               products.remove(product);
               return;
           }
        }
    }
}
