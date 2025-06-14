package com.mycompany.mavenproject3.compra.helper;

import com.mycompany.mavenproject3.compra.repository.CompraRepository;
import com.mycompany.mavenproject3.compra.view.TelaCompraFX;
import com.mycompany.mavenproject3.supabase.SupabaseService;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class SelecaoVisualHelper {

    public static void atualizarSelecaoVisualFX(TelaCompraFX tela) {
        for (GridPane grid : new GridPane[]{
                tela.getPlateiaA(), tela.getPlateiaB(), tela.getFrisasEsquerda(),
                tela.getFrisasDireita(), tela.getCamarotes(), tela.getBalcaoNobre()
        }) {
            for (javafx.scene.Node node : grid.getChildren()) {
                if (node instanceof Button botao) {
                    String nome = botao.getText();
                    String id = tela.getMapaPoltronas().get(nome);
                    if (id == null) continue;
                    boolean selecionada = tela.getSelecionadas().contains(id);
                    boolean ocupada = tela.getOcupadas().contains(id);
                    String baseStyle = botao.getStyle().replaceAll("-fx-border-color:.*?;", "")
                            .replaceAll("-fx-border-width:.*?;", "");
                    if (selecionada) {
                        botao.setStyle(baseStyle + "-fx-border-color: black; -fx-border-width: 3;");
                    } else if (!ocupada) {
                        botao.setStyle(baseStyle);
                    }
                }
            }
        }

        double totalBruto = tela.getSelecionadas().stream()
                .mapToDouble(id -> Double.parseDouble(tela.getMapaPrecos().getOrDefault(id, "0")))
                .sum();

        double desconto = 0.0;
        try {
            String assinaturaId = SupabaseService.buscarAssinaturaId(tela.getUsuarioLogado().getId());
            desconto = CompraRepository.buscarDescontoAssinatura(assinaturaId);
        } catch (Exception e) {
            System.err.println("Erro ao buscar desconto: " + e.getMessage());
        }

        double totalComDesconto = totalBruto * (1 - (desconto / 100.0));
        String texto = tela.getSelecionadas().isEmpty()
                ? "R$--"
                : String.format("R$%.2f (com %.0f%% de desconto) - %d poltrona(s)",
                totalComDesconto, desconto, tela.getSelecionadas().size());

        tela.setPreco(texto);
    }
}
