package br.com.socialenari.socialEnari.service;

import br.com.socialenari.socialEnari.model.Comunidade;
import br.com.socialenari.socialEnari.model.PublicacaoComunidade;
import br.com.socialenari.socialEnari.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PublicacaoComunidadeService {

    // Lista que armazena as comunidades
    private List<Comunidade> comunidades = new ArrayList<>();

    // Lista que armazena as publicações por comunidade
    private List<PublicacaoComunidade> publicacoes = new ArrayList<>();

    // Buscar uma comunidade pela ID
    public Optional<Comunidade> buscarComunidadePorId(UUID comunidadeId) {
        return comunidades.stream()
                .filter(comunidade -> comunidade.getId().equals(comunidadeId))
                .findFirst();
    }

    // Criar publicação dentro de uma comunidade
    public void criarPublicacao(Usuario usuario, String conteudo, Comunidade comunidade) {
        PublicacaoComunidade publicacao = new PublicacaoComunidade(usuario, conteudo, null, comunidade);
        publicacoes.add(publicacao); // Adiciona à lista global de publicações
    }

    // Obter publicações de uma comunidade
    public List<PublicacaoComunidade> obterPublicacoesPorComunidade(Comunidade comunidade) {
        List<PublicacaoComunidade> publicacoesDaComunidade = new ArrayList<>();
        for (PublicacaoComunidade publicacao : publicacoes) {
            if (publicacao.getComunidade().equals(comunidade)) {
                publicacoesDaComunidade.add(publicacao);
            }
        }
        return publicacoesDaComunidade;
    }
}
