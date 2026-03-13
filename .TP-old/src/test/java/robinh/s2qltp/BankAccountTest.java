package robinh.s2qltp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BankAccountTest {

    @Mock
    private AuditLogger logger;

    private BankAccount newAccount(double initialBalance) {
        return new BankAccount("test-account", initialBalance, logger);
    }

    @Test
    void depositDoitAugmenterLeSolde() {
        BankAccount account = newAccount(0);

        // Act
        account.deposit(100);

        // Assert
        assertEquals(100, account.getBalance(), 0.001,
                "Après un dépôt de 100, le solde doit être 100");
    }

    @Test
    void deuxDepositsSuccessifsDoiventCumulerLeSolde() {
        BankAccount account = newAccount(0);

        // Act
        account.deposit(100);
        account.deposit(50);

        // Assert
        assertEquals(150, account.getBalance(), 0.001,
                "Deux dépôts (100 + 50) doivent donner un solde de 150");
    }

    @Test
    void depositMontantNulDoitLeverException() {
        BankAccount account = newAccount(0);

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> account.deposit(0),
                "Un dépôt de 0 doit lever IllegalArgumentException");
    }

    @Test
    void depositMontantNegatifDoitLeverException() {
        BankAccount account = newAccount(0);

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> account.deposit(-50),
                "Un dépôt négatif doit lever IllegalArgumentException");
    }

    @Test
    void withdrawDoitReduireLeSolde() {
        BankAccount account = newAccount(0);
        account.deposit(200);

        // Act
        account.withdraw(50);

        // Assert
        assertEquals(150, account.getBalance(), 0.001,
                "Après un retrait de 50 sur 200, le solde doit être 150");
    }

    @Test
    void withdrawEgalAuSoldeDoitMettreLeCompteAZero() {
        BankAccount account = newAccount(0);
        account.deposit(100);

        // Act
        account.withdraw(100);

        // Assert
        assertEquals(0, account.getBalance(), 0.001,
                "Retirer exactement le solde doit mettre le compte à 0");
    }

    @Test
    void withdrawSuperieurAuSoldeDoitLeverException() {
        BankAccount account = newAccount(0);
        account.deposit(100);

        // Act + Assert
        assertThrows(IllegalStateException.class,
                () -> account.withdraw(200),
                "Retirer plus que le solde disponible doit lever IllegalStateException");
    }

    @Test
    void withdrawMontantNulDoitLeverException() {
        BankAccount account = newAccount(0);
        account.deposit(100);

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> account.withdraw(0),
                "Un retrait de 0 doit lever IllegalArgumentException");
    }

    @Test
    void withdrawMontantNegatifDoitLeverException() {
        BankAccount account = newAccount(0);
        account.deposit(100);

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> account.withdraw(-20),
                "Un retrait négatif doit lever IllegalArgumentException");
    }

    @Test
    void withdrawSurCompteVideDoitLeverException() {
        BankAccount account = newAccount(0);

        // Act + Assert
        assertThrows(IllegalStateException.class,
                () -> account.withdraw(10),
                "Retirer d'un compte vide doit lever IllegalStateException");
    }
}
