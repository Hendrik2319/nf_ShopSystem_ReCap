import lombok.NonNull;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Main {

    @SuppressWarnings("ExtractMethodRecommender")
    public static void main(String[] args) {

        ProductRepo productRepo = new ProductRepo();
        productRepo.addProduct(new Product("1", "Product 1"));
        productRepo.addProduct(new Product("2", "Product 2"));
        productRepo.addProduct(new Product("3", "Product 3"));
        productRepo.addProduct(new Product("4", "Product 4"));
        productRepo.addProduct(new Product("5", "Product 5"));
        productRepo.addProduct(new Product("6", "Product 6"));
        productRepo.addProduct(new Product("7", "Product 7"));

        ShopService shopService = new ShopService(productRepo, new OrderMapRepo(), new IdService());

/*
        try {
            shopService.addOrder(List.of("1", "1", "2"));
            shopService.addOrder(List.of("3", "4", "5"));
            shopService.addOrder(List.of("6", "7", "1", "2"));
        } catch (ProductNotFoundException e) {
            System.err.printf("ProductNotFoundException: %s%n", e.getMessage());
        }

        System.out.println(Arrays.toString(TransactionFileParser.getValues("pref   A B   C", "pref ")));
*/

        new TransactionFileParser(new File("transactions.txt"), shopService).readTransactionsFile();
    }

    record TransactionFileParser(@NonNull File file, @NonNull ShopService shopService) {

        @SuppressWarnings("UnusedReturnValue")
        private Map<String,String> readTransactionsFile() {
            HashMap<String, String> orderIds = new HashMap<>();

            List<String> lines;
            try (Stream<String> stream = Files.lines(file.toPath(), StandardCharsets.UTF_8)) {
                lines = stream.toList();
            } catch (IOException e) {
                System.err.printf("IOException while reading file \"%s\": %s%n", file.getAbsolutePath(), e.getMessage());
                lines = List.of();
            }

//            System.out.printf("%d lines%n", lines.size());
            String[] values;
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
//                System.out.printf("parse [%d] \"%s\"%n", i, line);

                if ((values = getValues(line, "addOrder")) != null) {
                    addOrder(i, line, orderIds, values);
                }

                if ((values = getValues(line, "setStatus")) != null) {
                    setStatus(i, line, orderIds, values);
                }

                if (line.equals("printOrders")) {
                    printOrders(i, line);
                }
            }


            return orderIds;
        }

        private void addOrder(int lineIndex, String line, @NonNull HashMap<String, String> orderIds, @NonNull String[] values) {
//            System.out.printf("parse [%d] \"%s\" ->  %s %s%n", lineIndex, line, "addOrder", Arrays.toString(values));

            if (values.length<1) {
                System.err.printf("Error in line %d \"%s\": too few arguments%n", lineIndex, line);
                return;
            }

            String orderIdAlias = values[0];

            List<String> products = Arrays.asList(Arrays.copyOfRange(values, 1, values.length));
            Order order;
            try {
                order = shopService.addOrder(products);
            } catch (ProductNotFoundException e) {
                System.err.printf("Error in line %d \"%s\": at least one product id is not assigned with an existing product%n", lineIndex, line);
                return;
            }

            orderIds.put(orderIdAlias, order.id());
        }

        private void setStatus(int lineIndex, String line, @NonNull HashMap<String, String> orderIds, @NonNull String[] values) {
//            System.out.printf("parse [%d] \"%s\" ->  %s %s%n", lineIndex, line, "setStatus", Arrays.toString(values));

            if (values.length!=2) {
                System.err.printf("Error in line %d \"%s\": 2 arguments expected%n", lineIndex, line);
                return;
            }

            String orderIdAlias = values[0];
            String orderId = orderIds.get(orderIdAlias);
            if (orderId==null) {
                System.err.printf("Error in line %d \"%s\": unknown alias \"%s\"%n", lineIndex, line, orderIdAlias);
                return;
            }

            String orderStateStr = values[1];
            OrderState orderState;
            try {
                orderState = OrderState.valueOf(orderStateStr);
            } catch (IllegalArgumentException e) {
                System.err.printf("Error in line %d \"%s\": unknown OrderState \"%s\"%n", lineIndex, line, orderStateStr);
                return;
            }

            shopService.updateOrder(orderId, orderState);
        }

        @SuppressWarnings("unused")
        private void printOrders(int lineIndex, String line) {
//            System.out.printf("parse [%d] \"%s\" ->  %s%n", lineIndex, line, "printOrders");

            List<Order> orders = shopService.getOrderRepo().getOrders();
            System.out.printf("ShopService:   %d order(s)%n", orders.size());
            for (int i = 0; i < orders.size(); i++) {
                Order order = orders.get(i);
                if (order==null)
                    System.out.printf("   [%d]  <null>%n", i+1);
                else {
                    List<Product> products = order.products();
                    System.out.printf("   [%d]  ID: %s%n", i+1, order.id());
                    System.out.printf("      Order State: %s%n", order.orderState());
                    System.out.printf("      Order Date : %s%n", order.orderDate());
                    System.out.printf("      %d product(s)%n", products.size());
                    for (int j = 0; j < products.size(); j++) {
                        Product product = products.get(j);
                        if (product==null)
                            System.out.printf("         [%d]  <null>%n", j+1);
                        else
                            System.out.printf("         [%d]  \"%s\"  [%s]%n", j + 1, product.name(), product.id());
                    }
                }
            }
        }

        static String[] getValues(@NonNull String line, @NonNull String prefix) {
            prefix = prefix.stripTrailing()+" ";
            if (!line.startsWith(prefix)) return null;
            return line
                    .replaceAll("\\s+", " ")
                    .substring(prefix.length())
                    .split(" ");
        }
    }
}
