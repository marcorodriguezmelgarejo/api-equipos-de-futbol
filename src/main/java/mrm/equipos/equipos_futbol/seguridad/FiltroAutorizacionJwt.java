package mrm.equipos.equipos_futbol.seguridad;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class FiltroAutorizacionJwt extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JWTokenService jwtTokenService;

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization"); // Obtiene el header
        String token = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) { // Si se pasó "Bearer <Token>" en el header
            token = authHeader.substring(7); // Agarra el token
            username = jwtTokenService.extractUsername(token); // Extrae el usuario del token, primero validando su firma contra la clave privada usada para generarlo
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) { // Si se pudo obtener el usuario del token, y todavía no se autenticó el request (no lo va a estar, porque este filtro se llama una sola vez)
            UserDetails userDetails = userDetailsService.loadUserByUsername(username); // Obtiene los User Details
            if (jwtTokenService.validateToken(token, userDetails)) { // Valida el token
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken); // Autentica el request
            }
        }
        filterChain.doFilter(request, response); // Llama el siguiente filtro en la cadena
    }
}
