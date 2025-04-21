package mrm.equipos.equipos_futbol.equipos.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Entity
public class Equipo {

    // Para que nadie pueda usar el constructor aparte de Spring y esta misma clase, hago que el constructor sea protected, y pongo esta entidad en su propio paquete
    protected Equipo() {}

    @Id
    private Integer id;

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
