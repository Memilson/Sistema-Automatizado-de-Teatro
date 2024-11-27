package com.mycompany.mavenproject3;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
public class TelaPrincipal extends JFrame {
    private JLabel labelNomeUsuario;
    public TelaPrincipal(String nomeUsuario) {
        setTitle("Sistema de Vendas de Ingressos - Teatro ABC");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1, 10, 10));
        JLabel titulo = new JLabel("Sistema de Vendas de Ingressos", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(titulo);
        JButton btnComprar = new JButton("Comprar Ingresso");
        btnComprar.addActionListener(e -> JOptionPane.showMessageDialog(this, "Tela de compra de ingressos não implementada ainda."));
        add(btnComprar);
        JButton btnVerAssentos = new JButton("Ver Assentos Livres");
        btnVerAssentos.addActionListener(e -> JOptionPane.showMessageDialog(this, "Tela de assentos livres não implementada ainda."));
        add(btnVerAssentos);
        JButton btnRelatorio = new JButton("Relatório");
        btnRelatorio.addActionListener(e -> JOptionPane.showMessageDialog(this, "Tela de relatório não implementada ainda."));
        add(btnRelatorio);
        JButton btnRegistro = new JButton("Registro de Conta");
        btnRegistro.addActionListener(e -> JOptionPane.showMessageDialog(this, "Tela de registro de conta não implementada ainda."));
        add(btnRegistro);
        labelNomeUsuario = new JLabel("Usuário: " + nomeUsuario, SwingConstants.RIGHT);
        add(labelNomeUsuario);
    }public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaPrincipal telaPrincipal = new TelaPrincipal("Usuário Exemplo");
            telaPrincipal.setVisible(true);
        });
    }
}
