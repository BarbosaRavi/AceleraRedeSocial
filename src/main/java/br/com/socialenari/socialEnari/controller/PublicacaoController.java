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

    /**
     * Endpoint para criar uma nova publicação.
     * Recebe o conteúdo da publicação e o usuário autenticado da sessão.
     * Verifica se o usuário está logado, caso contrário retorna erro 401.
     */
    @PostMapping
    public ResponseEntity<String> criarPublicacao(@RequestBody String conteudo, @SessionAttribute(name = "usuarioLogado", required = false) Usuario usuarioLogado) {
        if (usuarioLogado == null) {
            return ResponseEntity.status(401).body("Usuário não autenticado.");
        }

        // Cria a publicação com o nome do usuário logado
        publicacaoService.criarPublicacao(usuarioLogado.getNome(), conteudo);
        return ResponseEntity.ok("Publicação criada com sucesso.");
    }

    /**
     * Endpoint para listar todas as publicações.
     * Retorna as publicações formatadas em texto simples.
     */
    @GetMapping
    public ResponseEntity<String> listarPublicacoes() {
        List<String> publicacoes = publicacaoService.formatarPublicacoes();
        return ResponseEntity.ok(String.join("\n", publicacoes)); // Retorna as publicações separadas por linha
    }

    /**
     * Endpoint para curtir uma publicação.
     * Recebe o ID da publicação a ser curtida e o usuário autenticado.
     * Verifica se o usuário está logado e se a publicação existe.
     */
    @PostMapping("/{id}/curtir")
    public ResponseEntity<String> curtirPublicacao(@PathVariable int id, @SessionAttribute(name = "usuarioLogado", required = false) Usuario usuarioLogado) {
        if (usuarioLogado == null) {
            return ResponseEntity.status(401).body("Usuário não autenticado.");
        }

        // Tenta curtir a publicação
        publicacaoService.curtirPublicacao(id, usuarioLogado.getNome());
        return ResponseEntity.ok("Publicação curtida/descurtida com sucesso.");
    }

    /**
     * Endpoint para descurtir uma publicação.
     * Similar ao curtir, mas remove a curtida, caso ela exista.
     */
    @PostMapping("/{id}/descurtir")
    public ResponseEntity<String> descurtirPublicacao(@PathVariable int id, @SessionAttribute(name = "usuarioLogado", required = false) Usuario usuarioLogado) {
        if (usuarioLogado == null) {
            return ResponseEntity.status(401).body("Usuário não autenticado.");
        }

        // Remove a curtida, se ela existir
        publicacaoService.curtirPublicacao(id, usuarioLogado.getNome());
        return ResponseEntity.ok("Publicação descurtida com sucesso.");
    }

    /**
     * Endpoint para listar os nomes dos usuários que curtiram uma publicação.
     * Recebe o ID da publicação e retorna a lista de usuários que curtiram.
     */
    @GetMapping("/{id}/likes")
    public ResponseEntity<List<Like>> listarCurtidas(@PathVariable int id) {
        List<Like> curtidas = publicacaoService.getLikes(id);
        return curtidas != null ? ResponseEntity.ok(curtidas) : ResponseEntity.status(404).build();
    }
}
