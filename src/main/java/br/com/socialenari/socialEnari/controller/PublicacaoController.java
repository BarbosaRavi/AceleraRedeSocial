package br.com.socialenari.socialEnari.controller;

import br.com.socialenari.socialEnari.model.Like;
import br.com.socialenari.socialEnari.model.Usuario;
import br.com.socialenari.socialEnari.service.PublicacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publicacoes")
public class PublicacaoController {

    @Autowired
    private PublicacaoService publicacaoService;

    @PostMapping
    public ResponseEntity<String> criarPublicacao(@RequestBody String conteudo, 
                                                  @SessionAttribute(name = "usuarioLogado", required = false) Usuario usuarioLogado) {
        if (usuarioLogado == null) {
            return ResponseEntity.status(401).body("Usuário não autenticado.");
        }

        publicacaoService.criarPublicacao(usuarioLogado, conteudo); // Passando o objeto Usuario
        return ResponseEntity.ok("Publicação criada com sucesso.");
    }

    @GetMapping
    public ResponseEntity<List<String>> listarPublicacoes() {
        List<String> publicacoes = publicacaoService.formatarPublicacoes();
        return ResponseEntity.ok(publicacoes);
    }

    @PostMapping("/{id}/curtir")
    public ResponseEntity<String> curtirPublicacao(@PathVariable int id, 
                                                   @SessionAttribute(name = "usuarioLogado", required = false) Usuario usuarioLogado) {
        if (usuarioLogado == null) {
            return ResponseEntity.status(401).body("Usuário não autenticado.");
        }

        publicacaoService.curtirPublicacao(id, usuarioLogado.getNome()); // Passando o nome do usuário
        return ResponseEntity.ok("Publicação curtida/descurtida com sucesso.");
    }

    @GetMapping("/{id}/likes")
    public ResponseEntity<List<Like>> listarCurtidas(@PathVariable int id) {
        List<Like> curtidas = publicacaoService.getLikes(id);
        return ResponseEntity.ok(curtidas); // Retorna a lista de curtidas
    }
}
