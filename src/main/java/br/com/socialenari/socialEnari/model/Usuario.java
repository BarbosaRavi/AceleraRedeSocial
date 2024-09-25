package br.com.socialenari.socialEnari.model;

import java.time.LocalDate;
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

    public Usuario(String nome, String email, String senha, String confirmeSenha, LocalDate dataNascimento) {
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.confirmeSenha = confirmeSenha;
        this.dataNascimento = dataNascimento;
        this.idade = idadeUtils.calcularIdade(dataNascimento);
    }

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
}
