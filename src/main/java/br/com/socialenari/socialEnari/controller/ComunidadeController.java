package br.com.socialenari.socialEnari.controller;

import br.com.socialenari.socialEnari.model.Comunidade;
import br.com.socialenari.socialEnari.model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;  // Importando UUID

@Controller
@RequestMapping("/comunidades")
public class ComunidadeController {

    private List<Comunidade> comunidades = new ArrayList<>();

    // Carregar a lista de comunidades
    @GetMapping("/carregar")
    @ResponseBody
    public String carregarComunidades() {
        StringBuilder htmlComunidades = new StringBuilder();
        for (Comunidade comunidade : comunidades) {
            // Gerar o link com o ID em formato UUID
            htmlComunidades.append("<li>")
                    .append("<a href='/comunidades/detalhe/")
                    .append(comunidade.getId())  // Usando o UUID da comunidade na URL
                    .append("'>")
                    .append(comunidade.getComunidadeNome())
                    .append(" - ")
                    .append(comunidade.getComunidadeDescricao())
                    .append("</a>")
                    .append("</li>");
        }
        return htmlComunidades.toString();
    }

    // Criar uma nova comunidade
    @PostMapping("/criar")
    public String criarComunidade(@RequestParam String nomeComunidade,
                                  @RequestParam String descricaoComunidade,
                                  @SessionAttribute("usuarioLogado") Usuario usuarioLogado) {
        Comunidade novaComunidade = new Comunidade(nomeComunidade, descricaoComunidade, usuarioLogado);
        comunidades.add(novaComunidade);
        return "redirect:/comunidades"; // Redireciona para a página de comunidades
    }

    // Exibir todas as comunidades
    @GetMapping
    public String exibirComunidades(Model model) {
        model.addAttribute("comunidades", comunidades);
        return "comunidades"; // Retorna a página comunidades.html
    }

    // Exibir detalhes de uma comunidade específica, usando o UUID como path variable
    @GetMapping("/detalhe/{id}")
    public String comunidadeDetalhe(@PathVariable UUID id, Model model) {
        // Busca a comunidade pelo UUID
        for (Comunidade comunidade : comunidades) {
            if (comunidade.getId().equals(id)) { // Comparação correta com UUID
                model.addAttribute("comunidade", comunidade);
                model.addAttribute("nomeUsuario", "Usuário Exemplo"); // Substitua pelo nome do usuário logado
                return "comunidadeDetalhe"; // Retorna a página de detalhes da comunidade
            }
        }
        return "comunidadeDetalhes"; // Retorna uma página de erro 404 caso a comunidade não seja encontrada
    }
}
