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

        // Verifica se o usuário tem uma foto de perfil, caso contrário, define um placeholder
        String fotoPerfil = usuarioLogado.getFotoPerfil() != null ? usuarioLogado.getFotoPerfil() : "/images/default-profile.png";
        model.addAttribute("fotoPerfil", fotoPerfil);
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
            String diretorio = "uploads/";
            String nomeArquivo = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File imagem = new File(diretorio + nomeArquivo);

            // Cria o diretório se não existir
            if (!imagem.getParentFile().exists()) {
                imagem.getParentFile().mkdirs();
            }

            // Salva o arquivo no servidor
            file.transferTo(imagem);

            // Atualiza o caminho da imagem no perfil do usuário
            usuarioLogado.setFotoPerfil("/uploads/" + nomeArquivo);
            usuarioService.atualizarUsuario(usuarioLogado);
            
            System.out.println("Salvando imagem em: " + imagem.getAbsolutePath());
        }

        return "redirect:/perfil"; // Redireciona de volta para o perfil
    }

    // Salva a bio do usuário
    @PostMapping("/salvarBio")
    public String salvarBio(@RequestParam("bio") String bio,
                            @SessionAttribute(name = "usuarioLogado", required = false) Usuario usuarioLogado) {
        if (usuarioLogado == null) {
            return "redirect:/login";
        }

        // Atualiza a bio do usuário
        usuarioLogado.setBio(bio);
        usuarioService.atualizarUsuario(usuarioLogado);

        return "redirect:/perfil"; // Redireciona de volta para o perfil
    }
}
