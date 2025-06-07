package com.mycompany.mavenproject3.admin;

import com.mycompany.mavenproject3.usuario.model.Usuario;

import javax.swing.*;

public class TelaAdmin extends JFrame {
    private JTabbedPane tabbedPane;

    public TelaAdmin(Usuario usuario) {
        if (!usuario.isAdmin()) {
            JOptionPane.showMessageDialog(this, "Acesso negado.");
            dispose();
            return;
        }

        setTitle("Painel Administrativo");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane();

        tabbedPane.add("Relatórios", new PainelRelatorios());
        tabbedPane.add("Criar Peça", new PainelCriarPeca());
        tabbedPane.add("Usuários", new PainelUsuarios());

        add(tabbedPane);
        setVisible(true);
    }
}
