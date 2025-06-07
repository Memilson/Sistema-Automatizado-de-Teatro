package com.mycompany.mavenproject3.common;

public class ValidadorCPF {
    public static boolean validar(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");
        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false;
        }
        try {
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);}
            int digito1 = 11 - (soma % 11);
            if (digito1 > 9) digito1 = 0;
            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);}
            int digito2 = 11 - (soma % 11);
            if (digito2 > 9) digito2 = 0;
            return (Character.getNumericValue(cpf.charAt(9)) == digito1) &&
                    (Character.getNumericValue(cpf.charAt(10)) == digito2);
        } catch (Exception e) {
            return false;
        }
    }
}