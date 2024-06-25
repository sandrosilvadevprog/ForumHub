package hub.forum.api.dados;

import hub.forum.api.domain.topico.status.Status;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record DadosRegistroTopico(
        @NotBlank
        String mensagem,
        @NotBlank
        String nomeCurso,
        @NotBlank
        String titulo,
        @NotBlank
        String autor,

        LocalDateTime data,
        Status status) {
}
