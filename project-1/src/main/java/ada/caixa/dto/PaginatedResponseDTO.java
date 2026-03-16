package ada.caixa.dto;

import java.util.List;

public record PaginatedResponseDTO<T>(
        int page,
        int size,
        long totalElements,
        int totalPages,
        List<T> data
) {
}
