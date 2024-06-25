package hub.forum.api.dados;

import hub.forum.api.domain.topico.Topico;
import hub.forum.api.domain.topico.status.Status;

import java.time.LocalDateTime;

public record DadosDetalhamentoTopico(Long id,
                                      String mensagem,
                                      String nomeCurso,
                                      String titulo,
                                      String autor,
                                      LocalDateTime data,
                                      Status status) {
    public DadosDetalhamentoTopico(Topico topico){
        this(topico.getId(),topico.getMensagem(),topico.getNomeCurso(),topico.getTitulo(),topico.getAutor(),topico.getData(), topico.getStatus());
    }
}
