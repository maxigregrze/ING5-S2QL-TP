package robinh.s2qltp;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartPromoTest {

    // Q5

    @ParameterizedTest(name = "code=''{0}'' → total attendu={1}")
    @CsvSource({
        " ,    100.0",
        "PROMO10, 90.0",
        "PROMO20, 80.0"
    })
    void getTotalDoitAppliquerLaBonneReduction(String code, double expectedTotal) {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("PROD-001", 10, 10.0);
        if (code != null && !code.isBlank()) {
            cart.applyPromoCode(code.trim());
        }
        assertEquals(expectedTotal, cart.getTotal(), 0.001);
    }

    @ParameterizedTest(name = "qty={0}, prix={1}, code=''{2}'' → total attendu={3}")
    @CsvSource({
        "2, 15.0,      ,  30.0",
        "2, 15.0, PROMO10, 27.0",
        "2, 15.0, PROMO20, 24.0",
        "5, 20.0,      , 100.0",
        "5, 20.0, PROMO10, 90.0",
        "5, 20.0, PROMO20, 80.0"
    })
    void getTotalAvecPlusieursArticlesDoitAppliquerLaBonneReduction(
            int quantity, double unitPrice, String code, double expectedTotal) {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("PROD-001", quantity, unitPrice);
        if (code != null && !code.isBlank()) {
            cart.applyPromoCode(code.trim());
        }
        assertEquals(expectedTotal, cart.getTotal(), 0.001);
    }
}
