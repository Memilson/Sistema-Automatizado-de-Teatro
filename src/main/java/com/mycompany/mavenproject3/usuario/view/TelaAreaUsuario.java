
package com.mycompany.mavenproject3.usuario.view;

import com.mycompany.mavenproject3.core.SessaoUsuario;
import com.mycompany.mavenproject3.login.view.TelaLogin;
import com.mycompany.mavenproject3.pagamentos.view.TelaPagamentoSimulado;
import com.mycompany.mavenproject3.supabase.SupabaseService;
import com.mycompany.mavenproject3.usuario.model.Usuario;
import com.mycompany.mavenproject3.usuario.service.UsuarioService;
import com.mycompany.mavenproject3.usuario.repository.UsuarioRepositorySupabase;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class TelaAreaUsuario extends JFrame {

    private final JLabel nomeLabel = new JLabel();
    private final JLabel cpfLabel = new JLabel();
    private final JLabel nascimentoLabel = new JLabel();
    private final JLabel assinaturaNomeLabel = new JLabel();
    private final JLabel vipLabel = new JLabel();
    private final JLabel prioridadeLabel = new JLabel();
    private final JLabel descontoLabel = new JLabel();
    private final JLabel tipoPagamentoLabel = new JLabel();
    private final JLabel ultimaCompraLabel = new JLabel();
    private final JLabel usadosVipLabel = new JLabel();
    private final JLabel restantesVipLabel = new JLabel();

    public TelaAreaUsuario() {
        if (SessaoUsuario.getUserId() == null) {
            JOptionPane.showMessageDialog(null, "Você precisa estar logado para acessar essa área.");
            new TelaLogin();
            dispose();
            return;
        }

        setTitle("Área do Usuário");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(0, 1, 8, 8));

        add(new JLabel("👤 Nome:"));            add(nomeLabel);
        add(new JLabel("📄 CPF:"));             add(cpfLabel);
        add(new JLabel("📅 Nascimento:"));      add(nascimentoLabel);
        add(new JLabel("📌 Assinatura:"));      add(assinaturaNomeLabel);
        add(new JLabel("⭐ Ingressos VIP/mês:")); add(vipLabel);
        add(new JLabel("⏱️ Prioridade reserva (h):")); add(prioridadeLabel);
        add(new JLabel("💸 Desconto (%):"));    add(descontoLabel);
        add(new JLabel("📆 Tipo de pagamento:")); add(tipoPagamentoLabel);
        add(new JLabel("🕓 Última compra:"));   add(ultimaCompraLabel);
        add(new JLabel("✅ VIPs usados este mês:")); add(usadosVipLabel);
        add(new JLabel("🎟️ VIPs restantes:")); add(restantesVipLabel);

        JComboBox<String> comboPlanos = new JComboBox<>();
        HashMap<String, String> mapaPlanoIdPorNome = new HashMap<>();

        try {
            String resposta = SupabaseService.get("/rest/v1/assinaturas?select=id,nome", true);
            JSONArray planos = new JSONArray(resposta);
            for (int i = 0; i < planos.length(); i++) {
                JSONObject plano = planos.getJSONObject(i);
                String nome = plano.getString("nome");
                String id = plano.getString("id");
                comboPlanos.addItem(nome);
                mapaPlanoIdPorNome.put(nome, id);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar planos.");
        }

        JButton confirmarPlanoBtn = new JButton("Alterar Plano (Simulado)");
        confirmarPlanoBtn.addActionListener(e -> {
            String nomeSelecionado = (String) comboPlanos.getSelectedItem();
            String novoPlanoId = mapaPlanoIdPorNome.get(nomeSelecionado);
            if (novoPlanoId == null) return;

            new TelaPagamentoSimulado(novoPlanoId, () -> {
                String userId = SessaoUsuario.getUserId();
                boolean sucesso = UsuarioService.atualizarPlanoViaRPC(userId, novoPlanoId);
                if (sucesso) {
                    JOptionPane.showMessageDialog(null, "Plano alterado com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao alterar o plano.");
                }

            });
        });

        add(new JLabel("📝 Escolher novo plano:"));
        add(comboPlanos);
        add(confirmarPlanoBtn);

        carregarDados();
        setVisible(true);
    }

    private void carregarDados() {
        String userId = SessaoUsuario.getUserId();
        Usuario usuario = UsuarioRepositorySupabase.buscarPorIdAuth(userId);
        if (usuario == null) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados do usuário.");
            dispose();
            return;
        }

        nomeLabel.setText(usuario.getNome());
        cpfLabel.setText(usuario.getCpf());
        nascimentoLabel.setText(usuario.getNascimento());

        try {
            String assinaturaId = usuario.getAssinaturaId();
            String assinaturaJson = SupabaseService.get("/rest/v1/assinaturas?id=eq." + assinaturaId, true);
            JSONArray assinaturaArray = new JSONArray(assinaturaJson);
            if (assinaturaArray.isEmpty()) return;

            JSONObject assinatura = assinaturaArray.getJSONObject(0);
            int vipMensal = assinatura.optInt("ingressos_vip_mes", 0);

            assinaturaNomeLabel.setText(assinatura.optString("nome", "N/A"));
            vipLabel.setText(String.valueOf(vipMensal));
            prioridadeLabel.setText(String.valueOf(assinatura.optInt("prioridade_reserva_horas", 0)));
            descontoLabel.setText(String.valueOf(assinatura.optDouble("desconto_percentual", 0.0)));
            tipoPagamentoLabel.setText(tipoPagamentoToTexto(assinatura.optInt("tipo_pagamento", 0)));

            // 🔁 Corrigido: busca TODAS as vendas do usuário (não só do mês)
            String filtro = "/rest/v1/venda?usuario_id=eq." + userId + "&select=data_compra,foi_ingresso_vip";
            String vendasJson = SupabaseService.get(filtro, true);
            JSONArray vendas = new JSONArray(vendasJson);

            int usadosVip = 0;
            String ultimaData = "Nunca";

            for (int i = 0; i < vendas.length(); i++) {
                JSONObject venda = vendas.getJSONObject(i);
                if (venda.optBoolean("foi_ingresso_vip", false)) usadosVip++;

                String data = venda.optString("data_compra", null);
                if (data != null && !data.isEmpty()) {
                    LocalDateTime dt = LocalDateTime.parse(data);
                    ultimaData = dt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                }
            }

            usadosVipLabel.setText(String.valueOf(usadosVip));
            restantesVipLabel.setText(String.valueOf(Math.max(0, vipMensal - usadosVip)));
            ultimaCompraLabel.setText(ultimaData);

        } catch (Exception e) {
            System.err.println("Erro ao carregar assinatura: " + e.getMessage());
        }
    }
    public TelaAreaUsuario(Usuario usuario) {
        SessaoUsuario.iniciar(usuario.getId());
        new TelaAreaUsuario(); // reutiliza o construtor atual
    }


    private String tipoPagamentoToTexto(int codigo) {
        return switch (codigo) {
            case 1 -> "Mensal";
            case 2 -> "Trimestral";
            case 3 -> "Anual";
            case 4 -> "Vitalício";
            default -> "Desconhecido";
        };
    }
}