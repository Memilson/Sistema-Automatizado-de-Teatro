package com.mycompany.mavenproject3.compra.controller;

import com.mycompany.mavenproject3.compra.repository.CompraRepository;
import com.mycompany.mavenproject3.supabase.SupabaseService;
import org.json.JSONArray;

public class CompraController {
    public static boolean realizarCompra(String pecaId, String sessaoId, String usuarioId, String poltronaId, String precoOriginalStr) {
        try {
            // Buscar assinatura do usuário
            String assinaturaId = SupabaseService.buscarAssinaturaId(usuarioId);
            if (assinaturaId == null) return false;

            // Buscar desconto da assinatura
            String assinaturaJson = SupabaseService.get("/rest/v1/assinaturas?id=eq." + assinaturaId + "&select=desconto_percentual", true);
            JSONArray array = new JSONArray(assinaturaJson);
            double desconto = 0.0;
            if (!array.isEmpty()) {
                desconto = array.getJSONObject(0).optDouble("desconto_percentual", 0.0);
            }

            // Aplicar desconto
            double precoOriginal = Double.parseDouble(precoOriginalStr);
            double precoComDesconto = precoOriginal * (1 - desconto / 100.0);

            // ✅ Corrigir formatação com Locale.US para evitar vírgula
            String precoFinalStr = String.format(java.util.Locale.US, "%.2f", precoComDesconto);

            // Salvar venda
            return CompraRepository.salvarVenda(pecaId, sessaoId, usuarioId, poltronaId, precoFinalStr);

        } catch (Exception e) {
            System.err.println("Erro ao calcular desconto na compra: " + e.getMessage());
            return false;
        }
    }
}
