package fr.bankapp.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.bankapp.contrats.DecisionRisque;
import fr.bankapp.contrats.ErreurRisque;
import fr.bankapp.contrats.EvaluationRisque;
import fr.bankapp.contrats.ServiceRisque;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/** Le contrat, réalisé par le service Risque distant, appelé en HTTP. */
public class RisqueDistant implements ServiceRisque {

    /** Qualité promise par le contrat : une réponse en moins de 2 secondes. */
    static final Duration DELAI_MAXIMUM = Duration.ofSeconds(2);

    private final HttpClient client = HttpClient.newBuilder().connectTimeout(DELAI_MAXIMUM).build();
    private final ObjectMapper json = new ObjectMapper();

    @Override
    public EvaluationRisque evaluerRisque(String idClient, BigDecimal montant, int dureeMois) {
        String adresse = Annuaire.rechercher("risque.evaluation");
        URI uri = URI.create(adresse + "?idClient=" + idClient + "&montant=" + montant + "&dureeMois=" + dureeMois);
        HttpRequest requete = HttpRequest.newBuilder(uri).timeout(DELAI_MAXIMUM).GET().build();
        try {
            HttpResponse<String> reponse = client.send(requete, HttpResponse.BodyHandlers.ofString());
            JsonNode corps = json.readTree(reponse.body());
            if (reponse.statusCode() != 200) {
                throw new ErreurRisque(corps.path("erreur").asText("ERREUR_INCONNUE"), idClient);
            }
            return new EvaluationRisque(corps.get("score").asInt(), DecisionRisque.valueOf(corps.get("decision").asText()));
        } catch (IOException e) {
            throw new ErreurRisque("SERVICE_INDISPONIBLE", adresse);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ErreurRisque("SERVICE_INDISPONIBLE", adresse);
        }
    }
}
