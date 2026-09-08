package ni.edu.uam.api_taller2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.LinkedHashMap;
import java.util.Map;

// Manejador GLOBAL de errores de la API.
// @RestControllerAdvice intercepta las excepciones lanzadas por cualquier controlador
// y las transforma en respuestas JSON uniformes, con su codigo HTTP correspondiente.
// Gracias a esta clase el controlador se mantiene limpio: solo se ocupa del camino feliz.
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ------------------------------------------------------------------
    // 400 BAD REQUEST - Errores de validacion de @Valid sobre el DTO.
    // Se ejecuta cuando un campo incumple @NotBlank, @Email, @Min, @PastOrPresent, etc.
    // Devuelve un mensaje general y el detalle campo por campo.
    // ------------------------------------------------------------------
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> manejarErroresDeValidacion(
            MethodArgumentNotValidException ex) {

        // Mapa con el detalle de cada campo invalido: nombreDelCampo -> mensaje
        Map<String, String> errores = new LinkedHashMap<>();

        // Se recorren todos los campos rechazados por Bean Validation
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.put(error.getField(), error.getDefaultMessage())
        );

        // Se arma el cuerpo de la respuesta con el formato pedido en el taller
        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("estado", HttpStatus.BAD_REQUEST.value());
        respuesta.put("mensaje", "Los datos enviados no son validos");
        respuesta.put("errores", errores);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }

    // ------------------------------------------------------------------
    // 404 NOT FOUND - El identificador solicitado no existe en el almacenamiento.
    // Se dispara con la excepcion propia RecursoNoEncontradoException.
    // ------------------------------------------------------------------
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> manejarRecursoNoEncontrado(
            RecursoNoEncontradoException ex) {

        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("estado", HttpStatus.NOT_FOUND.value());
        respuesta.put("mensaje", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
    }

    // ------------------------------------------------------------------
    // 400 BAD REQUEST - El JSON enviado no se puede leer.
    // Ocurre con JSON mal formado o con un valor invalido para la enumeracion
    // (por ejemplo "departamento": "COCINA", que no existe en Departamento).
    // ------------------------------------------------------------------
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> manejarJsonIlegible(
            HttpMessageNotReadableException ex) {

        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("estado", HttpStatus.BAD_REQUEST.value());
        respuesta.put("mensaje", "El cuerpo de la solicitud no es un JSON valido "
                + "o contiene un valor no permitido (revise fechas, numeros y el departamento)");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }

    // ------------------------------------------------------------------
    // 400 BAD REQUEST - El valor de la ruta no corresponde al tipo esperado.
    // Ejemplo: GET /api/empleados/abc cuando el id debe ser numerico.
    // ------------------------------------------------------------------
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> manejarTipoDeParametroInvalido(
            MethodArgumentTypeMismatchException ex) {

        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("estado", HttpStatus.BAD_REQUEST.value());
        respuesta.put("mensaje", "El valor '" + ex.getValue()
                + "' no es valido para el parametro '" + ex.getName() + "'");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }

    // ------------------------------------------------------------------
    // 500 INTERNAL SERVER ERROR - Red de seguridad para cualquier error no previsto.
    // Evita que el cliente reciba una pagina de error HTML en lugar de JSON.
    // ------------------------------------------------------------------
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> manejarErrorInesperado(Exception ex) {

        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("estado", HttpStatus.INTERNAL_SERVER_ERROR.value());
        respuesta.put("mensaje", "Ocurrio un error inesperado en el servidor");
        respuesta.put("detalle", ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
    }
}
