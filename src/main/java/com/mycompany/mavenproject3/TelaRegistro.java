package com.mycompany.mavenproject3;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class TelaRegistro extends JFrame {
    // Declaração dos campos de entrada de dados
    private final JTextField campoNome;
    private final JTextField campoUsuario;
    private final JTextField campoSenha;
    private final JTextField campoCPF;
    private final JTextField campoNascimento;
    public TelaRegistro() {
        // Configuração do layout da janela (frontend)
        setTitle("Registro de Conta");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 10, 10));
        add(new JLabel("Nome:"));
        campoNome = new JTextField();
        add(campoNome);
        add(new JLabel("Usuário:"));
        campoUsuario = new JTextField();
        add(campoUsuario);
        add(new JLabel("Senha:"));
        campoSenha = new JTextField();
        add(campoSenha);
        add(new JLabel("CPF:"));
        campoCPF = new JTextField();
        add(campoCPF);
        add(new JLabel("Nascimento:"));
        campoNascimento = new JTextField();
        add(campoNascimento);
        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener((ActionEvent e) -> {
            registrarUsuario(); // Chama a função que processa os dados (backend)
        });
        add(btnRegistrar);
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener((ActionEvent e) -> { 
            dispose();
        });
        add(btnCancelar);
    }
    // Método para processar os dados e salvar no Excel (backend)
    private void registrarUsuario() {
        // Obtém os dados preenchidos pelo usuário
        String nome = campoNome.getText().trim();
        String usuario = campoUsuario.getText().trim();
        String senha = campoSenha.getText().trim();
        String cpf = campoCPF.getText().trim();
        String nascimento = campoNascimento.getText().trim();
        // Validação dos campos (backend)
        if (nome.isEmpty() || usuario.isEmpty() || senha.isEmpty() || cpf.isEmpty() || nascimento.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Arquivo Excel onde os dados serão salvos
        File arquivoExcel = new File("usuarios.xlsx");
        try (Workbook workbook = arquivoExcel.exists() ?
                new XSSFWorkbook(new FileInputStream(arquivoExcel)) : new XSSFWorkbook()) {
            // Busca ou cria a planilha
            Sheet sheet = workbook.getSheet("Usuarios");
            if (sheet == null) {
                sheet = workbook.createSheet("Usuarios");
                Row header = sheet.createRow(0);
                header.createCell(0).setCellValue("Nome");
                header.createCell(1).setCellValue("Usuário");
                header.createCell(2).setCellValue("Senha");
                header.createCell(3).setCellValue("CPF");
                header.createCell(4).setCellValue("Nascimento");
            }
            // Adiciona os dados na próxima linha disponível
            int ultimaLinha = sheet.getLastRowNum() + 1;
            Row row = sheet.createRow(ultimaLinha);
            row.createCell(0).setCellValue(nome);
            row.createCell(1).setCellValue(usuario);
            row.createCell(2).setCellValue(senha);
            row.createCell(3).setCellValue(cpf);
            row.createCell(4).setCellValue(nascimento);
            // Salva os dados no arquivo Excel
            try (FileOutputStream fos = new FileOutputStream(arquivoExcel)) {
                workbook.write(fos);
            }// Exibe mensagem de sucesso e limpa os campos
            JOptionPane.showMessageDialog(this, "Usuário registrado com sucesso!");
            campoNome.setText("");
            campoUsuario.setText("");
            campoSenha.setText("");
            campoCPF.setText("");
            campoNascimento.setText("");
            System.exit(0);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar os dados: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void main(String[] args) {
        // Inicializa a interface gráfica (frontend)
        SwingUtilities.invokeLater(() -> {
            TelaRegistro telaRegistro = new TelaRegistro();
            telaRegistro.setVisible(true);
        });
    }
}
