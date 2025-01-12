package br.com.alura.challenge.forumhub.domain.topico;

import br.com.alura.challenge.forumhub.domain.curso.Curso;
import br.com.alura.challenge.forumhub.domain.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record DadosDetalhamentoTopico(
        Long id,
        String titulo,
        String mensagem,
        @JsonProperty("data_criacao") LocalDateTime dataCriacao,
        EnumStatusTopico status,
        DadosDetalhamentoTopicoUsuario usuario,
        DadosDetalhamentoTopicoCurso curso) {

    public DadosDetalhamentoTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getStatus(),
                new DadosDetalhamentoTopicoUsuario(topico.getUsuario()),
                new DadosDetalhamentoTopicoCurso(topico.getCurso()));
    }

    private record DadosDetalhamentoTopicoUsuario(Long id, String nome) {
        public DadosDetalhamentoTopicoUsuario(Usuario usuario) {
            this(usuario.getId(), usuario.getNome());
        }
    }

    private record DadosDetalhamentoTopicoCurso(Long id, String nome, String categoria) {
        public DadosDetalhamentoTopicoCurso(Curso curso) {
            this(curso.getId(), curso.getNome(), curso.getCategoria());
        }
    }
}
