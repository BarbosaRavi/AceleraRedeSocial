package br.com.socialenari.socialEnari.controller;

import br.com.socialenari.socialEnari.model.Usuario;
import br.com.socialenari.socialEnari.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.UUID;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/adicionarAmigo")
    public String adicionarAmigo(@RequestParam UUID usuarioId, Principal principal) {
        Usuario usuarioSolicitante = usuarioService.buscarPorEmail(principal.getName());
        Usuario usuarioDestino = usuarioService.buscarPorId(usuarioId);

        usuarioService.enviarSolicitacaoDeAmizade(usuarioDestino.getId(), usuarioSolicitante);

        return "redirect:/perfil"; // Esta linha pode ser removida se você não quiser redirecionar após o envio
    }

    @PostMapping("/aceitarAmigo")
    public String aceitarAmigo(@RequestParam UUID solicitanteId, Principal principal) {
        Usuario usuarioAtual = usuarioService.buscarPorEmail(principal.getName());
        usuarioService.aceitarSolicitacaoDeAmizade(solicitanteId, usuarioAtual);

        return "redirect:/perfil"; // Esta linha pode ser removida se você não quiser redirecionar após a aceitação
    }
}
