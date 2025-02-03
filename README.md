# SafeZone Backend

## Descripción del Proyecto

SafeZone es una plataforma móvil y web diseñada para mejorar la seguridad en comunidades locales. Integra una red de alertas comunitarias, mapas colaborativos con zonas de riesgo, un botón de pánico virtual y talleres de prevención. El backend, desarrollado con Java y Spring Boot, gestiona la API REST para el manejo de reportes, usuarios y la integración con sistemas de seguridad pública.

## Versión del Lenguaje

- **Java:** 17
- **Spring Boot:** 3.4.2

## Dependencias

- **Spring Boot Starter Data JPA:** Para la gestión de datos con JPA.
- **Spring Boot Starter Web:** Para la creación de servicios RESTful.
- **H2 Database:** Base de datos en memoria para pruebas.
- **Lombok:** Para simplificar el código Java mediante anotaciones.
- **Spring Boot Starter Test:** Para pruebas unitarias y de integración.

## Instrucciones de Instalación y Ejecución

### Prerrequisitos

- Java 17 o superior
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
