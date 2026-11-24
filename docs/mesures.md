# Mesures du couplage — Avant / Après

| Mesure | Avant (étape 2) | Après (étape 4) |
| --- | --- | --- |
| Règle 1 : dépendances de Prêts, CRM, Mobile vers Risque | 3 | 0 |
| Règle 2 : accès aux tables de Risque hors de Risque | 2 | 0 |
| Cycles entre applications | 8 | 5 |
| Tests cassés hors de Risque quand Risque renomme une colonne | 5 | 0 |
| Temps moyen d’une évaluation du risque (local / distant, même machine) | 0,8 ms (appel local) | 1,6 à 2,2 ms (appel distant, 2 à 3 fois plus lent) |
| Demandes de prêt traitées quand le service Risque est arrêté | toutes | aucune (`SERVICE_INDISPONIBLE`) |
| Réponses différentes pour la cliente C005 selon l’application | 3 (REFUSE, pré-accord, ACCORDE) | 1 (REFUSE partout) |

Les règles d'architecture sont vérifiées avec :

`mvn -q -pl monolithe test "-Dgroups=architecture" "-Dgroupes.exclus=aucun"`

Résultat observé : règles 1 et 2 au vert, `>>> Cycles entre applications : 5`.

L'expérience du renommage de colonne (`SCORE` → `SCORE_BASE` dans `schema.sql` et `ScoreDAO.java`) a été rejouée après l'extraction : les 24 tests restent verts, aucun test hors de l'application Risque ne casse. Le renommage a ensuite été annulé.
