package com.mycompany.mavenproject3.usuario.view;

import com.mycompany.mavenproject3.login.view.TelaLogin;
import com.mycompany.mavenproject3.usuario.controller.AreaUsuarioController;
import com.mycompany.mavenproject3.usuario.dto.UsuarioDashboardDTO;
import org.json.JSONArray;

import javax.swing.*;
import java.awt.*;

public class TelaAreaUsuario extends JFrame {

    private final AreaUsuarioController controller;

    public TelaAreaUsuario(AreaUsuarioController controller) {
        this.controller = controller;

        String userId = controller.getUserIdLogado();
        if (userId == null) {
            JOptionPane.showMessageDialog(null, "Você precisa estar logado.");
            new TelaLogin();
            dispose();
            return;
        }

        UsuarioDashboardDTO dto = controller.carregarDashboard(userId);
        if (dto == null) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados.");
            dispose();
            return;
        }

        setTitle("Área do Usuário");
        setSize(600, 750);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(0, 1, 8, 8));

        add(new JLabel("👤 Nome:"));                 add(new JLabel(dto.getNome()));
        add(new JLabel("📄 CPF:"));                  add(new JLabel(dto.getCpf()));
        add(new JLabel("📅 Nascimento:"));           add(new JLabel(dto.getNascimento()));
        add(new JLabel("📌 Assinatura:"));           add(new JLabel(dto.getAssinaturaNome()));
        add(new JLabel("⭐ Ingressos VIP/mês:"));     add(new JLabel(String.valueOf(dto.getIngressosVipMes())));
        add(new JLabel("⏱️ Prioridade reserva (h):")); add(new JLabel(String.valueOf(dto.getPrioridadeReservaHoras())));
        add(new JLabel("💸 Desconto (%):"));         add(new JLabel(String.valueOf(dto.getDescontoPercentual())));
        add(new JLabel("📆 Tipo de pagamento:"));    add(new JLabel(dto.getTipoPagamento()));
        add(new JLabel("🕓 Última compra:"));        add(new JLabel(dto.getUltimaCompra()));
        add(new JLabel("✅ VIPs usados este mês:")); add(new JLabel(String.valueOf(dto.getUsadosVip())));
        add(new JLabel("🎟️ VIPs restantes:"));      add(new JLabel(String.valueOf(dto.getRestantesVip())));

        JButton botaoCartao = new JButton("💳 Cadastrar Cartão");
        JButton botaoPlano = new JButton("📦 Mudar Plano");

        botaoCartao.addActionListener(e -> {
            JTextField campoNumero = new JTextField();
            JTextField campoValidade = new JTextField();
            JTextField campoCVV = new JTextField();
            JTextField campoNome = new JTextField();

            JPanel painel = new JPanel(new GridLayout(0, 1));
            painel.add(new JLabel("Número do cartão:"));
            painel.add(campoNumero);
            painel.add(new JLabel("Validade (MM/AA):"));
            painel.add(campoValidade);
            painel.add(new JLabel("CVV:"));
            painel.add(campoCVV);
            painel.add(new JLabel("Nome no cartão:"));
            painel.add(campoNome);

            int resposta = JOptionPane.showConfirmDialog(this, painel, "Cadastrar Cartão", JOptionPane.OK_CANCEL_OPTION);
            if (resposta == JOptionPane.OK_OPTION) {
                controller.marcarCartaoComoVerificado();
                JOptionPane.showMessageDialog(this, "Cartão salvo com sucesso! Plano liberado.");
            }
        });

        botaoPlano.addActionListener(e -> {
            if (!controller.isCartaoVerificado()) {
                JOptionPane.showMessageDialog(this, "Você precisa cadastrar um cartão antes de mudar o plano.");
                return;
            }

            try {
                JSONArray planos = controller.buscarPlanos();

                if (planos.length() == 0) {
                    JOptionPane.showMessageDialog(this, "Nenhum plano disponível.");
                    return;
                }

                String[] nomes = controller.extrairNomesPlanos(planos);
                JComboBox<String> combo = new JComboBox<>(nomes);

                int resultado = JOptionPane.showConfirmDialog(this, combo, "Selecione o novo plano", JOptionPane.OK_CANCEL_OPTION);
                if (resultado == JOptionPane.OK_OPTION) {
                    String nomeSelecionado = (String) combo.getSelectedItem();
                    String idSelecionado = controller.encontrarIdPlano(planos, nomeSelecionado);

                    boolean sucesso = controller.atualizarPlano(userId, idSelecionado);
                    JOptionPane.showMessageDialog(this, sucesso ? "Plano atualizado com sucesso!" : "Erro ao atualizar plano.");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao carregar os planos.");
            }
        });

        add(botaoCartao);
        add(botaoPlano);

        setVisible(true);
    }
}
