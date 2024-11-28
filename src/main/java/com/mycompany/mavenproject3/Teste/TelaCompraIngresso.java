package com.mycompany.mavenproject3.Teste;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class TelaCompraIngresso extends JFrame {
    private String nomeUsuario, cpfUsuario;
    private JPanel panelPoltronas;
    private JComboBox<String> comboPeca, comboSessao, comboArea;
    private JButton btnComprar;
    private JLabel lblPreco;
    private Map<String, Boolean[]> poltronasMap = new HashMap<>();
    private static final Map<String, Double> PRECOS_AREA = new HashMap<>();
    private JToggleButton[] botoesPoltronas; // Array para armazenar os botões das poltronas
    private int poltronaSelecionada = -1; // Índice da poltrona selecionada

    static {
        PRECOS_AREA.put("Plateia A", 40.00);
        PRECOS_AREA.put("Plateia B", 60.00);
        PRECOS_AREA.put("Camarote", 80.00);
        PRECOS_AREA.put("Frisa", 120.00);
        PRECOS_AREA.put("Balcão Nobre", 250.00);
    }

    private static final int PLATEIA_A = 25, PLATEIA_B = 100, CAMAROTE = 10, FRISA = 5, BALCAO_NOBRE = 50;

    public TelaCompraIngresso(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
        this.cpfUsuario = carregarCpfDoArquivo(nomeUsuario);
        inicializarInterface();
        carregarPoltronasExcel();
    }

    private void inicializarInterface() {
        setTitle("Compra de Ingresso");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelInfo = criarPainelInfo();
        JPanel panelSelecao = criarPainelSelecao();

        add(panelInfo, BorderLayout.NORTH);
        add(panelSelecao, BorderLayout.WEST);

        panelPoltronas = new JPanel(new GridLayout(0, 10, 5, 5));
        add(panelPoltronas, BorderLayout.CENTER);

        btnComprar = new JButton("Comprar");
        btnComprar.addActionListener(e -> comprarIngresso());
        add(btnComprar, BorderLayout.SOUTH);
    }

    private JPanel criarPainelInfo() {
        JPanel panelInfo = new JPanel(new GridLayout(2, 1));
        JLabel titulo = new JLabel("Compra de Ingresso - " + nomeUsuario, SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelInfo.add(titulo);

        JLabel labelCPF = new JLabel("CPF: " + cpfUsuario, SwingConstants.CENTER);
        labelCPF.setFont(new Font("Arial", Font.PLAIN, 14));
        panelInfo.add(labelCPF);

        return panelInfo;
    }

    private JPanel criarPainelSelecao() {
        JPanel panelSelecao = new JPanel(new GridLayout(4, 2));
        panelSelecao.add(new JLabel("Escolha a Peça", SwingConstants.CENTER));
        comboPeca = new JComboBox<>(new String[]{"Peça 1", "Peça 2", "Peça 3"});
        comboPeca.addActionListener(e -> atualizarPoltronas());
        panelSelecao.add(comboPeca);

        panelSelecao.add(new JLabel("Escolha a Sessão", SwingConstants.CENTER));
        comboSessao = new JComboBox<>(new String[]{"Manhã", "Tarde", "Noite"});
        comboSessao.addActionListener(e -> atualizarPoltronas());
        panelSelecao.add(comboSessao);

        panelSelecao.add(new JLabel("Escolha a Área", SwingConstants.CENTER));
        comboArea = new JComboBox<>(new String[]{"Plateia A", "Plateia B", "Frisa", "Camarote", "Balcão Nobre"});
        comboArea.addActionListener(e -> {
            atualizarPoltronas();
            atualizarPrecoArea();
        });
        panelSelecao.add(comboArea);

        lblPreco = new JLabel("Preço: R$ 0,00", SwingConstants.CENTER);
        lblPreco.setFont(new Font("Arial", Font.BOLD, 14));
        panelSelecao.add(lblPreco);

        return panelSelecao;
    }

    private String carregarCpfDoArquivo(String nomeUsuario) {
        File file = new File("usuarios.xlsx");
        if (!file.exists()) {
            return "CPF Não Encontrado";
        }

        try (FileInputStream fis = new FileInputStream(file); Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                String nome = row.getCell(0).getStringCellValue();
                if (nome.equals(nomeUsuario)) {
                    return row.getCell(3).getStringCellValue();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "CPF Não Encontrado";
    }

    private void atualizarPoltronas() {
        String area = (String) comboArea.getSelectedItem();
        String sessao = (String) comboSessao.getSelectedItem();
        String peca = (String) comboPeca.getSelectedItem();
        mostrarPoltronas(area, sessao, peca);
    }

    private void atualizarPrecoArea() {
        String area = (String) comboArea.getSelectedItem();
        Double preco = PRECOS_AREA.get(area);
        lblPreco.setText("Preço: R$ " + String.format("%.2f", preco));
    }

    private void mostrarPoltronas(String area, String sessao, String peca) {
        String chave = peca + "-" + sessao + "-" + area;
        Boolean[] poltronas = poltronasMap.computeIfAbsent(chave, k -> new Boolean[obterNumeroPoltronas(area)]);
        panelPoltronas.removeAll();

        botoesPoltronas = new JToggleButton[poltronas.length]; // Inicializa o array de botões

        for (int i = 0; i < poltronas.length; i++) {
            poltronas[i] = poltronas[i] == null ? false : poltronas[i];
            String poltronaNumero = gerarNumeroPoltrona(i);
            JToggleButton btnPoltrona = new JToggleButton(poltronaNumero);
            btnPoltrona.setFont(new Font("Arial", Font.PLAIN, 12));
            btnPoltrona.setText(poltronas[i] ? "Ocupada" : "Livre");
            btnPoltrona.setEnabled(!poltronas[i]);

            final int finalI = i; // Captura a variável para o uso no listener
            btnPoltrona.addActionListener(e -> {
                if (poltronaSelecionada != -1 && poltronaSelecionada != finalI) {
                    botoesPoltronas[poltronaSelecionada].setSelected(false);
                }
                poltronaSelecionada = btnPoltrona.isSelected() ? finalI : -1;
            });

            botoesPoltronas[i] = btnPoltrona; // Armazena o botão no array
            panelPoltronas.add(btnPoltrona);
        }

        panelPoltronas.revalidate();
        panelPoltronas.repaint();
    }

    private String gerarNumeroPoltrona(int index) {
        char coluna = (char) ('A' + (index % 26));
        int linha = (index / 26) + 1;
        return "" + coluna + linha;
    }

    private int obterNumeroPoltronas(String area) {
        switch (area) {
            case "Plateia A":
                return PLATEIA_A;
            case "Plateia B":
                return PLATEIA_B;
            case "Frisa":
                return FRISA;
            case "Camarote":
                return CAMAROTE;
            case "Balcão Nobre":
                return BALCAO_NOBRE;
            default:
                return 0;
        }
    }

    private void carregarPoltronasExcel() {
        File file = new File("vendas.xlsx");
        try {
            Workbook workbook;
            Sheet sheet;

            if (file.exists()) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    workbook = new XSSFWorkbook(fis);
                }
            } else {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet("Vendas");
                Row header = sheet.createRow(0);
                header.createCell(0).setCellValue("Peça");
                header.createCell(1).setCellValue("Sessão");
                header.createCell(2).setCellValue("Área");
                header.createCell(3).setCellValue("Estado Poltronas");
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    workbook.write(fos);
                }
            }

            sheet = workbook.getSheet("Vendas");
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;
                String peca = row.getCell(0).getStringCellValue();
                String sessao = row.getCell(1).getStringCellValue();
                String area = row.getCell(2).getStringCellValue();
                String poltronasEstado = row.getCell(3).getStringCellValue();
                String chave = peca + "-" + sessao + "-" + area;
                Boolean[] poltronas = new Boolean[obterNumeroPoltronas(area)];
                String[] estados = poltronasEstado.split(" ");
                for (int i = 0; i < estados.length; i++) {
                    poltronas[i] = estados[i].equals("Ocupada");
                }
                poltronasMap.put(chave, poltronas);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void comprarIngresso() {
        if (poltronaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione uma poltrona.");
            return;
        }

        String peca = (String) comboPeca.getSelectedItem();
        String sessao = (String) comboSessao.getSelectedItem();
        String area = (String) comboArea.getSelectedItem();
        String chave = peca + "-" + sessao + "-" + area;
        Boolean[] poltronas = poltronasMap.get(chave);

        if (poltronas == null || poltronaSelecionada >= poltronas.length) {
            JOptionPane.showMessageDialog(this, "Seleção inválida.");
            return;
        }

        if (!poltronas[poltronaSelecionada]) {
            poltronas[poltronaSelecionada] = true;
            gerarIngresso(peca, sessao, area, poltronaSelecionada);
            mostrarPoltronas(area, sessao, peca);
            salvarVendasExcel();
        } else {
            JOptionPane.showMessageDialog(this, "Poltrona já ocupada. Escolha outra poltrona.");
        }
    }

    private void gerarIngresso(String peca, String sessao, String area, int poltronaIndex) {
        String poltrona = gerarNumeroPoltrona(poltronaIndex);
        Double preco = PRECOS_AREA.get(area);
        String ingresso = "Ingresso Comprado\n" +
                "Usuário: " + nomeUsuario + "\n" +
                "CPF: " + cpfUsuario + "\n" +
                "Peça: " + peca + "\n" +
                "Sessão: " + sessao + "\n" +
                "Área: " + area + "\n" +
                "Poltrona: " + poltrona + "\n" +
                "Preço: R$ " + String.format("%.2f", preco);
        JOptionPane.showMessageDialog(this, ingresso, "Ingresso", JOptionPane.INFORMATION_MESSAGE);
    }

    private void salvarVendasExcel() {
        File file = new File("vendas.xlsx");

        try (Workbook workbook = file.exists() ? new XSSFWorkbook(new FileInputStream(file)) : new XSSFWorkbook()) {
            Sheet sheet = workbook.getSheet("Vendas");
            if (sheet == null) {
                sheet = workbook.createSheet("Vendas");
                Row header = sheet.createRow(0);
                header.createCell(0).setCellValue("Peça");
                header.createCell(1).setCellValue("Sessão");
                header.createCell(2).setCellValue("Área");
                header.createCell(3).setCellValue("Nome Usuário");
                header.createCell(4).setCellValue("CPF Usuário");
                header.createCell(5).setCellValue("Poltrona");
            }

            int rowNum = sheet.getPhysicalNumberOfRows();
            String peca = (String) comboPeca.getSelectedItem();
            String sessao = (String) comboSessao.getSelectedItem();
            String area = (String) comboArea.getSelectedItem();
            String poltrona = gerarNumeroPoltrona(poltronaSelecionada);

            Row row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(peca);
            row.createCell(1).setCellValue(sessao);
            row.createCell(2).setCellValue(area);
            row.createCell(3).setCellValue(nomeUsuario);
            row.createCell(4).setCellValue(cpfUsuario);
            row.createCell(5).setCellValue(poltrona);

            try (FileOutputStream fileOut = new FileOutputStream(file)) {
                workbook.write(fileOut);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaCompraIngresso tela = new TelaCompraIngresso("Usuário Teste");
            tela.setVisible(true);
        });
    }
}