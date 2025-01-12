package br.com.alura.challenge.forumhub.domain.topico;

import br.com.alura.challenge.forumhub.domain.curso.Curso;
import br.com.alura.challenge.forumhub.domain.resposta.Resposta;
import br.com.alura.challenge.forumhub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "topicos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Topico {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensagem;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Enumerated(EnumType.STRING)
    private EnumStatusTopico status;

    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    private Curso curso;

    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resposta> respostas;

    public Topico(DadosCadastroTopico dados,Usuario usuario,Curso curso) {
        this.titulo = dados.titulo();
        this.mensagem = dados.mensagem();
        this.dataCriacao = LocalDateTime.now();
        this.status = EnumStatusTopico.ABERTO;
        this.usuario = usuario;
        this.curso = curso;
    }

    public void atualizarTopico(DadosAtualizacaoTopico dadosAtualizacaoTopico, Curso curso){
        this.titulo = dadosAtualizacaoTopico.titulo();
        this.mensagem = dadosAtualizacaoTopico.mensagem();
        this.status =  dadosAtualizacaoTopico.status();
        this.curso = curso;
    }
}
