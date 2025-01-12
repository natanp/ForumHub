package br.com.alura.challenge.forumhub.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    boolean existsByTituloAndMensagemIgnoreCase(String titulo, String mensagem);

    boolean existsByTituloAndMensagemIgnoreCaseAndIdNot(String titulo, String mensagem,Long id);

    @Query("""
            SELECT topico FROM Topico topico
            JOIN topico.curso curso
            WHERE (:nomeCurso IS NULL OR UPPER(curso.nome) LIKE UPPER(CONCAT('%', :nomeCurso, '%')))
            AND (:ano IS NULL OR topico.dataCriacao BETWEEN :inicioAno AND :fimAno)
            """)
    Page<Topico> listarTopicosComFiltro(@Param("nomeCurso") String nomeCurso,
                                        @Param("ano") Integer ano,
                                        @Param("inicioAno") LocalDateTime inicioAno,
                                        @Param("fimAno") LocalDateTime fimAno,
                                        Pageable pageable);
}
