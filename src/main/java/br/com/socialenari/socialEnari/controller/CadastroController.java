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
public class CadastroController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/cadastro")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("usuario", new Usuario(null, null, null, null, null, null));
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String registrarUsuario(@ModelAttribute Usuario usuario, Model model) {
        if (!usuario.getSenha().equals(usuario.getConfirmeSenha())) {
            model.addAttribute("mensagem", "As senhas não conferem.");
            return "cadastro";
        }

        String mensagem = usuarioService.cadastrarUsuario(usuario);
        model.addAttribute("mensagem", mensagem);

        if (mensagem.equals("Usuário cadastrado com sucesso!")) {
        	System.out.println("Usuário criado: " + usuario.getNome() + " com UUID: " + usuario.getId());
            return "redirect:/login";
        }

        return "cadastro";
    }
}
