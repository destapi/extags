package works.hop.web.config;

import com.google.gson.Gson;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("application.properties")
public class AppConfig {

    final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Bean
    public Gson gson() {
        return new Gson();
    }

    @Bean
    public Validator validator(){
        return factory.getValidator();
    }
}
