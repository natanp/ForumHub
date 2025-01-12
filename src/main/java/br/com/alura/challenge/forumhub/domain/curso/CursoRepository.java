package br.com.alura.challenge.forumhub.domain.curso;

import br.com.alura.challenge.forumhub.domain.topico.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}
