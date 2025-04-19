package mrm.equipos.equipos_futbol.equipos;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Setter
@Getter
public class Equipo extends AbstractPersistable<Integer> {
    @Column(length = 40)
    private String nombre;

    /*
     * Hubiera preferido modelar las ligas y los países como entidades de la base de datos y no como Strings. Esto
     * hubiera permitido parametrizar en la base de datos los valores posibles para estos dos campos. No lo hago porque
     * entiendo que la consigna buscaba que se pudieran crear equipos nuevos con cualquier valor posible en los campos
     * liga y país.
     */
    @Column(length = 40)
    private String liga;

    @Column(length = 40)
    private String pais;
}
