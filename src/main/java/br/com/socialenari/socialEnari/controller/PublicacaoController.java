package br.com.socialenari.socialEnari.controller;

import br.com.socialenari.socialEnari.model.Publicacao;
import br.com.socialenari.socialEnari.model.Usuario;
import br.com.socialenari.socialEnari.service.PublicacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publicacoes")
@SessionAttributes("usuarioLogado")
public class PublicacaoController {

    @Autowired
    private PublicacaoService publicacaoService;

    // Método para criar uma nova publicação
    @PostMapping(consumes = "text/plain")
    public ResponseEntity<String> criarPublicacao(
            @RequestBody String conteudo, 
            @SessionAttribute(name = "usuarioLogado", required = false) Usuario usuarioLogado) {

        if (usuarioLogado == null) {
            return ResponseEntity.status(401).body("Usuário não autenticado.");
        }

        if (conteudo == null || conteudo.isEmpty()) {
            return ResponseEntity.badRequest().body("Conteúdo da publicação não pode estar vazio.");
        }

        Publicacao publicacao = new Publicacao();
        publicacao.setConteudo(conteudo);
        publicacao.setUsuario(usuarioLogado.getNome());
        publicacaoService.adicionarPublicacao(publicacao);

        return ResponseEntity.ok("Publicação criada com sucesso.");
    }

    // Método para buscar todas as publicações
    @GetMapping(produces = "text/plain")
    public ResponseEntity<String> getTodasPublicacoes() {
        List<Publicacao> publicacoes = publicacaoService.getTodasPublicacoes();

        if (publicacoes.isEmpty()) {
            return ResponseEntity.ok("Nenhuma publicação encontrada.");
        }

        StringBuilder publicacoesString = new StringBuilder();
        for (Publicacao publicacao : publicacoes) {
            publicacoesString.append(publicacao.getUsuario())
                    .append(": ")
                    .append(publicacao.getConteudo())
                    .append(" (")
                    .append(publicacao.getDataHora().toString())
                    .append(") - ")
                    .append(publicacao.getCurtidas())
                    .append(" curtidas\n");
        }

        return ResponseEntity.ok(publicacoesString.toString());
    }

    // Método para curtir uma publicação
    @PostMapping("/{index}/curtir")
    public ResponseEntity<String> curtirPublicacao(@PathVariable int index) {
        boolean sucesso = publicacaoService.curtirPublicacao(index);

        if (sucesso) {
            return ResponseEntity.ok("Publicação curtida com sucesso.");
        } else {
            return ResponseEntity.status(404).body("Publicação não encontrada.");
        }
    }
}
