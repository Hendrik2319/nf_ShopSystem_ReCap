import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
public class ShopService {
    private final ProductRepo productRepo = new ProductRepo();
    private final OrderRepo orderRepo = new OrderMapRepo();

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

        Order newOrder = new Order(UUID.randomUUID().toString(), products);

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
