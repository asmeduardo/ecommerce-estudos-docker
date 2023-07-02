# Define a imagem base
FROM openjdk:17-jdk

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o arquivo JAR do Eureka Server para o contêiner
ADD ./target/discovery-server-0.0.1-SNAPSHOT.jar discovery-server.jar

# Define as configurações de ambiente para o Contêiner
ENV SPRING_PROFILES_ACTIVE=docker

# Expõe a porta do Eureka Server
EXPOSE 8761

# Comando para iniciar o Eureka Server quando o contêiner for iniciado
ENTRYPOINT ["java", "-jar", "discovery-server.jar"]
