package mrm.equipos.equipos_futbol.manejo_de_errores;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EntidadNoEncontradaAdvice {
    @ExceptionHandler(EntidadNoEncontradaException.class)
    public ResponseEntity<RespuestaError> entidadNoEncontradaHandler(EntidadNoEncontradaException ex) {
        var respuestaError = new RespuestaError(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(respuestaError, HttpStatus.NOT_FOUND);
    }

    // TODO: hacer un handler como este, pero para el error 400 que puede ocurrir al hacer un POST (pensar qué puede fallar para ver qué catcheamos)
    // TODO: hacer como fallback un handler para Exception que retorne un 500 y el mensaje de la excepción
}
