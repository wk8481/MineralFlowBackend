package be.kdg.prgramming6.landside;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Testcontainers
@ContextConfiguration(initializers = AbstractDatabaseTest.DataSourceInitializer.class)
@ActiveProfiles("test")
public abstract class AbstractDatabaseTest {
    private static final MySQLContainer<?> DATABASE;

    static {
        DATABASE = new MySQLContainer<>("mysql:8.0.30")
                .withUsername("root")
                .withPassword("")
                .withPrivilegedMode(true)
                .withInitScript("initScript.sql");
        try {
            DATABASE.start();
        } catch (Exception e) {
            System.err.println("Container startup failed. Logs:");
            System.err.println(DATABASE.getLogs());
            throw e;
        }
    }

    @Test
    public void containerIsRunning() {
        assertTrue(DATABASE.isCreated());
        assertTrue(DATABASE.isRunning());
    }

    public static class DataSourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "spring.datasource.url=" + DATABASE.getJdbcUrl(),
                    "spring.datasource.username=" + DATABASE.getUsername(),
                    "spring.datasource.password=" + DATABASE.getPassword(),
                    "spring.sql.init.mode=always",
                    "spring.jpa.generate-ddl=true"
            );
        }
    }
}