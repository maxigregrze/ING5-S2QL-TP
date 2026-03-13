// AuditLogger.java --- interface de journalisation
package robinh.s2qltp;

public interface AuditLogger {
    void log(String operation, double amount, String accountId);
}
