# SGPC-API-FINAL

## 🚀 Descripción del Proyecto

Este  proyecto **SGPC-API-FINAL**, una API RESTful desarrollada con **Java 21** y **Spring Boot** para la gestión de un sistema administrativo de una constructora. Se diseño para proporcionar servicios backend que pueden ser consumidos por aplicaciones cliente como **React** y otros frameworks.

Siguiendo las **mejores prácticas de desarrollo**, me aseguré de que fuera **altamente configurable** para facilitar su despliegue en distintos entornos, incluyendo producción.

---

## 🏗 Estructura del Proyecto

Mantengo la estructura del proyecto siguiendo las convenciones estándar de **Spring Boot** para facilitar la mantenibilidad y escalabilidad:

```bash
sgpc-api-final/
│
├── .idea/                 # Configuración del entorno de desarrollo
├── .mvn/                  # Configuración de Maven
├── .vscode/               # Configuración de VS Code
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

Para ejecutar este proyecto, necesitas tener instalado lo siguiente:

- **JDK 21** o superior.
- **Maven** o **Gradle**.
- **Base de datos MySQL** (o cualquier otra compatible, aunque aquí lo configuré con MySQL).

---

## 📌 Configuración de la Base de Datos

Uso **variables de entorno** para configurar la base de datos y la seguridad. Esto permite una mayor flexibilidad y evita exponer información sensible en el código.

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

## 🚀 Cómo Compilar y Ejecutar la Aplicación

Para compilar y ejecutar con **Maven**, simplemente usa:

```bash
mvn spring-boot:run
```


Una vez que el servidor esté corriendo, la API estará disponible en:

```bash
http://localhost:8080
```

---

## 🧑‍💻 Documentación con Swagger

Para facilitar la interacción con la API, integré **Swagger**. Puedes acceder a la documentación interactiva en:

```bash
http://localhost:8080/swagger-ui/index.html
```

Desde ahí, puedes probar todos los endpoints sin necesidad de herramientas externas.

---

## 🔐 Seguridad

Esta API usa **JWT (JSON Web Token)** para la autenticación. Los usuarios deben incluir su token JWT en cada solicitud después de autenticarse.

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

Para mejorar la experiencia de usuario y depuración, la API maneja diversas **excepciones personalizadas**:

- **`ResourceNotFoundException`**: Cuando un recurso no es encontrado.
- **`BadRequestException`**: Cuando los datos de entrada son inválidos.
- **`EntityInUseException`**: Cuando se intenta eliminar un recurso en uso.

---



