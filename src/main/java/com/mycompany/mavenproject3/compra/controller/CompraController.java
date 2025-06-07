package com.mycompany.mavenproject3.compra.controller;

import com.mycompany.mavenproject3.compra.repository.CompraRepository;

public class CompraController {
    public static boolean realizarCompra(String pecaId, String sessaoId, String usuarioId, String poltronaId, String preco) {
        return CompraRepository.salvarVenda(pecaId, sessaoId, usuarioId, poltronaId, preco);
    }
}
