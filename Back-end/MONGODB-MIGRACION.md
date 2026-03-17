# Viabilidad de migración a MongoDB

**Sí, es viable** cambiar de MySQL a MongoDB en este proyecto. Resumen de impacto y pasos.

## Ventajas
- **Spring Data MongoDB** se integra bien con el stack actual (Kotlin, Spring Boot).
- Mismo estilo de repositorios (`MongoRepository` en lugar de `CrudRepository`/JPA).
- Escalado horizontal y esquema flexible si más adelante lo necesitás.
- Con Docker podés usar la imagen oficial `mongo` junto al backend.

## Cambios necesarios

| Área | MySQL (actual) | MongoDB |
|------|----------------|--------|
| Dependencia | `spring-boot-starter-data-jpa` + `mysql-connector-j` | `spring-boot-starter-data-mongodb` |
| Entidades | `@Entity`, `@Id`, `@Column`, `@ManyToOne`, etc. | `@Document`, `@Id`, referencias o embebidos |
| Repositorios | `CrudRepository<T, Long>` | `MongoRepository<T, String>` (id suele ser String/ObjectId) |
| Consultas | JPQL, `@Query` nativo, métodos por nombre | Métodos por nombre, `@Query` con JSON/Query DSL |
| Transacciones | `@Transactional`, JPA | Soporte transaccional en réplicas (MongoDB 4+) |
| Plugins Kotlin | `plugin.jpa`, `noarg`, `allopen` para JPA | No necesitás JPA; `allopen` si lo usás para otros beans |

## Pasos sugeridos
1. Añadir `spring-boot-starter-data-mongodb` y quitar JPA + MySQL.
2. Sustituir anotaciones JPA por anotaciones de Spring Data MongoDB en `Hospital`, `Usuario`, `Turno` (y `Especialidad` si aplica).
3. Cambiar `HospitalDAO`, `UsuarioDAO`, `TurnoDAO` a `MongoRepository`; adaptar tipos de ID si pasás a `String`/`ObjectId`.
4. Reemplazar consultas `@Query` nativas por métodos por nombre o queries de MongoDB.
5. Configurar `spring.data.mongodb.uri` en `application.properties` (y variables de entorno para Docker).
6. Ajustar tests (por ejemplo con MongoDB embebido o Testcontainers).

## Configuración ejemplo (application.properties)
```properties
# Reemplazar datasource por:
spring.data.mongodb.uri=${SPRING_DATA_MONGODB_URI:mongodb://localhost:27017/turnero}
```

## Docker
En `docker-compose` podés exponer un servicio `mongo` y definir:
`SPRING_DATA_MONGODB_URI=mongodb://mongo:27017/turnero`.

---

Si querés, en un siguiente paso se puede hacer la migración concreta (dependencias, entidades y repos) en el código actual.
