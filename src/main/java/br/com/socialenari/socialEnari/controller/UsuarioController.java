package br.com.socialenari.socialEnari.controller;

import br.com.socialenari.socialEnari.model.Usuario;
import br.com.socialenari.socialEnari.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    private final List<Usuario> usuariosCadastrados = new ArrayList<>();

    @GetMapping("/cadastro")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String registrarUsuario(@ModelAttribute Usuario usuario, Model model) {
        if (!usuario.getSenha().equals(usuario.getConfirmeSenha())) {
            model.addAttribute("mensagem", "As senhas não conferem.");
            return "cadastro";
        }

        if (usuarioService.validarIdade(usuario)) {
            usuariosCadastrados.add(usuario);
            model.addAttribute("mensagem", "Usuário cadastrado com sucesso!");
            return "redirect:/login";
        } else {
            model.addAttribute("mensagem", "Não tem idade suficiente para se cadastrar.");
            return "cadastro";
        }
    }

    @GetMapping("/login")
    public String exibirFormularioLogin(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute Usuario usuario, Model model) {
        System.out.println("Tentando login para: " + usuario.getEmail());
        for (Usuario usuarioCadastrado : usuariosCadastrados) {
            if (usuarioCadastrado.getEmail().trim().equals(usuario.getEmail().trim()) &&
                usuarioCadastrado.getSenha().trim().equals(usuario.getSenha().trim())) {
                System.out.println("Login bem-sucedido para: " + usuario.getEmail());
                return "redirect:/imagem";
            }
        }
        System.out.println("Falha no login para: " + usuario.getEmail());
        model.addAttribute("mensagem", "Email e/ou senha incorretos.");
        return "login";
    }

    @GetMapping("/imagem")
    public String exibirImagem(Model model) {
        model.addAttribute("imagem", "/6103.png");
        return "imagem";
    }
}
