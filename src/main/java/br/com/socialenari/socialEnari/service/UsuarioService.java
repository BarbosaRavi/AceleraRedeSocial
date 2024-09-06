package br.com.socialenari.socialEnari.service;

import br.com.socialenari.socialEnari.model.Usuario;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class UsuarioService {

    public int calcularIdade(LocalDate nascimento) {
        LocalDate hoje = LocalDate.now();
        return Period.between(nascimento, hoje).getYears();
    }

    public boolean validarIdade(Usuario usuario) {
        int idade = calcularIdade(usuario.getNascimento());
        usuario.setIdade(idade);
        return idade >= 13;
    }
}
