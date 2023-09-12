import lombok.NonNull;
import lombok.With;

import java.time.ZonedDateTime;
import java.util.List;

@With
public record Order(
        @NonNull String id,
        @NonNull List<Product> products,
        @NonNull OrderState orderState,
        @NonNull ZonedDateTime orderDate
) {
    public Order(@NonNull String id, @NonNull List<Product> products, @NonNull ZonedDateTime orderDate) {
        this(id, products, OrderState.PROCESSING, orderDate);
    }
}
