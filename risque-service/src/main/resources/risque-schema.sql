-- Base PROPRE au service Risque. Aucune autre application n'y a accès.
CREATE TABLE T_SCORE (
    ID_CLIENT    VARCHAR(10) PRIMARY KEY,
    SCORE        INT NOT NULL
);
CREATE TABLE T_INCIDENT (
    ID_CLIENT       VARCHAR(10) NOT NULL,
    DATE_INCIDENT   DATE NOT NULL
);
-- Copie de la date de naissance, pour ne plus lire la base du CRM
CREATE TABLE T_PROFIL (
    ID_CLIENT       VARCHAR(10) PRIMARY KEY,
    DATE_NAISSANCE  DATE NOT NULL
);
