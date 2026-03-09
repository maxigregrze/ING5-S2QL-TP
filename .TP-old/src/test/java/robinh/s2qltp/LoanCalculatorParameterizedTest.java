package robinh.s2qltp;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

class LoanCalculatorParameterizedTest {


    @ParameterizedTest(name = "capital={0}, taux={1}, durée={2} ans")
    @CsvSource({
        "1000,  0.05,  5",   
        "5000,  0.03, 10",   
        "10000, 0.04, 20",   
        "200000, 0.03, 25", 
        "500,   0.1,  2"    
    })
    void calculMensualiteDoitRetournerUneValeurPositive(
            double capital, double taux, int duree) {

        // Arrange
        LoanCalculator calc = new LoanCalculator();

        // Act
        double resultat = calc.calculMensualite(capital, taux, duree);

        // Assert
        assertTrue(resultat > 0,
                String.format("La mensualité doit être positive pour capital=%.0f, taux=%.3f, durée=%d", capital, taux, duree));
    }

    @ParameterizedTest(name = "capital={0}, taux={1}, durée={2} ans → attendu≈{3}")
    @CsvSource({
        "1200, 0.0,  1, 100.00",   
        "1000, 0.12, 1,  88.85"    
    })
    void calculMensualiteDoitCorrespondreALaValeurAttendue(
            double capital, double taux, int duree, double attendu) {

        // Arrange
        LoanCalculator calc = new LoanCalculator();

        // Act
        double resultat = calc.calculMensualite(capital, taux, duree);

        // Assert
        assertEquals(attendu, resultat, 0.01,
                String.format("Mensualité attendue ≈ %.2f pour capital=%.0f, taux=%.2f, durée=%d", attendu, capital, taux, duree));
    }

    @ParameterizedTest(name = "capital={0}, taux={1}, durée={2} → exception attendue")
    @CsvSource({
        "-1000,  0.05, 10", 
        "    0,  0.05, 10",  
        "10000, -0.01, 10", 
        "10000,  0.05,  0",  
        "10000,  0.05, -1"   
    })
    void parametresInvalidesDoiventLeverException(
            double capital, double taux, int duree) {

        // Arrange
        LoanCalculator calc = new LoanCalculator();

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> calc.calculMensualite(capital, taux, duree),
                "Des paramètres invalides doivent toujours lever IllegalArgumentException");
    }
}
