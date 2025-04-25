package mrm.equipos.equipos_futbol.manejo_de_errores;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Manejo acá todas las excepciones, si hubiera más las separaría en distintos advice
@RestControllerAdvice
public class ManejadorGlobalDeExcepciones {

    @ExceptionHandler(EntidadNoEncontradaException.class)
    public ResponseEntity<RespuestaError> entidadNoEncontradaHandler(EntidadNoEncontradaException ex) {
        var respuestaError = new RespuestaError(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(respuestaError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SolicitudInvalidaException.class)
    public ResponseEntity<RespuestaError> solicitudInvalidaHandler(SolicitudInvalidaException ex) {
        var respuestaError = new RespuestaError(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(respuestaError, HttpStatus.BAD_REQUEST);
    }

    // Manejo todos los otros errores (error de conexión con la base de datos, etc)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RespuestaError> exceptionHandler(Exception ex) {
        var respuestaError = new RespuestaError(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(respuestaError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
