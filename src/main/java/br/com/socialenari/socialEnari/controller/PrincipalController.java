package br.com.socialenari.socialEnari.controller;

import br.com.socialenari.socialEnari.model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class PrincipalController {

    @GetMapping("/principal")
    public String exibirPaginaPrincipal(@SessionAttribute("usuarioLogado") Usuario usuarioLogado, Model model) {
        model.addAttribute("nomeUsuario", usuarioLogado.getNome());
        return "principal";
    }
}
