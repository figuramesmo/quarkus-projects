package ada.caixa.service;

import ada.caixa.dto.UserRequestDTO;
import ada.caixa.dto.UserResponseDTO;
import ada.caixa.dto.UserSignInRequestDTO;
import ada.caixa.entity.User;
import ada.caixa.repository.UserRepository;
import io.quarkus.security.AuthenticationFailedException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    public UserResponseDTO getUserCredentialsAuthenticated(UserSignInRequestDTO userDTO) {
        User user = userRepository.findBuSignInCredentialsOptional(
                userDTO.email(),
                userDTO.password()
        ).orElseThrow(
                () -> new AuthenticationFailedException("Invalid email or password")
        );

        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
}
