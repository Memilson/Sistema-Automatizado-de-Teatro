package com.mycompany.mavenproject3.admin.controller;

import com.mycompany.mavenproject3.supabase.SupabaseService;
import javafx.scene.control.TextArea;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RelatorioController {

    public static void gerarFX(TextArea destino) {
        try {
            String resposta = SupabaseService.get("/rest/v1/venda?select=preco,data_compra,foi_ingresso_vip", true);
            JSONArray vendas = new JSONArray(resposta);

            int totalVendas = vendas.length();
            int vipUsados = 0;
            double totalArrecadado = 0.0;
            String ultimaVenda = "Nunca";

            for (int i = 0; i < vendas.length(); i++) {
                JSONObject venda = vendas.getJSONObject(i);
                totalArrecadado += venda.optDouble("preco", 0.0);

                if (venda.optBoolean("foi_ingresso_vip", false)) {
                    vipUsados++;
                }

                String dataStr = venda.optString("data_compra", null);
                if (dataStr != null && !dataStr.isEmpty()) {
                    LocalDateTime data = LocalDateTime.parse(dataStr);
                    String formatada = data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                    if (ultimaVenda.equals("Nunca") || data.isAfter(LocalDateTime.parse(ultimaVenda, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))) {
                        ultimaVenda = formatada;
                    }
                }
            }

            destino.setText(
                    "📅 Última venda: " + ultimaVenda + "\n" +
                            "🧾 Total de vendas: " + totalVendas + "\n" +
                            "💰 Total arrecadado: R$ " + String.format("%.2f", totalArrecadado) + "\n" +
                            "✅ VIPs usados: " + vipUsados + "\n" +
                            "🎟️ Vendas normais: " + (totalVendas - vipUsados)
            );

        } catch (Exception ex) {
            destino.setText("Erro ao carregar relatório: " + ex.getMessage());
        }
    }
}
