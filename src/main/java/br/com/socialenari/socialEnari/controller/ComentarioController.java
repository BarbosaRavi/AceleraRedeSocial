package br.com.socialenari.socialEnari.controller;

import br.com.socialenari.socialEnari.model.Comentario;
import br.com.socialenari.socialEnari.model.Usuario;
import br.com.socialenari.socialEnari.service.ComentarioService;
import br.com.socialenari.socialEnari.service.UsuarioService; // Importando o serviço de usuários
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/comentarios")
public class ComentarioController {
    private final ComentarioService comentarioService;
    private final UsuarioService usuarioService; // Adicionando o serviço de usuários

    public ComentarioController(ComentarioService comentarioService, UsuarioService usuarioService) {
        this.comentarioService = comentarioService;
        this.usuarioService = usuarioService; // Inicializando o serviço de usuários
    }

    @PostMapping("/adicionar")
    public String adicionarComentario(@RequestParam String conteudo, 
                                      @RequestParam int publicacaoId, 
                                      @RequestParam String usuarioEmail) {
        Usuario usuario = usuarioService.buscarPorEmail(usuarioEmail); // Busca o usuário pelo email

        if (usuario != null) {
            Comentario comentario = new Comentario(null, conteudo, usuario, publicacaoId);
            comentarioService.adicionarComentario(comentario);
            return "redirect:/publicacao/" + publicacaoId; // Redireciona para a publicação após adicionar o comentário
        } else {
            return "redirect:/publicacao/" + publicacaoId + "?erro=usuario_invalido"; // Redireciona com erro se o usuário não for encontrado
        }
    }

    @GetMapping("/{publicacaoId}")
    @ResponseBody
    public List<Comentario> listarComentarios(@PathVariable int publicacaoId) {
        return comentarioService.listarComentariosPorPublicacao(publicacaoId);
    }
}
