package fr.bankapp.prets;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/** Calcul de la mensualité d'un prêt à taux fixe. */
public class SimulateurPret {

    public static final BigDecimal TAUX_ANNUEL = new BigDecimal("0.05");

    public BigDecimal mensualite(BigDecimal montant, int dureeMois) {
        BigDecimal tauxMensuel = TAUX_ANNUEL.divide(BigDecimal.valueOf(12), MathContext.DECIMAL64);
        BigDecimal facteur = BigDecimal.ONE.add(tauxMensuel).pow(dureeMois, MathContext.DECIMAL64);
        BigDecimal mensualite = montant.multiply(tauxMensuel).multiply(facteur)
                .divide(facteur.subtract(BigDecimal.ONE), MathContext.DECIMAL64);
        return mensualite.setScale(2, RoundingMode.HALF_UP);
    }
}
