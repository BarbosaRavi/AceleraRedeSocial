package br.com.socialenari.socialEnari.controller;

import br.com.socialenari.socialEnari.model.Publicacao;
import br.com.socialenari.socialEnari.model.Usuario;
import br.com.socialenari.socialEnari.service.PublicacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publicacoes")
public class PublicacaoController {

    @Autowired
    private PublicacaoService publicacaoService;

    // Método para autenticar o usuário
    @PostMapping("/login")
    public String login(@RequestBody Usuario usuario) {
        // Aqui você deve implementar a lógica de autenticação do usuário
        // Se a autenticação for bem-sucedida:
        publicacaoService.setUsuarioLogado(usuario); // Define o usuário logado
        return "Usuário logado com sucesso!";
    }

    // Método para criar uma nova publicação
    @PostMapping
    public String criarPublicacao(@RequestBody String conteudo) {
        try {
            publicacaoService.adicionarPublicacao(conteudo);
            return "Publicação criada com sucesso!";
        } catch (IllegalStateException e) {
            return e.getMessage(); // Retorna a mensagem de erro se o usuário não estiver logado
        }
    }

    // Método para obter todas as publicações
    @GetMapping
    public List<Publicacao> obterPublicacoes() {
        return publicacaoService.obterPublicacoes();
    }
}
