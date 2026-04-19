package ada.caixa.security;

import ada.caixa.dto.JWTokenResponseDTO;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.jwt.Claims;

import java.time.Duration;
import java.util.Set;

@ApplicationScoped
public class JWTGenerator {
    public JWTokenResponseDTO generateToken(
            Long expiresIn
    ) {
        String token = Jwt.claims()
                .expiresIn(expiresIn)
                .sign();
        return new JWTokenResponseDTO(
                token,
                expiresIn
        );
    }

    public JWTokenResponseDTO generateToken(
            Long userId,
            String role,
            Long expiresIn
    ) {

        String token = Jwt.claims()
                .claim(Claims.sub, userId.toString())
                .expiresIn(expiresIn)
                .groups(role)
                .sign();
        return new JWTokenResponseDTO(
                token,
                expiresIn
        );
    }
}
