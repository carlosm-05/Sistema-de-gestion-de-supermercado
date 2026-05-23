# Supermercado

Sistema web de gestión de supermercado (Spring Boot + Thymeleaf).

## Requisitos

- **Java 21** o superior ([descargar](https://adoptium.net/))
- **Git** ([descargar](https://git-scm.com/))

## Paso 1 — Clonar el repositorio

```bash
git clone <URL_DEL_REPO>
cd supermercado
```

> Reemplaza `<URL_DEL_REPO>` por la URL que te dé GitHub al crear el repo.

## Paso 2 — Ejecutar

```bash
# En Windows:
.\mvnw.cmd clean spring-boot:run

# En macOS / Linux:
./mvnw clean spring-boot:run
```

La primera vez descargará dependencias (tarda un poco).

## Paso 3 — Abrir en el navegador

```
http://localhost:8080
```

## Usuarios por defecto

| Usuario | Contraseña | Rol    |
|---------|-----------|--------|
| admin   | admin123  | ADMIN  |
| pedro   | pedro123  | EMPLEADO |

También puedes crear una cuenta propia desde la página de login en **"Crear cuenta"**.

## Notas

- La base de datos es H2 en memoria (`jdbc:h2:mem:vetdb`). Los datos se pierden al reiniciar.
- Consola H2 disponible en `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:vetdb`, usuario: `sa`, contraseña: en blanco).
- El proyecto usa Java 21. Si tienes una versión diferente, cambia `<java.version>` en `pom.xml`.
