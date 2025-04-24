package mrm.equipos.equipos_futbol.seguridad.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(name = "Token")
public class TokenDTO {
    private String token;
}
