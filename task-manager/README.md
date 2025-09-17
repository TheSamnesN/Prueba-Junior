Task Manager API

Sistema de gestión de tareas desarrollado como prueba técnica para nivel Junior. Permite registrar usuarios y gestionar tareas asociadas a cada uno. Cada usuario puede crear, listar, modificar y eliminar únicamente sus propias tareas

Tecnologías utilizadas

- Java 17
- Spring Boot 3.5
- Maven/Gradle
- H2 Database (modo en memoria)
- JPA/Hibernate
- RESTful API
- Postman (colección de pruebas)
- (Opcional) Spring Security + BCrypt

Estructura del proyecto
# src/ ├── controller/  
# Controladores REST ├── dto/
# Objetos de transferencia de datos ├── entity/ 
# Entidades JPA ├── exception/ 
# Manejo global de errores ├── repository/ 
# Interfaces JPA ├── service/
# Lógica de negocio └── TaskManagerApplication.java



##  Cómo ejecutar el proyecto

1. Clona el repositorio:
   ```bash
   git clone https://github.com/tu-usuario/task-manager.git
   cd task-manager

./gradlew bootRun

http://localhost:8080
