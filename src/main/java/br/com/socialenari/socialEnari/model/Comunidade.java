package br.com.socialenari.socialEnari.model;

import java.util.List;
import java.util.ArrayList;

public class Comunidade {

    private static int contadorId = 0; // Contador estático para IDs únicos
    private int id; // Identificador único da comunidade
    private String ComunidadeNome; // Nome da comunidade
    private String ComunidadeDescricao; // Descrição da comunidade
    private Usuario ComunidadeAdm; // Administrador da comunidade
    private List<Usuario> ComunidadeMembros; // Lista de membros da comunidade

    public Comunidade(String nome, String descricao, Usuario administrador) {
        this.id = contadorId++;
        this.ComunidadeNome = nome;
        this.ComunidadeDescricao = descricao;
        this.ComunidadeAdm = administrador;
        this.ComunidadeMembros = new ArrayList<>();
    }

    // Getters e setters
    public int getId() {
        return id;
    }

    public String getComunidadeNome() {
        return ComunidadeNome;
    }

    public void setComunidadeNome(String comunidadeNome) {
        this.ComunidadeNome = comunidadeNome;
    }

    public String getComunidadeDescricao() {
        return ComunidadeDescricao;
    }

    public void setComunidadeDescricao(String comunidadeDescricao) {
        this.ComunidadeDescricao = comunidadeDescricao;
    }

    public Usuario getComunidadeAdm() {
        return ComunidadeAdm;
    }

    public void setComunidadeAdm(Usuario comunidadeAdm) {
        this.ComunidadeAdm = comunidadeAdm;
    }

    public List<Usuario> getComunidadeMembros() {
        return ComunidadeMembros;
    }

    public void adicionarMembro(Usuario membro) {
        this.ComunidadeMembros.add(membro);
    }
}
