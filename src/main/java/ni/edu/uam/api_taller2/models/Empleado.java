package ni.edu.uam.api_taller2.models;

import java.math.BigDecimal;
import java.time.LocalDate;

// Modelo de dominio (entidad principal del proyecto de asignatura).
// Representa al empleado tal como se guarda en el almacenamiento en memoria.
// No lleva anotaciones de validacion: esas viven en el DTO, que es el objeto
// que viaja por HTTP. Asi se separa "lo que entra por la API" de "lo que se guarda".
public class Empleado {

    private Long id;                      // Identificador unico, lo asigna el servicio
    private String nombreCompleto;        // Atributo de tipo texto
    private String correo;                // Atributo de tipo texto con formato de correo
    private BigDecimal salario;           // Atributo numerico decimal
    private Integer aniosExperiencia;     // Atributo numerico entero
    private LocalDate fechaContratacion;  // Atributo de tipo fecha
    private Boolean activo;               // Atributo de tipo booleano
    private Departamento departamento;    // Atributo de tipo enumeracion

    // Constructor vacio requerido para crear instancias sin datos iniciales.
    public Empleado() {
    }

    // Constructor completo, usado para cargar los datos de ejemplo al iniciar la API.
    public Empleado(Long id,
                    String nombreCompleto,
                    String correo,
                    BigDecimal salario,
                    Integer aniosExperiencia,
                    LocalDate fechaContratacion,
                    Boolean activo,
                    Departamento departamento) {

        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.salario = salario;
        this.aniosExperiencia = aniosExperiencia;
        this.fechaContratacion = fechaContratacion;
        this.activo = activo;
        this.departamento = departamento;
    }

    // --- Metodos de acceso (getters y setters) ---
    // Jackson los utiliza para convertir el objeto a JSON y viceversa.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public Integer getAniosExperiencia() {
        return aniosExperiencia;
    }

    public void setAniosExperiencia(Integer aniosExperiencia) {
        this.aniosExperiencia = aniosExperiencia;
    }

    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(LocalDate fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }
}
