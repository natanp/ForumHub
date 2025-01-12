package br.com.alura.challenge.forumhub.domain.topico;

import br.com.alura.challenge.forumhub.domain.curso.Curso;
import br.com.alura.challenge.forumhub.domain.curso.CursoRepository;
import br.com.alura.challenge.forumhub.domain.topico.validacoes.ValidadorTopico;
import br.com.alura.challenge.forumhub.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private List<ValidadorTopico<DadosCadastroTopico>> validadoresCadastroTopico;
    @Autowired
    private List<ValidadorTopico<DadosAtualizacaoTopico>> validadoresAtualizacaoTopico;

    @Transactional
    public DadosDetalhamentoTopico cadastrarNovoTopico(DadosCadastroTopico dados) {
        validadoresCadastroTopico.forEach(v -> v.validar(dados));

        var usuarioAutor = usuarioRepository.getReferenceById(dados.idUsuario());
        var curso = cursoRepository.getReferenceById(dados.idCurso());

        var topico = new Topico(dados, usuarioAutor, curso);
        topicoRepository.save(topico);

        return new DadosDetalhamentoTopico(topico);
    }

    @Transactional
    public DadosDetalhamentoTopico atualizarTopico(DadosAtualizacaoTopico dadosAtualizacaoTopico) {

        validadoresAtualizacaoTopico.forEach(v -> v.validar(dadosAtualizacaoTopico));

        Optional<Topico> topico = topicoRepository.findById(dadosAtualizacaoTopico.id());
        Optional<Curso> curso = cursoRepository.findById(dadosAtualizacaoTopico.idCurso());
        if (topico.isPresent() && curso.isPresent()) {
            var topicoEncontrado = topico.get();
            var cursoEncontrado = curso.get();
            topicoEncontrado.atualizarTopico(dadosAtualizacaoTopico, cursoEncontrado);
            topicoRepository.save(topicoEncontrado);
            return new DadosDetalhamentoTopico(topicoEncontrado);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi encontrado tópico para atualizar com o id %d".formatted(dadosAtualizacaoTopico.id()));
        }
    }

    @Transactional
    public void deletarTopico(Long id) {

      Optional<Topico> topico = topicoRepository.findById(id);
        if (topico.isPresent()) {
            var topicoEncontrado = topico.get();
            topicoRepository.deleteById(topicoEncontrado.getId());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi encontrado tópico para deletar com o id %d".formatted(id));
        }
    }

    public Page<DadosListagemTopico> listarTopicos(Pageable page) {
        return topicoRepository.findAll(page).map(DadosListagemTopico::new);
    }

    public Page<DadosListagemTopico> listarTopicosComFiltro(Pageable page, String nomeCurso, Integer ano) {
        LocalDateTime inicioAno = null;
        LocalDateTime fimAno = null;

        if (ano != null) {
            if (ano < 0) {
                throw new IllegalArgumentException("O ano fornecido não pode ser negativo");
            }
            inicioAno = LocalDate.of(ano, Month.JANUARY, 1).atStartOfDay();
            fimAno = LocalDate.of(ano, Month.DECEMBER, 31).atTime(LocalTime.MAX);
        }

        return topicoRepository.listarTopicosComFiltro(nomeCurso, ano, inicioAno, fimAno, page).map(DadosListagemTopico::new);
    }

    public DadosDetalhamentoTopico detalharTopico(Long id) {
        return topicoRepository.findById(id).map(DadosDetalhamentoTopico::new)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi encontrado tópico com o id %d".formatted(id)));
    }
}
