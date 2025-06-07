package com.mycompany.mavenproject3.usuario.repository;

import com.mycompany.mavenproject3.supabase.SupabaseService;
import com.mycompany.mavenproject3.usuario.model.Usuario;
import org.json.JSONArray;
import org.json.JSONObject;

public class UsuarioRepositorySupabase {

    public static Usuario buscarPorIdAuth(String idAuth) {
        try {
            String url = "/rest/v1/usuarios?id=eq." + idAuth + "&select=id,nome,cpf,nascimento,assinatura_id";
            String json = SupabaseService.get(url, true);

            if (json == null || !json.trim().startsWith("[")) {
                System.err.println("Resposta inválida ou erro da API: " + json);
                return null;
            }

            JSONArray array = new JSONArray(json);
            if (!array.isEmpty()) {
                JSONObject obj = array.getJSONObject(0);
                return new Usuario(
                        obj.getString("id"),
                        obj.optString("nome", null),
                        obj.optString("cpf", null),
                        obj.optString("nascimento", null),
                        obj.optString("assinatura_id", null)
                );
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar usuário: " + e.getMessage());
        }
        return null;
    }
}