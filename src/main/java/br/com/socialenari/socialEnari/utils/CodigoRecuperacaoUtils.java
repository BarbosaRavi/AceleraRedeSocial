package br.com.socialenari.socialEnari.utils;

import java.util.Random;

public class CodigoRecuperacaoUtils {

    private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int TAMANHO_CODIGO = 6;

    public static String gerarCodigo() {
        StringBuilder codigo = new StringBuilder(TAMANHO_CODIGO);
        Random random = new Random();
        for (int i = 0; i < TAMANHO_CODIGO; i++) {
            int indice = random.nextInt(CARACTERES.length());
            codigo.append(CARACTERES.charAt(indice));
        }
        return codigo.toString();
    }
}
