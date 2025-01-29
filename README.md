# SGPC-API-FINAL

## 🚀 Descripción del Proyecto

SGPC-API-FINAL es una API RESTful desarrollada con **Java 21** y **Spring Boot** para la gestión de un sistema administrativo de una constructora. Su backend proporciona servicios que pueden ser consumidos por aplicaciones cliente como **React** y otros frameworks.

El proyecto está diseñado siguiendo **las mejores prácticas de desarrollo** y es **altamente configurable** para su despliegue en entornos de producción.

---

## 🏗 Estructura del Proyecto

El proyecto sigue las convenciones estándar para aplicaciones **Spring Boot**:

```bash
sgpc-api-final/
│
├── .idea/
├── .mvn/
├── .vscode/
│
├── src/main/java/com/grupoingenios/sgpc/sgpc_api_final/
│   ├── config/          # Configuración de la aplicación (seguridad, beans, etc.)
│   ├── constants/       # Definición de constantes reutilizables
│   ├── controller/      # Controladores REST
│   ├── entity/          # Entidades JPA
│   ├── exception/       # Excepciones personalizadas
│   ├── mapper/          # MapStruct: Mapeo entre entidades y DTOs
│   ├── repository/      # Repositorios JPA
│   ├── service/         # Servicios con la lógica de negocio
│   ├── validation/      # Validaciones personalizadas
│
└── resources/
    ├── application.properties
    ├── application-dev.properties
```

---

## ⚙ Instalación

### 🛠 Pre-requisitos

Para ejecutar este proyecto necesitas:

- **JDK 21** o superior.
- **Maven** o **Gradle**.
- **Base de datos MySQL** (Puedes utilizar otra, pero en este ejemplo se usa MySQL).

---

## 📌 Configuración de la Base de Datos

La aplicación utiliza **variables de entorno** para configurar su base de datos y seguridad.

### `application.properties`

```properties
# Nombre de la aplicación
spring.application.name=sgpc-api-final

# Configuración del servidor
server.port=${SERVER_PORT}

# Perfil activo
spring.profiles.active=${SPRING_PROFILES_ACTIVE:dev}

# Configuración JWT
jwt.secret=${JWT_SECRET}
jwt.expiration=3600000

# Propiedades JPA
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.open-in-view=false
```

### `application-dev.properties`

```properties
# Configuración MySQL para el perfil de desarrollo (Ejemplo con Docker)
spring.datasource.url=${SPRING_DATASOURCE_URL}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Propiedades JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Configuración de logs para depuración
logging.level.org.hibernate.SQL=debug
```

### `.env.example`

```properties
# Puerto del servidor
SERVER_PORT=

# Perfil activo de Spring Boot
SPRING_PROFILES_ACTIVE=

# Configuración de la base de datos
SPRING_DATASOURCE_URL=
SPRING_DATASOURCE_USERNAME=
SPRING_DATASOURCE_PASSWORD=

# Clave secreta JWT
JWT_SECRET=
```

---

## 🚀 Compilar y Ejecutar la Aplicación

Para compilar y ejecutar con **Maven**:

```bash
mvn spring-boot:run
```

Para compilar y ejecutar con **Gradle**:

```bash
gradle bootRun
```

Una vez iniciada, la API estará disponible en:

```bash
http://localhost:8080
```

---

## 🧑‍💻 Documentación con Swagger

La API cuenta con **Swagger** para una interacción más fácil con los endpoints.

Puedes acceder a la documentación interactiva en:

```bash
http://localhost:8080/swagger-ui/index.html
```

Desde Swagger puedes probar todos los endpoints sin necesidad de herramientas externas.

---

## 🔐 Seguridad

La API utiliza **JWT (JSON Web Token)** para la autenticación. Los usuarios deben incluir su token JWT en cada solicitud después de autenticarse.

### **Ejemplo de autenticación**

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -d '{"username":"usuario", "password":"contraseña"}' \
  -H "Content-Type: application/json"
```

### **Autorización con JWT**

Cada solicitud protegida debe incluir el header:

```http
Authorization: Bearer <TOKEN>
```

---

## 🚨 Manejo de Excepciones

La API maneja diversas **excepciones personalizadas** para una mejor experiencia de usuario y depuración:

- **`ResourceNotFoundException`**: Cuando un recurso no es encontrado.
- **`BadRequestException`**: Cuando los datos de entrada son inválidos.
- **`EntityInUseException`**: Cuando se intenta eliminar un recurso en uso.

---

## 📌 Conclusión

Este proyecto está diseñado para ser **altamente configurable** mediante **variables de entorno**, lo que facilita su despliegue en entornos como **Docker, Heroku o Kubernetes**.

Adicionalmente, la integración con **Swagger** facilita el uso y prueba de los servicios expuestos.

**✅ Listo para Producción y Desarrollo!**

