package br.com.zupacademy.mercadolivre.configurations.exceptions.handler;

import br.com.zupacademy.mercadolivre.configurations.exceptions.dto.GenericExceptionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GenericExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<GenericExceptionDTO> handler(MethodArgumentNotValidException exception) {

        List<GenericExceptionDTO> listDTOElements = new ArrayList<>();

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(e -> {
            String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            GenericExceptionDTO erro = new GenericExceptionDTO(e.getField(), mensagem);
            listDTOElements.add(erro);
        });

        return listDTOElements;
    }
}