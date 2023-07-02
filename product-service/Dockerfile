# Use a base image com suporte ao Java
FROM openjdk:17-jdk

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copie o arquivo JAR do microserviço para o diretório de trabalho
ADD ./target/product-service-0.0.1-SNAPSHOT.jar product-service.jar

# Exponha a porta em que o microserviço está escutando (por exemplo, porta 8081)
EXPOSE 8081

# Comando para iniciar o microserviço quando o contêiner for iniciado
ENTRYPOINT ["java", "-jar", "product-service.jar"]
