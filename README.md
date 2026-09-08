# Taller #2 — API REST de Empleados con validación de datos (Spring Boot)

**Asignatura:** Servicios Web — UAM
**Proyecto:** `api-taller2`
**Entidad seleccionada:** `Empleado`
**Ruta base:** `/api/empleados`

---

## 1. Descripción

API REST construida con Spring Boot que administra la entidad **Empleado** del proyecto de
asignatura. Implementa los cinco endpoints obligatorios (GET, GET por id, POST, PUT y DELETE),
un DTO con reglas de validación, respuestas JSON con códigos HTTP reales y un manejador global
de errores. El almacenamiento es **en memoria** (una lista dentro del servicio), por lo que no
requiere base de datos y los datos se reinician al detener la aplicación.

---

## 2. Requisitos y ejecución

| Requisito | Versión |
|---|---|
| JDK | 17 o superior |
| Maven | Incluido en el proyecto (`mvnw` / `mvnw.cmd`) |
| Spring Boot | 4.1.1 |
| Dependencias | Spring Web (MVC) y Validation |

### Ejecutar desde IntelliJ IDEA
1. Abrir la carpeta `api-taller2` como proyecto Maven.
2. Esperar a que IntelliJ descargue las dependencias.
3. Ejecutar la clase `ApiTaller2Application` (botón ▶).
4. La API queda publicada en `http://localhost:8080`.

### Ejecutar desde la terminal
```bash
./mvnw spring-boot:run        # Linux / macOS
mvnw.cmd spring-boot:run      # Windows
```

Al iniciar se cargan **3 empleados de ejemplo** (ids 1, 2 y 3) para poder probar de inmediato.

---

## 3. Entidad seleccionada: Empleado

El DTO tiene un identificador y **siete atributos adicionales** de distintos tipos de dato,
cumpliendo el mínimo de cinco exigido por el taller.

| Atributo | Tipo de dato | Descripción |
|---|---|---|
| `id` | Long | Identificador único, generado por la API |
| `nombreCompleto` | Texto | Nombre y apellidos del empleado |
| `correo` | Texto (email) | Correo institucional |
| `salario` | Número decimal | Salario mensual |
| `aniosExperiencia` | Número entero | Años de experiencia laboral |
| `fechaContratacion` | Fecha | Fecha de ingreso a la empresa |
| `activo` | Booleano | Indica si el empleado sigue laborando |
| `departamento` | Enumeración | DESARROLLO, INFRAESTRUCTURA, SOPORTE, ADMINISTRACION, VENTAS |

### Ejemplo de JSON

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

---

## 4. Tabla de endpoints

| Método | Ruta | Función | Respuestas |
|---|---|---|---|
| `GET` | `/api/empleados` | Lista todos los registros | `200 OK` + arreglo JSON |
| `GET` | `/api/empleados/{id}` | Busca un registro por identificador | `200 OK` / `404 Not Found` |
| `POST` | `/api/empleados` | Registra un nuevo empleado | `201 Created` / `400 Bad Request` |
| `PUT` | `/api/empleados/{id}` | Actualiza un registro existente | `200 OK` / `400` / `404` |
| `DELETE` | `/api/empleados/{id}` | Elimina un registro | `200 OK` / `404 Not Found` |
| `GET` | `/api/empleados/departamento/{departamento}` | *(adicional)* Filtra por enumeración | `200 OK` / `400 Bad Request` |

El `POST` exitoso devuelve además la cabecera `Location` con la URL del recurso creado.

---

## 5. Reglas de validación (`EmpleadoDTO`)

Se implementan **14 reglas** sobre 7 atributos, muy por encima del mínimo de cinco.

| Atributo | Anotaciones | Mensaje cuando falla |
|---|---|---|
| `nombreCompleto` | `@NotBlank`, `@Size(3–80)` | Obligatorio / debe tener entre 3 y 80 caracteres |
| `correo` | `@NotBlank`, `@Email` | Obligatorio / formato válido |
| `salario` | `@NotNull`, `@DecimalMin(0, exclusivo)`, `@Digits(8,2)` | Obligatorio / mayor que cero / máximo 8 enteros y 2 decimales |
| `aniosExperiencia` | `@NotNull`, `@Min(0)`, `@Max(50)` | Obligatorio / no negativo / no mayor a 50 |
| `fechaContratacion` | `@NotNull`, `@PastOrPresent` | Obligatoria / no puede ser futura |
| `activo` | `@NotNull` | Debe indicarse true o false |
| `departamento` | `@NotNull` | Obligatorio y limitado a los valores de la enumeración |

