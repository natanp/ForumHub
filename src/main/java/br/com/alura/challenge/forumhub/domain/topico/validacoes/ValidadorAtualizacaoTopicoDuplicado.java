package br.com.alura.challenge.forumhub.domain.topico.validacoes;

import br.com.alura.challenge.forumhub.domain.exception.ValidacaoException;
import br.com.alura.challenge.forumhub.domain.topico.DadosAtualizacaoTopico;
import br.com.alura.challenge.forumhub.domain.topico.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorAtualizacaoTopicoDuplicado implements ValidadorTopico<DadosAtualizacaoTopico> {

    @Autowired
    private TopicoRepository topicoRepository;

    @Override
    public void validar(DadosAtualizacaoTopico topico) {
        if (topicoRepository.existsByTituloAndMensagemIgnoreCaseAndIdNot(topico.titulo(), topico.mensagem(),topico.id())) {
            throw new ValidacaoException("Não é permitido tópicos duplicados, já existe outro tópico com esse título e conteúdo");
        }
    }
}
