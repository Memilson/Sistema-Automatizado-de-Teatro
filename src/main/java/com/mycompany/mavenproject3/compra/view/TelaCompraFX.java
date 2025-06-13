package com.mycompany.mavenproject3.compra.view;

import com.mycompany.mavenproject3.Main;
import com.mycompany.mavenproject3.compra.helper.CompraViewHelper;
import com.mycompany.mavenproject3.compra.helper.SelecaoVisualHelper;
import com.mycompany.mavenproject3.usuario.model.Usuario;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.*;

public class TelaCompraFX {

    private final Usuario usuarioLogado;
    private final ComboBox<String> comboPeca = new ComboBox<>();
    private final ComboBox<String> comboSessao = new ComboBox<>();
    private final Label precoLabel = new Label("Preço: R$--");

    private final Set<String> selecionadas = new HashSet<>();
    private final Set<String> ocupadas = new HashSet<>();
    private final HashMap<String, String> mapaPecas = new HashMap<>();
    private final HashMap<String, String> mapaSessoes = new HashMap<>();
    private final Map<String, String> mapaPoltronas = new HashMap<>();
    private final Map<String, String> mapaPrecos = new HashMap<>();

    private final GridPane plateiaA = new GridPane();
    private final GridPane plateiaB = new GridPane();
    private final GridPane frisasEsquerda = new GridPane();
    private final GridPane frisasDireita = new GridPane();
    private final GridPane camarotes = new GridPane();
    private final GridPane balcaoNobre = new GridPane();

    private String poltronaSelecionadaId = null;
    private String poltronaSelecionadaNome = null;

    public TelaCompraFX(Usuario usuario) {
        this.usuarioLogado = usuario;
    }

    public void start(Stage stage) {
        if (usuarioLogado == null) {
            new Alert(Alert.AlertType.ERROR, "Você precisa estar logado.").showAndWait();
            return;
        }

        stage.setTitle("DramaCore Theatre - Compra de Ingressos");

        Label titulo = new Label("\uD83E\uDE91 Mapa do Teatro Riachuelo");
        titulo.setFont(Font.font("Georgia", 28));
        titulo.setTextFill(Color.web("#d4af37"));

        HBox topo = new HBox(20, new Label("Peça:"), comboPeca, new Label("Sessão:"), comboSessao);
        topo.setAlignment(Pos.CENTER);
        topo.setPadding(new Insets(10));

        configurarSetor(plateiaA, "#d4af37");
        configurarSetor(plateiaB, "#b6e0a5");
        configurarSetor(frisasEsquerda, "#f4d03f");
        configurarSetor(frisasDireita, "#f4d03f");
        configurarSetor(camarotes, "#d7bde2");
        configurarSetor(balcaoNobre, "#f9c0cb");

        VBox centro = new VBox(15);
        centro.setAlignment(Pos.CENTER);

        VBox palco = new VBox(new Label("PALCO"));
        palco.setAlignment(Pos.CENTER);
        palco.setStyle("-fx-background-color: #444; -fx-text-fill: white; -fx-padding: 5px;");

        HBox frisas = new HBox(40, frisasEsquerda, plateiaA, plateiaB, frisasDireita);
        frisas.setAlignment(Pos.CENTER);

        centro.getChildren().addAll(palco, frisas, camarotes, balcaoNobre);

        ScrollPane scrollPane = new ScrollPane(centro);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(500);

        Button confirmar = new Button("Confirmar Compra");
        Button voltar = new Button("Voltar ao Menu Principal");

        confirmar.setOnAction(e -> CompraViewHelper.finalizarCompra(this));
        voltar.setOnAction(e -> {
            try {
                new Main(usuarioLogado).start(new Stage());
                stage.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        HBox botoes = new HBox(20, confirmar, voltar);
        botoes.setAlignment(Pos.CENTER);

        VBox layout = new VBox(20, titulo, topo, scrollPane, precoLabel, botoes);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #0d0d0d, #1a1a1a);");

        stage.setScene(new Scene(layout, 1280, 768));
        stage.show();

        CompraViewHelper.carregarPecas(this);
        comboPeca.setOnAction(e -> CompraViewHelper.carregarSessoes(this));
        comboSessao.setOnAction(e -> CompraViewHelper.carregarAssentos(this));
    }

    private void configurarSetor(GridPane grid, String cor) {
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setAlignment(Pos.CENTER);
        grid.setStyle("-fx-background-color: " + cor + "; -fx-padding: 10px; -fx-border-radius: 10px;");
    }

    public ComboBox<String> getComboPeca() { return comboPeca; }
    public ComboBox<String> getComboSessao() { return comboSessao; }
    public GridPane getPainelAssentos() { return plateiaB; }
    public GridPane getPlateiaA() { return plateiaA; }
    public GridPane getPlateiaB() { return plateiaB; }
    public GridPane getFrisasEsquerda() { return frisasEsquerda; }
    public GridPane getFrisasDireita() { return frisasDireita; }
    public GridPane getCamarotes() { return camarotes; }
    public GridPane getBalcaoNobre() { return balcaoNobre; }
    public HashMap<String, String> getMapaPecas() { return mapaPecas; }
    public HashMap<String, String> getMapaSessoes() { return mapaSessoes; }
    public Set<String> getOcupadas() { return ocupadas; }
    public Map<String, String> getMapaPoltronas() { return mapaPoltronas; }
    public Map<String, String> getMapaPrecos() { return mapaPrecos; }
    public Set<String> getSelecionadas() { return selecionadas; }
    public String getPoltronaSelecionadaId() { return poltronaSelecionadaId; }
    public void setPoltronaSelecionadaId(String id) { this.poltronaSelecionadaId = id; }
    public String getPoltronaSelecionadaNome() { return poltronaSelecionadaNome; }
    public void setPoltronaSelecionadaNome(String nome) { this.poltronaSelecionadaNome = nome; }
    public Usuario getUsuarioLogado() { return usuarioLogado; }
    public void atualizarSelecaoVisual(String nome) { SelecaoVisualHelper.atualizarSelecaoVisualFX(this); }
    public void setPreco(String preco) { precoLabel.setText("Preço: R$" + preco); }
}
