package mrm.equipos.equipos_futbol.seguridad;

import lombok.AllArgsConstructor;
import mrm.equipos.equipos_futbol.seguridad.DTO.Credenciales;
import mrm.equipos.equipos_futbol.seguridad.DTO.TokenDTO;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class LoginController {
    private AuthenticationManager authenticationManager;
    private JWTokenService jwtTokenService;

    @PostMapping("/login")
    public TokenDTO login(@RequestBody Credenciales credenciales) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credenciales.getUsername(), credenciales.getPassword())); // Si hay un error de autenticación, lo maneja el handler
        return new TokenDTO(jwtTokenService.generateToken(auth.getName()));
    }
}
