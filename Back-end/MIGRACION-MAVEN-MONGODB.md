# Migración completa: Maven + MongoDB

Documentación de la migración del Back-end de **Gradle a Maven** y de **MySQL/JPA a MongoDB**.

---

## 1. Build: Gradle → Maven

### Cambios realizados
- **`pom.xml`** creado en `Back-end/` con:
  - Spring Boot 2.7.18, Kotlin 1.9.24, Java 17
  - `spring-boot-starter-data-mongodb` (reemplaza JPA y MySQL)
  - Mismas dependencias de negocio: jjwt, Twilio, ModelMapper, etc.
  - `kotlin-maven-plugin` con `extensions=true` para compilar desde `src/main/java`
- **Eliminados:** `build.gradle`, `settings.gradle`, `gradle.properties`
- **Dockerfile** actualizado: usa Maven (`COPY pom.xml` + `COPY src`, `RUN mvn package -DskipTests -B`), JAR en `target/`
- **`.dockerignore`** actualizado (sin referencias a Gradle)

### Comandos Maven
```bash
cd Back-end
mvn compile              # Solo compilar
mvn package -DskipTests  # Generar JAR sin tests
mvn test                 # Ejecutar tests (requiere MongoDB)
mvn spring-boot:run      # Ejecutar la aplicación
```

---

## 2. Base de datos: MySQL/JPA → MongoDB

### Entidades → Documentos
- **Hospital**, **Usuario**, **Turno**: `@Document(collection = "...")`, `@Id var id: String?`
- **Turno**: `@DBRef(lazy = true)` para `hospital` y `paciente`
- **Especialidad**: sigue siendo `enum`, se persiste como string

### Repositorios
- **HospitalDAO**, **UsuarioDAO**, **TurnoDAO**: ahora extienden `MongoRepository<T, String>`
- **HospitalDAO**: `findByEspecialidadesContaining(Especialidad)` (en lugar de query nativa)
- **UsuarioDAO**: sin método `borrarUsuarioDeTodosSusTurnos` (la lógica está en el servicio)
- **TurnoDAO**: `findByHospital_Id`, `findByPaciente_Id`, `findByHospital_IdAndEspecialidadAndPacienteIsNull`; desasignar paciente se hace en servicio (find + desasignar + save)

### Configuración
- **`application.properties`**: solo `spring.data.mongodb.uri=${SPRING_DATA_MONGODB_URI:mongodb://localhost:27017/turnero}`

---

## 3. IDs: Long/Int → String

- Servicios, controladores y DTOs usan **`String`** para todos los IDs (compatible con MongoDB ObjectId).
- Path variables: `@PathVariable hospitalId: String`, etc.
- DTOs: `var id: String?` en HospitalDTO, UsuarioDTO, TurnoDTO, MiniHospitalDTO, PacienteDTO, etc.
- **Impacto en el frontend:** los IDs en la API son ahora strings (ej. `GET /hospital/507f1f77bcf86cd799439011`). Si el frontend usaba IDs numéricos, debe actualizarse.

---

## 4. Servicios

- Eliminado `@Transactional` (no aplica a MongoDB como en JPA).
- **HospitalServiceImp**: `recuperarPorEspecialidad(busqueda)` convierte el string a `Especialidad` y usa `findByEspecialidadesContaining`.
- **TurnoServiceImp**: `borrarUsuarioDeTodosSusTurnos(usuarioId)` hace find por `paciente._id`, desasignar y save.
- **UsuarioServiceImp**: mismas validaciones; uso de `findByEmail` / `findByToken` / `findByDni` sin cambios de contrato.

---

## 5. Tests

- **`Back-end/src/test/resources/application.properties`**:  
  `spring.data.mongodb.uri=mongodb://localhost:27017/turnero_test` (base distinta para tests).
- Tests actualizados para usar **String** en IDs: `recuperar(user.id!!)`, `eliminar(user.id!!)`, `recuperar(turno.id!!)`, etc.
- **Requisito:** MongoDB debe estar corriendo en `localhost:27017` para ejecutar los tests.

---

## Uso

### Desarrollo local
1. Tener **MongoDB** corriendo en `localhost:27017`.
2. Ejecutar:
   ```bash
   cd Back-end
   mvn spring-boot:run
   ```
3. Para tests: la base `turnero_test` se usa automáticamente si está configurada en `src/test/resources/application.properties`.

### Docker
```bash
cd Back-end
docker build -t turnero-backend .
docker run -p 8080:8080 \
  -e SPRING_DATA_MONGODB_URI=mongodb://host.docker.internal:27017/turnero \
  turnero-backend
```

Con **docker-compose** (ejemplo con servicio MongoDB):
- Servicio `mongo` en el mismo compose.
- Variable de entorno para el backend:  
  `SPRING_DATA_MONGODB_URI=mongodb://mongo:27017/turnero`

---

## Resumen de archivos clave

| Antes (Gradle + MySQL)     | Después (Maven + MongoDB)        |
|----------------------------|-----------------------------------|
| `build.gradle`             | `pom.xml`                         |
| `spring-boot-starter-data-jpa` | `spring-boot-starter-data-mongodb` |
| `mysql-connector-j`       | (eliminado)                       |
| `@Entity`, `@Column`, etc.| `@Document`, `@Id`                |
| `CrudRepository<T, Long>`  | `MongoRepository<T, String>`      |
| IDs `Long`/`Int` en API   | IDs `String` en API              |

---

*Documento generado a partir de la migración realizada en el proyecto Turnero.*
