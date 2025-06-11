package com.mycompany.mavenproject3.admin;

import com.mycompany.mavenproject3.supabase.SupabaseService;
import com.mycompany.mavenproject3.usuario.model.Usuario;
import com.mycompany.mavenproject3.usuario.repository.UsuarioRepository;
import com.mycompany.mavenproject3.usuario.repository.UsuarioRepositorySupabase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PainelUsuarios extends JPanel {

    private final JTable tabela;
    private final DefaultTableModel modeloTabela;

    // Novo: instância do repositório (não mais static)
    private final UsuarioRepository usuarioRepository = new UsuarioRepositorySupabase(new SupabaseService());

    public PainelUsuarios() {
        setLayout(new BorderLayout());

        modeloTabela = new DefaultTableModel(new Object[]{"Nome", "CPF", "Plano", "Admin?", "ID"}, 0);
        tabela = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabela);

        JButton botaoAtualizar = new JButton("Atualizar Lista");
        JButton botaoPromover = new JButton("Promover para Admin");
        JButton botaoAlterarPlano = new JButton("Alterar Assinatura");

        JPanel botoes = new JPanel();
        botoes.add(botaoAtualizar);
        botoes.add(botaoPromover);
        botoes.add(botaoAlterarPlano);

        add(new JLabel("👥 Gerenciamento de Usuários", SwingConstants.CENTER), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);

        // Botões
        botaoAtualizar.addActionListener(e -> carregarUsuarios());

        botaoPromover.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha >= 0) {
                String idUsuario = (String) modeloTabela.getValueAt(linha, 4);
                boolean sucesso = usuarioRepository.promoverParaAdmin(idUsuario);
                JOptionPane.showMessageDialog(this,
                        sucesso ? "Usuário promovido para Admin." : "Erro ao promover usuário.");
                if (sucesso) carregarUsuarios();
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um usuário.");
            }
        });

        botaoAlterarPlano.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha >= 0) {
                String idUsuario = (String) modeloTabela.getValueAt(linha, 4);
                String novoPlano = JOptionPane.showInputDialog(this, "ID do novo plano:");
                if (novoPlano != null && !novoPlano.isBlank()) {
                    boolean sucesso = usuarioRepository.alterarAssinatura(idUsuario, novoPlano);
                    JOptionPane.showMessageDialog(this,
                            sucesso ? "Plano alterado com sucesso." : "Erro ao alterar plano.");
                    if (sucesso) carregarUsuarios();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um usuário.");
            }
        });

        carregarUsuarios();
    }

    private void carregarUsuarios() {
        modeloTabela.setRowCount(0); // limpa a tabela

        List<Usuario> usuarios = usuarioRepository.buscarTodos();
        for (Usuario u : usuarios) {
            modeloTabela.addRow(new Object[]{
                    u.getNome(),
                    u.getCpf(),
                    u.getAssinaturaNome(),
                    u.isAdmin() ? "Sim" : "Não",
                    u.getId()
            });
        }

        // Oculta a coluna do ID
        if (tabela.getColumnModel().getColumnCount() > 4) {
            tabela.getColumnModel().getColumn(4).setMinWidth(0);
            tabela.getColumnModel().getColumn(4).setMaxWidth(0);
            tabela.getColumnModel().getColumn(4).setWidth(0);
        }
    }
}
