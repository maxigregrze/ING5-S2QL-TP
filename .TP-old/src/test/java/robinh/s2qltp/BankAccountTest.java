package robinh.s2qltp;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void depositDoitAugmenterLeSolde() {
        // Arrange
        BankAccount account = new BankAccount();

        // Act
        account.deposit(100);

        // Assert
        assertEquals(100, account.getBalance(), 0.001,
                "Après un dépôt de 100, le solde doit être 100");
    }

    @Test
    void deuxDepositsSuccessifsDoiventCumulerLeSolde() {
        // Arrange
        BankAccount account = new BankAccount();

        // Act
        account.deposit(100);
        account.deposit(50);

        // Assert
        assertEquals(150, account.getBalance(), 0.001,
                "Deux dépôts (100 + 50) doivent donner un solde de 150");
    }

    @Test
    void depositMontantNulDoitLeverException() {
        // Arrange
        BankAccount account = new BankAccount();

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> account.deposit(0),
                "Un dépôt de 0 doit lever IllegalArgumentException");
    }

    @Test
    void depositMontantNegatifDoitLeverException() {
        // Arrange
        BankAccount account = new BankAccount();

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> account.deposit(-50),
                "Un dépôt négatif doit lever IllegalArgumentException");
    }

    @Test
    void withdrawDoitReduireLeSolde() {
        // Arrange
        BankAccount account = new BankAccount();
        account.deposit(200);

        // Act
        account.withdraw(50);

        // Assert
        assertEquals(150, account.getBalance(), 0.001,
                "Après un retrait de 50 sur 200, le solde doit être 150");
    }

    @Test
    void withdrawEgalAuSoldeDoitMettreLeCompteAZero() {
        // Arrange
        BankAccount account = new BankAccount();
        account.deposit(100);

        // Act
        account.withdraw(100);

        // Assert
        assertEquals(0, account.getBalance(), 0.001,
                "Retirer exactement le solde doit mettre le compte à 0");
    }

    @Test
    void withdrawSuperieurAuSoldeDoitLeverException() {
        // Arrange
        BankAccount account = new BankAccount();
        account.deposit(100);

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> account.withdraw(200),
                "Retirer plus que le solde disponible doit lever IllegalArgumentException");
    }

    @Test
    void withdrawMontantNulDoitLeverException() {
        // Arrange
        BankAccount account = new BankAccount();
        account.deposit(100);

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> account.withdraw(0),
                "Un retrait de 0 doit lever IllegalArgumentException");
    }

    @Test
    void withdrawMontantNegatifDoitLeverException() {
        // Arrange
        BankAccount account = new BankAccount();
        account.deposit(100);

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> account.withdraw(-20),
                "Un retrait négatif doit lever IllegalArgumentException");
    }

    @Test
    void withdrawSurCompteVideDoitLeverException() {
        // Arrange
        BankAccount account = new BankAccount();

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> account.withdraw(10),
                "Retirer d'un compte vide doit lever IllegalArgumentException");
    }
}
