package ni.edu.uam.api_taller2.controllers;

import jakarta.validation.Valid;
import ni.edu.uam.api_taller2.dto.EmpleadoDTO;
import ni.edu.uam.api_taller2.dto.RespuestaMensaje;
import ni.edu.uam.api_taller2.models.Departamento;
import ni.edu.uam.api_taller2.services.EmpleadoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

// Controlador REST de la entidad Empleado.
// @RestController indica que cada metodo devuelve datos (JSON) y no una vista HTML.
// @RequestMapping fija la ruta base del recurso: /api/empleados
@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    // Referencia a la capa de servicio, donde esta la logica de negocio.
    private final EmpleadoService servicio;

    // Inyeccion de dependencias por constructor:
    // Spring crea el EmpleadoService y lo entrega al controlador automaticamente.
    public EmpleadoController(EmpleadoService servicio) {
        this.servicio = servicio;
    }

    // ------------------------------------------------------------------
    // GET /api/empleados
    // Lista todos los registros. Respuesta: 200 OK con un arreglo JSON.
    // ------------------------------------------------------------------
    @GetMapping
    public ResponseEntity<List<EmpleadoDTO>> listar() {
        return ResponseEntity.ok(servicio.listar());
    }

    // ------------------------------------------------------------------
    // GET /api/empleados/{id}
    // Busca un registro por identificador.
    // Respuesta: 200 OK con el empleado, o 404 Not Found si el id no existe
    // (el 404 lo genera el GlobalExceptionHandler al recibir la excepcion del servicio).
    // ------------------------------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(servicio.buscarPorId(id));
    }

    // ------------------------------------------------------------------
    // GET /api/empleados/departamento/{departamento}
    // Endpoint adicional: filtra los empleados por la enumeracion Departamento.
    // Respuesta: 200 OK con el arreglo filtrado, o 400 si el departamento no existe.
    // ------------------------------------------------------------------
    @GetMapping("/departamento/{departamento}")
    public ResponseEntity<List<EmpleadoDTO>> listarPorDepartamento(
            @PathVariable Departamento departamento) {

        return ResponseEntity.ok(servicio.listarPorDepartamento(departamento));
    }

    // ------------------------------------------------------------------
    // POST /api/empleados
    // Registra un nuevo empleado.
    // @Valid dispara las reglas de validacion del DTO antes de entrar al metodo:
    // si algo falla, se devuelve 400 Bad Request con el detalle de los campos.
    // Respuesta correcta: 201 Created, con la cabecera Location del nuevo recurso.
    // ------------------------------------------------------------------
    @PostMapping
    public ResponseEntity<EmpleadoDTO> registrar(@Valid @RequestBody EmpleadoDTO empleado) {

        EmpleadoDTO creado = servicio.guardar(empleado);

        // Cabecera Location: indica la URL donde queda disponible el registro creado.
        URI ubicacion = URI.create("/api/empleados/" + creado.getId());

        return ResponseEntity.created(ubicacion).body(creado);
    }

    // ------------------------------------------------------------------
    // PUT /api/empleados/{id}
    // Actualiza por completo un registro existente.
    // Respuestas: 200 OK, 400 Bad Request (datos invalidos) o 404 Not Found (id inexistente).
    // ------------------------------------------------------------------
    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoDTO> actualizar(@PathVariable Long id,
                                                  @Valid @RequestBody EmpleadoDTO empleado) {

        return ResponseEntity.ok(servicio.actualizar(id, empleado));
    }

    // ------------------------------------------------------------------
    // DELETE /api/empleados/{id}
    // Elimina un registro.
    // Respuestas: 200 OK con un mensaje JSON, o 404 Not Found si el id no existe.
    // ------------------------------------------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<RespuestaMensaje> eliminar(@PathVariable Long id) {

        servicio.eliminar(id);

        RespuestaMensaje respuesta = new RespuestaMensaje(
                HttpStatus.OK.value(),
                "Empleado con id " + id + " eliminado correctamente");

        return ResponseEntity.ok(respuesta);
    }
}
