package hub.forum.api.controller;

import hub.forum.api.dados.DadosAtualizacaoTopico;
import hub.forum.api.dados.DadosDetalhamentoTopico;
import hub.forum.api.dados.DadosRegistroTopico;
import hub.forum.api.domain.topico.Topico;
import hub.forum.api.domain.topico.TopicoRepository;
import hub.forum.api.domain.topico.Topico;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {
    @Autowired
    private TopicoRepository repository;
    @PostMapping
    @Transactional
    public ResponseEntity registrar(@RequestBody @Valid DadosRegistroTopico dados, UriComponentsBuilder  uriBuilder ){
        var topico = new Topico(dados);
        repository.save(topico);
        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(dados);



    }
    @GetMapping
    public ResponseEntity<Page<Topico>> listar(@PageableDefault(size = 10) Pageable paginavel){
        var page = repository.findAll(paginavel);
        return ResponseEntity.ok(page);

    }
    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoTopico dados){
        var topico = repository.getReferenceById(dados.id());
        topico.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));

    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){
        var topico = repository.getReferenceById(id);
        return ResponseEntity.ok(new  DadosDetalhamentoTopico(topico));
    }
}
