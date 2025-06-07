package com.mycompany.mavenproject3.usuario.service;

import com.mycompany.mavenproject3.supabase.SupabaseService;
import org.json.JSONArray;
import org.json.JSONObject;

public class UsuarioService {
    public static boolean atualizarPlanoUsuario(String userId, String novoPlanoId) {
        try {
            // Verifica se o plano existe no Supabase
            String check = SupabaseService.get("/rest/v1/assinaturas?id=eq." + novoPlanoId + "&select=id", true);
            if (check == null || !check.trim().startsWith("[")) {
                System.err.println("Plano inválido (não retornou array)");
                return false;
            }

            JSONArray arr = new JSONArray(check);
            if (arr.isEmpty()) {
                System.err.println("Plano não encontrado no banco.");
                return false;
            }

            // 🔁 Busca os dados obrigatórios atuais do usuário (para não violar os NOT NULLs)
            String usuarioJson = SupabaseService.get(
                    "/rest/v1/usuarios?id=eq." + userId + "&select=nome,cpf,nascimento,telefone", true
            );

            JSONArray usuarioArray = new JSONArray(usuarioJson);
            if (usuarioArray.isEmpty()) {
                System.err.println("Usuário não encontrado.");
                return false;
            }
            JSONObject usuarioAtual = usuarioArray.getJSONObject(0);

            // 📦 Prepara o corpo do PATCH incluindo os campos obrigatórios + novo plano
            JSONObject json = new JSONObject();
            json.put("nome", usuarioAtual.optString("nome", ""));
            json.put("cpf", usuarioAtual.optString("cpf", ""));
            json.put("nascimento", usuarioAtual.opt("nascimento")); // pode ser null
            json.put("telefone", usuarioAtual.opt("telefone"));     // pode ser null
            json.put("assinatura_id", novoPlanoId);

            // 🔄 PATCH com override (Prefer: return=minimal)
            String resposta = SupabaseService.patch(
                    "/rest/v1/usuarios?id=eq." + userId,
                    json.toString(),
                    true,
                    false
            );

            // 🧪 Verificação básica de sucesso
            System.out.println("Resposta do PATCH: " + resposta);
            return resposta != null && !resposta.contains("error");

        } catch (Exception e) {
            System.err.println("Erro ao atualizar plano: " + e.getMessage());
            return false;
        }
    }
}
