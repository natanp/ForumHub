package br.com.alura.challenge.forumhub.infra.security.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

public record ApiErrorResponse(String status,
                               String mensagem,
                               String path,
                               LocalDateTime data_horario) {
    public ApiErrorResponse(HttpStatusCode status, Exception ex, HttpServletRequest request){
        this(status.toString(),ex.getMessage(),request.getRequestURI(),LocalDateTime.now());
    }

    public ApiErrorResponse(HttpStatusCode status, String mensagem, HttpServletRequest request){
        this(status.toString(),mensagem,request.getRequestURI(),LocalDateTime.now());
    }

    public ApiErrorResponse(ResponseStatusException ex, HttpServletRequest request){
        this(ex.getStatusCode().toString(),ex.getReason(),request.getRequestURI(),LocalDateTime.now());
    }
}
