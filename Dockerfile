# ====== Etapa 1: Fase de construcción (es temporal)======
# Usamos una imagen de Maven con JDK 23 para construir la aplicación
FROM maven:3.9-eclipse-temurin-23 AS imagen_construccion

#
WORKDIR /app
COPY pom.xml .

COPY src ./src
RUN mvn clean package

# ====== Etapa 2: Fase de ejecución (con la que generará la imagen) ======
# En teoría sólo necesitamos la JRE para ejecutar la aplicación (ojo, a veces puede dar problemas y se usa la JDK )
# No se necesita el entorno de construcción completo (Maven + JDK + src), sólo el resultado de la construcción (el .jar) y un entorno de ejecución (JRE)
FROM eclipse-temurin:23-jre AS imagen_ejecucion

WORKDIR /app

# Copia el jar generado: lo que se obtendría (aaee_mapamundi-0.0.1.jar según el pom.xml) se copia como app.jar 
# LA idea es simplificar el comando de arranque
COPY --from=imagen_construccion /app/target/*.jar app.jar

# Puerto típico de Spring Boot (se podría cambiar si la aplicación usa otro)
EXPOSE 8080

# Comando de arranque
# En las actividades anteriores se ejectuaba el comando "java -jar target/aaee_mapamundi-0.0.1.jar" (info en el pom.xml) para iniciar la aplicación
# Aquí se hace lo mismo pero apuntando al jar copiado en esta etapa
ENTRYPOINT ["java", "-jar", "app.jar"]