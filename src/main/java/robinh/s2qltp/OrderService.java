package robinh.s2qltp;
import java.time.LocalDateTime;

public class OrderService {
    private final InventoryRepository inventory;

    public OrderService(InventoryRepository inventory) {
        this.inventory = inventory;
    }

    public Order placeOrder(ShoppingCart cart, String customerId) {
        if (cart.isEmpty()) {
            throw new IllegalStateException("Le panier est vide");
        }
        if (customerId == null || customerId.isBlank()) {
            throw new IllegalArgumentException("Customer ID invalide");
        }
        for (ShoppingCart.CartItem item : cart.getItems()) {
            int stock = inventory.getStock(item.productId());
            if (stock < item.quantity()) {
                throw new OutOfStockException(
                        "Stock insuffisant pour : " + item.productId());
            }
        }
        return new Order(customerId, cart.getTotal(), LocalDateTime.now());
    }
}
