package com.mycompany.mavenproject3.admin.view;

import com.mycompany.mavenproject3.admin.controller.RelatorioController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class PainelRelatorios extends BorderPane {

    private final TextArea areaTexto = new TextArea();
    private final Button botaoGerar = new Button("Gerar Relatório");

    public PainelRelatorios() {
        this.setPadding(new Insets(20));

        Label titulo = new Label("\uD83D\uDCCA Relatórios de Vendas");
        titulo.setFont(Font.font("Georgia", 22));
        titulo.setStyle("-fx-text-fill: #d4af37;");

        areaTexto.setEditable(false);
        areaTexto.setWrapText(true);
        areaTexto.setStyle("-fx-font-family: 'Consolas'; -fx-control-inner-background: #1a1a1a; -fx-text-fill: white;");

        botaoGerar.setOnAction(e -> RelatorioController.gerarFX(areaTexto));
        botaoGerar.setStyle("-fx-background-color: #d4af37; -fx-text-fill: black; -fx-font-weight: bold;");

        VBox topo = new VBox(titulo);
        topo.setAlignment(Pos.CENTER);
        topo.setPadding(new Insets(10));

        VBox rodape = new VBox(botaoGerar);
        rodape.setAlignment(Pos.CENTER);
        rodape.setPadding(new Insets(10));

        this.setTop(topo);
        this.setCenter(areaTexto);
        this.setBottom(rodape);
        this.setStyle("-fx-background-color: linear-gradient(to bottom, #0d0d0d, #1a1a1a);");
    }
}
