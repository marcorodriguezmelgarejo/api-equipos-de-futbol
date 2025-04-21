package mrm.equipos.equipos_futbol.equipos;

import lombok.AllArgsConstructor;
import mrm.equipos.equipos_futbol.equipos.entity.Equipo;
import mrm.equipos.equipos_futbol.equipos.manejo_de_errores.EquipoNoEncontradoException;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Set;

@RestController
@AllArgsConstructor
public class EquiposController {

    // NOTA: no creo un Servicio porque no hay comportamiento de dominio. Si tuviéramos que realizar, junto con la operación de CRUD, operaciones particulares del dominio de los equipos de fútbol, agregaría una clase EquiposService anotada con @Service, y la invocaría desde el controller para realizar las operaciones deseadas. Otra alternativa que muchas veces prefiero, es agregar el comportamiento directamente en la clase de modelo, en este caso Equipo, para mantener el encapsulamiento y agrupar la lógica con los datos, como dicta el Paradigma Orientado a Objetos.

    private EquiposRepository repo;

    @GetMapping("/equipos")
    public Collection<Equipo> getEquipos() {
        return repo.findAll();
    }

    @GetMapping("/equipos/{id}")
    public Equipo getEquipoById(@PathVariable Integer id) {
        return repo.findById(id).orElseThrow(EquipoNoEncontradoException::new);
    }

    @GetMapping("/equipos/buscar")
    public Set<Equipo> findByNombre(@RequestParam("nombre") String nombre) {
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
