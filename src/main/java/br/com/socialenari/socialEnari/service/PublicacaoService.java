package br.com.socialenari.socialEnari.service;

import br.com.socialenari.socialEnari.model.Publicacao;
import br.com.socialenari.socialEnari.model.Like;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

    // Método para obter todas as publicações de um usuário específico
    public List<Publicacao> obterPublicacoesPorUsuario(String usuario) {
        List<Publicacao> publicacoesUsuario = new ArrayList<>();
        for (Publicacao publicacao : publicacoes) {
            if (publicacao.getUsuario().equals(usuario)) {
                publicacoesUsuario.add(publicacao);
            }
        }
        return publicacoesUsuario;
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

    // Método para calcular o tempo decorrido desde a publicação
    private String calcularTempoPassado(LocalDateTime dataPublicacao) {
        LocalDateTime agora = LocalDateTime.now();
        Duration duracao = Duration.between(dataPublicacao, agora);

        long dias = duracao.toDays();
        long horas = duracao.toHours();
        long minutos = duracao.toMinutes();
        long segundos = duracao.getSeconds();

        if (dias > 0) {
            return dias + " dias atrás";
        } else if (horas > 0) {
            return horas + " horas atrás";
        } else if (minutos > 0) {
            return minutos + " minutos atrás";
        } else {
            return segundos + " segundos atrás";
        }
    }

    // Método para formatar a exibição das publicações com tempo decorrido
    public List<String> formatarPublicacoes() {
        List<String> publicacoesFormatadas = new ArrayList<>();
        for (Publicacao pub : publicacoes) {
            String tempoPassado = calcularTempoPassado(pub.getDataHora());
            int totalCurtidas = getLikes(pub.getId()).size(); // Conta as curtidas corretamente
            String publicacaoFormatada = pub.getUsuario() + ": " + pub.getConteudo() + " (" + tempoPassado + ")";
            publicacoesFormatadas.add(publicacaoFormatada + " - " + totalCurtidas + " curtidas"); // Adiciona as curtidas separadamente
        }
        return publicacoesFormatadas;
    }
}
