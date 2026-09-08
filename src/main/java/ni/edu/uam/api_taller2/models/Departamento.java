package ni.edu.uam.api_taller2.models;

// Enumeracion que representa los departamentos validos de la empresa.
// Al ser un enum, Spring Boot solo acepta estos valores exactos en el JSON;
// cualquier otro texto provoca un error 400 manejado por el GlobalExceptionHandler.
public enum Departamento {

    DESARROLLO,        // Area de construccion de software
    INFRAESTRUCTURA,   // Area de servidores y redes
    SOPORTE,           // Area de atencion a usuarios
    ADMINISTRACION,    // Area administrativa y contable
    VENTAS             // Area comercial
}
