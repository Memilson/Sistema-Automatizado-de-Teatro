package com.mycompany.mavenproject3.admin.view;

import com.mycompany.mavenproject3.admin.controller.RelatorioController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import com.mycompany.mavenproject3.view.UIUtils;

public class PainelRelatorios extends BorderPane {

    private final VBox conteudo = new VBox(20);
    private final Button botaoGerar = new Button("Gerar Relatório Detalhado");
    private final Button botaoExportar = new Button("Exportar PDF");

    public PainelRelatorios() {
        this.setPadding(new Insets(20));

        Label titulo = new Label("📊 Relatórios Detalhados de Vendas");
        titulo.setFont(Font.font("Georgia", 22));
        titulo.setStyle("-fx-text-fill: #d4af37; -fx-effect: dropshadow(one-pass-box, #a6762d, 2, 0, 0, 0);");

        ScrollPane scroll = new ScrollPane(conteudo);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        conteudo.setPadding(new Insets(15));
        conteudo.setAlignment(Pos.TOP_CENTER);
        conteudo.setStyle("-fx-background-color: linear-gradient(to bottom, #0d0d0d, #1a1a1a);");

        botaoGerar.setOnAction(e -> RelatorioController.gerar(conteudo));
        botaoExportar.setOnAction(e -> RelatorioController.exportarPDF(conteudo));

        UIUtils.estilizarBotao(botaoGerar);
        UIUtils.estilizarBotao(botaoExportar);

        VBox topo = new VBox(10, titulo);
        topo.setAlignment(Pos.CENTER);
        topo.setPadding(new Insets(10));

        VBox rodape = new VBox(10, botaoGerar, botaoExportar);
        rodape.setAlignment(Pos.CENTER);
        rodape.setPadding(new Insets(20));

        this.setTop(topo);
        this.setCenter(scroll);
        this.setBottom(rodape);
        this.setStyle("-fx-background-color: linear-gradient(to bottom, #0d0d0d, #1a1a1a);");
    }

}
