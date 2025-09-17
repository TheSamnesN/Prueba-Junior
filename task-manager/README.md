Tecnologías
- Java 17
- Spring Boot 3.5
- Spring Data JPA (H2 por defecto)
- JUnit + Mockito (tests)
- Postman (colección incluida)

Resumen funcional
- Registrar usuarios (id autogenerado, name, email único).
- Crear / Modificar / Listar / Eliminar tareas.
- Cada tarea: id, title, description, status (PENDING, IN_PROGRESS, COMPLETED), createdAt, updatedAt, owner (usuario).
- Un usuario solo puede listar, modificar o eliminar sus propias tareas.
- Manejo de errores centralizado (JSON consistente para 400/404/409/500).

Quick start (local)
Desde la raíz del proyecto:
- Compilar y ejecutar:
./gradlew bootRun


- Alternativa: limpiar, compilar y ejecutar tests:
./gradlew clean build
./gradlew test


La API queda en: http://localhost:8080

Endpoints principales
- POST /api/users
- Crear usuario
- Body: { "name":"...", "email":"...", "password":"..." } (password opcional si no usas seguridad)
- Respuestas: 201 (creado) / 400 / 409
- GET /api/users/{id}
- Obtener usuario por id
- POST /api/users/{userId}/tasks
- Crear tarea para userId
- Body: { "title":"...", "description":"..." }
- GET /api/users/{userId}/tasks
- Listar tareas del usuario
- PUT /api/users/{userId}/tasks/{taskId}
- Actualizar tarea (solo si pertenece al userId)
- DELETE /api/users/{userId}/tasks/{taskId}
- Eliminar tarea (solo si pertenece al userId)
Todos los endpoints devuelven JSON; los errores siguen el formato:
{
  "timestamp": "...",
  "status": 404,
  "error": "Not Found",
  "message": "User not found with id: 9999",
  "path": "/api/users/9999"
}



Validaciones implementadas
- name y email obligatorios al crear usuario
- email con formato válido y único (409 si ya existe)
- title obligatorio al crear tarea
- comprobación que el user es dueño para modificar/eliminar tareas

Pruebas y Postman
- Tests: ejecutar ./gradlew test. Reporte en build/reports/tests/test/index.html.
- Postman: colección incluida en /postman/task-manager-collection.json (crear usuario, crear tarea, listar, casos 400/404/409).

Notas para entrega
- Incluye /postman con la colección JSON.
- Incluye README.md (este archivo).
- Añade .gitignore (excluir /build, .idea, *.iml).
- Si añades Spring Security: documenta credenciales de prueba en el README.

Si quieres, genero ahora:
- el archivo /postman/task-manager-collection.json listo para pegar, o
- un ejemplo mínimo de request/response en curl/PowerShell para pegar en el README. ¿Cuál prefieres?
