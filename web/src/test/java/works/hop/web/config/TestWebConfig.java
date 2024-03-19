package works.hop.web.config;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestWebConfig {

    final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Bean
    public Validator validator() {
        return factory.getValidator();
    }
}
