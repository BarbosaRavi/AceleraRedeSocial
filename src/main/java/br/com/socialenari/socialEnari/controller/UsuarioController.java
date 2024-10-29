import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UsuarioController {

    // Método para exibir o perfil do usuário
    @GetMapping("/perfil/{id}")
    public String exibirPerfil(@PathVariable("id") Long id, Model model) {
        // Aqui você deve buscar o usuário pelo ID
        Usuario usuario = usuarioService.findById(id);
        
        // Verifique se o usuário existe
        if (usuario == null) {
            return "redirect:/404"; // Redirecionar para uma página 404 ou de erro
        }

        // Busque as publicações desse usuário
        List<Publicacao> publicacoes = publicacaoService.findByUsuarioId(id);

        // Adiciona os dados ao modelo
        model.addAttribute("usuario", usuario);
        model.addAttribute("publicacoes", publicacoes);
        model.addAttribute("fotoPerfil", usuario.getFotoPerfil());

        return "perfil_usuario"; // Retorne o nome da sua nova página HTML
    }
}
