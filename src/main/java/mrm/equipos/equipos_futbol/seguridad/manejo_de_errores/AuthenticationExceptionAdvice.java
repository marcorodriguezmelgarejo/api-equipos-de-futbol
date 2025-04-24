package mrm.equipos.equipos_futbol.seguridad.manejo_de_errores;

import mrm.equipos.equipos_futbol.manejo_de_errores.RespuestaError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthenticationExceptionAdvice {
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<RespuestaError> entidadNoEncontradaHandler() {
        var respuestaError = new RespuestaError("Credenciales inválidas", HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(respuestaError, HttpStatus.UNAUTHORIZED);
    }
}
