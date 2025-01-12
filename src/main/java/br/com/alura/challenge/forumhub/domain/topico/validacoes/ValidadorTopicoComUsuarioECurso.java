package br.com.alura.challenge.forumhub.domain.topico.validacoes;

import br.com.alura.challenge.forumhub.domain.curso.CursoRepository;
import br.com.alura.challenge.forumhub.domain.exception.ValidacaoException;
import br.com.alura.challenge.forumhub.domain.topico.DadosCadastroTopico;
import br.com.alura.challenge.forumhub.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorTopicoComUsuarioECurso implements ValidadorTopico<DadosCadastroTopico> {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Override
    public void validar(DadosCadastroTopico dados) {
        if (!usuarioRepository.existsById(dados.idUsuario())) {
            throw new ValidacaoException("Não foi possível criar um novo tópico, pois Id do usuário fornecido não existe!");
        }
        if (!cursoRepository.existsById(dados.idCurso())) {
            throw new ValidacaoException("Não foi possível criar um novo tópico, pois Id do curso fornecido não existe!");
        }
    }
}
