package mrm.equipos.equipos_futbol.equipos;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import mrm.equipos.equipos_futbol.equipos.DTO.EquipoDTO;
import mrm.equipos.equipos_futbol.equipos.entity.Equipo;
import mrm.equipos.equipos_futbol.equipos.manejo_de_errores.EquipoNoEncontradoException;
import mrm.equipos.equipos_futbol.manejo_de_errores.SolicitudInvalidaException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/equipos", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class EquiposController {
    // NOTA: no creo un Servicio porque no hay comportamiento de dominio. Si tuviéramos que realizar, junto con la operación de CRUD, operaciones particulares del dominio de los equipos de fútbol, agregaría una clase EquiposService anotada con @Service, y la invocaría desde el controller para realizar las operaciones deseadas. Otra alternativa que muchas veces prefiero, es agregar el comportamiento directamente en la clase de modelo, en este caso Equipo, para mantener el encapsulamiento y agrupar la lógica con los datos, como dicta el Paradigma Orientado a Objetos.
    private EquiposRepository repo;

    private ModelMapper mapper;

    @GetMapping
    public Set<EquipoDTO> getEquipos() {
        return repo.findAll().stream().map(this::dto).collect(Collectors.toSet());
    }

    @GetMapping("/{id}")
    public EquipoDTO getEquipoById(@PathVariable Integer id) {
        return repo.findById(id).map(this::dto).orElseThrow(EquipoNoEncontradoException::new);
    }

    @GetMapping("/buscar")
    public Set<EquipoDTO> findByNombre(@RequestParam("nombre") String nombre) {
        return repo.findByNombreContainingIgnoreCase(nombre).stream().map(this::dto).collect(Collectors.toSet());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EquipoDTO addEquipo(@Valid @RequestBody EquipoDTO equipoDTO, BindingResult bindingResult) {
        validarErrores(bindingResult);
        if (repo.existsById(equipoDTO.getId()))
            throw new SolicitudInvalidaException(); // Idealmente agregaríamos un mensaje de error personalizado en lugar de el genérico
        Equipo equipoAgregado = repo.save(model(equipoDTO));
        return dto(equipoAgregado);
    }

    @PutMapping("/{id}")
    public EquipoDTO replaceEquipo(@PathVariable Integer id, @Valid @RequestBody EquipoDTO equipoActualizado,
                                   BindingResult bindingResult) {
        validarErrores(bindingResult);
        return repo.findById(id)
                .map(equipoAnterior ->
                        {
                            equipoAnterior.setNombre(equipoActualizado.getNombre());
                            equipoAnterior.setLiga(equipoActualizado.getLiga());
                            equipoAnterior.setPais(equipoActualizado.getPais());
                            return repo.save(equipoAnterior);
                        })
                .map(this::dto)
                .orElseThrow(EquipoNoEncontradoException::new);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEquipo(@PathVariable Integer id) {
        repo.findById(id).ifPresentOrElse(equipo -> repo.delete(equipo),
                () -> {throw new EquipoNoEncontradoException();}
        );
    }

    private void validarErrores(BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new SolicitudInvalidaException();
    }

    private EquipoDTO dto(Equipo modelEquipo) {
        return mapper.map(modelEquipo, EquipoDTO.class);
    }

    private Equipo model(EquipoDTO equipoDTO) {
        return mapper.map(equipoDTO, Equipo.class);
    }
}
