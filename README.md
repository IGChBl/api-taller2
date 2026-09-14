# Taller #2 - API de Empleados

Proyecto: api-taller2
Asignatura: Servicios Web (UAM)
Entidad seleccionada: Empleado
Ruta base: /api/empleados

## Descripción

API REST hecha en Spring Boot para administrar empleados. Tiene los cinco endpoints
pedidos, un DTO con validaciones y un manejador global de errores. Los datos se guardan
en una lista en memoria, así que no se necesita base de datos y se reinician cada vez que
se detiene la aplicación.

## Requisitos

- JDK 17 o superior
- Spring Boot 4.1.1 con las dependencias Spring Web y Validation
- Maven (el proyecto incluye el wrapper mvnw)

## Cómo ejecutarlo

En IntelliJ IDEA: abrir la carpeta como proyecto Maven y ejecutar la clase
ApiTaller2Application. La API queda en http://localhost:8080

Desde la terminal:

```
mvnw.cmd spring-boot:run
```

Al iniciar se cargan 3 empleados de ejemplo (ids 1, 2 y 3).

## Entidad Empleado

| Atributo | Tipo |
|---|---|
| id | Long |
| nombreCompleto | texto |
| correo | texto (email) |
| salario | decimal |
| aniosExperiencia | entero |
| fechaContratacion | fecha |
| activo | booleano |
| departamento | enum (DESARROLLO, INFRAESTRUCTURA, SOPORTE, ADMINISTRACION, VENTAS) |

Ejemplo de JSON:

```json
{
  "id": 1,
  "nombreCompleto": "Ivan Chavarria",
  "correo": "ivan.chavarria@uam.edu.ni",
  "salario": 25000.00,
  "aniosExperiencia": 3,
  "fechaContratacion": "2023-02-15",
  "activo": true,
  "departamento": "DESARROLLO"
}
```

## Endpoints

| Método | Ruta | Función | Respuestas |
|---|---|---|---|
| GET | /api/empleados | Listar todos | 200 |
| GET | /api/empleados/{id} | Buscar por id | 200 / 404 |
| POST | /api/empleados | Registrar | 201 / 400 |
| PUT | /api/empleados/{id} | Actualizar | 200 / 400 / 404 |
| DELETE | /api/empleados/{id} | Eliminar | 200 / 404 |
| GET | /api/empleados/departamento/{departamento} | Filtrar por departamento | 200 / 400 |

## Validaciones

Están en EmpleadoDTO y se activan con @Valid en POST y PUT:

- nombreCompleto: @NotBlank y @Size(min = 3, max = 80)
- correo: @NotBlank y @Email
- salario: @NotNull, @DecimalMin mayor que cero y @Digits(8, 2)
- aniosExperiencia: @NotNull, @Min(0) y @Max(50)
- fechaContratacion: @NotNull y @PastOrPresent
- activo: @NotNull
- departamento: @NotNull

## Errores

Los errores los arma GlobalExceptionHandler con @RestControllerAdvice.

Datos inválidos (400):

```json
{
  "estado": 400,
  "mensaje": "Los datos enviados no son validos",
  "errores": {
    "nombreCompleto": "El nombre completo es obligatorio",
    "salario": "El salario debe ser mayor que cero"
  }
}
```

Id que no existe (404):

```json
{
  "estado": 404,
  "mensaje": "No se encontro el empleado con id 999"
}
```

## Estructura

```
src/main/java/ni/edu/uam/api_taller2
  ApiTaller2Application.java
  controllers/EmpleadoController.java
  dto/EmpleadoDTO.java, RespuestaMensaje.java
  models/Empleado.java, Departamento.java
  services/EmpleadoService.java
  exceptions/GlobalExceptionHandler.java, RecursoNoEncontradoException.java
```

## Autor

Iván Chavarría
