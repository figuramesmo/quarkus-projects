package ada.caixa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LessonRequestDTO(
        @NotNull(message = "O nome da aula é obrigatório")
        @NotBlank(message = "O nome da aula não pode ser vazio")
        String name
) {
}
