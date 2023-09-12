import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public record Need(String productId, double amount) {

    static ListBuilder builder() {
        return new ListBuilder();
    }

    @Getter
    static class ListBuilder {

        private final List<Need> list = new ArrayList<>();

        ListBuilder add(String productId, double amount) {
            list.add(new Need(productId,amount));
            return this;
        }
    }
}
