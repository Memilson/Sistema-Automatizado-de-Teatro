package com.mycompany.mavenproject3.usuario.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.mycompany.mavenproject3.supabase.SupabaseService;
import com.mycompany.mavenproject3.usuario.dto.UsuarioDashboardDTO;
import com.mycompany.mavenproject3.usuario.model.Usuario;
import com.mycompany.mavenproject3.usuario.service.SupabaseAssinaturaClient;
import com.mycompany.mavenproject3.usuario.service.UsuarioService;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;

public class UsuarioController {

    private final UsuarioService usuarioService;
    private final SupabaseAssinaturaClient assinaturaClient;
    private boolean cartaoVerificado = false;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        this.assinaturaClient = new SupabaseAssinaturaClient();
    }

    public boolean isCartaoVerificado() {
        return cartaoVerificado;
    }

    public void setCartaoVerificado(boolean verificado) {
        this.cartaoVerificado = verificado;
    }

    public SupabaseAssinaturaClient getAssinaturaClient() {
        return this.assinaturaClient;
    }


    public Usuario carregarUsuario(String id) {
        return usuarioService.buscarUsuarioPorId(id);
    }

    public boolean atualizarPlanoRPC(String userId, String novoPlanoId) {
        return usuarioService.atualizarAssinatura(userId, novoPlanoId);
    }

    public boolean alterarPlano(String usuarioId, String novaAssinaturaId) {
        return usuarioService.atualizarAssinatura(usuarioId, novaAssinaturaId);
    }

    public boolean cadastrarCartao(Component parent) {
        JTextField campoNumero = new JTextField();
        JTextField campoValidade = new JTextField();
        JTextField campoCVV = new JTextField();
        JTextField campoNome = new JTextField();

        JPanel painel = new JPanel(new GridLayout(0, 1));
        painel.add(new JLabel("Número do cartão:"));
        painel.add(campoNumero);
        painel.add(new JLabel("Validade (MM/AA):"));
        painel.add(campoValidade);
        painel.add(new JLabel("CVV:"));
        painel.add(campoCVV);
        painel.add(new JLabel("Nome no cartão:"));
        painel.add(campoNome);

        int resposta = JOptionPane.showConfirmDialog(parent, painel, "Cadastrar Cartão", JOptionPane.OK_CANCEL_OPTION);
        if (resposta == JOptionPane.OK_OPTION) {
            // Aqui você pode validar os campos futuramente.
            cartaoVerificado = true;
            return true;
        }
        return false;
    }

    private String tipoPagamentoToTexto(int codigo) {
        return switch (codigo) {
            case 1 -> "Mensal";
            case 2 -> "Trimestral";
            case 3 -> "Anual";
            case 4 -> "Vitalício";
            default -> "Desconhecido";
        };
    }

    public UsuarioDashboardDTO carregarDashboardUsuario(String userId) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(userId);
        if (usuario == null) return null;

        UsuarioDashboardDTO dto = new UsuarioDashboardDTO();
        dto.setNome(usuario.getNome());
        dto.setCpf(usuario.getCpf());
        dto.setNascimento(usuario.getNascimento());
        dto.setAssinaturaNome(usuario.getAssinaturaNome());

        try {
            String assinaturaJson = SupabaseService.get("/rest/v1/assinaturas?id=eq." + usuario.getAssinaturaId(), true);
            JSONObject assinatura = new JSONArray(assinaturaJson).getJSONObject(0);

            dto.setIngressosVipMes(assinatura.optInt("ingressos_vip_mes", 0));
            dto.setPrioridadeReservaHoras(assinatura.optInt("prioridade_reserva_horas", 0));
            dto.setDescontoPercentual(assinatura.optDouble("desconto_percentual", 0));
            dto.setTipoPagamento(tipoPagamentoToTexto(assinatura.optInt("tipo_pagamento", 0)));

            String vendasJson = SupabaseService.get("/rest/v1/venda?usuario_id=eq." + userId + "&select=data_compra,foi_ingresso_vip", true);
            JSONArray vendas = new JSONArray(vendasJson);

            int usados = 0;
            String ultima = "Nunca";

            for (int i = 0; i < vendas.length(); i++) {
                JSONObject venda = vendas.getJSONObject(i);
                if (venda.optBoolean("foi_ingresso_vip", false)) usados++;
                String data = venda.optString("data_compra", null);
                if (data != null && !data.isEmpty()) {
                    ultima = LocalDateTime.parse(data).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                }
            }

            dto.setUsadosVip(usados);
            dto.setRestantesVip(Math.max(0, dto.getIngressosVipMes() - usados));
            dto.setUltimaCompra(ultima);

        } catch (Exception e) {
            System.err.println("Erro no dashboard: " + e.getMessage());
        }

        return dto;
    }

}
