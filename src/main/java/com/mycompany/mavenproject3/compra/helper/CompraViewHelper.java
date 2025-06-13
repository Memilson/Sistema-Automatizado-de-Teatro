package com.mycompany.mavenproject3.compra.helper;
import com.mycompany.mavenproject3.compra.model.CarregarSessoes;
import com.mycompany.mavenproject3.compra.view.TelaCompraFX;
import com.mycompany.mavenproject3.compra.loader.AssentoLoader;
public class CompraViewHelper {

    // Para uso centralizado (caso precise chamar de um único ponto)
    public static void carregarAssentos(TelaCompraFX tela) {
        AssentoLoader.carregarAssentosFX(tela);
    }

    public static void atualizarSelecao(TelaCompraFX tela) {
        SelecaoVisualHelper.atualizarSelecaoVisualFX(tela);
    }

    public static void finalizarCompra(TelaCompraFX tela) {
        ConfirmarCompra.confirmarCompraFX(tela);
    }

    public static void carregarPecas(TelaCompraFX tela) {
        CarregarSessoes.carregarPecasFX(tela);
    }

    public static void carregarSessoes(TelaCompraFX tela) {
        CarregarSessoes.carregarSessoesFX(tela);
    }
}
