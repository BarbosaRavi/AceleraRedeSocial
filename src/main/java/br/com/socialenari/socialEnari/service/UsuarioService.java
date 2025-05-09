package br.com.socialenari.socialEnari.service;

import br.com.socialenari.socialEnari.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {

    private final List<Usuario> usuariosCadastrados = new ArrayList<>();

    // Método para cadastrar um novo usuário
    public String cadastrarUsuario(Usuario usuario) {
        if (isEmailExistente(usuario.getEmail())) {
            return "Email já cadastrado.";
        }

        if (validarIdade(usuario)) {
            usuariosCadastrados.add(usuario);
            return "Usuário cadastrado com sucesso!";
        } else {
            return "Idade insuficiente para cadastro. A idade mínima é 13 anos.";
        }
    }

    // Verifica se o email já está cadastrado
    private boolean isEmailExistente(String email) {
        return usuariosCadastrados.stream()
                .anyMatch(usuario -> usuario.getEmail().equalsIgnoreCase(email));
    }

    // Valida se o usuário tem a idade mínima para cadastro (13 anos)
    public boolean validarIdade(Usuario usuario) {
        return usuario.getIdade() >= 13;
    }

    // Verifica se as credenciais (email e senha) são válidas
    public Usuario verificarCredenciais(String email, String senha) {
        for (Usuario usuario : usuariosCadastrados) {
            if (usuario.getEmail().equalsIgnoreCase(email) && usuario.getSenha().equals(senha)) {
                return usuario; // Credenciais válidas, retorna o usuário
            }
        }
        return null; // Credenciais inválidas
    }

    // Método para buscar usuário pelo email
    public Usuario buscarPorEmail(String email) {
        return usuariosCadastrados.stream()
                .filter(usuario -> usuario.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    // Método para buscar usuário pelo ID
    public Usuario buscarPorId(UUID id) {
        return usuariosCadastrados.stream()
                .filter(usuario -> usuario.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Método para atualizar um usuário
    public void atualizarUsuario(Usuario usuarioAtualizado) {
        for (int i = 0; i < usuariosCadastrados.size(); i++) {
            Usuario usuario = usuariosCadastrados.get(i);
            if (usuario.getId().equals(usuarioAtualizado.getId())) { // Use o ID para atualizar
                usuariosCadastrados.set(i, usuarioAtualizado);
                return;
            }
        }
    }

    // Método para deletar um usuário pelo ID
    public boolean deletarUsuario(UUID id) {
        return usuariosCadastrados.removeIf(usuario -> usuario.getId().equals(id));
    }

    // Retorna uma lista de todos os usuários cadastrados
    public List<Usuario> listarTodosUsuarios() {
        return new ArrayList<>(usuariosCadastrados);
    }

    // Método para enviar uma solicitação de amizade
    public String enviarSolicitacaoDeAmizade(UUID idDestinatario, Usuario usuarioSolicitante) {
        Usuario destinatario = buscarPorId(idDestinatario);
        if (destinatario != null && !destinatario.getAmigos().contains(usuarioSolicitante)) {
            destinatario.adicionarPedidoDeAmizade(usuarioSolicitante);
            return "Solicitação de amizade enviada!";
        }
        return "Não foi possível enviar a solicitação de amizade.";
    }

    // Método para aceitar uma solicitação de amizade
    public String aceitarSolicitacaoDeAmizade(UUID idSolicitante, Usuario usuarioDestinatario) {
        Usuario solicitante = buscarPorId(idSolicitante);
        if (solicitante != null && usuarioDestinatario.getPedidosDeAmizade().contains(solicitante)) {
            solicitante.adicionarAmigo(usuarioDestinatario);
            usuarioDestinatario.adicionarAmigo(solicitante);
            usuarioDestinatario.removerPedidoDeAmizade(solicitante);
            return "Amizade aceita com sucesso!";
        }
        return "Não foi possível aceitar a solicitação de amizade.";
    }

    // Método para listar sugestões de amizade (usuários que ainda não são amigos)
    public List<Usuario> listarSugestoesDeAmigos(Usuario usuario) {
        List<Usuario> sugestoes = new ArrayList<>();
        for (Usuario u : usuariosCadastrados) {
            if (!u.equals(usuario) && !usuario.getAmigos().contains(u)) {
                sugestoes.add(u);
            }
        }
        return sugestoes;
    }
}
