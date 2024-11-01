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
import java.util.UUID;

@Controller
@RequestMapping("/perfil")
public class PerfilController {

    @Autowired
    private UsuarioService usuarioService;

    // Exibe o perfil do usuário logado
    @GetMapping
    public String mostrarPerfil(@SessionAttribute(name = "usuarioLogado", required = false) Usuario usuarioLogado, Model model) {
        if (usuarioLogado == null) {
            return "redirect:/login"; 
        }

        String fotoPerfil = usuarioLogado.getFotoPerfil() != null ? usuarioLogado.getFotoPerfil() : "/images/default-profile.png";
        model.addAttribute("fotoPerfil", fotoPerfil);
        model.addAttribute("usuario", usuarioLogado);

        return "perfil";
    }

    // Exibe o perfil de outro usuário pelo UUID
    @GetMapping("/{id}")
    public String mostrarPerfilOutroUsuario(@PathVariable("id") UUID id, Model model) {
        Usuario usuario = usuarioService.buscarPorId(id);

        if (usuario == null) {
            return "redirect:/";  // Redireciona se o usuário não for encontrado
        }

        String fotoPerfil = usuario.getFotoPerfil() != null ? usuario.getFotoPerfil() : "/images/default-profile.png";
        model.addAttribute("fotoPerfil", fotoPerfil);
        model.addAttribute("usuario", usuario); 

        return "perfil";
    }

    // Atualiza a bio e a foto do perfil do usuário logado
    @PostMapping("/salvarBio")
    public String salvarBio(@RequestParam("bio") String bio,
                            @RequestParam(value = "fotoPerfil", required = false) MultipartFile file,
                            @SessionAttribute(name = "usuarioLogado", required = false) Usuario usuarioLogado) throws IOException {
        if (usuarioLogado == null) {
            return "redirect:/login";
        }

        usuarioLogado.setBio(bio);

        if (file != null && !file.isEmpty()) {
            String diretorio = "uploads/";
            String nomeArquivo = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File imagem = new File(diretorio + nomeArquivo);

            if (!imagem.getParentFile().exists()) {
                imagem.getParentFile().mkdirs();
            }

            file.transferTo(imagem);

            usuarioLogado.setFotoPerfil("/uploads/" + nomeArquivo);
        }

        usuarioService.atualizarUsuario(usuarioLogado);
        return "redirect:/perfil"; 
    }
}
