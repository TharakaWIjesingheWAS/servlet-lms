package lk.ijse.dep.web.lms;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories("lk.ijse.dep.web.lms.repository")
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
@Configuration
public class JPAConfig {
}
