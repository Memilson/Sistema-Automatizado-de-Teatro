package com.mycompany.mavenproject3.compra.helper;

import com.mycompany.mavenproject3.compra.controller.CompraController;
import com.mycompany.mavenproject3.compra.view.TelaCompraFX;
import com.mycompany.mavenproject3.usuario.model.Usuario;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ConfirmarCompra {
    public static void confirmarCompraFX(TelaCompraFX tela) {
        String peca = tela.getComboPeca().getValue();
        String horario = tela.getComboSessao().getValue();
        if (peca == null || horario == null) {
            new Alert(Alert.AlertType.WARNING, "Selecione a peça e o horário da sessão.").showAndWait();
            return;
        }

        if (tela.getSelecionadas().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Selecione pelo menos uma poltrona.").showAndWait();
            return;
        }

        String sessaoId = tela.getMapaSessoes().get(horario);
        String pecaId = tela.getMapaPecas().get(peca);
        Usuario usuario = tela.getUsuarioLogado();
        String userId = usuario.getId();

        List<String> poltronasCompradas = new ArrayList<>();
        double totalPago = 0.0;
        boolean erro = false;

        for (String poltronaId : tela.getSelecionadas()) {
            String precoStr = tela.getMapaPrecos().get(poltronaId);
            double preco = Double.parseDouble(precoStr);
            boolean sucesso = CompraController.realizarCompra(pecaId, sessaoId, userId, poltronaId, precoStr);

            if (sucesso) {
                String nomePoltrona = tela.getMapaPoltronas().entrySet().stream()
                        .filter(entry -> Objects.equals(entry.getValue(), poltronaId))
                        .map(Map.Entry::getKey)
                        .findFirst()
                        .orElse("Poltrona");

                poltronasCompradas.add(nomePoltrona);
                totalPago += preco;
            } else {
                erro = true;
            }
        }

        if (!erro && !poltronasCompradas.isEmpty()) {
            ComprovanteCompra.gerarComprovante(
                    usuario.getCpf(),
                    usuario.getNome(),
                    peca,
                    horario,
                    poltronasCompradas,
                    totalPago
            );
            new Alert(Alert.AlertType.INFORMATION, "Compra finalizada com sucesso! Comprovante gerado.").showAndWait();
        } else if (erro) {
            new Alert(Alert.AlertType.ERROR, "Algumas compras falharam. Verifique a conexão ou tente novamente.").showAndWait();
        }

        tela.getSelecionadas().clear();
        tela.setPreco("--");
        CompraViewHelper.carregarAssentos(tela);
    }
}
