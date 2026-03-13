package robinh.s2qltp;
public class LoanCalculator {

    public double calculMensualite(double capital, double tauxAnnuel, int dureeAnnees) {

        if (capital <= 0) {
            throw new IllegalArgumentException("Capital invalide");
        }
        if (tauxAnnuel < 0) {
            throw new IllegalArgumentException("Taux invalide");
        }
        if (dureeAnnees <= 0) {
            throw new IllegalArgumentException("Durée invalide");
        }

        double tauxMensuel = tauxAnnuel / 12;
        int nbMois = dureeAnnees * 12;

        if (tauxMensuel == 0) {
            return capital / nbMois;
        }

        return (capital * tauxMensuel) / (1 - Math.pow(1 + tauxMensuel, -nbMois));
    }

    public double getInterestRate() {
        return 0.035; 
    }

    public double calculateMonthlyPayment(double capital, int months) {
        if (capital <= 0) throw new IllegalArgumentException("Capital invalide");
        if (months <= 0)  throw new IllegalArgumentException("Durée invalide");

        double annualRate = getInterestRate();
        double monthlyRate = annualRate / 12;

        if (monthlyRate == 0) {
            return capital / months;
        }
        return (capital * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -months));
    }
}
