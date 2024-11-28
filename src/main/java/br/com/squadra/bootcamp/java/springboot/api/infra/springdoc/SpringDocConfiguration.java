package br.com.squadra.bootcamp.java.springboot.api.infra.springdoc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

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

    @Bean
    public OpenApiCustomizer sortTagsCustomizer() {
        return openApi -> {
            openApi.setTags(Arrays.asList(
                    new Tag().name("uf").description("Operações relacionadas a Ufs"),
                    new Tag().name("municipio").description("Operações relacionadas a Municípios"),
                    new Tag().name("bairro").description("Operações relacionadas a Bairros"),
                    new Tag().name("pessoa").description("Operações relacionadas a Pessoas")
            ));
        };
    }

}
