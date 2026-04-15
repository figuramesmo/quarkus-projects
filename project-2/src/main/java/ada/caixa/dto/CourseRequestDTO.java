package ada.caixa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CourseRequestDTO(
        @NotNull(message = "O nome do curso é obrigatório")
        @NotBlank(message = "O nome do curso não pode ser vazio")
        @Size(min = 3, max = 100, message = "O nome do curso deve ter entre 3 e 100 caracteres")
        String name,
        @Size(max = 500, message = "A descrição do curso deve ter no máximo 500 caracteres")
        String description
) {
}
