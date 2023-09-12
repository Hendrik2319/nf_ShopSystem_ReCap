import java.util.List;

public class Main {

    public static void main(String[] args) {

        ProductRepo productRepo = new ProductRepo();
        productRepo.addProduct(new Product("prod1", "Product 1"));
        productRepo.addProduct(new Product("prod2", "Product 2"));
        productRepo.addProduct(new Product("prod3", "Product 3"));
        productRepo.addProduct(new Product("prod4", "Product 4"));
        productRepo.addProduct(new Product("prod5", "Product 5"));
        productRepo.addProduct(new Product("prod6", "Product 6"));
        productRepo.addProduct(new Product("prod7", "Product 7"));

        ShopService shopService = new ShopService(productRepo, new OrderMapRepo(), new IdService());

        try {
            shopService.addOrder(List.of("prod1", "prod1", "prod2"));
            shopService.addOrder(List.of("prod3", "prod4", "prod5"));
            shopService.addOrder(List.of("prod6", "prod7", "prod1", "prod2"));
        } catch (ProductNotFoundException e) {
            System.err.printf("ProductNotFoundException: %s%n", e.getMessage());
        }

    }
}
