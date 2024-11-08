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

    @GetMapping("/carregar")
    @ResponseBody
    public String carregarComunidades() {
        StringBuilder htmlComunidades = new StringBuilder();
        for (Comunidade comunidade : comunidades) {
            htmlComunidades.append("<li>")
                    .append("<a href='/comunidades/")
                    .append(comunidade.getId()) 
                    .append("'>")
                    .append(comunidade.getComunidadeNome())
                    .append(" - ")
                    .append(comunidade.getComunidadeDescricao())
                    .append("</a>")
                    .append("</li>");
        }
        return htmlComunidades.toString();
    }

    @PostMapping("/criar")
    public String criarComunidade(@RequestParam String nomeComunidade,
                                  @RequestParam String descricaoComunidade,
                                  @SessionAttribute("usuarioLogado") Usuario usuarioLogado) {
        Comunidade novaComunidade = new Comunidade(nomeComunidade, descricaoComunidade, usuarioLogado);
        comunidades.add(novaComunidade);
        return "redirect:/comunidades";
    }

    @GetMapping
    public String exibirComunidades(Model model) {
        model.addAttribute("comunidades", comunidades);
        return "comunidades";
    }

    @GetMapping("/detalhe/{id}")
    public String comunidadeDetalhe(@PathVariable UUID id, Model model) {
       
        for (Comunidade comunidade : comunidades) {
            if (comunidade.getId().equals(id)) { 
                model.addAttribute("comunidade", comunidade);
                model.addAttribute("nomeUsuario", "Usuário Exemplo"); 
                return "comunidadeDetalhe";
            }
        }
        return "comunidadeDetalhes"; 
    }
}
