package com.mycompany.mavenproject3.registro.controller;

import com.mycompany.mavenproject3.supabase.SupabaseService;

public class RegistroController {

    public static boolean registrar(String nome, String telefone, String email, String senha, String cpf, String nascimentoFormatado) {
        try {
            String userId = SupabaseService.registrarComMetadados(email, senha, nome, telefone);
            if (userId == null) {
                System.out.println("Usuário criado. Verifique o e-mail para ativar a conta.");
                return true; // Sucesso parcial — e-mail pendente de confirmação
            }

            boolean usuarioExiste = SupabaseService.usuarioExiste(userId);
            if (usuarioExiste) {
                System.out.println("Usuário já cadastrado na tabela 'usuarios'.");
                return true;
            }

            return SupabaseService.salvarDadosComplementares(userId, nome, cpf, nascimentoFormatado, telefone);

        } catch (Exception e) {
            System.err.println("Erro no processo de registro: " + e.getMessage());
            return false;
        }
    }
}
