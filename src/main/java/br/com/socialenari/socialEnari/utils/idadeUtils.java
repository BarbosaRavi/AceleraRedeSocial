package br.com.socialenari.socialEnari.utils;

import java.time.LocalDate;
import java.time.Period;

public class idadeUtils {

    public static int calcularIdade(LocalDate dataNascimento) {
        if (dataNascimento == null) {
            return 0;
        }
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }
}
