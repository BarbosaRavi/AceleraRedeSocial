package br.com.socialenari.socialEnari.service;

import br.com.socialenari.socialEnari.model.Publicacao;
import br.com.socialenari.socialEnari.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PublicacaoService {
    private List<Publicacao> publicacoes = new ArrayList<>();
    private Usuario usuarioLogado;

    public void adicionarPublicacao(Publicacao publicacao) {
        if (usuarioLogado != null) {
            publicacao.setUsuario(usuarioLogado.getNome());
            System.out.println("Adicionando publicação do usuário: " + usuarioLogado.getNome());
        } else {
            System.out.println("Usuário não logado, publicação não associada a nenhum usuário.");
        }
        publicacoes.add(publicacao);
    }

    public String obterPublicacoes() {
        StringBuilder sb = new StringBuilder();
        for (Publicacao publicacao : publicacoes) {
            String usuario = publicacao.getUsuario() != null ? publicacao.getUsuario() : "Usuário desconhecido";
            sb.append(usuario)
              .append(": ")
              .append(publicacao.getConteudo())
              .append(" (")
              .append(publicacao.getDataHora())
              .append(")\n");
        }
        return sb.toString();
    }

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }
}
