package mrm.equipos.equipos_futbol.equipos.manejo_de_errores;

import mrm.equipos.equipos_futbol.manejo_de_errores.EntidadNoEncontradaException;

public class EquipoNoEncontradoException extends EntidadNoEncontradaException {
    public EquipoNoEncontradoException() {
        super("Equipo no encontrado");
    }
}
