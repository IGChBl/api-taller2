package ni.edu.uam.api_taller2.exceptions;

// Excepcion propia que se lanza cuando se busca un empleado que no existe.
// Extiende RuntimeException para no obligar a capturarla en cada metodo:
// el GlobalExceptionHandler la intercepta y la convierte en un 404 en formato JSON.
public class RecursoNoEncontradoException extends RuntimeException {

    // Recibe el mensaje que se mostrara al cliente en la respuesta JSON.
    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
