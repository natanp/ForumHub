package br.com.alura.challenge.forumhub.domain.topico.validacoes;

import br.com.alura.challenge.forumhub.domain.curso.CursoRepository;
import br.com.alura.challenge.forumhub.domain.exception.ValidacaoException;
import br.com.alura.challenge.forumhub.domain.topico.DadosAtualizacaoTopico;
import br.com.alura.challenge.forumhub.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorAtualizacaoTopicoComCurso implements ValidadorTopico<DadosAtualizacaoTopico> {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Override
    public void validar(DadosAtualizacaoTopico dados) {
        if (!cursoRepository.existsById(dados.idCurso())) {
            throw new ValidacaoException("Não foi possível atualizar o tópico, pois Id do curso fornecido não existe!");
        }
    }
}
