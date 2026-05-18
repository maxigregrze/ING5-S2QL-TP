package robinh.s2qltp;

/**
 * Accès lecture au stock disponible par produit.
 */
public interface InventoryRepository {

    /**
     * Retourne la quantité en stock pour un identifiant produit.
     *
     * @param productId identifiant produit
     * @return quantité disponible (non négative)
     */
    int getStock(String productId);
}
