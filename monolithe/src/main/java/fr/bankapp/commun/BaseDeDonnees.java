package fr.bankapp.commun;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

/**
 * La base de données unique de BankApp, partagée par toutes les applications.
 * Le schéma est dans src/main/resources/schema.sql, les données dans donnees.sql.
 */
public final class BaseDeDonnees {

    private static final DataSource SOURCE =
            new DriverManagerDataSource("jdbc:h2:mem:bankapp;DB_CLOSE_DELAY=-1", "sa", "");
    private static final JdbcTemplate JDBC = new JdbcTemplate(SOURCE);

    private BaseDeDonnees() {
    }

    public static JdbcTemplate jdbc() {
        return JDBC;
    }

    /** Recrée toutes les tables et recharge les données. Appelée au démarrage et avant chaque test. */
    public static void initialiser() {
        JDBC.execute("DROP ALL OBJECTS");
        new ResourceDatabasePopulator(new ClassPathResource("schema.sql"), new ClassPathResource("donnees.sql"))
                .execute(SOURCE);
    }
}
