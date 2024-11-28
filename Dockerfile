# Etapa 1: Build
FROM amazoncorretto:17 AS build

# Define o diretório de trabalho na imagem
WORKDIR /app

RUN yum update -y && yum install -y gzip tar curl

# Copia os arquivos de build para o container
COPY ./mvnw .
COPY ./.mvn ./.mvn
COPY ./pom.xml .
COPY ./src ./src

RUN chmod +x ./mvnw

# Executa o Maven para compilar o projeto
RUN ./mvnw package -DskipTests



# Etapa 2: Runtime
FROM amazoncorretto:17

# Define o diretório de trabalho
WORKDIR /app

# Copia o artefato gerado na etapa de build
COPY --from=build /app/target/*.jar api-spring-boot-1.0.jar


# Exponha a porta 8080
EXPOSE 8080

# Executa o jar gerado
ENTRYPOINT ["java", "-jar", "api-spring-boot-1.0.jar", "--spring.profiles.active=prod"]
