package br.com.socialenari.socialEnari.service;

import br.com.socialenari.socialEnari.model.Comentario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ComentarioService {
    private final List<Comentario> comentarios = new ArrayList<>();

    public void adicionarComentario(Comentario comentario) {
        comentario.setId(UUID.randomUUID()); // Gerar um novo UUID para o comentário
        comentarios.add(comentario);
    }

    public List<Comentario> listarComentariosPorPublicacao(int publicacaoId) {
        List<Comentario> comentariosPublicacao = new ArrayList<>();
        for (Comentario comentario : comentarios) {
            if (comentario.getPublicacaoId() == publicacaoId) {
                comentariosPublicacao.add(comentario);
            }
        }
        return comentariosPublicacao;
    }
}
