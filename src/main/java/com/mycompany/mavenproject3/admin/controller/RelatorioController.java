package com.mycompany.mavenproject3.admin.controller;

import com.mycompany.mavenproject3.supabase.SupabaseService;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class RelatorioController {

    public static void gerar(VBox destino) {
        new Thread(() -> {
            try {
                String resposta = SupabaseService.get("/rest/v1/venda?select=*,usuarios(*),pecas(*),poltrona_modelo(*)", true);
                JSONArray vendas = new JSONArray(resposta);

                Map<String, Integer> vendasPorPeca = new HashMap<>();
                Map<String, Integer> setores = new HashMap<>();
                Map<String, Integer> faixasEtarias = new TreeMap<>();
                double total = 0;

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                String ultimaVenda = "Nunca";
                LocalDateTime ultimaVendaData = null;

                for (int i = 0; i < vendas.length(); i++) {
                    JSONObject v = vendas.getJSONObject(i);
                    total += v.optDouble("preco", 0);

                    JSONObject peca = v.optJSONObject("pecas");
                    String nomePeca = (peca != null) ? peca.optString("titulo", "Desconhecida") : "Desconhecida";
                    vendasPorPeca.put(nomePeca, vendasPorPeca.getOrDefault(nomePeca, 0) + 1);

                    JSONObject modelo = v.optJSONObject("poltrona_modelo");
                    String setor = (modelo != null) ? modelo.optString("setor", "Setor N/D") : "Setor N/D";
                    setores.put(setor, setores.getOrDefault(setor, 0) + 1);

                    JSONObject usuario = v.optJSONObject("usuarios");
                    if (usuario != null && usuario.has("nascimento")) {
                        String nascimentoStr = usuario.getString("nascimento");
                        LocalDate nascimento = LocalDate.parse(nascimentoStr);
                        int idade = Period.between(nascimento, LocalDate.now()).getYears();
                        String faixa = getFaixaEtaria(idade);
                        faixasEtarias.put(faixa, faixasEtarias.getOrDefault(faixa, 0) + 1);
                    }

                    String dataStr = v.optString("data_compra", null);
                    if (dataStr != null) {
                        LocalDateTime dataVenda = LocalDateTime.parse(dataStr);
                        if (ultimaVendaData == null || dataVenda.isAfter(ultimaVendaData)) {
                            ultimaVendaData = dataVenda;
                            ultimaVenda = formatter.format(dataVenda);
                        }
                    }
                }

                String finalUltimaVenda = ultimaVenda;
                double finalTotal = total;

                Platform.runLater(() -> {
                    destino.getChildren().clear();

                    Label resumo = new Label(
                            "\uD83D\uDCC5 Última Venda: " + finalUltimaVenda + "\n" +
                                    "\uD83D\uDCB0 Total Arrecadado: R$ " + String.format(Locale.US, "%.2f", finalTotal)
                    );
                    resumo.setStyle("-fx-font-family: 'Georgia'; -fx-text-fill: white; -fx-font-size: 16;");
                    VBox.setMargin(resumo, new Insets(10));

                    destino.getChildren().addAll(
                            resumo,
                            gerarGrafico("\uD83C\uDFAD Vendas por Peça", vendasPorPeca),
                            gerarGrafico("\uD83E\uDEA1 Vendas por Setor", setores),
                            gerarGrafico("\uD83D\uDC65 Faixas Etárias", faixasEtarias)
                    );
                });

            } catch (Exception ex) {
                Platform.runLater(() -> {
                    destino.getChildren().clear();
                    Label erro = new Label("Erro ao gerar relatório: " + ex.getMessage());
                    erro.setStyle("-fx-text-fill: red;");
                    destino.getChildren().add(erro);
                });
            }
        }).start();
    }

    public static void exportarPDF(VBox conteudo) {
        try {
            WritableImage snapshot = conteudo.snapshot(new SnapshotParameters(), null);
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Salvar Relatório como Imagem");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png"));
            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                ImageIO.write(javafx.embed.swing.SwingFXUtils.fromFXImage(snapshot, null), "png", file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getFaixaEtaria(int idade) {
        if (idade <= 17) return "0-17";
        else if (idade <= 24) return "18-24";
        else if (idade <= 34) return "25-34";
        else if (idade <= 49) return "35-49";
        else return "50+";
    }

    private static VBox gerarGrafico(String titulo, Map<String, Integer> dados) {
        VBox container = new VBox(10);
        Label label = new Label(titulo);
        label.setStyle("-fx-font-family: 'Georgia'; -fx-text-fill: #d4af37; -fx-font-size: 18;");

        PieChart chart = new PieChart();
        chart.setLabelsVisible(true);
        chart.setLegendVisible(true);
        chart.setClockwise(true);

        dados.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .forEach(entry -> {
                    chart.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
                });

        container.getChildren().addAll(label, chart);
        container.setPadding(new Insets(10));
        return container;
    }
}
