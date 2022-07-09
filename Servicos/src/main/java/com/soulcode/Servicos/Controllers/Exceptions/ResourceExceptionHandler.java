package com.soulcode.Servicos.Controllers.Exceptions;

import com.soulcode.Servicos.Services.Exceptions.DataIntegrityViolationException;
import com.soulcode.Servicos.Services.Exceptions.EmailSendingFailedException;
import com.soulcode.Servicos.Services.Exceptions.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(EntityNotFoundException e, HttpServletRequest request) {
        StandardError erro = new StandardError();
        erro.setTimestamp(Instant.now());
        erro.setStatus(HttpStatus.NOT_FOUND.value());
        erro.setError("Registro não encontrado");
        erro.setMessage(e.getMessage());
        erro.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    public ResponseEntity<StandardError> dataIntegrityViolationException(DataIntegrityViolationException e, HttpServletRequest request) {
        StandardError erro = new StandardError();
        erro.setTimestamp(Instant.now());
        erro.setStatus(HttpStatus.CONFLICT.value());
        erro.setError("atributo não pode ser duplicado");
        erro.setMessage(e.getMessage());
        erro.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    @ExceptionHandler(EmailSendingFailedException.class)
    public ResponseEntity<StandardError> emailSendingFailedException(EmailSendingFailedException e, HttpServletRequest request) {
        StandardError erro = new StandardError();
        erro.setTimestamp(Instant.now());
        erro.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        erro.setError("Erro ao enviar email");
        erro.setMessage(e.getMessage());
        erro.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }
}
