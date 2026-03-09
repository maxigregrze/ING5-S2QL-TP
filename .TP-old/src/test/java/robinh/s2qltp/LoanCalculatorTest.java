package robinh.s2qltp;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LoanCalculatorTest {

    // Q1

    @Test
    void calculMensualiteDoitRetournerUneValeurPositive() {
        // Arrange
        LoanCalculator calc = new LoanCalculator();

        // Act
        double resultat = calc.calculMensualite(10000, 0.05, 10);

        // Assert
        assertTrue(resultat > 0, "La mensualité doit être strictement positive");
    }

    // Q2

    @Test
    void capitalNegatifDoitLeverException() {
        // Arrange
        LoanCalculator calc = new LoanCalculator();

        // Act + Assert 
        assertThrows(IllegalArgumentException.class,
                () -> calc.calculMensualite(-1000, 0.05, 10),
                "Un capital négatif doit lever IllegalArgumentException");
    }

    @Test
    void capitalNulDoitLeverException() {
        LoanCalculator calc = new LoanCalculator();

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> calc.calculMensualite(0, 0.05, 10),
                "Un capital nul doit lever IllegalArgumentException");
    }

    @Test
    void tauxNegatifDoitLeverException() {
        // Arrange
        LoanCalculator calc = new LoanCalculator();

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> calc.calculMensualite(10000, -0.01, 10),
                "Un taux négatif doit lever IllegalArgumentException");
    }

    @Test
    void dureeNulleDoitLeverException() {
        // Arrange
        LoanCalculator calc = new LoanCalculator();

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> calc.calculMensualite(10000, 0.05, 0),
                "Une durée nulle doit lever IllegalArgumentException");
    }

    @Test
    void dureeNegativeDoitLeverException() {
        // Arrange
        LoanCalculator calc = new LoanCalculator();

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> calc.calculMensualite(10000, 0.05, -5),
                "Une durée négative doit lever IllegalArgumentException");
    }

    // Q3

    @Test
    void tauxZeroDoitRetournerCapitalDiviseParNombreDeMois() {
        // Arrange
        LoanCalculator calc = new LoanCalculator();

        // Act
        double resultat = calc.calculMensualite(1200, 0.0, 1);

        // Assert 
        assertEquals(100.0, resultat, 0.001,
                "Avec taux=0, mensualité = capital / nbMois = 1200/12 = 100");
    }

    @Test
    void capitalTresFaibleDoitRetournerResultatPositif() {
        // Arrange
        LoanCalculator calc = new LoanCalculator();

        // Act
        double resultat = calc.calculMensualite(0.01, 0.05, 1);

        // Assert
        assertTrue(resultat > 0,
                "Même avec un capital très faible, la mensualité doit être positive");
    }

    @Test
    void dureeUnAnDoitRetournerMensualiteCoherente() {
        // Arrange
        LoanCalculator calc = new LoanCalculator();

        // Act
        double mensualite1An  = calc.calculMensualite(10000, 0.05, 1);
        double mensualite10Ans = calc.calculMensualite(10000, 0.05, 10);

        // Assert
        assertTrue(mensualite1An > mensualite10Ans,
                "La mensualité sur 1 an doit être plus élevée que sur 10 ans");
    }
}
