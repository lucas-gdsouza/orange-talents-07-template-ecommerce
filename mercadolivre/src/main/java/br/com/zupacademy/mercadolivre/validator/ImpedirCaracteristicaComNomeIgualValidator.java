package br.com.zupacademy.mercadolivre.validator;


import br.com.zupacademy.mercadolivre.dto.requests.CadastroProdutoRequest;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Set;

public class ImpedirCaracteristicaComNomeIgualValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return CadastroProdutoRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        CadastroProdutoRequest request = (CadastroProdutoRequest) o;

        Set<String> nomesIguais = request.buscaCaracaracteristicasIguais();

        if (!nomesIguais.isEmpty()) {
            errors.reject("Características",
                    null,
                    "Houve a tentativa de adicionar uma característica que já existe. ");
        }
    }
}