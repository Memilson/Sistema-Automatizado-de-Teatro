package com.mycompany.mavenproject3.usuario.repository;

import com.mycompany.mavenproject3.usuario.model.Usuario;
import org.json.JSONObject;

public class UsuarioAdapter {
    public static Usuario fromJson(JSONObject obj) {
        String assinaturaNome = obj.has("assinaturas")
                ? obj.getJSONObject("assinaturas").optString("nome", "N/D")
                : null;

        Usuario u = new Usuario(
                obj.getString("id"),
                obj.optString("nome", null),
                obj.optString("cpf", null),
                obj.optString("nascimento", null),
                obj.optString("assinatura_id", null),
                obj.optBoolean("is_admin", false),
                assinaturaNome
        );
        u.setTelefone(obj.optString("telefone", null));
        return u;
    }
}
