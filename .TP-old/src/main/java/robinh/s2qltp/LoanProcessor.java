// LoanProcessor.java - à implémenter
package robinh.s2qltp;

public class LoanProcessor {

    private final LoanApprovalService approvalService;
    private final LoanCalculator calculator;
    private final AuditLogger logger;

    public LoanProcessor(LoanApprovalService approvalService,
                         LoanCalculator calculator,
                         AuditLogger logger) {
        this.approvalService = approvalService;
        this.calculator = calculator;
        this.logger = logger;
    }

    /**
     * Traite une demande de prêt complète.
     * @return LoanResult avec statut APPROVED/REJECTED et mensualité
     */
    public LoanResult process(String borrowerId, double amount, int months) {
        boolean approved = approvalService.approveLoan(borrowerId, amount);

        if (!approved) {
            logger.log("LOAN_REJECTED", amount, borrowerId);
            return new LoanResult(false, 0.0);
        }

        double monthly = calculator.calculateMonthlyPayment(amount, months);
        logger.log("LOAN_APPROVED", amount, borrowerId);
        return new LoanResult(true, monthly);
    }
}
