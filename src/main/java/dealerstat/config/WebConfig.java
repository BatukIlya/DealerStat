package dealerstat.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@EnableWebMvc
@ComponentScan("dealerstat")
public class WebConfig {

//    @Bean
//    public ValidatorFactory validatorFactory() {
//        return Validation.buildDefaultValidatorFactory();
//    }
//
//
//    @Bean
//    public HibernateValidator validationProvider() {
//        return new HibernateValidator();
//    }
}
