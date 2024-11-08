package br.com.socialenari.socialEnari.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;  // Importar UUID

public class Comunidade {

    private UUID comunidadeId; 
    private String comunidadeNome;
    private String comunidadeDescricao; 
    private Usuario comunidadeAdm;
    private List<Usuario> comunidadeMembros;

    public Comunidade(String nome, String descricao, Usuario administrador) {
        this.comunidadeId = UUID.randomUUID();
        this.comunidadeNome = nome;
        this.comunidadeDescricao = descricao;
        this.comunidadeAdm = administrador;
        this.comunidadeMembros = new ArrayList<>();
    }

    public UUID getId() {
        return comunidadeId;
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
