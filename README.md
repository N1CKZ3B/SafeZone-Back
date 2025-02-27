# Solver Backend

## Descripción del Proyecto

Solver consiste en el desarrollo de una aplicación digital que conecta a personas que ofrecen servicios básicos con quienes los necesitan de manera rápida, sencilla y segura. La plataforma facilita la contratación de trabajadores independientes para tareas cotidianas, promoviendo la economía local y brindando oportunidades de ingresos sin necesidad de empleos formales.

## Integrantes 
- Nicolas Achuri
- Tomás Suarez
- Juliana Briceño
- Erick Montero
- Ricardo Villamizar
- Cristian Alvarez


### Versiones
- **Java**: `21`
- **Maven**: `4.0.0`

### Dependencias principales

#### Spring Boot Starter Dependencies
- `spring-boot-starter-data-jpa`: Para integración con JPA (Java Persistence API).
- `spring-boot-starter-web`: Para construir aplicaciones web con Spring MVC.
- `spring-boot-starter-test`: Para pruebas en aplicaciones Spring Boot.
- `spring-boot-starter-data-mongodb`: Para integración con MongoDB.

#### Bases de datos y drivers
- `h2`: Base de datos en memoria para desarrollo y pruebas.
- `mongodb-driver-sync`: Driver de MongoDB para operaciones síncronas.

#### Lombok
- `lombok`: Para reducir el código boilerplate (getters, setters, constructores, etc.).

#### AWS SDK
- `aws-java-sdk-dynamodb`: SDK de AWS para DynamoDB (versión 1.x).
- `aws-java-sdk-s3`: SDK de AWS para S3 (versión 1.x).
- `dynamodb` y `dynamodb-enhanced`: SDK de AWS para DynamoDB (versión 2.x).

#### Documentación API
- `springdoc-openapi-starter-webmvc-ui`: Para generar documentación de API con OpenAPI (Swagger).

### Plugins de Maven
1. **maven-compiler-plugin**:
   - Configurado para usar Lombok como procesador de anotaciones.

2. **spring-boot-maven-plugin**:
   - Configurado para excluir Lombok en el empaquetado final.



## Instrucciones de Instalación y Ejecución

### Prerrequisitos

- Java 21
- Maven 3.6 o superior

### Clonación del Repositorio

```bash
https://github.com/N1CKZ3B/Solver-Back.git
cd Solver-Back
```
### Contrucción del Proyecto
```bash
mvn clean install
```

### Ejecución de la Aplicación
```bash
mvn spring-boot:run
```

### Acceso a la Aplicación
Una vez iniciada, la API estará disponible en:
```bash
http://localhost:8080

```

### Estructura
Esta estructura corresponde a un proyecto Spring Boot organizado de manera modular. En src/main/java, bajo el paquete eci.ieti.safezone, se encuentra el código fuente, con la clase principal DemoApplication.java como punto de entrada. La carpeta config contiene la configuración de DynamoDB, mientras que controller aloja los controladores que manejan solicitudes HTTP, como ApplicantController.java y UserController.java. Las excepciones personalizadas, como ApplicantException.java, y su manejo global están en exception. Los modelos de datos, como Applicant.java y User.java, se ubican en model, y la lógica de negocio en service, con clases como ApplicantService.java. Las pruebas, en src/test, siguen una estructura similar, con clases como ApplicantControllerTest.java para probar los controladores. Esta organización facilita el desarrollo, pruebas y mantenimiento de la aplicación.

```

src
├───main
│   ├───java
│   │   └───eci
│   │       └───ieti
│   │           └───solver
│   │               │   DemoApplication.java
│   │               │
│   │               ├───config
│   │               │       DynamoDBConfig.java
│   │               │       DynamoDBConnection.java
│   │               │
│   │               ├───controller
│   │               │       ApplicantController.java
│   │               │       DynamoDBController.java
│   │               │       OfferController.java
│   │               │       StatusController.java
│   │               │       UserController.java
│   │               │
│   │               ├───exception
│   │               │       ApplicantException.java
│   │               │       GlobalExceptionHandler.java
│   │               │       OfferException.java
│   │               │
│   │               ├───model
│   │               │       Applicant.java
│   │               │       Offer.java
│   │               │       User.java
│   │               │
│   │               └───service
│   │                       ApplicantService.java
│   │                       DynamoDBService.java
│   │                       OfferService.java
│   │                       UserService.java
│   │
│   └───resources
│           application.properties
│
└───test
     ───java
        └───eci
            └───ieti
                └───safezone
                     │   DemoApplicationTests.java
                     │
                     └───controller
                                ApplicantControllerTest.java
                                OfferControllerTest.java
                                UserControllerTest.java
```
### Planeación del Proyecto
La planeación detallada del desarrollo del backend, incluyendo el backlog y las historias de usuario, está disponible en el siguiente enlace:  
[Planeación del Backend](https://dev.azure.com/SolverECI/Solver/_workitems/recentlyupdated/)
