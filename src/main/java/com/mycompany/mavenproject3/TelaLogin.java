package com.mycompany.mavenproject3;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class TelaLogin extends JFrame {
    private final JTextField campoUsuario;
    private final JPasswordField campoSenha;

    public TelaLogin() {
        setTitle("Login");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 10, 10));
        add(new JLabel("Usuário:"));
        campoUsuario = new JTextField();
        add(campoUsuario);
        add(new JLabel("Senha:"));
        campoSenha = new JPasswordField();
        add(campoSenha);
        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(this::fazerLogin);
        add(btnLogin);
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        add(btnCancelar);
    }private void fazerLogin(ActionEvent e) {
        String usuario = campoUsuario.getText().trim();
        String senha = new String(campoSenha.getPassword()).trim();
        if (verificarCredenciais(usuario, senha)) {
            JOptionPane.showMessageDialog(this, "Login bem-sucedido!");
            new TelaPrincipal(usuario).setVisible(true);
            dispose(); 
        } else {
            JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            new TelaRegistro().setVisible(true);  }}
    private boolean verificarCredenciais(String usuario, String senha) {
        File arquivoExcel = new File("usuarios.xlsx");
        try (FileInputStream fis = new FileInputStream(arquivoExcel);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheet("Usuarios");
            if (sheet == null) return false;
            for (Row row : sheet) {
                Cell cellUsuario = row.getCell(1);
                Cell cellSenha = row.getCell(2);
                if (cellUsuario != null && cellSenha != null) {
                    String usuarioExcel = cellUsuario.getStringCellValue();
                    String senhaExcel = cellSenha.getStringCellValue();

                    if (usuario.equals(usuarioExcel) && senha.equals(senhaExcel)) {
                        return true; }}}
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao ler o arquivo de usuários: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return false;  }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaLogin telaLogin = new TelaLogin();
            telaLogin.setVisible(true);
        });
    }
}
