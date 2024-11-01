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

    // Página de perfil do usuário logado
    @GetMapping
    public String mostrarPerfil(@SessionAttribute(name = "usuarioLogado", required = false) Usuario usuarioLogado, Model model) {
        if (usuarioLogado == null) {
            return "redirect:/login"; // Redireciona para o login se não estiver logado
        }

        String fotoPerfil = usuarioLogado.getFotoPerfil() != null ? usuarioLogado.getFotoPerfil() : "/images/default-profile.png";
        model.addAttribute("fotoPerfil", fotoPerfil);
        model.addAttribute("usuario", usuarioLogado); // Passa o usuário logado para o template

        return "perfil"; // Retorna a página de perfil
    }

    // Exibe o perfil de outro usuário específico (pelo ID)
    @GetMapping("/{id}")
    public String mostrarPerfilOutroUsuario(@PathVariable("id") UUID id, Model model) {
        Usuario usuario = usuarioService.buscarPorId(id);

        if (usuario == null) {
            return "redirect:/"; // Redireciona para a página principal se o usuário não existir
        }

        String fotoPerfil = usuario.getFotoPerfil() != null ? usuario.getFotoPerfil() : "/images/default-profile.png";
        model.addAttribute("fotoPerfil", fotoPerfil);
        model.addAttribute("usuario", usuario); // Passa o usuário para o template

        return "perfil"; // Retorna a página de perfil do outro usuário
    }

    // Atualiza a foto de perfil e a bio do usuário logado
    @PostMapping("/salvarBio")
    public String salvarBio(@RequestParam("bio") String bio,
                            @RequestParam(value = "fotoPerfil", required = false) MultipartFile file,
                            @SessionAttribute(name = "usuarioLogado", required = false) Usuario usuarioLogado) throws IOException {
        if (usuarioLogado == null) {
            return "redirect:/login";
        }

        // Atualiza a bio do usuário
        usuarioLogado.setBio(bio);

        // Se uma nova foto for enviada, atualiza a foto de perfil
        if (file != null && !file.isEmpty()) {
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
        }

        usuarioService.atualizarUsuario(usuarioLogado);
        return "redirect:/perfil"; // Redireciona de volta para o perfil
    }
}