La validación se activa con `@Valid` en los endpoints `POST` y `PUT`.

---

## 6. Formato de las respuestas de error

**400 Bad Request** — datos inválidos (generado por `MethodArgumentNotValidException`):

```json
{
  "estado": 400,
  "mensaje": "Los datos enviados no son validos",
  "errores": {
    "nombreCompleto": "El nombre completo es obligatorio",
    "correo": "El correo debe tener un formato valido",
    "salario": "El salario debe ser mayor que cero"
  }
}
```

**404 Not Found** — identificador inexistente:

```json
{
  "estado": 404,
  "mensaje": "No se encontro el empleado con id 999"
}
```

Todos los errores se centralizan en `GlobalExceptionHandler`, anotado con `@RestControllerAdvice`.

---

## 7. Estructura del proyecto

```
api-taller2
├── pom.xml
├── README.md
├── evidencias/                      Capturas de las 8 pruebas
├── pruebas/
│   ├── empleados.http               Pruebas para el cliente HTTP de IntelliJ
│   └── Taller2-API-Empleados.postman_collection.json
└── src/main/java/ni/edu/uam/api_taller2
    ├── ApiTaller2Application.java   Clase principal
    ├── controllers/                 EmpleadoController  (rutas y códigos HTTP)
    ├── dto/                         EmpleadoDTO, RespuestaMensaje
    ├── models/                      Empleado, Departamento (enum)
    ├── services/                    EmpleadoService (lógica y datos en memoria)
    └── exceptions/                  GlobalExceptionHandler, RecursoNoEncontradoException
```

---

## 8. Recorrido de una solicitud (para la revisión oral)

Ejemplo con `POST /api/empleados`:

1. **Cliente** envía la petición HTTP con un JSON en el cuerpo.
2. **Spring (DispatcherServlet)** busca el controlador cuya ruta coincida: `EmpleadoController`.
3. **Jackson** convierte el JSON en un objeto `EmpleadoDTO` (deserialización).
4. **`@Valid`** ejecuta Bean Validation sobre el DTO.
   - Si algo falla, se lanza `MethodArgumentNotValidException`, la atrapa el
     `GlobalExceptionHandler` y **la petición nunca entra al método**: responde `400`.
5. **Controlador** delega en `EmpleadoService`.
6. **Servicio** convierte el DTO en la entidad `Empleado`, le asigna el id y lo guarda en la lista.
7. **Servicio** devuelve un `EmpleadoDTO` con el id ya generado.
8. **Controlador** lo envuelve en un `ResponseEntity` con estado `201 Created` y cabecera `Location`.
9. **Jackson** serializa el DTO a JSON y el cliente recibe la respuesta.

En el caso de un id inexistente, el paso 6 lanza `RecursoNoEncontradoException`, que el
`@RestControllerAdvice` traduce a un `404 Not Found` con mensaje JSON.

---

## 9. Pruebas realizadas

Ocho pruebas obligatorias documentadas en `pruebas/empleados.http` y en la colección de Postman:

| # | Prueba | Método y ruta | Resultado esperado |
|---|---|---|---|
| 1 | Listar empleados | `GET /api/empleados` | `200 OK` |
| 2 | Buscar por id | `GET /api/empleados/1` | `200 OK` |
| 3 | Registrar empleado | `POST /api/empleados` | `201 Created` |
| 4 | Actualizar empleado | `PUT /api/empleados/2` | `200 OK` |
| 5 | Eliminar empleado | `DELETE /api/empleados/3` | `200 OK` |
| 6 | Datos inválidos | `POST /api/empleados` | `400 Bad Request` |
| 7 | Datos inválidos | `PUT /api/empleados/1` | `400 Bad Request` |
| 8 | Identificador inexistente | `GET /api/empleados/999` | `404 Not Found` |

Las capturas correspondientes están en la carpeta `evidencias/`.

---

## 10. Autor

Iván Chavarría — Servicios Web, UAM — Taller #2
