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
        for (Usuario usuario : usuariosCadastrados) {
            if (usuario.getEmail().equalsIgnoreCase(email)) {
                return usuario;
            }
        }
        return null;
    }

    public Usuario buscarPorId(UUID id) {
        for (Usuario usuario : usuariosCadastrados) {
            if (usuario.getId().equals(id)) {
                return usuario;
            }
        }
        return null;
    }

    public List<Usuario> listarTodosUsuarios() {
        return new ArrayList<>(usuariosCadastrados);
    }
}
