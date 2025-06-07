package com.mycompany.mavenproject3.usuario.repository;

import com.mycompany.mavenproject3.supabase.SupabaseService;
import com.mycompany.mavenproject3.usuario.model.Usuario;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UsuarioRepositorySupabase {

    public static Usuario buscarPorIdAuth(String idAuth) {
        try {
            String url = "/rest/v1/usuarios?id=eq." + idAuth + "&select=id,nome,cpf,nascimento,assinatura_id,is_admin,telefone,assinaturas(nome)";
            String json = SupabaseService.get(url, true);

            if (json == null || !json.trim().startsWith("[")) {
                System.err.println("Resposta inválida ou erro da API: " + json);
                return null;
            }

            JSONArray array = new JSONArray(json);
            if (!array.isEmpty()) {
                JSONObject obj = array.getJSONObject(0);
                String assinaturaNome = null;
                if (obj.has("assinaturas")) {
                    assinaturaNome = obj.getJSONObject("assinaturas").optString("nome", "N/D");
                }

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
        } catch (Exception e) {
            System.err.println("Erro ao buscar usuário: " + e.getMessage());
        }
        return null;
    }

    public static List<Usuario> buscarTodosUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        try {
            String url = "/rest/v1/usuarios?select=id,nome,cpf,nascimento,telefone,assinatura_id,is_admin,assinaturas(nome)";
            String json = SupabaseService.get(url, true);

            assert json != null;
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String assinaturaNome = null;
                if (obj.has("assinaturas")) {
                    assinaturaNome = obj.getJSONObject("assinaturas").optString("nome", "N/D");
                }

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
                lista.add(u);
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar todos os usuários: " + e.getMessage());
        }
        return lista;
    }

    public static boolean promoverParaAdmin(String idUsuario) {
        String body = "{\"is_admin\": true}";
        return SupabaseService.patch("/rest/v1/usuarios?id=eq." + idUsuario, body);
    }

    public static boolean alterarAssinatura(String idUsuario, String novaAssinaturaId) {
        String body = "{\"assinatura_id\": \"" + novaAssinaturaId + "\"}";
        return SupabaseService.patch("/rest/v1/usuarios?id=eq." + idUsuario, body);
    }
    public static int totalUsuariosCadastrados() {
        try {
            String json = SupabaseService.get("/rest/v1/usuarios?select=id", true);
            assert json != null;
            JSONArray array = new JSONArray(json);
            return array.length();
        } catch (Exception e) {
            return 0;
        }
    }

}
