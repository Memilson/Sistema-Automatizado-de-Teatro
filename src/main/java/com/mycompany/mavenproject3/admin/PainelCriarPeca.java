package com.mycompany.mavenproject3.admin;

import javax.swing.*;
import java.awt.*;

public class PainelCriarPeca extends JPanel {
    public PainelCriarPeca() {
        setLayout(new GridLayout(4, 2));

        JTextField campoTitulo = new JTextField();
        JTextField campoHorario = new JTextField();
        JButton botaoSalvar = new JButton("Salvar Peça");

        add(new JLabel("Título da peça:"));
        add(campoTitulo);
        add(new JLabel("Horário da sessão:"));
        add(campoHorario);
        add(new JLabel());
        add(botaoSalvar);

        botaoSalvar.addActionListener(e -> {
            String titulo = campoTitulo.getText();
            String horario = campoHorario.getText();
            // Aqui insere no Supabase via API ou JDBC
        });
    }
}
