package mrm.equipos.equipos_futbol.equipos.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EquipoDTO {
    // Creo un DTO para desacoplar la interfaz de la API REST del modelo de datos usado internamente en el sistema
    protected EquipoDTO() {}
    private Integer id;
    private String nombre;
    private String liga;
    private String pais;
}
