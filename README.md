# SafeZone Backend

## Descripción del Proyecto

SafeZone es una plataforma móvil y web diseñada para mejorar la seguridad en comunidades locales. Integra una red de alertas comunitarias, mapas colaborativos con zonas de riesgo, un botón de pánico virtual y talleres de prevención. El backend, desarrollado con Java y Spring Boot, gestiona la API REST para el manejo de reportes, usuarios y la integración con sistemas de seguridad pública.

## Integrantes 
- Nicolas Achuri
- Tomás Suarez
- Juliana Briceño
- Erick Montero
- Ricardo Villamizar
- Cristian Alvarez


## Versión del Lenguaje

- **Java:** 21
- **Spring Boot:** 3.4.2

## Dependencias

- **Spring Boot Starter Data JPA:** Para la gestión de datos con JPA.
- **Spring Boot Starter Web:** Para la creación de servicios RESTful.
- **H2 Database:** Base de datos en memoria para pruebas.
- **Lombok:** Para simplificar el código Java mediante anotaciones.
- **Spring Boot Starter Test:** Para pruebas unitarias y de integración.

## Instrucciones de Instalación y Ejecución

### Prerrequisitos

- Java 21
- Maven 3.6 o superior

### Clonación del Repositorio

```bash
git clone https://github.com/N1CKZ3B/SafeZone-Back.git
cd safezone-backend
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
La estructura del proyecto sigue el estándar de Maven, organizando el código fuente en el directorio src/main/java bajo el paquete eci.ieti.safezone, que contiene los módulos controller y exception, con las clases StatusController.java y GlobalExceptionHandler.java, respectivamente. Los recursos del proyecto se ubican en src/main/resources, mientras que las pruebas unitarias están en src/test/java/eci/ieti/demo. Además, el archivo pom.xml gestiona las dependencias y la configuración del proyecto.
```bash
src
├── main
│   ├── java
│   │   └── eci
│   │       └── ieti
│   │           └── safezone
│   │               ├── controller
│   │               │   └── StatusController.java
│   │               └── exception
│   │                   └── GlobalExceptionHandler.java
│   └── resources
└── test
    └── java
        └── eci
            └── ieti
                └── demo
pom.xml
```
### Planeación del Proyecto
La planeación detallada del desarrollo del backend, incluyendo el backlog y las historias de usuario, está disponible en el siguiente enlace:  
[Planeación del Backend](https://ejemplo.com/planeacion-backend](https://trello.com/invite/b/67a00fc8bcfff22002613396/ATTI91a97041797d4657dfbe6f8f4eafc4c6AB986C2F/safezone-3))
