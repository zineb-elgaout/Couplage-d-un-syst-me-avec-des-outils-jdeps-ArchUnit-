# Avis d'architecte : extraction du service Risque de BankApp

## 1. Recommandation

Poursuivre l'extraction en généralisant le service Risque à tous ses consommateurs, puis extraire Comptes, le prochain goulet d'étranglement.

## 2. Métriques avant et après

| Mesure | Avant (étape 2) | Après (étape 4) |
| --- | --- | --- |
| Dépendances de Prêts, CRM, Mobile vers Risque | 3 | 0 |
| Accès aux tables de Risque hors de Risque | 2 | 0 |
| Cycles entre applications | 8 | 5 |
| Tests cassés hors de Risque lors d'un renommage de colonne | 5 | 0 |
| Temps moyen d'une évaluation (même machine) | 0,8 ms | 1,6 à 2,2 ms |
| Demandes traitées service Risque arrêté | toutes | aucune |
| Réponses contradictoires pour C005 | 3 | 1 |

## 3. Ce qu'on a gagné

- **Réutilisabilité / interopérabilité** : les trois consommateurs ne connaissent plus que le contrat `evaluerRisque`. Les 3 dépendances directes et les 2 accès SQL à `RISK_DB` ont disparu ; 3 cycles sur 8 sont cassés. Le contrat a ses propres types (`EvaluationRisque`, `DecisionRisque`, `ErreurRisque`), indépendants de l'application Risque.
- **Évolutivité** : la règle de risque n'existe plus qu'une fois. La cliente C005 recevait trois réponses contradictoires (REFUSE pour Risque, pré-accord pour Mobile, ACCORDE pour Prêts) ; elle est désormais refusée partout. Quand l'équipe Risque renomme sa colonne, aucun test hors de Risque ne casse, contre 5 avant : le changement reste derrière le contrat, personne n'a besoin d'être prévenu.
- **Livraison continue** : `AiguillageRisque` relit `bascule.properties` à chaque appel. La bascule locale → distante se fait sans redémarrage, avec retour arrière immédiat (patron Strangler Fig).

## 4. Ce que ça coûte

- **Réseau** : l'appel distant est 2 à 3 fois plus lent sur la même machine (1,6 à 2,2 ms contre 0,8 ms), avant même d'ajouter le transport sur un vrai réseau. `DELAI_MAXIMUM = 2 s` traduit la qualité promise par le contrat en délai d'attente.
- **Temps** : le couplage temporel est désormais visible : service Risque arrêté, aucune demande de prêt n'aboutit (`SERVICE_INDISPONIBLE`). Le retour en mode local fait repartir le guichet immédiatement. Pour limiter l'effet : délai d'attente court, nouvelle tentative, mode dégradé, voire échanges asynchrones.
- **Gouvernance et exploitation** : un processus de plus à déployer et surveiller, une adresse à publier dans l'annuaire, et une base propre à synchroniser (scores, incidents et dates de naissance copiés depuis le monolithe).

## 5. Quel service extraire ensuite

**Comptes.** Les 5 cycles restants passent tous par lui : Prêts, CRM et Mobile utilisent encore ses classes internes (`CompteDAO`) ou lisent directement `CPT_DB.T_COMPTE` (cas de `SimulationMobileService`). L'extraire cassera les cycles restants et supprimera le dernier accès SQL croisé. En second temps, Notification : quatre applications appellent encore la librairie Acme avec l'adresse `10.2.4.17` écrite en dur.
