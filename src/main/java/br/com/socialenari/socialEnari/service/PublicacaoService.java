package br.com.socialenari.socialEnari.service;

import br.com.socialenari.socialEnari.model.Publicacao;
import br.com.socialenari.socialEnari.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class PublicacaoService {

    private List<Publicacao> publicacoes = new LinkedList<>(); 
    private Usuario usuarioLogado;
    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario; 
    }

    public void adicionarPublicacao(String conteudo) {
        if (usuarioLogado == null) {
            throw new IllegalStateException("Usuário não está logado.");
        }

        Publicacao novaPublicacao = new Publicacao(); 
        novaPublicacao.setConteudo(conteudo);
        novaPublicacao.setUsuario(usuarioLogado.getNome());
        novaPublicacao.setCurtidas(0);

        publicacoes.add(novaPublicacao);
    }

    public List<Publicacao> obterPublicacoes() {
        return publicacoes;
    }

    public void adicionarCurtida(int index) {
        if (index >= 0 && index < publicacoes.size()) {
            Publicacao publicacao = publicacoes.get(index);
            publicacao.adicionarCurtida(); 
            throw new IndexOutOfBoundsException("Índice da publicação inválido.");
        }
    }
}
