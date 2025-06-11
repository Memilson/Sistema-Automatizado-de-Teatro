package com.mycompany.mavenproject3.usuario.view;

import com.mycompany.mavenproject3.core.SessaoUsuario;
import com.mycompany.mavenproject3.login.view.TelaLogin;
import com.mycompany.mavenproject3.usuario.controller.UsuarioController;
import com.mycompany.mavenproject3.usuario.dto.UsuarioDashboardDTO;

import javax.swing.*;
import java.awt.*;

public class TelaAreaUsuario extends JFrame {

    private final UsuarioController controller;

    public TelaAreaUsuario(UsuarioController controller) {
        this.controller = controller;

        String userId = SessaoUsuario.getUserId();
        if (userId == null) {
            JOptionPane.showMessageDialog(null, "Você precisa estar logado.");
            new TelaLogin();
            dispose();
            return;
        }

        UsuarioDashboardDTO dto = controller.carregarDashboardUsuario(userId);
        if (dto == null) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados.");
            dispose();
            return;
        }

        setTitle("Área do Usuário");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(0, 1, 8, 8));

        add(new JLabel("👤 Nome:"));            add(new JLabel(dto.getNome()));
        add(new JLabel("📄 CPF:"));             add(new JLabel(dto.getCpf()));
        add(new JLabel("📅 Nascimento:"));      add(new JLabel(dto.getNascimento()));
        add(new JLabel("📌 Assinatura:"));      add(new JLabel(dto.getAssinaturaNome()));
        add(new JLabel("⭐ Ingressos VIP/mês:")); add(new JLabel(String.valueOf(dto.getIngressosVipMes())));
        add(new JLabel("⏱️ Prioridade reserva (h):")); add(new JLabel(String.valueOf(dto.getPrioridadeReservaHoras())));
        add(new JLabel("💸 Desconto (%):"));    add(new JLabel(String.valueOf(dto.getDescontoPercentual())));
        add(new JLabel("📆 Tipo de pagamento:")); add(new JLabel(dto.getTipoPagamento()));
        add(new JLabel("🕓 Última compra:"));   add(new JLabel(dto.getUltimaCompra()));
        add(new JLabel("✅ VIPs usados este mês:")); add(new JLabel(String.valueOf(dto.getUsadosVip())));
        add(new JLabel("🎟️ VIPs restantes:")); add(new JLabel(String.valueOf(dto.getRestantesVip())));

        setVisible(true);
    }
}
