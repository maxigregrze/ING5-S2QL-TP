package robinh.s2qltp;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferServiceTest {

    @Mock
    private BankAccount sourceAccount;

    @Mock
    private BankAccount targetAccount;

    private TransferService transferService;

    @BeforeEach
    void setUp() {
        transferService = new TransferService(sourceAccount, targetAccount);
    }

    @Test
    void transfer_doitAppelerWithdrawSurSource_etDepositSurCible() {
        transferService.transfer(500.0);

        verify(sourceAccount).withdraw(500.0);
        verify(targetAccount).deposit(500.0);
    }
}
