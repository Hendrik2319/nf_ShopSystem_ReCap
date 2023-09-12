import java.util.ArrayList;
import java.util.List;

public class OrderListRepo implements OrderRepo{
    private final List<Order> orders = new ArrayList<>();

    @Override
    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public Order getOrderById(String id) {
        for (Order order : orders) {
            if (order.id().equals(id)) {
                return order;
            }
        }
        return null;
    }

    @Override
    public Order addOrder(Order newOrder) {
        orders.add(newOrder);
        return newOrder;
    }

    @Override
    public void removeOrder(String id) {
        for (Order order : orders) {
            if (order.id().equals(id)) {
                orders.remove(order);
                return;
            }
        }
    }
}
