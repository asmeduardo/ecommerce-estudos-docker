# Define a imagem base
FROM openjdk:17-jdk

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o arquivo JAR do Gateway Service para o contêiner
COPY ./target/gateway-service.jar gateway-service.jar

# Define as configurações de ambiente para o Contêiner
ENV SPRING_PROFILES_ACTIVE=docker

# Expõe a porta do Gateway Service
EXPOSE 8080

# Comando para iniciar o Gateway Service quando o contêiner for iniciado
ENTRYPOINT ["java", "-jar", "gateway-service.jar"]
