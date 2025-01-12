package br.com.alura.challenge.forumhub.domain.topico;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DadosCadastroTopico (
        @NotBlank(message = "{Topico.titulo.NotBlank}")
        String titulo,

        @NotBlank(message = "{Topico.mensagem.NotBlank}") @Size(max = 5000, message = "{Topico.mensagem.Size}")
        String mensagem,

        @NotNull(message = "{Topico.usuario.NotNull}") @JsonProperty("usuario_id")
        Long idUsuario,

        @NotNull(message = "{Topico.curso.NotNull}") @JsonProperty("curso_id")
        Long idCurso
) {

}
