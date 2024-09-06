package br.com.socialenari.socialEnari.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Usuario {
    private String userEmail;
    private String userSenha;
    private String confirmeSenha;
    private LocalDate userNascimento;
    private int userIdade;

    public Usuario() {
    }

    public Usuario(String userEmail, String userSenha, LocalDate userNascimento) {
        this.userEmail = userEmail;
        this.userSenha = userSenha;
        this.userNascimento = userNascimento;
    }

    public String getEmail() {
        return userEmail;
    }

    public void setEmail(String email) {
        this.userEmail = email;
    }

    public String getSenha() {
        return userSenha;
    }

    public void setSenha(String senha) {
        this.userSenha = senha;
    }

    public String getConfirmeSenha() {
        return confirmeSenha;
    }

    public void setConfirmeSenha(String confirmeSenha) {
        this.confirmeSenha = confirmeSenha;
    }

    public LocalDate getNascimento() {
        return userNascimento;
    }

    public void setNascimento(String nascimento) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.userNascimento = LocalDate.parse(nascimento, formatter);
    }

    public int getIdade() {
        return userIdade;
    }

    public void setIdade(int idade) {
        this.userIdade = idade;
    }
}
