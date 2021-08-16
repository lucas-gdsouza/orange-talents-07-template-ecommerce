package br.com.zupacademy.mercadolivre.components;

import br.com.zupacademy.mercadolivre.components.impl.Mailer;
import br.com.zupacademy.mercadolivre.domains.Compra;
import br.com.zupacademy.mercadolivre.domains.Pergunta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Emails {
    @Autowired
    private Mailer mailer;

    public void novaPergunta(Pergunta pergunta) {
        mailer.send("<html>...</html>", "Responda a Pergunta!!", pergunta.getUsuario().getLogin(),
                "emailmercadolivre@mercadolivre.com.br",
                pergunta.getProduto().getUsuario().getLogin());
    }

    public void novaCompra(Compra novaCompra) {
        mailer.send("nova compra..." + novaCompra, "Nova compra realizada!",
                novaCompra.getComprador().getLogin(),
                "emaildecompras@mercadolivre.com.br",
                novaCompra.getComprador().getLogin());
    }
}