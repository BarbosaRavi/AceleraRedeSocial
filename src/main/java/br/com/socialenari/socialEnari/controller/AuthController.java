package br.com.socialenari.socialEnari.controller;

import br.com.socialenari.socialEnari.model.Usuario;
import br.com.socialenari.socialEnari.service.PublicacaoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final PublicacaoService publicacaoService;

    public AuthController(PublicacaoService publicacaoService) {
        this.publicacaoService = publicacaoService;
    }

    @PostMapping("/login")
    public String login(@RequestBody Usuario usuario) {
        publicacaoService.setUsuarioLogado(usuario); 
        return "Login bem-sucedido!";
    }
}
