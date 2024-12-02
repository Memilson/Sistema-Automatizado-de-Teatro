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
    private final JTextField campoNome, campoUsuario, campoSenha, campoCPF, campoNascimento;

    public TelaRegistro() {
        setTitle("Registro de Conta");
        setSize(400,200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6,2,10,10));
        add(new JLabel("Nome:"));campoNome=new JTextField();add(campoNome);
        add(new JLabel("Usuário:"));campoUsuario=new JTextField();add(campoUsuario);
        add(new JLabel("Senha:"));campoSenha=new JTextField();add(campoSenha);
        add(new JLabel("CPF:"));campoCPF=new JTextField();add(campoCPF);
        add(new JLabel("Nascimento:"));campoNascimento=new JTextField();add(campoNascimento);
        JButton btnRegistrar=new JButton("Registrar");
        btnRegistrar.addActionListener((ActionEvent e)->registrarUsuario());add(btnRegistrar);
        JButton btnCancelar=new JButton("Cancelar");
        btnCancelar.addActionListener((ActionEvent e)->dispose());add(btnCancelar);
    }

    // Método para registrar novo usuário, validando CPF e garantindo unicidade no Excel
    private void registrarUsuario() {
        String nome=campoNome.getText().trim(), usuario=campoUsuario.getText().trim(), senha=campoSenha.getText().trim(), cpf=campoCPF.getText().trim(), nascimento=campoNascimento.getText().trim();
        if (nome.isEmpty() || usuario.isEmpty() || senha.isEmpty() || cpf.isEmpty() || nascimento.isEmpty()) {
            JOptionPane.showMessageDialog(this,"Por favor, preencha todos os campos.","Erro",JOptionPane.ERROR_MESSAGE);return;
        }
        if (!ValidadorCPF.validar(cpf)) {
            JOptionPane.showMessageDialog(this,"CPF inválido. Por favor, insira um CPF válido.","Erro",JOptionPane.ERROR_MESSAGE);return;
        }
        File arquivoExcel=new File("usuarios.xlsx");
        try (Workbook workbook=arquivoExcel.exists()?new XSSFWorkbook(new FileInputStream(arquivoExcel)):new XSSFWorkbook()) {
            Sheet sheet=workbook.getSheet("Usuarios");
            if (sheet==null) {
                sheet=workbook.createSheet("Usuarios");
                Row header=sheet.createRow(0);
                header.createCell(0).setCellValue("Nome");
                header.createCell(1).setCellValue("Usuário");
                header.createCell(2).setCellValue("Senha");
                header.createCell(3).setCellValue("CPF");
                header.createCell(4).setCellValue("Nascimento");
            }
            for (int i=1;i<=sheet.getLastRowNum();i++) {
                Row row=sheet.getRow(i);
                if (row!=null) {
                    String nomeExistente=row.getCell(0).getStringCellValue(), cpfExistente=row.getCell(3).getStringCellValue();
                    if (nomeExistente.equalsIgnoreCase(nome)) {
                        JOptionPane.showMessageDialog(this,"Nome já registrado. Por favor, insira um nome diferente.","Erro",JOptionPane.ERROR_MESSAGE);return;
                    }
                    if (cpfExistente.equalsIgnoreCase(cpf)) {
                        JOptionPane.showMessageDialog(this,"CPF já registrado. Por favor, insira um CPF diferente.","Erro",JOptionPane.ERROR_MESSAGE);return;
                    }
                }
            }
            int ultimaLinha=sheet.getLastRowNum()+1;
            Row row=sheet.createRow(ultimaLinha);
            row.createCell(0).setCellValue(nome);
            row.createCell(1).setCellValue(usuario);
            row.createCell(2).setCellValue(senha);
            row.createCell(3).setCellValue(cpf);
            row.createCell(4).setCellValue(nascimento);
            try (FileOutputStream fos=new FileOutputStream(arquivoExcel)) {
                workbook.write(fos);
            }
            JOptionPane.showMessageDialog(this,"Usuário registrado com sucesso!");
            campoNome.setText("");campoUsuario.setText("");campoSenha.setText("");campoCPF.setText("");campoNascimento.setText("");
            new TelaLogin().setVisible(true);
            dispose();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,"Erro ao salvar os dados: "+ex.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaRegistro telaRegistro=new TelaRegistro();
            telaRegistro.setVisible(true);
        });
    }
}