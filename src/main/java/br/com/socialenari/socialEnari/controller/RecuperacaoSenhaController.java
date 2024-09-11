package br.com.socialenari.socialEnari.controller;

import br.com.socialenari.socialEnari.model.Usuario;
import br.com.socialenari.socialEnari.service.EmailService;
import br.com.socialenari.socialEnari.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RecuperacaoSenhaController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/recuperacao")
    public String exibirFormularioRecuperacao() {
        return "recuperacao";
    }

    @PostMapping("/recuperacao")
    public String recuperarSenha(@RequestParam("email") String email, Model model) {
        Usuario usuario = usuarioService.buscarPorEmail(email);
        if (usuario != null) {
            // Gerar link de recuperação, por exemplo, usando um token ou link único
            String linkRecuperacao = "http://localhost:8080/recuperacao/" + usuario.getId(); // Exemplo de link

            String assunto = "Recuperação de Senha";
            String corpo = "Clique no link a seguir para recuperar sua senha: " + linkRecuperacao;

            emailService.enviarEmailRecuperacao(email, assunto, corpo);
            model.addAttribute("mensagem", "Instruções para recuperação de senha foram enviadas para o seu e-mail.");
        } else {
            model.addAttribute("mensagem", "Email não encontrado.");
        }
        return "recuperacao";
    }
}
