package robinh.s2qltp;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
    LoanCalculatorTest.class,
    LoanCalculatorParameterizedTest.class,
    BankAccountTest.class,
    LoanProcessorTest.class,
    TransferServiceTest.class,
    LoanApprovalServiceTest.class,
    LoanCalculatorSpyTest.class
})
class BankingTestSuite {
}
