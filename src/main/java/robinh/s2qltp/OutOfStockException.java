package robinh.s2qltp;

/**
 * Levée lorsque le stock est insuffisant pour honorer une commande.
 */
public class OutOfStockException extends RuntimeException {

    /**
     * Construit une exception avec un message explicite.
     *
     * @param message détail du problème de stock
     */
    public OutOfStockException(String message) {
        super(message);
    }
}
