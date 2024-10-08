package br.com.socialenari.socialEnari.controller;

import br.com.socialenari.socialEnari.model.Publicacao;
import br.com.socialenari.socialEnari.model.Usuario;
import br.com.socialenari.socialEnari.service.PublicacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PerfilController {

    private Map<String, String> usuariosBio = new HashMap<>();
    private Map<String, String> usuariosFoto = new HashMap<>();

    @Autowired
    private PublicacaoService publicacaoService; // Injete o serviço de publicações

    @GetMapping("/perfil")
    public String perfil(Model model, @SessionAttribute(name = "usuarioLogado", required = false) Usuario usuarioLogado) {
        if (usuarioLogado == null) {
            return "redirect:/login"; // Redireciona para a página de login se o usuário não estiver logado
        }

        String nomeUsuario = usuarioLogado.getNome();
        String bio = usuariosBio.getOrDefault(nomeUsuario, "Escreva sua bio...");
        String fotoPerfil = usuariosFoto.getOrDefault(nomeUsuario, "https://cdn-icons-png.flaticon.com/512/17/17004.png");
        
        // Obter as publicações do usuário
        List<Publicacao> publicacoes = publicacaoService.obterPublicacoesPorUsuario(nomeUsuario);

        model.addAttribute("nomeUsuario", nomeUsuario);
        model.addAttribute("bio", bio);
        model.addAttribute("fotoPerfil", fotoPerfil);
        model.addAttribute("publicacoes", publicacoes); // Adiciona as publicações ao modelo
        
        return "perfil"; // Renderiza a página perfil.html
    }

    @PostMapping("/perfil/salvarBio")
    public String salvarBio(@RequestParam("bio") String novaBio, @SessionAttribute(name = "usuarioLogado", required = false) Usuario usuarioLogado) {
        if (usuarioLogado == null) {
            return "redirect:/login"; // Redireciona para a página de login se o usuário não estiver logado
        }

        String nomeUsuario = usuarioLogado.getNome();
        usuariosBio.put(nomeUsuario, novaBio);
        return "redirect:/perfil?success=true";
    }

    @PostMapping("/perfil/uploadFoto")
    public String uploadFoto(@RequestParam("fotoPerfil") MultipartFile file, @SessionAttribute(name = "usuarioLogado", required = false) Usuario usuarioLogado) {
        if (usuarioLogado == null) {
            return "redirect:/login"; // Redireciona para a página de login se o usuário não estiver logado
        }

        String nomeUsuario = usuarioLogado.getNome();
        
        if (!file.isEmpty()) {
            try {
                String fotoUrl = "/imagens/perfil/" + file.getOriginalFilename();
                usuariosFoto.put(nomeUsuario, fotoUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return "redirect:/perfil?successUpload=true";
    }
}
