import java.util.List;

public record Order(
        String id,
        List<Product> products,
        OrderState orderState
) {
    public Order(String id, List<Product> products) {
        this(id, products, OrderState.PROCESSING);
    }
}
