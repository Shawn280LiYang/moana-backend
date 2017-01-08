package lab.io.rush.dao.datanucleus;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by liyang on 17/1/2.
 */
@Configuration
public class DataNucleusConfig {

    // Create an EntityManagerFactory for this "persistence-unit"
    // See the file "META-INF/persistence.xml"
    @Bean
    public EntityManagerFactory createEntityManagerFactory(){
        return Persistence.createEntityManagerFactory("Moana");
    }
}
