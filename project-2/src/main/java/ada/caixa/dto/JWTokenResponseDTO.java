package ada.caixa.dto;

import java.math.BigInteger;

public record JWTokenResponseDTO(
        String token,
        Long expiresIn
) {
}
