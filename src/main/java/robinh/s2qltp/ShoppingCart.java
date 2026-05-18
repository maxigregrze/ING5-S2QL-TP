package robinh.s2qltp;

import java.util.ArrayList;
import java.util.List;

/**
 * Panier d'achat avec articles, quantités, prix unitaires et code promo optionnel.
 */
public class ShoppingCart {

    private List<CartItem> items = new ArrayList<>();
    private String promoCode = null;

    /**
     * Ajoute une ligne au panier après validation des paramètres.
     *
     * @param productId identifiant produit non vide
     * @param quantity    quantité strictement positive
     * @param unitPrice   prix unitaire non négatif
     */
    public void addItem(String productId, int quantity, double unitPrice) {
        if (productId == null || productId.isBlank()) {
            throw new IllegalArgumentException("Product ID invalide");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantité invalide");
        }
        if (unitPrice < 0) {
            throw new IllegalArgumentException("Prix invalide");
        }
        items.add(new CartItem(productId, quantity, unitPrice));
    }

    /**
     * Enregistre un code promotionnel non vide.
     *
     * @param code code promo (ex. PROMO10)
     */
    public void applyPromoCode(String code) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Code promo invalide");
        }
        this.promoCode = code;
    }

    /**
     * Calcule le total TTC du panier en appliquant la remise du code promo si reconnue.
     *
     * @return montant total après remise éventuelle
     */
    public double getTotal() {
        double subtotal = items.stream()
                .mapToDouble(i -> i.quantity() * i.unitPrice())
                .sum();
        if ("PROMO10".equals(promoCode)) {
            return subtotal * 0.90;
        }
        if ("PROMO20".equals(promoCode)) {
            return subtotal * 0.80;
        }
        return subtotal;
    }

    /**
     * @return nombre de lignes distinctes dans le panier
     */
    public int getItemCount() {
        return items.size();
    }

    /**
     * @return {@code true} si aucune ligne n'est présente
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * @return vue de la liste des lignes (modifiable côté panier)
     */
    public List<CartItem> getItems() {
        return items;
    }

    /**
     * Ligne de panier : produit, quantité et prix unitaire.
     *
     * @param productId identifiant produit
     * @param quantity    quantité commandée
     * @param unitPrice   prix unitaire appliqué
     */
    public record CartItem(String productId, int quantity, double unitPrice) {
    }
}
