package com.mycompany.mavenproject3;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class TelaCompraIngresso extends JFrame {
    private String nomeUsuario;
    private String cpfUsuario;
    private JPanel panelPoltronas;
    private JComboBox<String> comboPeca, comboSessao, comboArea;
    private JButton btnComprar;

    private Map<String, Boolean[]> poltronasMap = new HashMap<>();

    private static final int PLATEIA_A = 25;
    private static final int PLATEIA_B = 50;
    private static final int CAMAROTE = 10;
    private static final int FRISA = 5;
    private static final int BALCAO_NOBRE = 50;

    // Construtor da tela com nome do usuário
    public TelaCompraIngresso(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
        this.cpfUsuario = carregarCpfDoArquivo(nomeUsuario);  // Carregar CPF do arquivo

        setTitle("Compra de Ingresso");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new GridLayout(2, 1));

        JLabel titulo = new JLabel("Compra de Ingresso - " + nomeUsuario, SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelInfo.add(titulo);

        JLabel labelCPF = new JLabel("CPF: " + cpfUsuario, SwingConstants.CENTER);
        labelCPF.setFont(new Font("Arial", Font.PLAIN, 14));
        panelInfo.add(labelCPF);

        add(panelInfo, BorderLayout.NORTH);

        JPanel panelSelecao = new JPanel();
        panelSelecao.setLayout(new GridLayout(4, 2));

        panelSelecao.add(new JLabel("Escolha a Peça", SwingConstants.CENTER));
        String[] pecas = {"Peça 1", "Peça 2", "Peça 3"};
        comboPeca = new JComboBox<>(pecas);
        comboPeca.addActionListener(e -> atualizarPoltronas());
        panelSelecao.add(comboPeca);

        panelSelecao.add(new JLabel("Escolha a Sessão", SwingConstants.CENTER));
        String[] sessoes = {"Manhã", "Tarde", "Noite"};
        comboSessao = new JComboBox<>(sessoes);
        comboSessao.addActionListener(e -> atualizarPoltronas());
        panelSelecao.add(comboSessao);

        panelSelecao.add(new JLabel("Escolha a Área", SwingConstants.CENTER));
        String[] areas = {"Plateia A", "Plateia B", "Frisa", "Camarote", "Balcão Nobre"};
        comboArea = new JComboBox<>(areas);
        comboArea.addActionListener(e -> atualizarPoltronas());
        panelSelecao.add(comboArea);

        add(panelSelecao, BorderLayout.WEST);

        panelPoltronas = new JPanel();
        panelPoltronas.setLayout(new GridLayout(0, 10, 5, 5));
        add(panelPoltronas, BorderLayout.CENTER);

        btnComprar = new JButton("Comprar");
        btnComprar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comprarIngresso();
            }
        });
        add(btnComprar, BorderLayout.SOUTH);

        carregarPoltronasExcel();
    }

    private String carregarCpfDoArquivo(String nomeUsuario) {
        File file = new File("usuarios.xlsx");

        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file); Workbook workbook = new XSSFWorkbook(fis)) {
                Sheet sheet = workbook.getSheetAt(0); // Supondo que as informações estejam na primeira aba

                // Percorrer as linhas do Excel e buscar o nome do usuário
                for (Row row : sheet) {
                    String nome = row.getCell(0).getStringCellValue();  // Nome do usuário na primeira coluna
                    if (nome.equals(nomeUsuario)) {
                        return row.getCell(3).getStringCellValue();  // Retorna o CPF da segunda coluna
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "CPF Não Encontrado";  // Caso o nome não seja encontrado
    }

    private void atualizarPoltronas() {
        String area = (String) comboArea.getSelectedItem();
        String sessao = (String) comboSessao.getSelectedItem();
        String peca = (String) comboPeca.getSelectedItem();

        mostrarPoltronas(area, sessao, peca);
    }

    private void mostrarPoltronas(String area, String sessao, String peca) {
        String chave = peca + "-" + sessao + "-" + area;

        if (!poltronasMap.containsKey(chave)) {
            int numPoltronas = obterNumeroPoltronas(area);
            Boolean[] poltronas = new Boolean[numPoltronas];
            for (int i = 0; i < numPoltronas; i++) {
                poltronas[i] = false;
            }
            poltronasMap.put(chave, poltronas);
        }

        panelPoltronas.removeAll();

        Boolean[] poltronas = poltronasMap.get(chave);

        for (int i = 0; i < poltronas.length; i++) {
            String poltronaNumero = gerarNumeroPoltrona(i);
            JToggleButton btnPoltrona = new JToggleButton(poltronaNumero);
            btnPoltrona.setFont(new Font("Arial", Font.PLAIN, 12));

            if (poltronas[i]) {
                btnPoltrona.setText("Ocupada");
                btnPoltrona.setEnabled(false);
            } else {
                btnPoltrona.setText("Livre");
            }

            int finalI = i;
            btnPoltrona.addActionListener(e -> {
                poltronas[finalI] = !poltronas[finalI];
                if (poltronas[finalI]) {
                    btnPoltrona.setText("Ocupada");
                    btnPoltrona.setEnabled(false);
                } else {
                    btnPoltrona.setText("Livre");
                }
            });

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

        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file); Workbook workbook = new XSSFWorkbook(fis)) {
                Sheet sheet = workbook.getSheet("Vendas");

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
    }

    private void comprarIngresso() {
        String peca = (String) comboPeca.getSelectedItem();
        String sessao = (String) comboSessao.getSelectedItem();
        String area = (String) comboArea.getSelectedItem();

        String chave = peca + "-" + sessao + "-" + area;
        Boolean[] poltronas = poltronasMap.get(chave);

        if (poltronas == null) {
            JOptionPane.showMessageDialog(this, "Seleção inválida.");
            return;
        }

        boolean poltronaEncontrada = false;

        for (int i = 0; i < poltronas.length; i++) {
            if (!poltronas[i]) {
                poltronas[i] = true;
                poltronaEncontrada = true;
                gerarIngresso(peca, sessao, area, i);
                break;
            }
        }

        if (!poltronaEncontrada) {
            JOptionPane.showMessageDialog(this, "Não há assentos disponíveis nesta seleção.");
            return;
        }

        mostrarPoltronas(area, sessao, peca);
        salvarVendasExcel();
    }

    private void gerarIngresso(String peca, String sessao, String area, int poltronaIndex) {
        String poltrona = gerarNumeroPoltrona(poltronaIndex);

        String ingresso = "Ingresso Comprado\n" +
                "Usuário: " + nomeUsuario + "\n" +
                "CPF: " + cpfUsuario + "\n" +
                "Peça: " + peca + "\n" +
                "Sessão: " + sessao + "\n" +
                "Área: " + area + "\n" +
                "Poltrona: " + poltrona;

        JOptionPane.showMessageDialog(this, ingresso, "Ingresso", JOptionPane.INFORMATION_MESSAGE);
    }

    private void salvarVendasExcel() {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Vendas");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Peça");
            headerRow.createCell(1).setCellValue("Sessão");
            headerRow.createCell(2).setCellValue("Área");
            headerRow.createCell(3).setCellValue("Poltronas");

            int rowNum = 1;

            for (Map.Entry<String, Boolean[]> entry : poltronasMap.entrySet()) {
                String chave = entry.getKey();
                Boolean[] poltronas = entry.getValue();

                String[] chaveParts = chave.split("-");

                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(chaveParts[0]);
                row.createCell(1).setCellValue(chaveParts[1]);
                row.createCell(2).setCellValue(chaveParts[2]);

                StringBuilder poltronasEstado = new StringBuilder();
                for (Boolean ocupado : poltronas) {
                    poltronasEstado.append(ocupado ? "Ocupada " : "Livre ");
                }
                row.createCell(3).setCellValue(poltronasEstado.toString().trim());
            }

            try (FileOutputStream fileOut = new FileOutputStream(new File("vendas.xlsx"))) {
                workbook.write(fileOut);
            }

            JOptionPane.showMessageDialog(this, "Vendas salvas com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao salvar vendas.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaCompraIngresso tela = new TelaCompraIngresso("Usuário Teste");
            tela.setVisible(true);
        });
    }
}
