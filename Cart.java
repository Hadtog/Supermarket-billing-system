package PROJECT;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    Map<Product, Integer> productMap; // Keeps track of products and their quantities

    public Cart() {
        productMap = new HashMap<>();
    }

    public void addProduct(Product product) {
        productMap.put(product, productMap.getOrDefault(product, 0) + 1);
    }


    public double getTotal() {
        double total = 0;
        for (Map.Entry<Product, Integer> entry : productMap.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }

    public Map<Product, Integer> getProductMap() {
        return productMap;
    }

    public String getCartDetails() {
        StringBuilder details = new StringBuilder();
        for (Map.Entry<Product, Integer> entry : productMap.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            details.append(String.format("[%d] %s - ₱%.2f\n", quantity, product.getName(), product.getPrice() * quantity));
        }
        return details.toString();
    }

    public void clearCart() {
        productMap.clear();
    }
}