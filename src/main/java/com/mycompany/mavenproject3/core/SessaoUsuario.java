package com.mycompany.mavenproject3.core;

public class SessaoUsuario {
    private static String userId;

    public static void iniciar(String id) {
        userId = id;
    }

    public static String getUserId() {
        return userId;
    }

    public static void encerrar() {
        userId = null;
    }
}