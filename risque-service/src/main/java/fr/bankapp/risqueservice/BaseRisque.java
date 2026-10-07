package fr.bankapp.risqueservice;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

/** La base du service Risque : distincte de celle du monolithe. */
public final class BaseRisque {

    private static final DataSource SOURCE =
            new DriverManagerDataSource("jdbc:h2:mem:risque;DB_CLOSE_DELAY=-1", "sa", "");
    private static final JdbcTemplate JDBC = new JdbcTemplate(SOURCE);

    private BaseRisque() {
    }

    public static JdbcTemplate jdbc() {
        return JDBC;
    }

    public static void initialiser() {
        JDBC.execute("DROP ALL OBJECTS");
        new ResourceDatabasePopulator(new ClassPathResource("risque-schema.sql"),
                new ClassPathResource("risque-migration.sql")).execute(SOURCE);
    }
}
