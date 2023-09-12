import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
@RequiredArgsConstructor
public class ShopService {
    private final ProductRepo productRepo;
    @Getter
    private final OrderRepo orderRepo;
    private final IdService idService;

    public Order addOrder(List<Need> productIds) throws ProductNotFoundException, NotEnoughAmountException {
        List<Product> products = new ArrayList<>();
        for (Need need : productIds) {
            Product storedProduct = productRepo
                    .getProductById(need.productId())
                    .orElseThrow(
                            () -> new ProductNotFoundException("Produkt mit der Id \"%s\" konnte nicht bestellt werden!", need.productId())
                    );
            if (storedProduct.amount() < need.amount())
                throw new NotEnoughAmountException("Nicht genügend Lagerbestand vom Produkt mit der Id \"%s\" vorhanden!", need.productId());

            storedProduct = storedProduct.withAmount( storedProduct.amount() - need.amount() );
            productRepo.removeProduct(storedProduct.id());
            productRepo.addProduct(storedProduct);

            products.add(
                    storedProduct.withAmount( need.amount() )
            );
        }

        Order newOrder = new Order(idService.generateId(), products, ZonedDateTime.now());

        return orderRepo.addOrder(newOrder);
    }

    public List<Order> listOrdersByOrderState( OrderState orderState ) {
        return orderRepo.getOrders()
                .stream()
                .filter(o -> o.orderState() == orderState)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean updateOrder(String id, OrderState orderState ) {
        Order order = orderRepo.getOrderById(id);
        if (order==null) return false;

        orderRepo.removeOrder(id);
        orderRepo.addOrder(order.withOrderState(orderState));
        return true;
    }

    public Map<OrderState,Order> getOldestOrderPerStatus() {
        EnumMap<OrderState, Order> map = new EnumMap<>(OrderState.class);

        orderRepo.getOrders().forEach(order -> {
            Order oldestOrder = map.get(order.orderState());
            if (oldestOrder==null || oldestOrder.orderDate().isAfter(order.orderDate()))
                map.put(order.orderState(), order);
        });

        return map;
    }
}
