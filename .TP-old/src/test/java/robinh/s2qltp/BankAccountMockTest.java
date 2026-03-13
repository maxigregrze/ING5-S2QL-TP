package robinh.s2qltp;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class BankAccountMockTest {
    
    @Mock
    private AuditLogger logger;

    @Captor ArgumentCaptor<String> operationCaptor;
    @Captor ArgumentCaptor<Double> amountCaptor;
    @Captor ArgumentCaptor<String> accountCaptor;

    private BankAccount account;

    @BeforeEach
    void setUp() {
        account = new BankAccount("ACC-001", 1000.0, logger);
    }

    @Test
    void deposit_doitAppelerLogger_avecBonsParametres() {
        account.deposit(500.0);

        // Vérifier que logger.log() a été appelé exactement 1 fois avec les bons arguments
        verify(logger, times(1)).log("DEPOSIT", 500.0, "ACC-001");
    }

    @Test
    void withdraw_doitAppelerLogger_avecBonsParametres() {
        account.withdraw(200.0);

        verify(logger, times(1)).log("WITHDRAW", 200.0, "ACC-001");
    }

    @Test
    void deposit_invalide_neDoitPasAppelerLogger() {
        assertThrows(IllegalArgumentException.class,
                () -> account.deposit(-100.0));

        verify(logger, never()).log(any(), anyDouble(), any());
    }

    @Test
    void depuisEtRetrait_doiventEtreJournalisesEnOrdre() {
    account.deposit(300.0);
    account.withdraw(100.0);

    // Vérifier que le dépôt est journalisé AVANT le retrait
    InOrder inOrder = inOrder(logger);
    inOrder.verify(logger).log("WITHDRAW", 100.0, "ACC-001");
    inOrder.verify(logger).log("DEPOSIT",  300.0, "ACC-001");
    }

    @Test
    void deposit_doitJournaliserAvecMontantExact() {
        // Déclarer le captor
        ArgumentCaptor<Double> localAmountCaptor = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<String> localOpCaptor     = ArgumentCaptor.forClass(String.class);

        account.deposit(750.50);

        // Capturer les arguments passés à logger.log()
        verify(logger).log(localOpCaptor.capture(), localAmountCaptor.capture(), anyString());
        // Inspecter les valeurs capturées
        assertEquals("DEPOSIT", localOpCaptor.getValue());
        assertEquals(750.50,    localAmountCaptor.getValue(), 0.001);
    }

    @Test
    void withdraw_doitJournaliserBonneOperation() {
        account.withdraw(300.0);

        verify(logger).log(
            operationCaptor.capture(),
            amountCaptor.capture(),
            accountCaptor.capture());

        assertEquals("WITHDRAW", operationCaptor.getValue());
        assertEquals(300.0,      amountCaptor.getValue(),  0.001);
        assertEquals("ACC-001",  accountCaptor.getValue());
    }

    @Test
    void deuxDeposits_getAllValues_doitContenirLesDeux() {
        account.deposit(200.0);
        account.deposit(400.0);

        verify(logger, times(2)).log(anyString(), amountCaptor.capture(), anyString());
        List<Double> montants = amountCaptor.getAllValues();
        assertEquals(List.of(200.0, 400.0), montants);
    }

}
