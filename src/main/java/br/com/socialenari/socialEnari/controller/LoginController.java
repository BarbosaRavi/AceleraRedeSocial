package br.com.socialenari.socialEnari.controller;

import br.com.socialenari.socialEnari.model.Usuario;
import br.com.socialenari.socialEnari.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("usuarioLogado")
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String exibirFormularioLogin(Model model) {
        model.addAttribute("usuario", new Usuario(null, null, null, null, null));
        return "login"; // Retorna a página de login
    }

    @PostMapping("/login")
    public String login(@ModelAttribute Usuario usuario, Model model) {
        Usuario usuarioCadastrado = usuarioService.buscarPorEmail(usuario.getEmail());
        if (usuarioCadastrado != null && usuarioCadastrado.getSenha().equals(usuario.getSenha())) {
            model.addAttribute("usuarioLogado", usuarioCadastrado); // Armazenar usuário logado
            return "redirect:/principal"; // Redireciona para a página principal
        }

        model.addAttribute("mensagem", "Email e/ou senha incorretos."); // Mensagem de erro
        return "login"; // Retorna para a página de login em caso de falha
    }
}
