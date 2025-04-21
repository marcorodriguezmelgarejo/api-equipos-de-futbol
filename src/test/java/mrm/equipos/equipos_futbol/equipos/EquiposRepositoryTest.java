package mrm.equipos.equipos_futbol.equipos;

import mrm.equipos.equipos_futbol.equipos.entity.Equipo;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class EquiposRepositoryTest { // Testea unitariamente el repositorio, usando una base de datos temporal que toma datos de data.sql

    @Autowired
    private EquiposRepository equiposRepository;

    @Nested
    class findByNombreIgnoreCaseFuncionaCorrectamente {
        @Test
        public void findByNombreIgnoreCaseEncuentraEquipoConDistintoCase() {
            Set<Equipo> resultados = equiposRepository.findByNombreContainingIgnoreCase("boca");
            Set<Equipo> resultadoEsperado = equiposRepository.findByNombre("Boca");
            assertEquals(resultados, resultadoEsperado);
        }

        @Test
        public void findByNombreIgnoreCaseNoEncuentraEquipoConOtroNombre() {
            Set<Equipo> resultados = equiposRepository.findByNombreContainingIgnoreCase("huracan");
            assertTrue(resultados.isEmpty());
        }

        @Test
        public void findByNombreIgnoreCaseRetornaDosResultadosCuandoHayDos() {
            Set<Equipo> resultados = equiposRepository.findByNombreContainingIgnoreCase("river");
            Set<Equipo> resultadosEsperados = Stream.concat(
                        equiposRepository.findByNombre("River Plate").stream(),
                        equiposRepository.findByNombre("River FC").stream())
                    .collect(Collectors.toSet());
            assertEquals(resultados, resultadosEsperados);
        }
    }
}
