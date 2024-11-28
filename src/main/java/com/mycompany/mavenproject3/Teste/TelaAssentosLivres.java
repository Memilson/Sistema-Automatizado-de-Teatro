package com.mycompany.mavenproject3.Teste;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
public class TelaAssentosLivres extends JFrame {
    private String nomeUsuario, cpfUsuario;
    private JPanel panelPoltronas;
    private JComboBox<String> comboPeca, comboSessao, comboArea;
    private Map<String, Boolean[]> poltronasMap = new HashMap<>();
    private static final int PLATEIA_A = 25, PLATEIA_B = 50, CAMAROTE = 10, FRISA = 5, BALCAO_NOBRE = 50;
    public TelaAssentosLivres(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
        this.cpfUsuario = carregarCpfDoArquivo(nomeUsuario);
        setTitle("Ver Assentos Livres");
        setSize(600,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new GridLayout(2,1));
        JLabel titulo = new JLabel("Assentos Livres - " + nomeUsuario, SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelInfo.add(titulo);
        JLabel labelCPF = new JLabel("CPF: " + cpfUsuario, SwingConstants.CENTER);
        labelCPF.setFont(new Font("Arial", Font.PLAIN, 14));
        panelInfo.add(labelCPF);
        add(panelInfo, BorderLayout.NORTH);
        JPanel panelSelecao = new JPanel();
        panelSelecao.setLayout(new GridLayout(4,2));
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
        panelPoltronas.setLayout(new GridLayout(0,10,5,5));
        add(panelPoltronas, BorderLayout.CENTER);
        carregarPoltronasExcel();
    }
    private String carregarCpfDoArquivo(String nomeUsuario) {
        File file = new File("usuarios.xlsx");
        if (file.exists()) {
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
        }
        return "CPF Não Encontrado";
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
            JButton btnPoltrona = new JButton(poltronaNumero);
            btnPoltrona.setFont(new Font("Arial", Font.PLAIN, 12));
            if (poltronas[i]) {
                btnPoltrona.setText("Ocupada");
                btnPoltrona.setEnabled(false);
            } else {
                btnPoltrona.setText("Livre");
                btnPoltrona.setEnabled(true);
            }
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
            case "Plateia A": return PLATEIA_A;
            case "Plateia B": return PLATEIA_B;
            case "Frisa": return FRISA;
            case "Camarote": return CAMAROTE;
            case "Balcão Nobre": return BALCAO_NOBRE;
            default: return 0;
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
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaAssentosLivres tela = new TelaAssentosLivres("Usuário Teste");
            tela.setVisible(true);
        });
    }
}
