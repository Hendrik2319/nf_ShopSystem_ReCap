import lombok.With;

@With
public record Product(
        String id,
        String name,
        double amount
) {
}
