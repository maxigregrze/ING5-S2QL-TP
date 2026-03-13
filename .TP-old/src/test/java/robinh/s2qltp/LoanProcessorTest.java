package robinh.s2qltp;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanProcessorTest {

    @Mock
    private LoanApprovalService approvalService;

    @Mock
    private LoanCalculator calculator;

    @Mock
    private AuditLogger logger;

    @Captor
    private ArgumentCaptor<String> operationCaptor;

    private LoanProcessor processor;

    @BeforeEach
    void setUp() {
        processor = new LoanProcessor(approvalService, calculator, logger);
    }

    @Test
    void process_doitRetournerApproved_quandApprouve() {
        // ARRANGE
        when(approvalService.approveLoan("alice", 20000.0)).thenReturn(true);
        when(calculator.calculateMonthlyPayment(20000.0, 24)).thenReturn(876.50);

        // ACT
        LoanResult result = processor.process("alice", 20000.0, 24);

        // ASSERT
        assertTrue(result.approved());
        assertEquals(876.50, result.monthlyPayment(), 0.001);
    }

    @Test
    void process_doitRetournerRejected_quandRefuse() {
        // ARRANGE
        when(approvalService.approveLoan("bob", 20000.0)).thenReturn(false);

        // ACT
        LoanResult result = processor.process("bob", 20000.0, 24);

        // ASSERT
        assertFalse(result.approved());
        assertEquals(0.0, result.monthlyPayment(), 0.001);
    }

    @Test
    void process_doitLoggerApproved_avecBonsMots() {
        // ARRANGE
        when(approvalService.approveLoan("alice", 20000.0)).thenReturn(true);
        when(calculator.calculateMonthlyPayment(20000.0, 24)).thenReturn(876.50);

        // ACT
        processor.process("alice", 20000.0, 24);

        // ASSERT
        verify(logger).log(operationCaptor.capture(), anyDouble(), anyString());
        assertEquals("LOAN_APPROVED", operationCaptor.getValue());
    }

    @Test
    void process_doitLoggerRejected_avecBonsMots() {
        // ARRANGE
        when(approvalService.approveLoan("bob", 20000.0)).thenReturn(false);

        // ACT
        processor.process("bob", 20000.0, 24);

        // ASSERT
        verify(logger).log(operationCaptor.capture(), anyDouble(), anyString());
        assertEquals("LOAN_REJECTED", operationCaptor.getValue());
    }

    @Test
    void process_neDoitPasCalculer_quandRefuse() {
        // ARRANGE
        when(approvalService.approveLoan("bob", 20000.0)).thenReturn(false);

        // ACT
        processor.process("bob", 20000.0, 24);

        // ASSERT 
        verify(calculator, never()).calculateMonthlyPayment(anyDouble(), anyInt());
    }

    @Test
    void process_doitPropager_quandAPIThrows() {
        // ARRANGE
        when(approvalService.approveLoan(anyString(), anyDouble()))
                .thenThrow(new RuntimeException("Service indisponible"));

        // ACT et ASSERT
        assertThrows(RuntimeException.class,
                () -> processor.process("alice", 20000.0, 24));
        verify(logger, never()).log(any(), anyDouble(), any());
    }

    @Test
    void process_deuxAppelsSuccessifs_premierApprouve_secondRefuse() {
        // ARRANGE
        when(approvalService.approveLoan("alice", 20000.0))
                .thenReturn(true)
                .thenReturn(false);
        when(calculator.calculateMonthlyPayment(20000.0, 24)).thenReturn(876.50);

        // ACT
        LoanResult premier = processor.process("alice", 20000.0, 24);
        LoanResult second  = processor.process("alice", 20000.0, 24);

        // ASSERT
        assertTrue(premier.approved());
        assertFalse(second.approved());
        assertEquals(0.0, second.monthlyPayment(), 0.001);
    }

}