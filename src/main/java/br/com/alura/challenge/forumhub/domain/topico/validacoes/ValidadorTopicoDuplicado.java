package br.com.alura.challenge.forumhub.domain.topico.validacoes;

import br.com.alura.challenge.forumhub.domain.exception.ValidacaoException;
import br.com.alura.challenge.forumhub.domain.topico.DadosCadastroTopico;
import br.com.alura.challenge.forumhub.domain.topico.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorTopicoDuplicado implements ValidadorTopico<DadosCadastroTopico> {

    @Autowired
    private TopicoRepository topicoRepository;

    @Override
    public void validar(DadosCadastroTopico topico) {
        if (topicoRepository.existsByTituloAndMensagemIgnoreCase(topico.titulo(), topico.mensagem())) {
            throw new ValidacaoException("Não é permitido tópicos duplicados, já existe um tópico com esse título e conteúdo");
        }
    }
}
