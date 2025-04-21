package mrm.equipos.equipos_futbol.equipos;

import mrm.equipos.equipos_futbol.equipos.entity.Equipo;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EquiposController.class)
@AutoConfigureJsonTesters
public class EquiposControllerTest { // Testea el controller en forma unitaria, mockeando el repositorio

    private static final Equipo RIVER = new Equipo(1, "River Plate", "Liga Arg", "Pais Arg");

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EquiposRepository equiposRepository;

    @Autowired
    private JacksonTester<Equipo> json;

    @Nested
    class findByNombre {
        @Test
        void findByNombreRetornaOKCuandoEncuentraElEquipo() throws Exception {
            when(equiposRepository.findById(1)).thenReturn(Optional.of(RIVER));
            mockMvc.perform(get("/equipos/1")).andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content()
                            .json(json.write(RIVER).getJson()));
        }

        @Test
        void findByNombreRetornaNotFoundCuandoNoEncuentraElEquipo() throws Exception {
            when(equiposRepository.findById(1)).thenReturn(Optional.empty());
            mockMvc.perform(get("/equipos/1")).andDo(print())
                    .andExpect(status().isNotFound());
        }
    }
}
