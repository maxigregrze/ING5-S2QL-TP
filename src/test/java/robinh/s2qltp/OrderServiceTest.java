package robinh.s2qltp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    private OrderService service;
    private ShoppingCart cart;

    @BeforeEach
    void setUp() {
        InventoryRepository stockSuffisant = productId -> 100;
        service = new OrderService(stockSuffisant);
        cart = new ShoppingCart();
    }

    // Q4

    @Test
    void commandeValideDoitRetournerOrder() {
        cart.addItem("PROD-001", 2, 15.0);
        Order order = service.placeOrder(cart, "CLIENT-42");
        assertNotNull(order);
        assertEquals(30.0, order.total(), 0.001);
        assertEquals("CLIENT-42", order.customerId());
    }

    @Test
    void panierVideDoitLeverIllegalStateException() {
        assertThrows(IllegalStateException.class,
                () -> service.placeOrder(cart, "CLIENT-42"));
    }

    @Test
    void customerIdNulDoitLeverException() {
        cart.addItem("PROD-001", 1, 10.0);
        assertThrows(IllegalArgumentException.class,
                () -> service.placeOrder(cart, null));
    }

    @Test
    void customerIdVideDoitLeverException() {
        cart.addItem("PROD-001", 1, 10.0);
        assertThrows(IllegalArgumentException.class,
                () -> service.placeOrder(cart, ""));
    }

    @Test
    void stockInsuffisantDoitLeverOutOfStockException() {
        InventoryRepository stockVide = productId -> 0;
        OrderService serviceStockVide = new OrderService(stockVide);
        cart.addItem("PROD-001", 5, 10.0);
        assertThrows(OutOfStockException.class,
                () -> serviceStockVide.placeOrder(cart, "CLIENT-42"));
    }

    @Test
    void stockExactementEgalALaQuantiteDoitPasserSansException() {
        InventoryRepository stockJuste = productId -> 3;
        OrderService serviceStockJuste = new OrderService(stockJuste);
        cart.addItem("PROD-001", 3, 10.0);
        Order order = serviceStockJuste.placeOrder(cart, "CLIENT-42");
        assertNotNull(order);
    }

    @Test
    void commandeAvecPromoDoitAppliquerReduction() {
        cart.addItem("PROD-001", 10, 10.0);
        cart.applyPromoCode("PROMO10");
        Order order = service.placeOrder(cart, "CLIENT-42");
        assertEquals(90.0, order.total(), 0.001);
    }
}
