import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@RequiredArgsConstructor
public class ShopService {
    private final ProductRepo productRepo;
    private final OrderRepo orderRepo;
    private final IdService idService;

    public Order addOrder(List<String> productIds) throws ProductNotFoundException {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds)
            products.add(
                    productRepo
                            .getProductById(productId)
                            .orElseThrow(
                                    () -> new ProductNotFoundException("Product mit der Id: %s konnte nicht bestellt werden!", productId)
                            )
            );

        Order newOrder = new Order(idService.generateId(), products, ZonedDateTime.now());

        return orderRepo.addOrder(newOrder);
    }

    public List<Order> listOrdersByOrderState( OrderState orderState ) {
        return orderRepo.getOrders()
                .stream()
                .filter(o -> o.orderState() == orderState)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public boolean updateOrder(String id, OrderState orderState ) {
        Order order = orderRepo.getOrderById(id);
        if (order==null) return false;

        orderRepo.removeOrder(id);
        orderRepo.addOrder(order.withOrderState(orderState));
        return true;
    }
}
