package com.github.gabguedes.ms_aluno.controller.handlers;

import com.github.gabguedes.ms_aluno.dto.CustomErrorDTO;
import com.github.gabguedes.ms_aluno.dto.FieldMessageDTO;
import com.github.gabguedes.ms_aluno.dto.ValidationErrorDTO;
import com.github.gabguedes.ms_aluno.service.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomErrorDTO> resourceNotFound(ResourceNotFoundException e,
                                                           HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;

        CustomErrorDTO customErrorDTO = new CustomErrorDTO(Instant.now().toString(),
                status.value(), e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(customErrorDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorDTO> methodArgumentNotValidation(MethodArgumentNotValidException e,
                                                                          HttpServletRequest request){
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        ValidationErrorDTO validationErrorDTO = new ValidationErrorDTO(Instant.now().toString(),
                status.value(), "Dados inválidos", request.getRequestURI());

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()){
            validationErrorDTO.addError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(status).body(validationErrorDTO);
    }


}
