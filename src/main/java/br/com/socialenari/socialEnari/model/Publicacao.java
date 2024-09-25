package br.com.socialenari.socialEnari.model;

public class Publicacao {
    private String conteudo;
    private String usuario; 
    private int curtidas;   

    // Construtor padrão
    public Publicacao() {
        this.curtidas = 0; 
    }

    // Getters e Setters
    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getCurtidas() {
        return curtidas;
    }

    public void setCurtidas(int curtidas) {
        this.curtidas = curtidas;
    }

    // Método para adicionar uma curtida
    public void adicionarCurtida() {
        this.curtidas++;
    }
}
