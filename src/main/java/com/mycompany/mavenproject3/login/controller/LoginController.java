package com.mycompany.mavenproject3.login.controller;

import com.mycompany.mavenproject3.supabase.SupabaseService;
import com.mycompany.mavenproject3.core.SessaoUsuario;
import org.json.JSONObject;

public class LoginController {
    public static boolean login(String email, String senha) {
        try {
            String response = SupabaseService.login(email, senha);
            if (response == null) return false;

            JSONObject obj = new JSONObject(response);
            if (obj.has("user")) {
                String userId = obj.getJSONObject("user").getString("id");
                SessaoUsuario.iniciar(userId);
                return true;
            }
        } catch (Exception e) {
            System.err.println("Erro no login: " + e.getMessage());
        }
        return false;
    }
}
