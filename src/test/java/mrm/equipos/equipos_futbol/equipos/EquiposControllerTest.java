package mrm.equipos.equipos_futbol.equipos;

import mrm.equipos.equipos_futbol.equipos.DTO.EquipoDTO;
import mrm.equipos.equipos_futbol.equipos.entity.Equipo;
import mrm.equipos.equipos_futbol.seguridad.JWTokenService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EquiposController.class)
@AutoConfigureMockMvc(addFilters = false) // Remueve el filtro de seguridad para probar más fácilmente (la autenticación ya la probé a mano, no la voy a probar en forma unitaria. En un desarrollo real sí lo haría)
@AutoConfigureJsonTesters
public class EquiposControllerTest { // Testea el controller en forma unitaria, mockeando el repositorio

    private static final Equipo RIVER_ENTITY = new Equipo(1, "River Plate", "Liga Arg", "Pais Arg");
    private static final EquipoDTO RIVER_DTO = new EquipoDTO(1, "River Plate", "Liga Arg", "Pais Arg");

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EquiposRepository equiposRepository;

    @Autowired
    private JacksonTester<EquipoDTO> jsonDTO;

    @MockitoBean
    private JWTokenService jwTokenService; // No se usa, pero si no pinchaba al injectar dependencias

    @Nested
    class findByNombre {
        @Test
        void findByNombreRetornaOKCuandoEncuentraElEquipo() throws Exception {
            when(equiposRepository.findById(1)).thenReturn(Optional.of(RIVER_ENTITY));
            mockMvc.perform(get("/equipos/1")).andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content()
                            .json(jsonDTO.write(RIVER_DTO).getJson()));
        }

        @Test
        void findByNombreRetornaNotFoundCuandoNoEncuentraElEquipo() throws Exception {
            when(equiposRepository.findById(1)).thenReturn(Optional.empty());
            mockMvc.perform(get("/equipos/1")).andDo(print())
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    class delete {
        @Test
        void deleteRetorna204ConBodyVacios() throws Exception {
            when(equiposRepository.findById(1)).thenReturn(Optional.of(RIVER_ENTITY));
            mockMvc.perform(delete("/equipos/1")).andDo(print())
                    .andExpect(status().isNoContent())
                    .andExpect(content().string(""));
        }
    }

    @Nested
    class create {
        @Test
        void createEquipoRetornaCreatedYLaEntidadCreada() throws Exception {
            when(equiposRepository.save(RIVER_ENTITY)).thenReturn(RIVER_ENTITY);
            mockMvc.perform(post("/equipos").contentType(MediaType.APPLICATION_JSON).content(jsonDTO.write(RIVER_DTO).getJson()))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(content().json(jsonDTO.write(RIVER_DTO).getJson()));
        }
    }
}
