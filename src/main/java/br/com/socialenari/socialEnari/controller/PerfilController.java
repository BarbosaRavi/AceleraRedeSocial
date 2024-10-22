package br.com.socialenari.socialEnari.controller;

import br.com.socialenari.socialEnari.model.Usuario;
import br.com.socialenari.socialEnari.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/perfil")
public class PerfilController {

    @Autowired
    private UsuarioService usuarioService;

    // Página de perfil do usuário
    @GetMapping
    public String mostrarPerfil(@SessionAttribute(name = "usuarioLogado", required = false) Usuario usuarioLogado, Model model) {
        if (usuarioLogado == null) {
            return "redirect:/login"; // Redireciona para o login se não estiver logado
        }

        model.addAttribute("usuario", usuarioLogado); // Passa o usuário logado para o template
        return "perfil"; // Retorna a página de perfil
    }

    // Atualiza a foto de perfil
    @PostMapping("/atualizarFoto")
    public String atualizarFotoPerfil(@RequestParam("fotoPerfil") MultipartFile file, 
                                      @SessionAttribute(name = "usuarioLogado", required = false) Usuario usuarioLogado) throws IOException {
        if (usuarioLogado == null) {
            return "redirect:/login";
        }

        if (!file.isEmpty()) {
            // Salva o arquivo na pasta 'static/images' (ou qualquer pasta configurada para imagens)
            String caminhoImagem = "static/images/" + file.getOriginalFilename();
            File imagem = new File(caminhoImagem);
            file.transferTo(imagem); // Salva o arquivo no servidor

            // Atualiza o caminho da imagem no perfil do usuário
            usuarioService.atualizarFotoPerfil(usuarioLogado, "/images/" + file.getOriginalFilename());
        }

        return "redirect:/perfil"; // Redireciona de volta para o perfil
    }
}
