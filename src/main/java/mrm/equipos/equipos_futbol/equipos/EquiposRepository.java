package mrm.equipos.equipos_futbol.equipos;


import mrm.equipos.equipos_futbol.equipos.entity.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface EquiposRepository extends JpaRepository<Equipo, Integer> {
    Set<Equipo> findByNombreContainingIgnoreCase(String nombre); // Hago que ignore las mayúsculas para que sea más flexible
    Set<Equipo> findByNombre(String nombre);
}
