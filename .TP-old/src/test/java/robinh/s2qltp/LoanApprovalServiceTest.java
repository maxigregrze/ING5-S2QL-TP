package robinh.s2qltp;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanApprovalServiceTest {

    @Mock
    private CreditScoringAPI scoringAPI; // mock créé automatiquement

    private LoanApprovalService approvalService;

    @BeforeEach
    void setUp() {
        approvalService = new LoanApprovalService(scoringAPI, 50000.0);
    }
    @Test
    void approveLoan_doitRetournerTrue_quandScoreSuffisantEtMontantOk() {
        // ARRANGE
        when(scoringAPI.isBlacklisted("alice")).thenReturn(false);
        when(scoringAPI.getScore("alice")).thenReturn(720);

        // ACT
        boolean result = approvalService.approveLoan("alice", 20000.0);

        // ASSERT
        assertTrue(result);
    }

    // Q1
    @Test
    void approveLoan_doitRetournerFalse_quandScoreInsuffisant() {
        // ARRANGE 
        when(scoringAPI.isBlacklisted("alice")).thenReturn(false);
        when(scoringAPI.getScore("alice")).thenReturn(550);

        // ACT
        boolean result = approvalService.approveLoan("alice", 20000.0);

        // ASSERT
        assertFalse(result);
    }

    // q2
    @Test
    void getScore_nonConfigure_doitRetourner0ParDefaut() {
        // ARRANGE 
        when(scoringAPI.isBlacklisted("inconnu")).thenReturn(false);

        // ACT
        boolean result = approvalService.approveLoan("inconnu", 20000.0);

        // ASSERT 
        assertFalse(result);
    }

    // Q3
    @Test
    void getLoanDecision_doitRetournerRejectedScore_quandScore580() {
        // ARRANGE
        when(scoringAPI.isBlacklisted("bob")).thenReturn(false);
        when(scoringAPI.getScore("bob")).thenReturn(580);

        // ACT
        String decision = approvalService.getLoanDecision("bob", 20000.0);

        // ASSERT
        assertEquals("REJECTED_SCORE", decision);
    }

    // Q'
    @Test
    void approveLoan_doitRetournerFalse_quandClientBlackliste() {
        // ARRANGE
        when(scoringAPI.isBlacklisted("eve")).thenReturn(true);

        // ACT
        boolean result = approvalService.approveLoan("eve", 20000.0);

        // ASSERT
        assertFalse(result);
        verify(scoringAPI, never()).getScore(any());
    }

    @Test
    void approveLoan_doitRetournerFalse_quandMontantTropEleve() {
        // ARRANGE
        when(scoringAPI.isBlacklisted("carol")).thenReturn(false);
        when(scoringAPI.getScore("carol")).thenReturn(701);

        // ACT
        boolean result = approvalService.approveLoan("carol", 60000.0);

        // ASSERT
        assertFalse(result);
    }

    @Test
    void approveLoan_doitRetournerFalse_quandAPIIndisponible() {
        // Configurer le mock pour lever une exception
        when(scoringAPI.isBlacklisted(anyString()))
                .thenThrow(new RuntimeException("API scoring indisponible"));

        boolean result = approvalService.approveLoan("alice", 20000.0);

        assertFalse(result, "Un refus par défaut est attendu si l'API est down");
    }

    @Test
    void getScore_doitLeverException_quandScoreNegatif() {
        when(scoringAPI.isBlacklisted("dave")).thenReturn(false);
        when(scoringAPI.getScore("dave"))
                .thenThrow(new IllegalStateException("Score invalide"));

        assertThrows(IllegalStateException.class,
                () -> approvalService.getLoanDecision("dave", 20000.0));
    }

    // Q12
    @Test
    void getScore_premierAppelReussit_secondAppelLeveException() {
        when(scoringAPI.isBlacklisted(anyString())).thenReturn(false);
        when(scoringAPI.getScore(anyString()))
                .thenReturn(700)
                .thenThrow(new RuntimeException("API down au 2e appel"));

        assertTrue(approvalService.approveLoan("alice", 20000.0));  
        assertFalse(approvalService.approveLoan("alice", 20000.0));  
    }

}