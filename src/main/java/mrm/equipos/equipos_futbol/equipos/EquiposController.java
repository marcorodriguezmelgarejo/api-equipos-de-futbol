package mrm.equipos.equipos_futbol.equipos;

import mrm.equipos.equipos_futbol.equipos.manejo_de_errores.EquipoNoEncontradoException;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
public class EquiposController {
    private EquiposRepository repo;

    public EquiposController() {}

    @GetMapping("/equipos")
    public Collection<Equipo> getEquipos() {
        return repo.findAll();
    }

    @GetMapping("/equipos/{id}")
    public Equipo getEquipoById(@PathVariable Integer id) {
        return repo.findById(id).orElseThrow(EquipoNoEncontradoException::new);
    }

    @GetMapping("/equipos/buscar")
    public List<Equipo> findByNombre(@RequestParam("nombre") String nombre) {
        return repo.findByNombreContainingIgnoreCase(nombre);
    }

    @PostMapping("/equipos")
    public Equipo addEquipo(@RequestBody Equipo equipo) {
        return repo.save(equipo); // TODO: asegurarse de que por default retorne 201 Created
        // TODO: si ocurre algún error, entiendo que puede ser porque no se haya informado algún campo, retornar:
        /*
        {
            "mensaje": "La solicitud es invalida",
            "codigo": 400
        }
         */
    }

    @PutMapping("/equipos/{id}")
    public Equipo replaceEquipo(@PathVariable Integer id, @RequestBody Equipo equipoActualizado) {
        return repo.findById(id)
                .map(equipoAnterior ->
                        {
                            equipoAnterior.setNombre(equipoActualizado.getNombre());
                            equipoAnterior.setLiga(equipoActualizado.getLiga());
                            equipoAnterior.setPais(equipoActualizado.getPais());
                            return repo.save(equipoAnterior);
                        })
                .orElseThrow(EquipoNoEncontradoException::new);
    }

    @DeleteMapping("/equipos/{id}")
    public void deleteEquipo(@PathVariable Integer id) {
        // TODO: asegurarse de que retorne 204 no content
        repo.findById(id).ifPresentOrElse(equipo -> repo.delete(equipo),
                () -> {throw new EquipoNoEncontradoException();}
        );
    }
}
