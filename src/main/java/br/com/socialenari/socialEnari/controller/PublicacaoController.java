package br.com.socialenari.socialEnari.controller;

import br.com.socialenari.socialEnari.model.Publicacao;
import br.com.socialenari.socialEnari.service.PublicacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/publicacoes")
public class PublicacaoController {

    @Autowired
    private PublicacaoService publicacaoService;

    @PostMapping(consumes = "text/plain")
    public void criarPublicacao(@RequestBody String conteudo) {
        if (conteudo == null || conteudo.isEmpty()) {
            throw new IllegalArgumentException("Conteúdo da publicação não pode estar vazio.");
        }

        Publicacao publicacao = new Publicacao();
        publicacao.setConteudo(conteudo);
        publicacaoService.adicionarPublicacao(publicacao);
    }

    @GetMapping
    public String obterPublicacoes() {
        return publicacaoService.obterPublicacoes();
    }
}
