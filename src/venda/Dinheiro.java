package venda;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Section;
import com.itextpdf.text.log.SysoCounter;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.Color;
import java.awt.Desktop;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;

public class Dinheiro extends JFrame {

	private JPanel contentPane;
	private JTextField txtDinheiro, txtTroco;
	private JButton btnPagar, btnCalcular;
	private String valorTotal;
	private String[][] compra;
	private int item;
	private float troco;
	private JTextField txtValor;

	public Dinheiro(String[][] pcompra, String pvalorTotal, int pItem) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 315, 164);
		this.valorTotal = pvalorTotal;
		this.compra = pcompra;
		this.item = pItem;
		this.setTitle("Pagamento em Dinheiro");
		this.setResizable(false);
		this.criaComponentes();
		this.criaAcoes();
		this.setVisible(true);

	}

	public void criaComponentes() {
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblDinheiro = new JLabel("Dinheiro:");
		lblDinheiro.setBounds(10, 42, 65, 14);
		contentPane.add(lblDinheiro);

		txtDinheiro = new JTextField();
		txtDinheiro.setBounds(85, 39, 86, 20);
		contentPane.add(txtDinheiro);
		txtDinheiro.setColumns(10);

		JLabel lblTroco = new JLabel("Troco:");
		lblTroco.setBounds(10, 73, 46, 14);
		contentPane.add(lblTroco);

		txtTroco = new JTextField();
		txtTroco.setEditable(false);
		txtTroco.setBounds(59, 70, 86, 20);
		contentPane.add(txtTroco);
		txtTroco.setColumns(10);

		btnPagar = new JButton("Pagar | F1 |");
		btnPagar.setBackground(Color.WHITE);
		btnPagar.setBounds(105, 101, 128, 23);
		contentPane.add(btnPagar);

		btnCalcular = new JButton("Calcular | F2 |");
		btnCalcular.setBackground(Color.WHITE);
		btnCalcular.setBounds(179, 7, 120, 23);
		contentPane.add(btnCalcular);

		JLabel lblValorTotal = new JLabel("Valor Total:");
		lblValorTotal.setBounds(10, 17, 65, 14);
		contentPane.add(lblValorTotal);

		txtValor = new JTextField();
		txtValor.setEditable(false);
		txtValor.setBounds(83, 14, 86, 20);
		contentPane.add(txtValor);
		txtValor.setColumns(10);
		txtValor.setText(valorTotal);

	}

	public void criaAcoes() {
		btnPagar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!txtTroco.getText().equals("")) {
					gerarPdf();
					JOptionPane.showMessageDialog(null, "Compra realizada com sucesso");
					setVisible(false);
				} else {
					JOptionPane.showMessageDialog(null, "Informe o dinheiro a ser pago");
				}

			}
		});

		btnPagar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0),
				"evento");
		btnPagar.getActionMap().put("evento", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!txtTroco.getText().equals("")) {
					gerarPdf();
					JOptionPane.showMessageDialog(null, "Compra realizada com sucesso");
					setVisible(false);
				} else {
					JOptionPane.showMessageDialog(null, "Informe o dinheiro a ser pago");
				}
			}
		});

		btnCalcular.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				valorTotal = converteValor(valorTotal);
				if (Float.parseFloat(txtDinheiro.getText()) >= Float.parseFloat(valorTotal)) {
					troco = Float.parseFloat(txtDinheiro.getText()) - Float.parseFloat(valorTotal);
					txtTroco.setText(converte(troco));
					// Venda venda = new Venda();
				} else {
					JOptionPane.showMessageDialog(null, "Dinheiro insuficiente");
					txtDinheiro.setText("");
				}

			}
		});

		btnCalcular.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0),
				"evento");
		btnCalcular.getActionMap().put("evento", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				valorTotal = converteValor(valorTotal);
				if (Float.parseFloat(txtDinheiro.getText()) >= Float.parseFloat(valorTotal)) {
					troco = Float.parseFloat(txtDinheiro.getText()) - Float.parseFloat(valorTotal);
					txtTroco.setText(converte(troco));
					// Venda venda = new Venda();
				} else {
					JOptionPane.showMessageDialog(null, "Dinheiro insuficiente");
					txtDinheiro.setText("");
				}
			}
		});
	}

	public void gerarPdf() {

		double dinheiro = Double.parseDouble(txtDinheiro.getText());

		Font fontePadrao = new Font(Font.FontFamily.TIMES_ROMAN, 19, Font.BOLD);
		Document cupom = new Document();
		// int cont = Integer.parseInt(arg0)
		try {
			PdfWriter.getInstance(cupom, new FileOutputStream("Nota Fiscal.pdf"));

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
			Paragraph cupomFiscal = new Paragraph("CUPOM   FISCAL", fontePadrao);
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

			c1 = new PdfPCell(new Phrase("DESCRIÇÃO"));
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

			for (int i = 0; i < item; i++) {
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
			cupom.add(new Paragraph(
					"TOTAL                      R$                                                      "
							+ converte(Double.parseDouble(valorTotal)),
					fontePadrao));
			cupom.add(new Paragraph("DINHEIRO               R$                                                      "
					+ converte(dinheiro), fontePadrao));
			cupom.add(new Paragraph(
					"TROCO                     R$                                                        "
							+ converte(troco),
					fontePadrao));
			cupom.add(
					new Paragraph("______________________________________________________________________________\n"));
			cupom.add(new Paragraph("Operador: " + compra[0][6]));

		} catch (FileNotFoundException | DocumentException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Erro ao gerar a nota fiscal");
		} finally {
			cupom.close();
		}

		try {
			Desktop.getDesktop().open(new File("Nota Fiscal.pdf"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Erro ao abrir a nota fiscal");
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
	
	public String converteValor(String valor){
		String valorConvertido = "";
		try {
			NumberFormat nf = NumberFormat.getNumberInstance(new Locale("pt", "BR")); 
			valorConvertido = String.valueOf(nf.parse(valor));
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return valorConvertido;
	}
	
	public String converte(double valor){
		NumberFormat doubleformat = NumberFormat.getInstance();
		doubleformat.setMinimumFractionDigits(2);
		doubleformat.setMaximumFractionDigits(2);
		String valorConv = doubleformat.format(valor);
		
		return valorConv;
	}
}
