package br.com.alura.challenge.forumhub.domain.topico.validacoes;

public interface ValidadorTopico<T> {
    void validar(T topico);
}
