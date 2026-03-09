package robinh.s2qltp;import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest {

    // Q1

    @Test
    void addItemDoitAugmenterLeNombreArticles() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("PROD-001", 2, 15.0);
        assertEquals(1, cart.getItemCount());
    }

    @Test
    void getTotalDoitRetournerSommePrixUnitairesMultipliesParQuantite() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("PROD-001", 2, 15.0);
        assertEquals(30.0, cart.getTotal(), 0.001);
    }

    @Test
    void cartVideDoitAvoirIsEmptyTrue() {
        ShoppingCart cart = new ShoppingCart();
        assertTrue(cart.isEmpty());
    }

    // Q2

    @Test
    void productIdNulDoitLeverException() {
        ShoppingCart cart = new ShoppingCart();
        assertThrows(IllegalArgumentException.class,
                () -> cart.addItem(null, 1, 10.0));
    }

    @Test
    void productIdVideDoitLeverException() {
        ShoppingCart cart = new ShoppingCart();
        assertThrows(IllegalArgumentException.class,
                () -> cart.addItem("", 1, 10.0));
    }

    @Test
    void quantiteNulleDoitLeverException() {
        ShoppingCart cart = new ShoppingCart();
        assertThrows(IllegalArgumentException.class,
                () -> cart.addItem("PROD-001", 0, 15.0));
    }

    @Test
    void quantiteNegativeDoitLeverException() {
        ShoppingCart cart = new ShoppingCart();
        assertThrows(IllegalArgumentException.class,
                () -> cart.addItem("PROD-001", -3, 15.0));
    }

    @Test
    void prixNegatifDoitLeverException() {
        ShoppingCart cart = new ShoppingCart();
        assertThrows(IllegalArgumentException.class,
                () -> cart.addItem("PROD-001", 1, -5.0));
    }

    @Test
    void codePromoVideDoitLeverException() {
        ShoppingCart cart = new ShoppingCart();
        assertThrows(IllegalArgumentException.class,
                () -> cart.applyPromoCode(""));
    }

    @Test
    void codePromoNulDoitLeverException() {
        ShoppingCart cart = new ShoppingCart();
        assertThrows(IllegalArgumentException.class,
                () -> cart.applyPromoCode(null));
    }

    // Q3

    @Test
    void quantiteUneDoitFonctionner() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("PROD-001", 1, 9.99);
        assertEquals(9.99, cart.getTotal(), 0.001);
    }

    @Test
    void articleGratuitDoitEtreAccepte() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("PROMO-FREE", 1, 0.0);
        assertEquals(0.0, cart.getTotal(), 0.001);
    }

    @Test
    void prixEleveDoitFonctionner() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("PROD-LUX", 1, 999.99);
        assertEquals(999.99, cart.getTotal(), 0.001);
    }

    @Test
    void panierAvecUnSeulArticleDoitFonctionner() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("PROD-001", 1, 10.0);
        assertEquals(1, cart.getItemCount());
    }
}
