package com.mycompany.mavenproject3.usuario.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.mycompany.mavenproject3.supabase.SupabaseService;
import com.mycompany.mavenproject3.usuario.dto.UsuarioDashboardDTO;
import com.mycompany.mavenproject3.usuario.model.Usuario;
import com.mycompany.mavenproject3.usuario.service.UsuarioService;
import org.json.JSONArray;
import org.json.JSONObject;

public class UsuarioController {

    private final UsuarioService usuarioService;
    private final SupabaseService supabase;

    public UsuarioController(UsuarioService usuarioService, SupabaseService supabase) {
        this.usuarioService = usuarioService;
        this.supabase = supabase;
    }

    public Usuario carregarUsuario(String id) {
        return usuarioService.buscarUsuarioPorId(id);
    }

    public boolean atualizarPlanoRPC(String userId, String novoPlanoId) {
        return usuarioService.atualizarAssinatura(userId, novoPlanoId);
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
            String assinaturaJson = supabase.get("/rest/v1/assinaturas?id=eq." + usuario.getAssinaturaId(), true);
            JSONObject assinatura = new JSONArray(assinaturaJson).getJSONObject(0);

            int vipMes = assinatura.optInt("ingressos_vip_mes", 0);
            int prioridade = assinatura.optInt("prioridade_reserva_horas", 0);
            double desconto = assinatura.optDouble("desconto_percentual", 0);
            String tipo = tipoPagamentoToTexto(assinatura.optInt("tipo_pagamento", 0));

            dto.setIngressosVipMes(vipMes);
            dto.setPrioridadeReservaHoras(prioridade);
            dto.setDescontoPercentual(desconto);
            dto.setTipoPagamento(tipo);

            String vendasJson = supabase.get("/rest/v1/venda?usuario_id=eq." + userId + "&select=data_compra,foi_ingresso_vip", true);
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
            dto.setRestantesVip(Math.max(0, vipMes - usados));
            dto.setUltimaCompra(ultima);

        } catch (Exception e) {
            System.err.println("Erro no dashboard: " + e.getMessage());
        }

        return dto;
    }
}
