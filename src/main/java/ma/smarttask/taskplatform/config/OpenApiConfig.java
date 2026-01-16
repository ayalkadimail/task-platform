package ma.smarttask.taskplatform.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI smartTaskOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Smart Task Platform API")
                        .description("Système intelligent de gestion de tâches pour étudiants")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Aya Al Kadiri")
                                .email("al.kadiriaya.502@gmail.com")));
    }
}