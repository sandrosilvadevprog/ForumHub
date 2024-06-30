package hub.forum.api.controller;

import hub.forum.api.dados.DadosDetalhamentoTopico;
import hub.forum.api.dados.DadosRegistroTopico;
import hub.forum.api.domain.topico.Topico;
import hub.forum.api.domain.topico.TopicoRepository;
import hub.forum.api.domain.topico.status.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class TopicoControllerTest {
    @Autowired
    private JacksonTester<DadosRegistroTopico> dadosRegistroTopicoJson;
    @Autowired
    private JacksonTester<DadosDetalhamentoTopico> dadosDetalhamentoTopicoJson;
    @Autowired
    private MockMvc mvc;

    @MockBean
    private TopicoRepository repository;

    @Test
    @DisplayName("Deveria devolver cód.400 quando info.inválida.")
    @WithMockUser
    void registrar_cenario1() throws Exception{
        var response = mvc
                .perform(post("/topicos"))
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
    @Test
    @DisplayName("Deveria devolver cód.200 quando info.válida.")
    @WithMockUser
    void registrar_cenario2() throws Exception{
        var data = LocalDateTime.now().plusHours(1);
        var dadosRegistro = new DadosRegistroTopico(null,"topico",
                "Curso",
                " topico",
                "autor",
                data,
                Status.RESPONDIDO);


        when(repository.save(any())).thenReturn(new Topico(dadosRegistro));
        var response = mvc
                .perform(
                        post("/topicos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(dadosRegistroTopicoJson.write(dadosRegistro).getJson()))
                .andReturn().getResponse();

        var dadosDetalhamento = new DadosDetalhamentoTopico(null,
                dadosRegistro.mensagem(),
                dadosRegistro.nomeCurso(),
                dadosRegistro.titulo(),
                dadosRegistro.autor(),
                dadosRegistro.data(),
                dadosRegistro.status()

        );
        var jsonEsperado = dadosDetalhamentoTopicoJson.write(dadosDetalhamento).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }
}