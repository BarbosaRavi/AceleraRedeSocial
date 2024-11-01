package br.com.socialenari.socialEnari.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.socialenari.socialEnari.model.Comunidade;
import br.com.socialenari.socialEnari.model.Usuario;

@Service
public class ComunidadeService {

    private List<Comunidade> comunidades = new ArrayList<>();

    public void criarComunidade(String nome, String descricao, Usuario administrador) {
        Comunidade novaComunidade = new Comunidade(nome, descricao, administrador);
        comunidades.add(novaComunidade);
    }

    public List<Comunidade> listarComunidades() {
        return comunidades;
    }
}
