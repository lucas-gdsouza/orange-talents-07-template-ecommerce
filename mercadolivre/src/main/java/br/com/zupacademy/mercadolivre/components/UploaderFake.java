package br.com.zupacademy.mercadolivre.components;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.zupacademy.mercadolivre.components.impl.Uploader;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Primary
public class UploaderFake implements Uploader {
    /**
     * @param imagens
     * @return links para imagens que foram uploadadas - Atenção: é um retorno fake.
     */
    @Override
    public Set<String> envia(List<MultipartFile> imagens) {

        return imagens.stream()
                .map(imagem -> "http://bucket.io/"
                        + imagem.getOriginalFilename())
                .collect(Collectors.toSet());
    }
}
