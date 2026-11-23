package fr.bankapp.integration;

import fr.bankapp.contrats.ErreurRisque;
import fr.bankapp.contrats.EvaluationRisque;
import fr.bankapp.contrats.ServiceRisque;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/** Aiguille chaque évaluation vers la réalisation locale ou distante selon bascule.properties. */
public class AiguillageRisque implements ServiceRisque {

    static final String CLE_BASCULE = "risque.realisation";

    private final String cheminBascule;
    private final ServiceRisque local = new RisqueLocal();
    private final ServiceRisque distant = new RisqueDistant();

    public AiguillageRisque() {
        this(System.getProperty("bankapp.bascule", "bascule.properties"));
    }

    public AiguillageRisque(String cheminBascule) {
        this.cheminBascule = cheminBascule;
    }

    @Override
    public EvaluationRisque evaluerRisque(String idClient, BigDecimal montant, int dureeMois) {
        Properties config = new Properties();
        try (var lecture = Files.newBufferedReader(Path.of(cheminBascule))) {
            config.load(lecture);
        } catch (IOException e) {
            throw new ErreurRisque("BASCULE_INDISPONIBLE", cheminBascule);
        }

        String realisation = config.getProperty(CLE_BASCULE, "local").trim();
        return switch (realisation) {
            case "local" -> local.evaluerRisque(idClient, montant, dureeMois);
            case "distant" -> distant.evaluerRisque(idClient, montant, dureeMois);
            default -> throw new ErreurRisque("REALISATION_INCONNUE", realisation);
        };
    }
}
