package com.mycompany.mavenproject3.compra.view;

import com.mycompany.mavenproject3.core.SessaoUsuario;
import com.mycompany.mavenproject3.compra.controller.CompraController;
import com.mycompany.mavenproject3.supabase.SupabaseService;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class TelaCompra extends JFrame {

    private JComboBox<String> comboPeca;
    private JComboBox<String> comboSessao;
    private JLabel precoLabel;
    private JPanel painelAssentos;

    private final HashMap<String, String> mapaPecas = new HashMap<>();
    private final HashMap<String, String> mapaSessoes = new HashMap<>();
    private final Set<String> ocupadas = new HashSet<>();
    private final Map<String, String> mapaPoltronas = new HashMap<>();
    private String poltronaSelecionadaId = null;
    private String poltronaSelecionadaNome = null;

    private String assentoSelecionado = null;

    public TelaCompra() {
        if (SessaoUsuario.getUserId() == null) {
            JOptionPane.showMessageDialog(null, "Você precisa estar logado.");
            return;
        }

        setTitle("Comprar Ingressos");
        setSize(800, 600);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel topo = new JPanel(new GridLayout(2, 2, 10, 10));
        comboPeca = new JComboBox<>();
        comboSessao = new JComboBox<>();
        precoLabel = new JLabel("Preço: R$50,00");

        topo.add(new JLabel("Peça:"));
        topo.add(comboPeca);
        topo.add(new JLabel("Sessão:"));
        topo.add(comboSessao);

        add(topo, BorderLayout.NORTH);

        painelAssentos = new JPanel();
        painelAssentos.setLayout(new GridLayout(0, 10, 5, 5));
        JScrollPane scrollPane = new JScrollPane(painelAssentos);
        add(scrollPane, BorderLayout.CENTER);

        JButton confirmar = new JButton("Confirmar Compra");
        confirmar.addActionListener(e -> confirmarCompra());
        add(confirmar, BorderLayout.SOUTH);

        comboPeca.addActionListener(e -> carregarSessoes());
        comboSessao.addActionListener(e -> carregarAssentos());

        carregarPecas();
        setVisible(true);
    }

    private void carregarPecas() {
        try {
            String resposta = SupabaseService.get("/rest/v1/pecas?select=id,titulo", true);
            assert resposta != null;
            JSONArray json = new JSONArray(resposta);
            comboPeca.removeAllItems();
            mapaPecas.clear();

            for (int i = 0; i < json.length(); i++) {
                JSONObject peca = json.getJSONObject(i);
                String titulo = peca.getString("titulo");
                String id = peca.getString("id");
                mapaPecas.put(titulo, id);
                comboPeca.addItem(titulo);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar peças.");
        }
    }

    private void carregarSessoes() {
        comboSessao.removeAllItems();
        mapaSessoes.clear();

        String peca = (String) comboPeca.getSelectedItem();
        if (peca == null) return;

        try {
            String pecaId = mapaPecas.get(peca);
            String resposta = SupabaseService.get("/rest/v1/sessoes?peca_id=eq." + pecaId + "&select=id,horario", true);
            assert resposta != null;
            JSONArray json = new JSONArray(resposta);

            for (int i = 0; i < json.length(); i++) {
                JSONObject s = json.getJSONObject(i);
                String horario = s.getString("horario");
                String id = s.getString("id");
                mapaSessoes.put(horario, id);
                comboSessao.addItem(horario);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar sessões.");
        }
    }

    private void carregarAssentos() {
        painelAssentos.removeAll();
        ocupadas.clear();
        poltronaSelecionadaId = null;
        poltronaSelecionadaNome = null;

        String peca = (String) comboPeca.getSelectedItem();
        String horario = (String) comboSessao.getSelectedItem();
        String sessaoId = mapaSessoes.get(horario);
        if (peca == null || sessaoId == null) return;

        try {
            // Buscar reservas existentes
            String pecaId = mapaPecas.get(peca);
            String reservasJson = SupabaseService.get("/rest/v1/venda?sessao_id=eq." + sessaoId + "&select=poltrona_modelo_id", true);
            System.out.println("DEBUG RESPOSTA reservasJson = " + reservasJson);
            if (reservasJson == null || !reservasJson.trim().startsWith("[")) {
                throw new RuntimeException("Resposta inesperada da API de reservas: " + reservasJson);
            }
            JSONArray reservas = new JSONArray(reservasJson);
            for (int i = 0; i < reservas.length(); i++) {
                ocupadas.add(reservas.getJSONObject(i).getString("poltrona_modelo_id"));
            }

            // Buscar poltronas disponíveis (modelo fixo)
            String poltronasJson = SupabaseService.get("/rest/v1/poltrona_modelo?select=id,fileira,numero", true);
            JSONArray poltronas = new JSONArray(poltronasJson);
            mapaPoltronas.clear();

            for (int i = 0; i < poltronas.length(); i++) {
                JSONObject poltrona = poltronas.getJSONObject(i);
                String id = poltrona.getString("id");
                String nome = poltrona.getString("fileira") + poltrona.getInt("numero");

                mapaPoltronas.put(nome, id);

                JButton botao = new JButton(nome);
                if (ocupadas.contains(id)) {
                    botao.setEnabled(false);
                    botao.setBackground(Color.GRAY);
                } else {
                    botao.setBackground(Color.GREEN);
                    botao.addActionListener(e -> {
                        poltronaSelecionadaId = id;
                        poltronaSelecionadaNome = nome;
                        atualizarSelecaoVisual(nome);
                    });
                }

                painelAssentos.add(botao);
            }

            painelAssentos.revalidate();
            painelAssentos.repaint();

        } catch (Exception e) {
            e.printStackTrace(); // <-- ESSENCIAL PRA VER O ERRO
            JOptionPane.showMessageDialog(this, "Erro ao carregar poltronas ou reservas.");
        }
    }

        private void atualizarSelecaoVisual(String nomeSelecionado) {
        for (Component comp : painelAssentos.getComponents()) {
            if (comp instanceof JButton) {
                JButton botao = (JButton) comp;
                if (botao.getText().equals(nomeSelecionado)) {
                    botao.setBackground(Color.BLUE);
                    botao.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                } else if (!ocupadas.contains(botao.getText())) {
                    botao.setBackground(Color.GREEN);
                    botao.setBorder(UIManager.getBorder("Button.border"));
                }
            }
        }
    }

    private void confirmarCompra() {
        String peca = (String) comboPeca.getSelectedItem();
        String horario = (String) comboSessao.getSelectedItem();
        String sessaoId = mapaSessoes.get(horario);

        if (peca == null || horario == null || poltronaSelecionadaId == null) {
            JOptionPane.showMessageDialog(this, "Selecione todos os campos e uma poltrona.");
            return;
        }

        String pecaId = mapaPecas.get(peca);
        String userId = SessaoUsuario.getUserId();

        boolean sucesso = CompraController.realizarCompra(
                pecaId, sessaoId, userId, poltronaSelecionadaId, "50.00"
        );

        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Compra realizada com sucesso!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao realizar compra.");
        }
    }
}
