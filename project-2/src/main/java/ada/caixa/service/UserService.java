package ada.caixa.service;

import ada.caixa.dto.UserRequestDTO;
import ada.caixa.dto.UserResponseDTO;
import ada.caixa.dto.UserSignInRequestDTO;
import ada.caixa.entity.User;
import ada.caixa.repository.UserRepository;
import io.quarkus.logging.Log;
import io.quarkus.security.AuthenticationFailedException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.jwt.JsonWebToken;

@ApplicationScoped
public class UserService {

    private final UserRepository userRepository;
    private final JsonWebToken jwt;

    public UserService(UserRepository userRepository, JsonWebToken jwt) {
        this.userRepository = userRepository;
        this.jwt = jwt;
    }

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        User user = new User(
                userRequestDTO.name(),
                userRequestDTO.email(),
                userRequestDTO.password()
        );

        userRepository.persist(user);

        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }

    @Transactional
    public UserResponseDTO getUserCredentialsAuthenticated() {
        Long userId = Long.parseLong(jwt.getSubject());
        Log.info("Authenticated user ID: " + userId);

        User user = userRepository.findByIdOptional(userId)
                .orElseThrow(
                        () -> new AuthenticationFailedException("User not found")
                );

        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
}
