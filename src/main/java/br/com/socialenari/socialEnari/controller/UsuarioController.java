package br.com.socialenari.socialEnari.controller;

import br.com.socialenari.socialEnari.model.Usuario;
import br.com.socialenari.socialEnari.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    private final Map<String, Usuario> usuariosCadastrados = new HashMap<>();

    @GetMapping("/cadastro")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String registrarUsuario(@ModelAttribute Usuario usuario, Model model) {
        if (usuarioService.validarIdade(usuario)) {
            usuariosCadastrados.put(usuario.getEmail(), usuario);
            model.addAttribute("mensagem", "Usuário cadastrado com sucesso!");
        } else {
            model.addAttribute("mensagem", "Não tem idade suficiente para se cadastrar.");
        }
        return "cadastro";
    }

    @GetMapping("/login")
    public String exibirFormularioLogin(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute Usuario usuario, Model model) {
        Usuario usuarioCadastrado = usuariosCadastrados.get(usuario.getEmail());
        if (usuarioCadastrado != null && usuarioCadastrado.getSenha().equals(usuario.getSenha())) {
            return "redirect:/imagem";
        } else {
            model.addAttribute("mensagem", "Email e/ou senha incorretos.");
            return "login";
        }
    }

    @GetMapping("/imagem")
    public String exibirImagem(Model model) {
        model.addAttribute("imagem", "/6103.png");
        return "imagem";
    }
}
