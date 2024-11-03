package br.com.socialenari.socialEnari.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import br.com.socialenari.socialEnari.utils.idadeUtils;

public class Usuario {
    private UUID id;
    private String nome;
    private String email;
    private String senha;
    private String confirmeSenha;
    private LocalDate dataNascimento;
    private int idade;
    private String fotoPerfil;
    private String bio;

    // Lista para armazenar os amigos e os pedidos de amizade
    private List<Usuario> amigos;
    private List<Usuario> pedidosDeAmizade;

    public Usuario(String nome, String email, String senha, String confirmeSenha, LocalDate dataNascimento, String bio) {
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.confirmeSenha = confirmeSenha;
        this.dataNascimento = dataNascimento;
        this.idade = idadeUtils.calcularIdade(dataNascimento);
        this.fotoPerfil = "/images/default-profile.png";  // Imagem padrão
        this.bio = bio;
        this.amigos = new ArrayList<>();
        this.pedidosDeAmizade = new ArrayList<>();
    }

    // Getters e Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getConfirmeSenha() {
        return confirmeSenha;
    }

    public void setConfirmeSenha(String confirmeSenha) {
        this.confirmeSenha = confirmeSenha;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
        this.idade = idadeUtils.calcularIdade(dataNascimento);
    }

    public int getIdade() {
        return idade;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<Usuario> getAmigos() {
        return amigos;
    }

    public void adicionarAmigo(Usuario amigo) {
        amigos.add(amigo);
    }

    public List<Usuario> getPedidosDeAmizade() {
        return pedidosDeAmizade;
    }

    public void adicionarPedidoDeAmizade(Usuario usuario) {
        pedidosDeAmizade.add(usuario);
    }

    public void removerPedidoDeAmizade(Usuario usuario) {
        pedidosDeAmizade.remove(usuario);
    }
}
