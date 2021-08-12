package br.com.zupacademy.mercadolivre.components;

import br.com.zupacademy.mercadolivre.components.impl.Send;
import br.com.zupacademy.mercadolivre.domains.Pergunta;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class EmailFake implements Send {

    @Override
    public void enviarEmail(Pergunta pergunta) {
        String remetente = pergunta.getUsuario().getLogin();
        String destino = pergunta.getProduto().getUsuario().getLogin();
        String assunto = "ML - Tem uma pergunta esperando por sua resposta";
        String textoDoEmail = pergunta.getTitulo();

        System.out.println("EmailFake{" +
                "remetente='" + remetente + '\'' +
                ", destino='" + destino + '\'' +
                ", assunto='" + assunto + '\'' +
                ", textoDoEmail='" + textoDoEmail + '\'' +
                '}');
    }
}
