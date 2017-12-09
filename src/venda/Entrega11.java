package venda;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.swing.JOptionPane;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import Janelas.Login;

public class Entrega11 {

	Login longin;

	public Entrega11(String[][] compra, int item, Login logq) {
		longin = logq;
		Font fontePadrao = new Font(Font.FontFamily.TIMES_ROMAN, 19, Font.BOLD);
		Document cupom = new Document();
		// int cont = Integer.parseInt(arg0)
		try {
			PdfWriter.getInstance(cupom, new FileOutputStream("Nota Entrega.pdf"));

			cupom.open();
			cupom.setPageSize(PageSize.A4);

			Paragraph nome = new Paragraph(
					"CENCOSUD BRASIL COMERCIAL LTDA\nAVENIDA CINCO, 384\nANEXO 1: 01 - CENTRO - CEP:38300-156\nITUIUTABA - MG\nCNPJ:39.346.861/0330-94    IE:001.834103.6495");
			nome.setAlignment(Element.ALIGN_CENTER);
			cupom.add(nome);

			cupom.add(new Paragraph("______________________________________________________________________________"));
			Paragraph DataeHora = new Paragraph(pegaData() + " " + pegaHora());
			DataeHora.setAlignment(Element.ALIGN_CENTER);
			cupom.add(DataeHora);
			Paragraph cupomFiscal = new Paragraph("CUPOM   Entrega", fontePadrao);
			cupomFiscal.setAlignment(Element.ALIGN_CENTER);
			cupom.add(cupomFiscal);

			cupom.add(new Paragraph("\n"));

			PdfPTable table = new PdfPTable(new float[] { 0.09f, 0.08f, 0.5f, 0.08f, 0.13f, 0.13f });
			table.setWidthPercentage(100.0f);
			table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);

			PdfPCell c1 = new PdfPCell(new Phrase("ITEM"));
			c1.setBorder(Rectangle.NO_BORDER);
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);

			c1 = new PdfPCell(new Phrase("COD"));
			c1.setBorder(Rectangle.NO_BORDER);
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);

			c1 = new PdfPCell(new Phrase("DESCRI√á√ÉO"));
			c1.setBorder(Rectangle.NO_BORDER);
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);

			c1 = new PdfPCell(new Phrase("QTD"));
			c1.setBorder(Rectangle.NO_BORDER);
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);

			c1 = new PdfPCell(new Phrase("VL UNI"));
			c1.setBorder(Rectangle.NO_BORDER);
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);

			c1 = new PdfPCell(new Phrase("TOTAL"));
			c1.setBorder(Rectangle.NO_BORDER);
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			for (int i = 0; i < longin.getGanbiara(); i++) {

				c1 = new PdfPCell(new Phrase(compra[i][0]));
				c1.setBorder(Rectangle.NO_BORDER);
				c1.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(c1);
				c1 = new PdfPCell(new Phrase(compra[i][1]));
				c1.setBorder(Rectangle.NO_BORDER);
				c1.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(c1);
				c1 = new PdfPCell(new Phrase(compra[i][2]));
				c1.setBorder(Rectangle.NO_BORDER);
				c1.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(c1);
				c1 = new PdfPCell(new Phrase(compra[i][3]));
				c1.setBorder(Rectangle.NO_BORDER);
				c1.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(c1);
				c1 = new PdfPCell(new Phrase(compra[i][4]));
				c1.setBorder(Rectangle.NO_BORDER);
				c1.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(c1);
				c1 = new PdfPCell(new Phrase(compra[i][5]));
				c1.setBorder(Rectangle.NO_BORDER);
				c1.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(c1);
			}
			cupom.add(table);
			cupom.add(
					new Paragraph("______________________________________________________________________________\n"));
			cupom.add(
					new Paragraph("______________________________________________________________________________\n"));
			cupom.add(new Paragraph("Operador: " + compra[0][6]));
			cupom.add(new Paragraph("Nome Cliente: " + longin.getAux()[0]));
			cupom.add(new Paragraph("CPF : " + longin.getAux()[1]));
			cupom.add(new Paragraph("CEP : " + longin.getAux()[2]));
			cupom.add(new Paragraph("EndereÁo : " + longin.getAux()[3]));
			cupom.add(new Paragraph("Numero : " + longin.getAux()[4]));
			cupom.add(new Paragraph("Bairro" + longin.getAux()[5]));
			cupom.add(new Paragraph("Ponto De ReferÍncia : " + longin.getAux()[6]));

		} catch (FileNotFoundException | DocumentException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Erro ao gerar a nota Entrega");
		} finally {
			cupom.close();
		}

		try {
			Desktop.getDesktop().open(new File("Nota Entrega.pdf"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Erro ao abrir a nota Entrega");
		}
	}

	public String pegaHora() {

		String hora = "hh:mm:ss";
		String hora1;
		java.util.Date agora = new java.util.Date();
		;

		SimpleDateFormat formata = new SimpleDateFormat(hora);
		hora1 = formata.format(agora);
		return hora1;
	}

	public String pegaData() {
		String data = "dd/MM/yyyy";
		String data1;
		java.util.Date agora = new java.util.Date();
		;
		SimpleDateFormat formata = new SimpleDateFormat(data);
		data1 = formata.format(agora);

		return data1;
	}

}
