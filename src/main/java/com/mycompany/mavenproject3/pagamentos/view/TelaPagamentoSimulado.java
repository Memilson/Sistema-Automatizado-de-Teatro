package com.mycompany.mavenproject3.pagamentos.view;

import com.mycompany.mavenproject3.core.SessaoUsuario;
import com.mycompany.mavenproject3.supabase.SupabaseService;

import javax.swing.*;
import java.awt.*;

public class TelaPagamentoSimulado extends JFrame {

    public TelaPagamentoSimulado(String novoAssinaturaId, Runnable onSuccess) {
        setTitle("Pagamento Simulado");
        setSize(300, 250);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 5, 5));

        JTextField nomeField = new JTextField();
        JTextField numeroCartao = new JTextField();
        JTextField validade = new JTextField();
        JTextField cvv = new JTextField();

        add(new JLabel("Nome no Cartão:"));
        add(nomeField);
        add(new JLabel("Número do Cartão:"));
        add(numeroCartao);
        add(new JLabel("Validade (MM/AA):"));
        add(validade);
        add(new JLabel("CVV:"));
        add(cvv);

        JButton pagarBtn = new JButton("Simular Pagamento");
        add(pagarBtn);

        pagarBtn.addActionListener(e -> {
            if (nomeField.getText().isEmpty() || numeroCartao.getText().length() < 12) {
                JOptionPane.showMessageDialog(this, "Dados inválidos ou incompletos.");
                return;
            }

            // Simula "validação" e salva novo plano
            try {
                String userId = SessaoUsuario.getUserId();
                if (userId == null) return;

                String body = String.format("{\"id\": \"%s\", \"assinatura_id\": \"%s\"}", userId, novoAssinaturaId);
                String resposta = SupabaseService.post("/rest/v1/usuarios", body, true);

                if (resposta != null && !resposta.contains("error")) {
                    JOptionPane.showMessageDialog(this, "Plano alterado com sucesso!");
                    onSuccess.run();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao atualizar plano.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao processar pagamento.");
            }
        });

        setVisible(true);
    }
}
