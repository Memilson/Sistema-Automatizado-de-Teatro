package com.mycompany.mavenproject3.admin;

import com.mycompany.mavenproject3.supabase.SupabaseService;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PainelRelatorios extends JPanel {

    public PainelRelatorios() {
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("📊 Relatórios de Vendas", SwingConstants.CENTER);
        add(titulo, BorderLayout.NORTH);

        JTextArea areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        add(new JScrollPane(areaTexto), BorderLayout.CENTER);

        JButton botaoGerar = new JButton("Gerar Relatório");
        add(botaoGerar, BorderLayout.SOUTH);

        botaoGerar.addActionListener(e -> {
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

                areaTexto.setText(
                        "📅 Última venda: " + ultimaVenda + "\n" +
                                "🧾 Total de vendas: " + totalVendas + "\n" +
                                "💰 Total arrecadado: R$ " + String.format("%.2f", totalArrecadado) + "\n" +
                                "✅ VIPs usados: " + vipUsados + "\n" +
                                "🎟️ Vendas normais: " + (totalVendas - vipUsados)
                );

            } catch (Exception ex) {
                areaTexto.setText("Erro ao carregar relatório: " + ex.getMessage());
            }
        });
    }
}
