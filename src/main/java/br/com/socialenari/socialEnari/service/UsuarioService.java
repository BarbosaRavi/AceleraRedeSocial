package br.com.socialenari.socialEnari.service;

import br.com.socialenari.socialEnari.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {

    private final List<Usuario> usuariosCadastrados = new ArrayList<>();

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

    private boolean isEmailExistente(String email) {
        return usuariosCadastrados.stream()
                .anyMatch(usuario -> usuario.getEmail().equalsIgnoreCase(email));
    }

    public boolean validarIdade(Usuario usuario) {
        return usuario.getIdade() >= 13;
    }

    public Usuario buscarPorEmail(String email) {
        return usuariosCadastrados.stream()
                .filter(usuario -> usuario.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    public Usuario buscarPorId(UUID id) {
        return usuariosCadastrados.stream()
                .filter(usuario -> usuario.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Usuario> listarTodosUsuarios() {
        return new ArrayList<>(usuariosCadastrados);
    }

    // Método para atualizar um usuário
    public void atualizarUsuario(Usuario usuarioAtualizado) {
        for (int i = 0; i < usuariosCadastrados.size(); i++) {
            Usuario usuario = usuariosCadastrados.get(i);
            if (usuario.getEmail().equalsIgnoreCase(usuarioAtualizado.getEmail())) {
                usuariosCadastrados.set(i, usuarioAtualizado);
                return;
            }
        }
    }
}
