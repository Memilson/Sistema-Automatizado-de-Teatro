package com.mycompany.mavenproject3.dados.view;

import com.mycompany.mavenproject3.Main;
import com.mycompany.mavenproject3.core.SessaoUsuario;
import com.mycompany.mavenproject3.common.ValidadorCPF;
import com.mycompany.mavenproject3.supabase.SupabaseService;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TelaDadosComplementares extends JFrame {
    private final JTextField nomeField;
    private final JFormattedTextField cpfField;
    private final JFormattedTextField nascimentoField;
    private final JFormattedTextField telefoneField;
    private final JLabel statusLabel;

    public TelaDadosComplementares() {
        setTitle("Dados Complementares");
        setSize(350, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));

        nomeField = new JTextField();
        cpfField = createFormattedField("###.###.###-##");
        nascimentoField = createFormattedField("##/##/####");
        telefoneField = createFormattedField("(##) #####-####");

        formPanel.add(new JLabel("Nome:"));        formPanel.add(nomeField);
        formPanel.add(new JLabel("CPF:"));         formPanel.add(cpfField);
        formPanel.add(new JLabel("Nascimento:"));  formPanel.add(nascimentoField);
        formPanel.add(new JLabel("Telefone:"));    formPanel.add(telefoneField);

        statusLabel = new JLabel("", SwingConstants.CENTER);

        JButton salvarButton = new JButton("Salvar");
        JButton voltarButton = new JButton("Menu Principal");

        salvarButton.addActionListener(this::salvarDados);
        voltarButton.addActionListener(e -> {
            new Main();
            dispose();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(salvarButton);
        buttonPanel.add(voltarButton);

        add(statusLabel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void salvarDados(ActionEvent e) {
        String nome = nomeField.getText();
        String cpf = cpfField.getText();
        String telefone = telefoneField.getText();
        String nascimentoStr = nascimentoField.getText();

        if (!ValidadorCPF.validar(cpf)) {
            statusLabel.setText("CPF inválido.");
            return;
        }

        try {
            Date nascimento = new SimpleDateFormat("dd/MM/yyyy").parse(nascimentoStr);
            String nascimentoFormatado = new SimpleDateFormat("yyyy-MM-dd").format(nascimento);

            String userId = SessaoUsuario.getUserId();
            if (userId == null) {
                statusLabel.setText("Usuário não autenticado.");
                return;
            }

            boolean sucesso = SupabaseService.salvarDadosComplementares(userId, nome, cpf, nascimentoFormatado, telefone);
            statusLabel.setText(sucesso ? "Dados salvos com sucesso!" : "Erro ao salvar dados.");
        } catch (ParseException ex) {
            statusLabel.setText("Data inválida.");
        }
    }

    private JFormattedTextField createFormattedField(String mask) {
        try {
            MaskFormatter formatter = new MaskFormatter(mask);
            formatter.setPlaceholderCharacter('_');
            return new JFormattedTextField(formatter);
        } catch (ParseException e) {
            return new JFormattedTextField();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaDadosComplementares::new);
    }
}
