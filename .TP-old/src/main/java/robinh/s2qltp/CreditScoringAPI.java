// CreditScoringAPI.java --- interface (dépendance externe)
package robinh.s2qltp;

public interface CreditScoringAPI {
    int getScore(String borrowerId);        // retourne 0-850
    boolean isBlacklisted(String borrowerId);
}
