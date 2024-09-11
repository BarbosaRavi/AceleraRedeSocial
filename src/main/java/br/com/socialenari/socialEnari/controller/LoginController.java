package br.com.socialenari.socialEnari.controller;

import br.com.socialenari.socialEnari.model.Usuario;
import br.com.socialenari.socialEnari.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String exibirFormularioLogin(Model model) {
        model.addAttribute("usuario", new Usuario(null, null, null, null, null));
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute Usuario usuario, Model model) {
        Usuario usuarioCadastrado = usuarioService.buscarPorEmail(usuario.getEmail());
        if (usuarioCadastrado != null && usuarioCadastrado.getSenha().equals(usuario.getSenha())) {
            return "redirect:/imagem";
        }

        model.addAttribute("mensagem", "Email e/ou senha incorretos.");
        return "login";
    }
}
