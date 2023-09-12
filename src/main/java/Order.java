import lombok.With;

import java.time.ZonedDateTime;
import java.util.List;

@With
public record Order(
        String id,
        List<Product> products,
        OrderState orderState,
        ZonedDateTime orderDate
) {
    public Order(String id, List<Product> products, ZonedDateTime orderDate) {
        this(id, products, OrderState.PROCESSING, orderDate);
    }
}
