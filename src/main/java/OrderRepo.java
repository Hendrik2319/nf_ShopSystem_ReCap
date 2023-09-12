import java.util.List;

@SuppressWarnings("unused")
public interface OrderRepo {

    List<Order> getOrders();

    Order getOrderById(String id);

    Order addOrder(Order newOrder);

    void removeOrder(String id);
}
