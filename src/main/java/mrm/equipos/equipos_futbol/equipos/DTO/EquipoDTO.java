package mrm.equipos.equipos_futbol.equipos.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(name = "Equipo") // Para que salga con este nombre en Swagger
public class EquipoDTO {
    // Creo un DTO para desacoplar la interfaz de la API REST del modelo de datos usado internamente en el sistema
    public EquipoDTO() {}
    @NotNull
    private Integer id;
    @NotBlank
    @Size(max = 40) // Valido los tamaños en la entrada de la API, para que falle antes de la inserción en la base de datos (principio Fail Fast)
    private String nombre;
    @NotBlank
    @Size(max = 40)
    private String liga;
    @NotBlank
    @Size(max = 40)
    private String pais;
}
