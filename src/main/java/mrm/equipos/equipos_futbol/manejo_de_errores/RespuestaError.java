package mrm.equipos.equipos_futbol.manejo_de_errores;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RespuestaError {
    private String mensaje;
    private int codigo;
}
