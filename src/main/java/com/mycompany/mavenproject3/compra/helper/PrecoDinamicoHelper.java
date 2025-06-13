package com.mycompany.mavenproject3.compra.helper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PrecoDinamicoHelper {

    public static double calcularPreco(JSONObject poltronaModelo, JSONArray todasPoltronasSessao, int totalPoltronas, double descontoPercentual) {
        double precoBase = poltronaModelo.optDouble("preco_base", 0);
        String setor = poltronaModelo.optString("setor", "");

        // 1. Ocupação da sessão
        int ocupadas = 0;
        for (int i = 0; i < todasPoltronasSessao.length(); i++) {
            JSONObject p = todasPoltronasSessao.getJSONObject(i);
            if (p.optBoolean("ocupada", false)) {
                ocupadas++;
            }
        }
        double ocupacao = totalPoltronas == 0 ? 0 : (double) ocupadas / totalPoltronas;

        // 2. Taxa por ocupação (pico de demanda)
        double fatorOcupacao;
        if (ocupacao >= 0.9) fatorOcupacao = 1.5;
        else if (ocupacao >= 0.7) fatorOcupacao = 1.3;
        else if (ocupacao >= 0.5) fatorOcupacao = 1.15;
        else fatorOcupacao = 1.0;

        // 3. Adicional por setor premium
        Map<String, Double> fatorSetor = new HashMap<>();
        fatorSetor.put("Camarote", 1.4);
        fatorSetor.put("Frisa", 1.25);
        fatorSetor.put("Balcao Nobre", 1.2);
        fatorSetor.put("Plateia A", 1.15);
        fatorSetor.put("Plateia B", 1.0);

        double fatorDoSetor = fatorSetor.getOrDefault(setor, 1.0);

        // 4. Desconto da assinatura (se houver)
        double precoParcial = precoBase * fatorOcupacao * fatorDoSetor;
        double descontoFinal = precoParcial * (descontoPercentual / 100);

        return Math.max(precoParcial - descontoFinal, 0);
    }
}
