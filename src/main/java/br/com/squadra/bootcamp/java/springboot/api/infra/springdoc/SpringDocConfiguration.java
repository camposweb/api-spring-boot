package br.com.squadra.bootcamp.java.springboot.api.infra.springdoc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Bootcamp Java - Spring Boot")
                        .description("API para o Bootcamp Java - Spring Boot | Projeto desenvolvido como desafio final do bootcamp java com spring boot da Squadra")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Leandro Campos")
                                .email("camposweb@gmail.com")
                                .url("https://github.com/camposweb")
                        )
                );

    }

}
