package fr.bankapp.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.bankapp.contrats.DecisionRisque;
import fr.bankapp.contrats.ErreurRisque;
import fr.bankapp.contrats.EvaluationRisque;
import fr.bankapp.contrats.ServiceRisque;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/** Adaptateur HTTP vers le service Risque distant, dont l'adresse est publiée dans l'annuaire. */
public class RisqueDistant implements ServiceRisque {

    static final String CLE_ANNUAIRE = "risque.evaluation";

    private final String cheminAnnuaire;
    private final ObjectMapper json = new ObjectMapper();

    public RisqueDistant() {
        this(System.getProperty("bankapp.annuaire", "annuaire/services.properties"));
    }

    public RisqueDistant(String cheminAnnuaire) {
        this.cheminAnnuaire = cheminAnnuaire;
    }

    @Override
    public EvaluationRisque evaluerRisque(String idClient, BigDecimal montant, int dureeMois) {
        String url = adresse() + "?idClient=" + encoder(idClient)
            + "&montant=" + encoder(montant.toPlainString())
            + "&dureeMois=" + dureeMois;

        HttpURLConnection connexion = null;
        try {
            connexion = (HttpURLConnection) URI.create(url).toURL().openConnection();
            connexion.setRequestMethod("GET");
            connexion.setConnectTimeout(2000);
            connexion.setReadTimeout(2000);

            int statut = connexion.getResponseCode();
            try (InputStream flux = statut < 400 ? connexion.getInputStream() : connexion.getErrorStream()) {
                JsonNode reponse = json.readTree(flux);
                if (statut >= 400) {
                    throw new ErreurRisque(reponse.path("erreur").asText("ERREUR_DISTANTE"), idClient);
                }
                return new EvaluationRisque(
                    reponse.path("score").asInt(),
                    DecisionRisque.valueOf(reponse.path("decision").asText())
                );
            }
        } catch (ErreurRisque e) {
            throw e;
        } catch (IOException | RuntimeException e) {
            throw new ErreurRisque("SERVICE_INDISPONIBLE", e.getMessage());
        } finally {
            if (connexion != null) {
                connexion.disconnect();
            }
        }
    }

    private String adresse() {
        Properties services = new Properties();
        try (var lecture = Files.newBufferedReader(Path.of(cheminAnnuaire))) {
            services.load(lecture);
        } catch (IOException e) {
            throw new ErreurRisque("ANNUAIRE_INDISPONIBLE", cheminAnnuaire);
        }
        String adresse = services.getProperty(CLE_ANNUAIRE);
        if (adresse == null || adresse.isBlank()) {
            throw new ErreurRisque("ANNUAIRE_INDISPONIBLE", CLE_ANNUAIRE);
        }
        return adresse;
    }

    private static String encoder(String valeur) {
        return URLEncoder.encode(valeur, StandardCharsets.UTF_8);
    }
}
