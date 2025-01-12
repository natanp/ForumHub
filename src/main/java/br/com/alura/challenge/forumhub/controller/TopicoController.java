package br.com.alura.challenge.forumhub.controller;

import br.com.alura.challenge.forumhub.domain.topico.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @PostMapping
    public ResponseEntity<DadosDetalhamentoTopico> cadastrarNovoTopico(
            @RequestBody @Valid DadosCadastroTopico dadosCadastroTopico, UriComponentsBuilder uriBuilder) {

        var topico = topicoService.cadastrarNovoTopico(dadosCadastroTopico);
        URI location = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.id()).toUri();
        return ResponseEntity.created(location).body(topico);
    }

    /**
     * Atualizar tópico fornecendo o id e os campos obrigatórios {@code id}, {@code título}, {@code mensagem},
     * {@code status}, {@code idCurso}
     * @param id id do tópico a ser atualizado
     * @param dadosAtualizacaoTopico campos a serem atualizados
     * @return {@code ResponseEntity<DadosDetalhamentoTopico>} Dados do tópico atualizados.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoTopico> atualizarTopico(
            @PathVariable Long id, @RequestBody @Valid DadosAtualizacaoTopico dadosAtualizacaoTopico) {
        var dadosAtualizacaoTopicoComId = new DadosAtualizacaoTopico(id, dadosAtualizacaoTopico.titulo(),
                dadosAtualizacaoTopico.mensagem(),dadosAtualizacaoTopico.status(),dadosAtualizacaoTopico.idCurso());
        var topico = topicoService.atualizarTopico(dadosAtualizacaoTopicoComId);
        return ResponseEntity.ok(topico);
    }

    /**
     * Deletar tópico pelo id
     * @param id id do tópico a ser deletado
     * @return {@code ResponseEntity<Void>} Código 204 {@link HttpStatus#NO_CONTENT NO_CONTENT}.
     */
    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> deletarTopico(@PathVariable Long id) {
        topicoService.deletarTopico(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Lista os tópicos usando paginação
     *
     * @param pageable parâmetros de paginação opcionais
     * @return {@code ResponseEntity<Page<DadosListagemTopico>>} tópicos listados e paginados. Se não for fornecido os
     * parâmetros de paginação na requisição, o padrão será aplicado (retornar 10 elementos ordenados de forma crescente pela data de
     * criação do tópico).
     */
    @GetMapping
    public ResponseEntity<Page<DadosListagemTopico>> listarTopicos(
            @PageableDefault(size = 10, sort = "dataCriacao", direction = Sort.Direction.ASC) Pageable pageable) {

        var topicos = topicoService.listarTopicos(pageable);
        return ResponseEntity.ok(topicos);
    }

    /**
     * Lista os tópicos usando filtros opcionais (nome do curso e ano de criação do tópico)
     * Se não for fornecido os parâmetros de filtragem a consulta será igual a {@link TopicoController#listarTopicos(Pageable)}.
     *
     * @param filtros  aceita os parâmetros {@code curso} ou {@code ano} na requisição
     * @param pageable parâmetros de paginação opcionais
     * @return {@code ResponseEntity<Page<DadosListagemTopico>>} tópicos filtrados e paginados
     * @throws IllegalArgumentException se for fornecido na requisição um parâmetro diferente de {@code curso} ou {@code ano}
     *                                  ou se fornecer um {@code ano} com valor negativo
     */
    @GetMapping("/filtros")
    public ResponseEntity<Page<DadosListagemTopico>> listarTopicosFiltrados(
            @RequestParam Map<String, String> filtros,
            @PageableDefault(size = 10, sort = "dataCriacao", direction = Sort.Direction.ASC) Pageable pageable) {

        List<String> filtrosPermitidos = Arrays.asList("curso", "ano");
        for (String key : filtros.keySet()) {
            if (!filtrosPermitidos.contains(key)) {
                throw new IllegalArgumentException("Parâmetro não permitido: " + key);
            }
        }

        String curso = filtros.get("curso");
        Integer ano = filtros.containsKey("ano") ? Integer.valueOf(filtros.get("ano")) : null;
        var topicos = topicoService.listarTopicosComFiltro(pageable, curso, ano);
        return ResponseEntity.ok(topicos);
    }

    /**
     * Detalhar tópico pelo id fornecido.
     *
     * @param id id do tópico
     * @return {@code ResponseEntity<DadosDetalhamentoTopico>} Detalhes do tópico.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoTopico> detalharTopico(@PathVariable Long id) {
        var topico = topicoService.detalharTopico(id);
        return ResponseEntity.ok(topico);
    }
}
