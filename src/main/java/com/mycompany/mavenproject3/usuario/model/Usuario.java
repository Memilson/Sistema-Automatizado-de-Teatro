package com.mycompany.mavenproject3.usuario.model;

public class Usuario {
    private String id;
    private String nome;
    private String cpf;
    private String nascimento;
    private String telefone;
    private String assinaturaId;

    public Usuario(String id, String nome, String cpf, String nascimento, String assinaturaId) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.nascimento = nascimento;
        this.assinaturaId = assinaturaId;
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getNascimento() { return nascimento; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getAssinaturaId() { return assinaturaId; }
}
