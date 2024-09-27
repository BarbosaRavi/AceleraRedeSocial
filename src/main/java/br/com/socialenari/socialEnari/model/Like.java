package br.com.socialenari.socialEnari.model;

public class Like {
    private String usuario;
    private int publicacaoId;

    // Getters e Setters
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getPublicacaoId() {
        return publicacaoId;
    }

    public void setPublicacaoId(int publicacaoId) {
        this.publicacaoId = publicacaoId;
    }
}
