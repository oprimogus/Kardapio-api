# Use uma imagem base do Java
FROM openjdk:17

RUN mvn clean package

# Copie o jar do seu aplicativo para a imagem
COPY target/*.jar /app.jar

EXPOSE 8080

# O comando para executar sua aplicação
CMD ["java", "-jar", "/app.jar"]
