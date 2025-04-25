package mrm.equipos.equipos_futbol.manejo_de_errores;

public class SolicitudInvalidaException extends RuntimeException {
    public SolicitudInvalidaException() {
        super("La solicitud es inválida"); // Yo no pondría un mensaje de error global, si no que haría que fuera distinto según la validación que haya fallado, pero se pidió específicamente esto.
    }
}
