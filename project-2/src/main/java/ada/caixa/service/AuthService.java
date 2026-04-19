package ada.caixa.service;

import ada.caixa.dto.JWTokenResponseDTO;
import ada.caixa.dto.UserSignInRequestDTO;
import ada.caixa.entity.User;
import ada.caixa.repository.UserRepository;
import ada.caixa.security.JWTGenerator;
import io.quarkus.security.AuthenticationFailedException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import java.time.Duration;

@ApplicationScoped
public class AuthService {

    private final UserRepository userRepository;
    private final JWTGenerator jwtGenerator;

    public AuthService (UserRepository userRepository, JWTGenerator jwtGenerator) {
        this.userRepository = userRepository;
        this.jwtGenerator = jwtGenerator;
    }

    @Transactional
    public JWTokenResponseDTO signIn(UserSignInRequestDTO userDTO){
        User user = userRepository.findBuSignInCredentialsOptional(
                userDTO.email(),
                userDTO.password()
        ).orElseThrow(
                () -> new AuthenticationFailedException("Invalid email or password")
        );

        Duration tokenExpiration = Duration.ofHours(1);

        return jwtGenerator.generateToken(
                user.getId(),
                user.getRole(),
                tokenExpiration.toMillis()
        );
    }

}
