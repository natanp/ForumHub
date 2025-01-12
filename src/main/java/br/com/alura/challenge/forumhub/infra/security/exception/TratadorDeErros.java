package br.com.alura.challenge.forumhub.infra.security.exception;

import br.com.alura.challenge.forumhub.domain.exception.ValidacaoException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity tratarErroResponseStatus(ResponseStatusException ex, HttpServletRequest request) {
        var apiErrorResponse = new ApiErrorResponse(ex, request);
        return ResponseEntity.status(ex.getStatusCode()).body(apiErrorResponse);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400Validacao(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<ApiErrorResponse> handleMissingPathVariableException(MissingPathVariableException ex,
                                                                     HttpServletRequest request,
                                                                     HttpServletResponse response) {
        return ResponseEntity.badRequest().body(new ApiErrorResponse(HttpStatus.BAD_REQUEST,"Parâmetro ausente: " + ex.getVariableName(),request));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpServletRequest request) {
        return ResponseEntity.badRequest().body(new ApiErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Erro ao processar a requisição. Verifique os campos e valores enviados.",
                request));
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<ApiErrorResponse> tratarErroRegraDeNegocio(ValidacaoException ex,HttpServletRequest request) {
        return ResponseEntity.badRequest().body(new ApiErrorResponse(HttpStatus.BAD_REQUEST,ex.getMessage(),request));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity tratarErroBadCredentials() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity tratarErroAuthentication() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha na autenticação");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity tratarErroAcessoNegado() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado");
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiErrorResponse> handleMissingParams(MissingServletRequestParameterException ex,HttpServletRequest request) {
        return ResponseEntity.badRequest().body(new ApiErrorResponse(HttpStatus.BAD_REQUEST,"Parâmetro ausente: " + ex.getParameterName(),request));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidParam(MethodArgumentTypeMismatchException ex,HttpServletRequest request) {
        return ResponseEntity.badRequest().body(new ApiErrorResponse(HttpStatus.BAD_REQUEST,
                "Parâmetro inválido: " + ex.getValue(),request));

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> tratarErro500(Exception ex, HttpServletRequest request) {
        return ResponseEntity.internalServerError().body(new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro: " + ex.getLocalizedMessage(),request));
    }

    private record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }
}
