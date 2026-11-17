# Audit du couplage

## Dépendances Java : 13 dépendances entre domaines

| # | De | Vers | Élément utilisé | jdeps | Techno | Lieu | Données | Temps |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 1 | Prêts | Comptes | `CompteDAO.getSolde()` — package `comptes.interne` | oui | X | | | X |
| 2 | Comptes | CRM | Classe du package `crm.interne` | oui | X | | | X |
| 3 | Comptes | Prêts | Classe du package `prets.interne` | oui | X | | | X |
| 4 | Comptes | Risque | Classe du package `risque` | oui | X | | | X |
| 5 | CRM | Comptes | Classe du package `comptes.interne` | oui | X | | | X |
| 6 | CRM | Risque | Classe du package `risque.interne` | oui | X | | | X |
| 7 | Mobile | Comptes | Classe du package `comptes` | oui | X | | | X |
| 8 | Mobile | CRM | Classe du package `crm.interne` | oui | X | | | X |
| 9 | Mobile | Prêts | Classe du package `prets.interne` | oui | X | | | X |
| 10 | Mobile | Risque | Classe du package `risque.interne` | oui | X | | | X |
| 11 | Prêts | CRM | Classe du package `crm.interne` | oui | X | | | X |
| 12 | Risque | Comptes | Classe du package `comptes.interne` | oui | X | | | X |
| 13 | Risque | CRM | Depuis `risque.interne` vers `crm.interne` | oui | X | | | X |

## Accès SQL croisés : 3
| # | De | Vers | Élément utilisé | jdeps | Techno | Lieu | Données | Temps |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 14 | Prêts | Risque | `PretService.java:25` — lecture de `RISK_DB.T_SCORE` | non | | | X | X |
| 15 | CRM | Risque | `CampagneService.java:34` — lecture de `RISK_DB.T_SCORE` | non | | | X | X |
| 16 | Mobile | Comptes | `SimulationMobileService.java:17` — lecture de `CPT_DB.T_COMPTE` | non | | | X | X |

## Appels directs à Acme SMS : 4
| # | De | Vers | Élément utilisé | jdeps | Techno | Lieu | Données | Temps |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 17 | Prêts | Acme SMS | `PretService.java:29` — `new AcmeSms("10.2.4.17").send(...)` | oui* | X | X | | X |
| 18 | Comptes | Acme SMS | `VirementService.java:35` — `new AcmeSms("10.2.4.17").send(...)` | oui* | X | X | | X |
| 19 | CRM | Acme SMS | `MessagerieService.java:11` — `new AcmeSms("10.2.4.17").send(...)` | oui* | X | X | | X |
| 20 | Mobile | Acme SMS | `AppMobileService.java:16` — `new AcmeSms("10.2.4.17").send(...)` | oui* | X | X | | X |

## Dimensions du couplage

- **Technologie** : fort si les deux côtés doivent partager le même langage ou la même librairie.
- **Localisation** : fort si l’adresse de l’autre est écrite dans le code.
- **Données** : fort si l’on accède directement à la base de données de l’autre.
- **Temps** : fort si l’appelant reste bloqué jusqu’à la réponse. Ici, tous les appels sont synchrones.

## Mesures brutes

| Mesure | Nombre |
| --- | --- |
| Dépendances croisées vues par jdeps entre domaines métier | 13 |
| Accès SQL croisés | 3 |
| Appels directs à Acme SMS | 4 |

