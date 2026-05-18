package robinh.s2qltp;

import java.time.LocalDateTime;

/**
 * Commande validée pour un client, avec montant total et horodatage.
 *
 * @param customerId identifiant client
 * @param total        montant total TTC ou facturé
 * @param createdAt    date et heure de création de la commande
 */
public record Order(String customerId, double total, LocalDateTime createdAt) {
}
