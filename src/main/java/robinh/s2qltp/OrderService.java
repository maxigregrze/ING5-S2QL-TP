package robinh.s2qltp;

import java.time.LocalDateTime;

/**
 * Service de placement de commande à partir d'un panier et du stock.
 */
public class OrderService {

    private final InventoryRepository inventory;

    /**
     * Crée le service avec le dépôt de stock injecté.
     *
     * @param inventory source de vérité pour les quantités en stock
     */
    public OrderService(InventoryRepository inventory) {
        this.inventory = inventory;
    }

    /**
     * Valide le panier, contrôle le stock puis crée la commande.
     *
     * @param cart       panier non vide avec lignes et quantités
     * @param customerId identifiant client non vide
     * @return commande créée avec total et horodatage
     * @throws IllegalStateException   si le panier est vide
     * @throws IllegalArgumentException si l'identifiant client est invalide
     * @throws OutOfStockException      si une ligne dépasse le stock disponible
     */
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
