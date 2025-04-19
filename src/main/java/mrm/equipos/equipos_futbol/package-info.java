/* Agrego estas dos annotations para que, a no ser que estén anotados con @Nullable, ningún mét0do definido dentro de
 * este paquete retorne null, y ningún campo de ninguna clase dentro de este paquete tenga valor null.
 * Es útil para evitar null pointers.
 * Por el juego de datos que me pasaron, asumo que todos los campos de todas las entidades son no nulleables.
 */
@NonNullApi
@NonNullFields
package mrm.equipos.equipos_futbol;

import org.springframework.lang.NonNullApi;
import org.springframework.lang.NonNullFields;