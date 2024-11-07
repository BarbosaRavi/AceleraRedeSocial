package br.com.socialenari.socialEnari.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;  // Importar UUID

public class Comunidade {

    private UUID id;  // Alterado para UUID
    private String comunidadeNome; // Nome da comunidade
    private String comunidadeDescricao; // Descrição da comunidade
    private Usuario comunidadeAdm; // Administrador da comunidade
    private List<Usuario> comunidadeMembros; // Lista de membros da comunidade

    // Construtor
    public Comunidade(String nome, String descricao, Usuario administrador) {
        this.id = UUID.randomUUID();  // Gerando um UUID único para cada comunidade
        this.comunidadeNome = nome;
        this.comunidadeDescricao = descricao;
        this.comunidadeAdm = administrador;
        this.comunidadeMembros = new ArrayList<>();
    }

    // Getters e setters
    public UUID getId() {
        return id;
    }

    public String getComunidadeNome() {
        return comunidadeNome;
    }

    public void setComunidadeNome(String comunidadeNome) {
        this.comunidadeNome = comunidadeNome;
    }

    public String getComunidadeDescricao() {
        return comunidadeDescricao;
    }

    public void setComunidadeDescricao(String comunidadeDescricao) {
        this.comunidadeDescricao = comunidadeDescricao;
    }

    public Usuario getComunidadeAdm() {
        return comunidadeAdm;
    }

    public void setComunidadeAdm(Usuario comunidadeAdm) {
        this.comunidadeAdm = comunidadeAdm;
    }

    public List<Usuario> getComunidadeMembros() {
        return comunidadeMembros;
    }

    public void adicionarMembro(Usuario membro) {
        this.comunidadeMembros.add(membro);
    }
}
