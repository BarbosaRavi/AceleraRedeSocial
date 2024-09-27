package br.com.socialenari.socialEnari.service;

import br.com.socialenari.socialEnari.model.Publicacao;
import br.com.socialenari.socialEnari.model.Like;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.time.LocalDateTime;

@Service

public class PublicacaoService {

    private List<Publicacao> publicacoes = new LinkedList<>(); // Lista de publicações
    private List<Like> likes = new ArrayList<>(); // Lista de likes (curtidas)

    // Método para criar uma nova publicação
    public void criarPublicacao(String usuario, String conteudo) {
        Publicacao novaPublicacao = new Publicacao(usuario, conteudo, LocalDateTime.now());
        publicacoes.add(novaPublicacao);
    }

    // Método para obter todas as publicações
    public List<Publicacao> obterPublicacoes() {
        return publicacoes;
    }

    // Método para curtir ou descurtir uma publicação
    public void curtirPublicacao(int idPublicacao, String usuario) {
        Like curtidaExistente = buscarLike(idPublicacao, usuario);

        // Se o usuário já curtiu, remove a curtida (descurtir)
        if (curtidaExistente != null) {
            likes.remove(curtidaExistente);
        } else {
            // Se o usuário não curtiu ainda, adiciona uma nova curtida
            Like novoLike = new Like();
            novoLike.setPublicacaoId(idPublicacao);
            novoLike.setUsuario(usuario);
            likes.add(novoLike);
        }
    }

    // Método auxiliar para buscar uma curtida (like) por ID de publicação e usuário
    private Like buscarLike(int idPublicacao, String usuario) {
        for (Like like : likes) {
            if (like.getPublicacaoId() == idPublicacao && like.getUsuario().equals(usuario)) {
                return like; // Curtida encontrada
            }
        }
        return null; // Nenhuma curtida encontrada
    }

    // Método para obter todas as curtidas de uma publicação específica
    public List<Like> getLikes(int idPublicacao) {
        List<Like> likesDaPublicacao = new ArrayList<>();
        for (Like like : likes) {
            if (like.getPublicacaoId() == idPublicacao) {
                likesDaPublicacao.add(like);
            }
        }
        return likesDaPublicacao;
    }

    // Método para formatar a exibição das publicações
    public List<String> formatarPublicacoes() {
        List<String> publicacoesFormatadas = new ArrayList<>();
        for (Publicacao pub : publicacoes) {
            String publicacaoFormatada = pub.getUsuario() + ": " + pub.getConteudo() + " (" + pub.getDataHora() + ") - " 
                + getLikes(pub.getId()).size() + " curtidas";
            publicacoesFormatadas.add(publicacaoFormatada);
        }
        return publicacoesFormatadas;
    }
}