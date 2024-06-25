package hub.forum.api.domain.topico;

import hub.forum.api.dados.DadosAtualizacaoTopico;
import hub.forum.api.dados.DadosRegistroTopico;
import hub.forum.api.domain.topico.status.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "topicos")
@Entity(name = "topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensagem;
    @Column(name = "nome_curso")
    private String nomeCurso;
    private String titulo;
    private String autor;
    private LocalDateTime data;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Topico(DadosRegistroTopico dados) {
        this.mensagem = dados.mensagem();
        this.nomeCurso = dados.nomeCurso();
        this.titulo = dados.titulo();
        this.autor = dados.autor();
        this.data = dados.data();
        this.status = dados.status();
    }

    public void atualizarInformacoes(DadosAtualizacaoTopico dados) {
        if(dados.mensagem() != null){
            this.mensagem = dados.mensagem();
        }
        if(dados.nomeCurso() != null){
            this.nomeCurso = dados.nomeCurso();
        }
        if(dados.titulo() != null){
            this.titulo = dados.titulo();
        }
        if(dados.autor() != null){
            this.autor = dados.autor();
        }
        if(dados.data() != null){
            this.data = LocalDateTime.parse(dados.data());
        }
        if(dados.status() != null){
            this.status = dados.status();
        }
    }
}
