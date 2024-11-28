package com.mycompany.mavenproject3;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TelaRelatorio extends JFrame {

    private static String nomeUsuario;
    private JTextArea textArea;

    public TelaRelatorio(String nomeUsuario) {
        setTitle("Relatório de Vendas");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        gerarRelatorio();
    }

    private void gerarRelatorio() {
        Map<String, Integer> vendasPorPeca = new HashMap<>();
        Map<String, Integer> ocupacaoPorSessao = new HashMap<>();
        Map<String, Double> lucroPorPeca = new HashMap<>();

        try (FileInputStream fis = new FileInputStream("vendas.xlsx");
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet("Vendas");
            if (sheet == null) {
                textArea.setText("Nenhuma venda registrada.");
                return;
            }

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header

                String peca = row.getCell(0).getStringCellValue();
                String sessao = row.getCell(1).getStringCellValue();
                String area = row.getCell(2).getStringCellValue();

                double preco = TelaCompraIngresso.PRECOS_AREA.get(area);

                vendasPorPeca.put(peca, vendasPorPeca.getOrDefault(peca, 0) + 1);
                ocupacaoPorSessao.put(sessao, ocupacaoPorSessao.getOrDefault(sessao, 0) + 1);
                lucroPorPeca.put(peca, lucroPorPeca.getOrDefault(peca, 0.0) + preco);
            }

            StringBuilder relatorio = new StringBuilder();
            relatorio.append("Relatório de Vendas\n\n");

            // Peça com mais e menos ingressos vendidos
            String pecaMaisVendida = vendasPorPeca.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
            String pecaMenosVendida = vendasPorPeca.entrySet().stream().min(Map.Entry.comparingByValue()).get().getKey();
            relatorio.append("Peça com mais ingressos vendidos: ").append(pecaMaisVendida).append("\n");
            relatorio.append("Peça com menos ingressos vendidos: ").append(pecaMenosVendida).append("\n\n");

            // Sessão com maior e menor ocupação
            String sessaoMaisOcupada = ocupacaoPorSessao.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
            String sessaoMenosOcupada = ocupacaoPorSessao.entrySet().stream().min(Map.Entry.comparingByValue()).get().getKey();
            relatorio.append("Sessão com maior ocupação: ").append(sessaoMaisOcupada).append("\n");
            relatorio.append("Sessão com menor ocupação: ").append(sessaoMenosOcupada).append("\n\n");

            // Lucro médio por peça
            for (Map.Entry<String, Double> entry : lucroPorPeca.entrySet()) {
                String peca = entry.getKey();
                double lucroMedio = entry.getValue() / vendasPorPeca.get(peca);
                relatorio.append("Lucro médio da peça ").append(peca).append(": R$ ").append(String.format("%.2f", lucroMedio)).append("\n");
            }

            textArea.setText(relatorio.toString());

        } catch (IOException e) {
            e.printStackTrace();
            textArea.setText("Erro ao gerar relatório: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaRelatorio relatorio = new TelaRelatorio(nomeUsuario);
            relatorio.setVisible(true);
        });
    }
}