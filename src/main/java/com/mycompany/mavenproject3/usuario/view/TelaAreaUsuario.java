package com.mycompany.mavenproject3.usuario.view;
import com.mycompany.mavenproject3.login.view.TelaLogin;
import com.mycompany.mavenproject3.usuario.controller.AreaUsuarioController;
import com.mycompany.mavenproject3.usuario.controller.UsuarioViewController;
import com.mycompany.mavenproject3.usuario.dto.UsuarioDashboardDTO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import com.mycompany.mavenproject3.usuario.controller.UsuarioViewController;
public class TelaAreaUsuario {

    private final AreaUsuarioController controller;
    private final UsuarioViewController viewController;
    public TelaAreaUsuario(AreaUsuarioController controller) {
        this.controller = controller;
        this.viewController = new UsuarioViewController(controller); // instância correta
    }

    public void start(Stage stage) {
        String userId = controller.getUserIdLogado();
        if (userId == null) {
            new TelaLogin().start(new Stage());
            stage.close();
            return;
        }

        UsuarioDashboardDTO dto = controller.carregarDashboard(userId);
        if (dto == null) {
            Alert alerta = new Alert(Alert.AlertType.ERROR, "Erro ao carregar dados.");
            alerta.showAndWait();
            stage.close();
            return;
        }

        stage.setTitle("DramaCore Theatre - Área do Usuário");

        Text titulo = new Text("👤 Área do Usuário");
        titulo.setFont(Font.font("Georgia", 28));
        titulo.setFill(Color.web("#d4af37"));
        titulo.setEffect(new DropShadow(4, Color.web("#a6762d")));

        VBox dados = new VBox(10,
                criarInfo("👤 Nome:", dto.getNome()),
                criarInfo("📄 CPF:", dto.getCpf()),
                criarInfo("📅 Nascimento:", dto.getNascimento()),
                criarInfo("📌 Assinatura:", dto.getAssinaturaNome()),
                criarInfo("⭐ Ingressos VIP/mês:", String.valueOf(dto.getIngressosVipMes())),
                criarInfo("⏱️ Prioridade reserva (h):", String.valueOf(dto.getPrioridadeReservaHoras())),
                criarInfo("💸 Desconto (%):", String.valueOf(dto.getDescontoPercentual())),
                criarInfo("📆 Tipo de pagamento:", dto.getTipoPagamento()),
                criarInfo("🕓 Última compra:", dto.getUltimaCompra()),
                criarInfo("✅ VIPs usados este mês:", String.valueOf(dto.getUsadosVip())),
                criarInfo("🎟️ VIPs restantes:", String.valueOf(dto.getRestantesVip()))
        );

        dados.setAlignment(Pos.CENTER_LEFT);

        Button btnCartao = new Button("💳 Cadastrar Cartão");
        Button btnPlano = new Button("📦 Mudar Plano");
        estilizarBotao(btnCartao);
        estilizarBotao(btnPlano);

        btnCartao.setOnAction(e -> viewController.cadastrarCartao(stage));
        btnPlano.setOnAction(e -> viewController.mudarPlano(stage));

        HBox botoes = new HBox(20, btnCartao, btnPlano);
        botoes.setAlignment(Pos.CENTER);

        VBox layout = new VBox(30, titulo, dados, botoes);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(30));
        layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #0d0d0d, #1a1a1a);");

        Scene cena = new Scene(layout, 800, 700);
        stage.setScene(cena);
        stage.setMaximized(true);
        stage.show();
    }

    private HBox criarInfo(String titulo, String valor) {
        Label labelTitulo = new Label(titulo);
        labelTitulo.setTextFill(Color.GOLD);
        labelTitulo.setFont(Font.font("Georgia", 16));

        Label labelValor = new Label(valor);
        labelValor.setTextFill(Color.WHITE);
        labelValor.setFont(Font.font("Georgia", 16));

        HBox box = new HBox(10, labelTitulo, labelValor);
        box.setAlignment(Pos.CENTER_LEFT);
        return box;
    }

    private void estilizarBotao(Button btn) {
        btn.setFont(Font.font("Georgia", 15));
        btn.setStyle("-fx-background-color: linear-gradient(to bottom, #ffcc00, #b8860b);" +
                " -fx-text-fill: black; -fx-background-radius: 10px;");
        btn.setPrefWidth(220);
        btn.setPrefHeight(45);
    }
}
