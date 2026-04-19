package ada.caixa.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @NotNull(message = "O do usuario é obrigatorio")
        @NotBlank(message = "O nome do usuario não pode ser vazio")
        String name,
        @NotNull(message = "O email do usuario é obrigatorio")
        @Email(message = "Invalid email format")
        String email,
        @Size(min = 8, message = "A senha deve conter no mínimo 8 caracteres")
        String password
) {
}
