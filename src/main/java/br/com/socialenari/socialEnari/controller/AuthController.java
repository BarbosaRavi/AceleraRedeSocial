package br.com.socialenari.socialEnari.controller;

import br.com.socialenari.socialEnari.model.Usuario;
import br.com.socialenari.socialEnari.service.PublicacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private PublicacaoService publicacaoService;

    @ModelAttribute("usuarioLogado")
    public Usuario setUpUsuarioLogado() {
        return null; // Inicializa como null
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        model.asMap().remove("usuarioLogado"); // Remove o usuário da sessão
        publicacaoService.setUsuarioLogado(null); // Limpa o usuário no serviço também
        return "redirect:/login"; // Redireciona para a página de login
    }
}
