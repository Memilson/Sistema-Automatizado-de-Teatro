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
import com.mycompany.mavenproject3.view.UIUtils;

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

        Label titulo = new Label("🎭 Escolha seu Lugar no Teatro");
        titulo.setFont(Font.font("Georgia", 34));
        titulo.setTextFill(Color.web("#ffd700"));

        HBox topo = new HBox(15,
                estilizarLabel("Peça:"), comboPeca,
                estilizarLabel("Sessão:"), comboSessao
        );
        topo.setAlignment(Pos.CENTER);
        topo.setPadding(new Insets(10));

        configurarSetor(plateiaA, "#4e342e");
        configurarSetor(plateiaB, "#6d4c41");
        configurarSetor(frisasEsquerda, "#8d6e63");
        configurarSetor(frisasDireita, "#8d6e63");
        configurarSetor(camarotes, "#a1887f");
        configurarSetor(balcaoNobre, "#d7ccc8");

        VBox centroLayout = new VBox(20,
                criarPalco(),
                new HBox(40,
                        criarSetorComTitulo("FRISAS ESQUERDA", frisasEsquerda),
                        criarSetorComTitulo("PLATEIA A", plateiaA),
                        criarSetorComTitulo("PLATEIA B", plateiaB),
                        criarSetorComTitulo("FRISAS DIREITA", frisasDireita)
                ),
                criarSetorComTitulo("CAMAROTES", camarotes),
                criarSetorComTitulo("BALCÃO NOBRE", balcaoNobre)
        );
        centroLayout.setAlignment(Pos.CENTER);

        ScrollPane scrollPane = new ScrollPane(centroLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(500);
        scrollPane.setStyle("-fx-background: transparent; -fx-padding: 10px;");

        precoLabel.setFont(Font.font("Georgia", 18));
        precoLabel.setTextFill(Color.web("#ffcc00"));

        Button confirmar = new Button();
        confirmar.setText("");
        confirmar.setGraphic(new Label("✔ Confirmar Compra"));

        Button voltar = new Button();
        voltar.setText("");
        voltar.setGraphic(new Label("↩ Voltar ao Menu Principal"));

        UIUtils.estilizarBotao(confirmar);
        UIUtils.estilizarBotao(voltar);

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
        botoes.setPadding(new Insets(10));

        VBox layout = new VBox(25, titulo, topo, scrollPane, precoLabel, botoes);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(30));
        layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #000000, #1c1c1c);");

        Scene cena = new Scene(layout, 1280, 768);
        stage.setScene(cena);
        stage.setMaximized(true);
        stage.show();

        CompraViewHelper.carregarPecas(this);
        comboPeca.setOnAction(e -> CompraViewHelper.carregarSessoes(this));
        comboSessao.setOnAction(e -> CompraViewHelper.carregarAssentos(this));
    }

    private VBox criarSetorComTitulo(String titulo, GridPane grid) {
        Label label = new Label(titulo);
        label.setFont(Font.font("Georgia", 16));
        label.setTextFill(Color.web("#ffd700"));
        VBox box = new VBox(6, label, grid);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private VBox criarPalco() {
        Label palcoLabel = new Label("PALCO");
        palcoLabel.setFont(Font.font("Georgia", 18));
        palcoLabel.setTextFill(Color.WHITE);
        VBox palco = new VBox(palcoLabel);
        palco.setAlignment(Pos.CENTER);
        palco.setStyle("-fx-background-color: #2c2c2c; -fx-padding: 10px; -fx-background-radius: 8px;");
        return palco;
    }

    private void configurarSetor(GridPane grid, String cor) {
        grid.setHgap(6);
        grid.setVgap(6);
        grid.setAlignment(Pos.CENTER);
        grid.setStyle("-fx-background-color: " + cor + "; -fx-padding: 12px; -fx-border-radius: 12px; -fx-background-radius: 12px;");
    }



    private Label estilizarLabel(String texto) {
        Label label = new Label(texto);
        label.setFont(Font.font("Georgia", 14));
        label.setTextFill(Color.web("#cccccc"));
        return label;
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
    public void setPreco(String preco) { precoLabel.setText("Preço: " + preco); }
}
