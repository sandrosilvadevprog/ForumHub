package hub.forum.api.dados;

import hub.forum.api.domain.topico.status.Status;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoTopico(
        @NotNull
        Long id,
        String mensagem,
        String nomeCurso,
        String titulo,
        String autor,
        @NotNull
        String data,
        Status status
) {
}
