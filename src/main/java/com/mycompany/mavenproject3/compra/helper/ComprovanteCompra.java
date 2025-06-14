package com.mycompany.mavenproject3.compra.helper;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ComprovanteCompra {

    public static void gerarComprovante(String cpf, String nomeUsuario, String tituloPeca, String horarioSessao, List<String> poltronas, double total, double descontoPercentual) {
        Document document = new Document(PageSize.A6, 25, 25, 20, 20);
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Salvar Comprovante");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
            fileChooser.setInitialFileName("comprovante_compra.pdf");

            File file = fileChooser.showSaveDialog(new Stage());
            if (file == null) return;

            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            Font tituloFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD, new BaseColor(212, 175, 55));
            Font textoFont = new Font(Font.FontFamily.TIMES_ROMAN, 12);
            Font destaqueFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK);

            Paragraph titulo = new Paragraph("🎭 DramaCore Theatre 🎭", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(10);
            document.add(titulo);

            document.add(new Paragraph("📅 Data: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), textoFont));
            document.add(new Paragraph("👤 Usuário: " + nomeUsuario, textoFont));
            document.add(new Paragraph("🪪 CPF: " + cpf, textoFont));
            document.add(new Paragraph("🎬 Peça: " + tituloPeca, textoFont));
            document.add(new Paragraph("🕒 Sessão: " + horarioSessao, textoFont));
            document.add(new Paragraph("💺 Poltronas:", destaqueFont));

            for (String p : poltronas) {
                document.add(new Paragraph("   • " + p, textoFont));
            }

            if (descontoPercentual > 0) {
                document.add(new Paragraph(String.format("\n🎁 Desconto aplicado: %.0f%%", descontoPercentual), textoFont));
            }

            document.add(new Paragraph("\n💰 Total pago: R$" + String.format("%.2f", total), destaqueFont));

            Paragraph rodape = new Paragraph(
                    "\nAgradecemos sua preferência!\nTenha um espetáculo memorável.",
                    textoFont
            );
            rodape.setAlignment(Element.ALIGN_CENTER);
            rodape.setSpacingBefore(10);
            document.add(rodape);

        } catch (Exception e) {
            new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR, "Erro ao gerar PDF.").showAndWait();
            e.printStackTrace();
        } finally {
            document.close();
        }
    }
}
