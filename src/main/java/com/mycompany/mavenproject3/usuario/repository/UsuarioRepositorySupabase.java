package com.mycompany.mavenproject3.usuario.repository;

import com.mycompany.mavenproject3.supabase.SupabaseService;
import com.mycompany.mavenproject3.usuario.model.Usuario;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UsuarioRepositorySupabase implements UsuarioRepository {

    private final SupabaseService supabase;

    public UsuarioRepositorySupabase(SupabaseService supabase) {
        this.supabase = supabase;
    }

    @Override
    public Usuario buscarPorId(String idAuth) {
        try {
            String url = "/rest/v1/usuarios?id=eq." + idAuth + "&select=id,nome,cpf,nascimento,assinatura_id,is_admin,telefone,assinaturas(nome)";
            String json = SupabaseService.get(url, true);
            assert json != null;
            JSONArray array = new JSONArray(json);
            if (!array.isEmpty()) {
                return UsuarioAdapter.fromJson(array.getJSONObject(0));
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar usuário: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Usuario> buscarTodos() {
        List<Usuario> lista = new ArrayList<>();
        try {
            String url = "/rest/v1/usuarios?select=id,nome,cpf,nascimento,telefone,assinatura_id,is_admin,assinaturas(nome)";
            String json = SupabaseService.get(url, true);
            assert json != null;
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                lista.add(UsuarioAdapter.fromJson(array.getJSONObject(i)));
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar todos os usuários: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public boolean promoverParaAdmin(String id) {
        String body = "{\"is_admin\": true}";
        return SupabaseService.patch("/rest/v1/usuarios?id=eq." + id, body);
    }

    @Override
    public boolean alterarAssinatura(String id, String novaAssinaturaId) {
        String body = "{\"assinatura_id\": \"" + novaAssinaturaId + "\"}";
        return SupabaseService.patch("/rest/v1/usuarios?id=eq." + id, body);
    }
    @Override
    public boolean alterarPlanoAssinatura(String usuarioId, String novaAssinaturaId) {
        try {
            JSONObject update = new JSONObject();
            update.put("assinatura_id", novaAssinaturaId);

            String filtro = "?id=eq." + usuarioId;
            SupabaseService.patch("/rest/v1/usuarios" + filtro, update.toString());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int totalUsuariosCadastrados() {
        try {
            String json = SupabaseService.get("/rest/v1/usuarios?select=id", true);
            assert json != null;
            JSONArray array = new JSONArray(json);
            return array.length();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public boolean temDadosComplementares(String usuarioId) {
        try {
            String url = "/rest/v1/usuarios?id=eq." + usuarioId + "&select=nome";
            String json = SupabaseService.get(url, true);
            if (json != null) {
                JSONArray array = new JSONArray(json);
                if (!array.isEmpty()) {
                    JSONObject obj = array.getJSONObject(0);
                    return obj.has("nome") && !obj.isNull("nome") && !obj.getString("nome").isEmpty();
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao verificar dados complementares: " + e.getMessage());
        }
        return false;
    }
    @Override
    public Usuario findByEmailAndSenha(String email, String senha) {
        try {
            String url = "/rest/v1/usuarios?email=eq." + email + "&senha=eq." + senha + "&select=*";
            String json = SupabaseService.get(url, true);
            assert json != null;
            JSONArray array = new JSONArray(json);
            if (!array.isEmpty()) {
                return UsuarioAdapter.fromJson(array.getJSONObject(0));
            }
        } catch (Exception e) {
            System.err.println("Erro ao autenticar usuário: " + e.getMessage());
        }
        return null;
    }

    public SupabaseService getSupabase() {
        return supabase;
    }
}
