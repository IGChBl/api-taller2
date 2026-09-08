package ni.edu.uam.api_taller2.dto;

// DTO de respuesta simple.
// Se usa para devolver un JSON claro (nunca un texto suelto como "correcto")
// en operaciones que no retornan un empleado, por ejemplo el DELETE.
public class RespuestaMensaje {

    private int estado;       // Codigo HTTP de la respuesta (200, 404, etc.)
    private String mensaje;   // Descripcion legible del resultado

    // Constructor que recibe el codigo y el mensaje a devolver.
    public RespuestaMensaje(int estado, String mensaje) {
        this.estado = estado;
        this.mensaje = mensaje;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
