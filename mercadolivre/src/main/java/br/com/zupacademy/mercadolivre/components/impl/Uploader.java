package br.com.zupacademy.mercadolivre.components.impl;

import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

public interface Uploader {

    /**
     * @param imagens
     * @return links para imagens que foram uploadadas
     */
    Set<String> envia(List<MultipartFile> imagens);
}