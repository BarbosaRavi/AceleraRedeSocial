package br.com.socialenari.socialEnari.controller;

import br.com.socialenari.socialEnari.model.Comunidade;
import br.com.socialenari.socialEnari.model.PublicacaoComunidade;
import br.com.socialenari.socialEnari.model.Usuario;
import br.com.socialenari.socialEnari.service.PublicacaoComunidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/comunidades/{comunidadeId}/publicacoes")
public class PublicacaoComunidadeController {

    @Autowired
    private PublicacaoComunidadeService publicacaoService;

    // Criar uma nova publicação dentro de uma comunidade
    @PostMapping
    @ResponseBody
    public String criarPublicacao(@PathVariable UUID comunidadeId, @RequestParam String conteudo, 
                                  @SessionAttribute(name = "usuarioLogado", required = false) Usuario usuarioLogado) {
        if (usuarioLogado == null) {
            return "Usuário não autenticado";
        }

        Optional<Comunidade> comunidadeOptional = publicacaoService.buscarComunidadePorId(comunidadeId);
        if (!comunidadeOptional.isPresent()) {
            return "Comunidade não encontrada";
        }

        Comunidade comunidade = comunidadeOptional.get();
        publicacaoService.criarPublicacao(usuarioLogado, conteudo, comunidade);
        return "Publicação criada com sucesso";
    }

    // Listar as publicações de uma comunidade específica
    @GetMapping
    public String listarPublicacoes(@PathVariable UUID comunidadeId, Model model) {
        Optional<Comunidade> comunidadeOptional = publicacaoService.buscarComunidadePorId(comunidadeId);
        if (!comunidadeOptional.isPresent()) {
            model.addAttribute("erro", "Comunidade não encontrada.");
            return "erro";  // Página de erro
        }
        Comunidade comunidade = comunidadeOptional.get();

        model.addAttribute("publicacoes", publicacaoService.obterPublicacoesPorComunidade(comunidade));
        return "publicacoesContainer";  // Nome do template para o container das publicações
    }
}
