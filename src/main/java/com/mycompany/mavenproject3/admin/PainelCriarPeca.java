package com.mycompany.mavenproject3.admin;

import com.mycompany.mavenproject3.supabase.SupabaseService;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.util.UUID;

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

            if (titulo.isEmpty() || horario.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
                return;
            }

            try {
                UUID pecaId = UUID.randomUUID();
                JSONObject novaPeca = new JSONObject();
                novaPeca.put("id", pecaId.toString());
                novaPeca.put("titulo", titulo);

                SupabaseService.post("/rest/v1/pecas", novaPeca.toString(), true);

                JSONObject novaSessao = new JSONObject();
                novaSessao.put("id", UUID.randomUUID().toString());
                novaSessao.put("horario", horario);
                novaSessao.put("peca_id", pecaId.toString());
                SupabaseService.post("/rest/v1/sessoes", novaSessao.toString(), true);
                JOptionPane.showMessageDialog(this, "Peça e sessão criadas com sucesso!");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao criar peça/sessão.");
            }
        });

    }}