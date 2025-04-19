package mrm.equipos.equipos_futbol.equipos;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquiposRepository extends JpaRepository<Equipo, Integer> {
    public List<Equipo> findByNombreContainingIgnoreCase(String nombre); // Hago que ignore las mayúsculas para que sea más flexible
}
