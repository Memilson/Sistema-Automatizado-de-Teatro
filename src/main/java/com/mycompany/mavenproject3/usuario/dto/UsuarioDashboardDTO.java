package com.mycompany.mavenproject3.usuario.dto;

public class UsuarioDashboardDTO {
    private String nome;
    private String cpf;
    private String nascimento;
    private String assinaturaNome;
    private int ingressosVipMes;
    private int prioridadeReservaHoras;
    private double descontoPercentual;
    private String tipoPagamento;
    private String ultimaCompra;private int usadosVip;
    private int restantesVip;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getAssinaturaNome() {
        return assinaturaNome;
    }

    public void setAssinaturaNome(String assinaturaNome) {
        this.assinaturaNome = assinaturaNome;
    }

    public int getIngressosVipMes() {
        return ingressosVipMes;
    }

    public void setIngressosVipMes(int ingressosVipMes) {
        this.ingressosVipMes = ingressosVipMes;
    }

    public double getDescontoPercentual() {
        return descontoPercentual;
    }

    public void setDescontoPercentual(double descontoPercentual) {
        this.descontoPercentual = descontoPercentual;
    }

    public int getPrioridadeReservaHoras() {
        return prioridadeReservaHoras;
    }

    public void setPrioridadeReservaHoras(int prioridadeReservaHoras) {
        this.prioridadeReservaHoras = prioridadeReservaHoras;
    }

    public String getUltimaCompra() {
        return ultimaCompra;
    }

    public void setUltimaCompra(String ultimaCompra) {
        this.ultimaCompra = ultimaCompra;
    }

    public String getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public int getRestantesVip() {
        return restantesVip;
    }

    public void setRestantesVip(int restantesVip) {
        this.restantesVip = restantesVip;
    }

    public int getUsadosVip() {
        return usadosVip;
    }

    public void setUsadosVip(int usadosVip) {
        this.usadosVip = usadosVip;
    }
}
