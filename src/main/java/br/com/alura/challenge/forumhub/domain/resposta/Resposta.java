package br.com.alura.challenge.forumhub.domain.resposta;

import br.com.alura.challenge.forumhub.domain.topico.Topico;
import br.com.alura.challenge.forumhub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "respostas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensagem;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @ManyToOne(fetch = FetchType.LAZY)
    private Topico topico;

    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean solucao;

}
