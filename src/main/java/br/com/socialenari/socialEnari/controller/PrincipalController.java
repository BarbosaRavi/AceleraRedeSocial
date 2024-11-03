package br.com.socialenari.socialEnari.controller;

import br.com.socialenari.socialEnari.model.Usuario;
import br.com.socialenari.socialEnari.service.PublicacaoService;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class PrincipalController {

    private final PublicacaoService publicacaoService;

    public PrincipalController(PublicacaoService publicacaoService) {
        this.publicacaoService = publicacaoService; // Injeta o serviço no controlador
    }

    @GetMapping("/principal")
    public String exibirPaginaPrincipal(@SessionAttribute("usuarioLogado") Usuario usuarioLogado, Model model) {
        List<Usuario> amigos = usuarioLogado.getAmigos(); // Obtenha a lista de amigos

        model.addAttribute("usuario", usuarioLogado); // Adiciona o usuário logado ao modelo
        model.addAttribute("amigos", amigos); // Adiciona a lista de amigos ao modelo
        model.addAttribute("publicacoes", publicacaoService.obterPublicacoes()); // Usa o serviço injetado para obter publicações

        return "principal"; // Carrega o template HTML principal
    }
}
