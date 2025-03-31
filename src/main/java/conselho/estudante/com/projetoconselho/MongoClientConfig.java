package conselho.estudante.com.projetoconselho;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import java.util.logging.Logger;

@Configuration
public class MongoClientConfig extends AbstractMongoClientConfiguration {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(MongoClientConfig.class);

    @Value("logs_db")
    private String database;

    @Value("${spring.data.mongodb.uri}")
    private String connectionString;

    @Override
    protected String getDatabaseName() {
        return database;
    }

    @Bean
    public MongoClientSettings mongoClientSettings() {
        LOGGER.info("MongoDB connection string: " + connectionString);
        return MongoClientSettings.builder()
                .applyConnectionString( new ConnectionString(connectionString) )
                .build();
    }
}
