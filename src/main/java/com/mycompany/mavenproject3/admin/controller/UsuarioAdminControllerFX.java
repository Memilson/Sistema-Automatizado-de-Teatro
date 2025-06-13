package com.mycompany.mavenproject3.admin.controller;

import com.mycompany.mavenproject3.supabase.SupabaseService;
import com.mycompany.mavenproject3.usuario.model.Usuario;
import com.mycompany.mavenproject3.usuario.repository.UsuarioRepository;
import com.mycompany.mavenproject3.usuario.repository.UsuarioRepositorySupabase;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.util.List;

public class UsuarioAdminControllerFX {

    private static final UsuarioRepository usuarioRepository =
            new UsuarioRepositorySupabase(new SupabaseService());

    public static void carregarUsuarios(TableView<Usuario> tabela) {
        List<Usuario> usuarios = usuarioRepository.buscarTodos();
        ObservableList<Usuario> dados = FXCollections.observableArrayList(usuarios);
        tabela.setItems(dados);
    }

    public static void promoverUsuario(TableView<Usuario> tabela) {
        Usuario selecionado = tabela.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            mostrarAlerta("Selecione um usuário.");
            return;
        }

        boolean sucesso = usuarioRepository.promoverParaAdmin(selecionado.getId());
        mostrarAlerta(sucesso ? "Usuário promovido para Admin." : "Erro ao promover usuário.");
        if (sucesso) carregarUsuarios(tabela);
    }

    public static void alterarPlano(TableView<Usuario> tabela) {
        Usuario selecionado = tabela.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            mostrarAlerta("Selecione um usuário.");
            return;
        }

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Alterar Plano");
        dialog.setHeaderText("Informe o ID do novo plano:");
        dialog.setContentText("Plano:");

        dialog.showAndWait().ifPresent(novoPlano -> {
            if (!novoPlano.isBlank()) {
                boolean sucesso = usuarioRepository.alterarAssinatura(selecionado.getId(), novoPlano);
                mostrarAlerta(sucesso ? "Plano alterado com sucesso." : "Erro ao alterar plano.");
                if (sucesso) carregarUsuarios(tabela);
            }
        });
    }

    private static void mostrarAlerta(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, mensagem, ButtonType.OK);
        alert.showAndWait();
    }

    public static void configurarColunas(TableView<Usuario> tabela) {
        TableColumn<Usuario, String> nomeCol = new TableColumn<>("Nome");
        nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Usuario, String> cpfCol = new TableColumn<>("CPF");
        cpfCol.setCellValueFactory(new PropertyValueFactory<>("cpf"));

        TableColumn<Usuario, String> planoCol = new TableColumn<>("Plano");
        planoCol.setCellValueFactory(new PropertyValueFactory<>("assinaturaNome"));

        TableColumn<Usuario, String> adminCol = new TableColumn<>("Admin?");
        adminCol.setCellValueFactory(new PropertyValueFactory<>("admin"));

        tabela.getColumns().addAll(nomeCol, cpfCol, planoCol, adminCol);
    }
}
