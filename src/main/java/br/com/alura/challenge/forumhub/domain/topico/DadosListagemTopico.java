package br.com.alura.challenge.forumhub.domain.topico;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record DadosListagemTopico(
        Long id,
        String titulo,
        String mensagem,
        @JsonProperty("data_criacao") LocalDateTime dataCriacao,
        EnumStatusTopico status,
        @JsonProperty("autor") String usuario,
        @JsonProperty("curso") String curso) {

    public DadosListagemTopico(Topico topico) {
        this(topico.getId(),topico.getTitulo(), topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getStatus(),
                topico.getUsuario().getNome(),
                topico.getCurso().getNome());
    }


}
