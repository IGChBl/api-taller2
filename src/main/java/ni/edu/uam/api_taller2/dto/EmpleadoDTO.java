package ni.edu.uam.api_taller2.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import ni.edu.uam.api_taller2.models.Departamento;

import java.math.BigDecimal;
import java.time.LocalDate;

// DTO (Data Transfer Object) del empleado.
// Es el unico objeto que viaja por HTTP: se usa tanto para RECIBIR datos
// (POST y PUT, validado con @Valid) como para DEVOLVER datos en formato JSON.
// Aqui se concentran todas las reglas de validacion del taller.
public class EmpleadoDTO {

    // Identificador del registro.
    // No se valida porque el cliente no lo envia: lo genera el servicio.
    private Long id;

    // Validacion 1 y 2: el nombre es obligatorio y debe medir entre 3 y 80 caracteres.
    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(min = 3, max = 80, message = "El nombre completo debe tener entre 3 y 80 caracteres")
    private String nombreCompleto;

    // Validacion 3 y 4: el correo es obligatorio y debe tener formato valido (usuario@dominio).
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe tener un formato valido")
    private String correo;

    // Validacion 5, 6 y 7: el salario es obligatorio, debe ser mayor que cero
    // y admite como maximo 8 enteros y 2 decimales.
    @NotNull(message = "El salario es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El salario debe ser mayor que cero")
    @Digits(integer = 8, fraction = 2, message = "El salario admite maximo 8 enteros y 2 decimales")
    private BigDecimal salario;

    // Validacion 8, 9 y 10: los anios de experiencia son obligatorios y deben estar entre 0 y 50.
    @NotNull(message = "Los anios de experiencia son obligatorios")
    @Min(value = 0, message = "Los anios de experiencia no pueden ser negativos")
    @Max(value = 50, message = "Los anios de experiencia no pueden ser mayores a 50")
    private Integer aniosExperiencia;

    // Validacion 11 y 12: la fecha de contratacion es obligatoria y no puede ser futura.
    @NotNull(message = "La fecha de contratacion es obligatoria")
    @PastOrPresent(message = "La fecha de contratacion no puede ser una fecha futura")
    private LocalDate fechaContratacion;

    // Validacion 13: se debe indicar explicitamente si el empleado esta activo (true o false).
    @NotNull(message = "Debe indicar si el empleado esta activo (true o false)")
    private Boolean activo;

    // Validacion 14: el departamento es obligatorio y solo acepta los valores de la enumeracion.
    @NotNull(message = "El departamento es obligatorio: DESARROLLO, INFRAESTRUCTURA, SOPORTE, ADMINISTRACION o VENTAS")
    private Departamento departamento;

    // Constructor vacio: lo necesita Jackson para convertir el JSON entrante en un objeto Java.
    public EmpleadoDTO() {
    }

    // --- Metodos de acceso (getters y setters) ---
    // Jackson usa los getters para generar el JSON de salida
    // y los setters para llenar el objeto con el JSON de entrada.

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
