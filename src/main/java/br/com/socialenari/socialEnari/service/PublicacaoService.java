package br.com.socialenari.socialEnari.service;

import br.com.socialenari.socialEnari.model.Publicacao;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class PublicacaoService {

    private List<Publicacao> publicacoes = new LinkedList<>(); // Armazenando em memória usando LinkedList

    // Método para adicionar uma nova publicação
    public void adicionarPublicacao(Publicacao publicacao) {
        publicacao.setDataHora();
        publicacoes.add(publicacao);
    }

    // Método para retornar todas as publicações
    public List<Publicacao> getTodasPublicacoes() {
        return publicacoes;
    }

    // Método para curtir uma publicação pelo índice
    public boolean curtirPublicacao(int index) {
        if (index >= 0 && index < publicacoes.size()) {
            Publicacao publicacao = publicacoes.get(index);
            publicacao.curtir();
            return true;
        }
        return false; // Retorna false se o índice for inválido
    }
}
