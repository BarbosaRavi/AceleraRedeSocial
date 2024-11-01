package br.com.socialenari.socialEnari.service;

import br.com.socialenari.socialEnari.model.Publicacao;
import br.com.socialenari.socialEnari.model.Like;
import br.com.socialenari.socialEnari.model.Usuario;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class PublicacaoService {
    private List<Publicacao> publicacoes = new LinkedList<>();
    private List<Like> likes = new ArrayList<>();

    public void criarPublicacao(Usuario usuario, String conteudo) {
        Publicacao novaPublicacao = new Publicacao(usuario, conteudo, LocalDateTime.now());
        publicacoes.add(novaPublicacao); // Adiciona a nova publicação à lista

        // Log para depuração
        System.out.println("Nova publicação criada: ID = " + novaPublicacao.getId() + 
                           ", Conteúdo = " + conteudo + ", Usuário = " + usuario.getNome());
    }

    public List<Publicacao> obterPublicacoes() {
        System.out.println("Obtendo publicações. Total de publicações: " + publicacoes.size());
        return publicacoes; // Retorna todas as publicações
    }

    public void curtirPublicacao(int idPublicacao, String usuario) {
        Like curtidaExistente = buscarLike(idPublicacao, usuario);

        if (curtidaExistente != null) {
            likes.remove(curtidaExistente);
            System.out.println("Curtida removida: Publicação ID = " + idPublicacao + ", Usuário = " + usuario);
        } else {
            Like novoLike = new Like();
            novoLike.setPublicacaoId(idPublicacao);
            novoLike.setUsuario(usuario);
            likes.add(novoLike);
            System.out.println("Curtida adicionada: Publicação ID = " + idPublicacao + ", Usuário = " + usuario);
        }
    }

    private Like buscarLike(int idPublicacao, String usuario) {
        for (Like like : likes) {
            if (like.getPublicacaoId() == idPublicacao && like.getUsuario().equals(usuario)) {
                return like;
            }
        }
        return null;
    }

    public List<Like> getLikes(int idPublicacao) {
        List<Like> likesDaPublicacao = new ArrayList<>();
        for (Like like : likes) {
            if (like.getPublicacaoId() == idPublicacao) {
                likesDaPublicacao.add(like);
            }
        }
        return likesDaPublicacao;
    }

    public List<String> formatarPublicacoes() {
        List<String> publicacoesFormatadas = new ArrayList<>();
        for (Publicacao pub : publicacoes) {
            String usuarioId = pub.getUsuario().getId().toString();
            String tempoPassado = calcularTempoPassado(pub.getDataHora());
            int totalCurtidas = getLikes(pub.getId()).size();

            String publicacaoFormatada = "<a href=/perfil/" + usuarioId + ">" + pub.getUsuario().getNome() + "</a>: " 
                                          + pub.getConteudo() + " (" + tempoPassado + ")";
            publicacoesFormatadas.add(publicacaoFormatada + " - " + totalCurtidas + " curtidas");
        }

        // Log para depuração
        System.out.println("Total de publicações formatadas: " + publicacoesFormatadas.size());
        return publicacoesFormatadas;
    }

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
}
