package ni.edu.uam.api_taller2.services;

import ni.edu.uam.api_taller2.dto.EmpleadoDTO;
import ni.edu.uam.api_taller2.exceptions.RecursoNoEncontradoException;
import ni.edu.uam.api_taller2.models.Departamento;
import ni.edu.uam.api_taller2.models.Empleado;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

// Capa de servicio: contiene la logica de negocio de la entidad Empleado.
// @Service la registra como componente de Spring para poder inyectarla en el controlador.
// El almacenamiento es en memoria (una lista), porque el taller no exige base de datos.
@Service
public class EmpleadoService {

    // Lista que hace las veces de tabla de la base de datos.
    private final List<Empleado> empleados = new ArrayList<>();

    // Generador de identificadores unicos y seguro entre peticiones concurrentes.
    private final AtomicLong secuencia = new AtomicLong(0);

    // Constructor: carga tres empleados de ejemplo al iniciar la aplicacion
    // para que el endpoint de listado devuelva datos desde la primera prueba.
    public EmpleadoService() {
        cargarDatosDeEjemplo();
    }

    // Inserta los registros iniciales en la lista en memoria.
    private void cargarDatosDeEjemplo() {

        guardarEnLista(new Empleado(null, "Ivan Chavarria", "ivan.chavarria@uam.edu.ni",
                new BigDecimal("25000.00"), 3, LocalDate.of(2023, 2, 15),
                true, Departamento.DESARROLLO));

        guardarEnLista(new Empleado(null, "Maria Lopez", "maria.lopez@uam.edu.ni",
                new BigDecimal("31500.50"), 7, LocalDate.of(2020, 8, 1),
                true, Departamento.INFRAESTRUCTURA));

        guardarEnLista(new Empleado(null, "Carlos Mendoza", "carlos.mendoza@uam.edu.ni",
                new BigDecimal("18000.00"), 1, LocalDate.of(2025, 1, 20),
                false, Departamento.SOPORTE));
    }

    // ------------------------------------------------------------------
    // LISTAR: devuelve todos los empleados convertidos a DTO.
    // ------------------------------------------------------------------
    public List<EmpleadoDTO> listar() {

        List<EmpleadoDTO> resultado = new ArrayList<>();

        for (Empleado empleado : empleados) {
            resultado.add(convertirADTO(empleado));
        }

        return resultado;
    }

    // ------------------------------------------------------------------
    // LISTAR POR DEPARTAMENTO: filtra la lista por el valor de la enumeracion.
    // ------------------------------------------------------------------
    public List<EmpleadoDTO> listarPorDepartamento(Departamento departamento) {

        List<EmpleadoDTO> resultado = new ArrayList<>();

        for (Empleado empleado : empleados) {
            if (empleado.getDepartamento() == departamento) {
                resultado.add(convertirADTO(empleado));
            }
        }

        return resultado;
    }

    // ------------------------------------------------------------------
    // BUSCAR POR ID: devuelve un empleado o lanza 404 si no existe.
    // ------------------------------------------------------------------
    public EmpleadoDTO buscarPorId(Long id) {
        return convertirADTO(obtenerEmpleadoOFallar(id));
    }

    // ------------------------------------------------------------------
    // GUARDAR: registra un nuevo empleado y le asigna un identificador.
    // ------------------------------------------------------------------
    public EmpleadoDTO guardar(EmpleadoDTO datos) {

        Empleado nuevo = convertirADominio(datos);
        guardarEnLista(nuevo);

        return convertirADTO(nuevo);
    }

    // ------------------------------------------------------------------
    // ACTUALIZAR: reemplaza los datos de un empleado existente.
    // Conserva el mismo identificador; si no existe, lanza 404.
    // ------------------------------------------------------------------
    public EmpleadoDTO actualizar(Long id, EmpleadoDTO datos) {

        Empleado existente = obtenerEmpleadoOFallar(id);

        existente.setNombreCompleto(datos.getNombreCompleto());
        existente.setCorreo(datos.getCorreo());
        existente.setSalario(datos.getSalario());
        existente.setAniosExperiencia(datos.getAniosExperiencia());
        existente.setFechaContratacion(datos.getFechaContratacion());
        existente.setActivo(datos.getActivo());
        existente.setDepartamento(datos.getDepartamento());

        return convertirADTO(existente);
    }

    // ------------------------------------------------------------------
    // ELIMINAR: borra el empleado de la lista; si no existe, lanza 404.
    // ------------------------------------------------------------------
    public void eliminar(Long id) {
        Empleado existente = obtenerEmpleadoOFallar(id);
        empleados.remove(existente);
    }

    // ------------------------------------------------------------------
    // METODOS AUXILIARES PRIVADOS
    // ------------------------------------------------------------------

    // Agrega el empleado a la lista asignandole el siguiente identificador.
    private void guardarEnLista(Empleado empleado) {
        empleado.setId(secuencia.incrementAndGet());
        empleados.add(empleado);
    }

    // Busca el empleado por id; si no aparece lanza la excepcion que produce el 404.
    private Empleado obtenerEmpleadoOFallar(Long id) {

        Optional<Empleado> encontrado = empleados.stream()
                .filter(empleado -> empleado.getId().equals(id))
                .findFirst();

        return encontrado.orElseThrow(() -> new RecursoNoEncontradoException(
                "No se encontro el empleado con id " + id));
    }

    // Convierte el objeto de dominio en el DTO que se devuelve como JSON.
    private EmpleadoDTO convertirADTO(Empleado empleado) {

        EmpleadoDTO dto = new EmpleadoDTO();

        dto.setId(empleado.getId());
        dto.setNombreCompleto(empleado.getNombreCompleto());
        dto.setCorreo(empleado.getCorreo());
        dto.setSalario(empleado.getSalario());
        dto.setAniosExperiencia(empleado.getAniosExperiencia());
        dto.setFechaContratacion(empleado.getFechaContratacion());
        dto.setActivo(empleado.getActivo());
        dto.setDepartamento(empleado.getDepartamento());

        return dto;
    }

    // Convierte el DTO recibido en la peticion en un objeto de dominio.
    private Empleado convertirADominio(EmpleadoDTO dto) {

        Empleado empleado = new Empleado();

        empleado.setNombreCompleto(dto.getNombreCompleto());
        empleado.setCorreo(dto.getCorreo());
        empleado.setSalario(dto.getSalario());
        empleado.setAniosExperiencia(dto.getAniosExperiencia());
        empleado.setFechaContratacion(dto.getFechaContratacion());
        empleado.setActivo(dto.getActivo());
        empleado.setDepartamento(dto.getDepartamento());

        return empleado;
    }
}
