# Define a imagem base
FROM openjdk:17-jdk

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o arquivo JAR do Config Server para o contêiner
ADD ./target/config-server-0.0.1-SNAPSHOT.jar config-server.jar

# Define as configurações de ambiente para o Contêiner
ENV SPRING_PROFILES_ACTIVE=docker

# Expõe a porta do Config Server
EXPOSE 8888

# Comando para iniciar o Config Server quando o contêiner for iniciado
ENTRYPOINT ["java", "-jar", "config-server.jar"]
