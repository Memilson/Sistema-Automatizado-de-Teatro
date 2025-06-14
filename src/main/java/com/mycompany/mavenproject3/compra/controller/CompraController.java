package com.mycompany.mavenproject3.compra.controller;

import com.mycompany.mavenproject3.compra.repository.CompraRepository;
import com.mycompany.mavenproject3.supabase.SupabaseService;
import org.json.JSONArray;

public class CompraController {
    public static boolean realizarCompra(String pecaId, String sessaoId, String usuarioId, String poltronaId, String precoOriginalStr) {
        try {
            // Buscar assinatura
            String assinaturaId = SupabaseService.buscarAssinaturaId(usuarioId);
            double desconto = 0.0;

            if (assinaturaId != null) {
                desconto = CompraRepository.buscarDescontoAssinatura(assinaturaId);
            }

            // Buscar o preço base da poltrona
            String poltronaJson = SupabaseService.get(
                    "/rest/v1/poltrona_modelo?id=eq." + poltronaId + "&select=preco_base",
                    true
            );

            JSONArray poltronas = new JSONArray(poltronaJson);
            if (poltronas.isEmpty()) return false;

            double precoBase = poltronas.getJSONObject(0).optDouble("preco_base", 0.0);

            // Aplicar o desconto
            double precoComDesconto = precoBase * (1 - (desconto / 100.0));
            String precoFinalStr = String.format(java.util.Locale.US, "%.2f", precoComDesconto);

            // Salvar no banco
            return CompraRepository.salvarVenda(pecaId, sessaoId, usuarioId, poltronaId, precoFinalStr);

        } catch (Exception e) {
            System.err.println("Erro ao aplicar desconto e salvar venda: " + e.getMessage());
            return false;
        }
    }
}