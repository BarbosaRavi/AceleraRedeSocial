package br.com.socialenari.socialEnari.service;

import br.com.socialenari.socialEnari.model.Publicacao;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PublicacaoService {

    private List<Publicacao> publicacoes = new ArrayList<>();
    private Map<Integer, Set<String>> likesMap = new HashMap<>(); // Armazenando likes por post

    public void adicionarPublicacao(Publicacao publicacao) {
        publicacao.setDataHora();
        publicacoes.add(publicacao);
        likesMap.put(publicacoes.size() - 1, new HashSet<>()); // Inicializa a lista de likes
    }

    public List<Publicacao> getTodasPublicacoes() {
        return publicacoes;
    }

    public boolean curtirPublicacao(int index, String usuario) {
        if (index >= 0 && index < publicacoes.size()) {
            likesMap.get(index).add(usuario); // Adiciona o usuário à lista de likes
            return true;
        }
        return false;
    }

    public boolean descurtirPublicacao(int index, String usuario) {
        if (index >= 0 && index < publicacoes.size()) {
            likesMap.get(index).remove(usuario); // Remove o usuário da lista de likes
            return true;
        }
        return false;
    }

    public int contarCurtidas(int index) {
        return likesMap.get(index).size(); // Retorna o número de curtidas
    }

    public List<String> getCurtidores(int index) {
        if (likesMap.containsKey(index)) {
            return new ArrayList<>(likesMap.get(index)); // Retorna a lista de curtidores
        }
        return Collections.emptyList();
    }
}
