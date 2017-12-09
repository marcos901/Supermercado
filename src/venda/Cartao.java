package venda;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

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

import Janelas.Inicial;

import java.awt.Color;
import java.awt.Desktop;

import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ItemEvent;

public class Cartao extends JFrame {

	private JPanel contentPane, panelAVista, panelAPrazo;
	private JTabbedPane tabbedPane;
	private JButton btnPagarAVista, btnPagarParcelado;
	private JLabel lblParcelas, lblValor;
	private JTextField txtValor;
	private JComboBox cmbParcelas;
	private String valorTotal = null;
	private JTextField txtParcela;
	private double parcela = 0.0;
	private String[][] compra;
	private int item, idFuncionario, idVenda;
	private String hora2;

	public Cartao(String pcompra[][], String pvalorTotal, int pItem) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 290, 208);

		this.setTitle("Pagamento em CartÃ£o");
		this.valorTotal = pvalorTotal;
		this.compra = pcompra;
		this.item = pItem;
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

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 284, 179);
		contentPane.add(tabbedPane);

		panelAVista = new JPanel();
		panelAVista.setBackground(Color.WHITE);
		tabbedPane.addTab("Débito", null, panelAVista, null);
		panelAVista.setLayout(null);

		btnPagarAVista = new JButton("Pagar |  F1 |");
		btnPagarAVista.setBackground(Color.WHITE);
		btnPagarAVista.setBounds(78, 42, 121, 46);
		panelAVista.add(btnPagarAVista);

		panelAPrazo = new JPanel();
		panelAPrazo.setBackground(Color.WHITE);
		tabbedPane.addTab("Crédito", null, panelAPrazo, null);
		panelAPrazo.setLayout(null);

		lblParcelas = new JLabel("Parcelas:");
		lblParcelas.setBounds(10, 11, 56, 14);
		panelAPrazo.add(lblParcelas);

		cmbParcelas = new JComboBox();
		cmbParcelas.setModel(new DefaultComboBoxModel(new String[] { "1x", "2x", "4x", "6x" }));
		cmbParcelas.setSelectedIndex(-1);
		cmbParcelas.setBounds(67, 8, 56, 20);
		panelAPrazo.add(cmbParcelas);

		btnPagarParcelado = new JButton("Pagar | F2 |");
		btnPagarParcelado.setBounds(88, 120, 116, 23);
		panelAPrazo.add(btnPagarParcelado);

		lblValor = new JLabel("Valor:");
		lblValor.setBounds(10, 76, 46, 14);
		panelAPrazo.add(lblValor);

		txtValor = new JTextField();
		txtValor.setEditable(false);
		txtValor.setBounds(10, 89, 86, 20);
		txtValor.setText(valorTotal);
		panelAPrazo.add(txtValor);
		txtValor.setColumns(10);

		JLabel lblValorParcelado = new JLabel("Valor Parcelado: ");
		lblValorParcelado.setBounds(10, 34, 100, 14);
		panelAPrazo.add(lblValorParcelado);

		txtParcela = new JTextField();
		txtParcela.setEditable(false);
		txtParcela.setBounds(10, 50, 86, 20);
		panelAPrazo.add(txtParcela);
		txtParcela.setColumns(10);

	}

	public void criaAcoes() {
		btnPagarAVista.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				salvarCompra();
				pegaIdVenda();
				salvarProdutos();
				gerarPdfDebito();
				JOptionPane.showMessageDialog(null, "Compra realizada com sucesso");
				setVisible(false);
			}
		});

		btnPagarAVista.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0),
				"evento");
		btnPagarAVista.getActionMap().put("evento", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				salvarCompra();
				pegaIdVenda();
				salvarProdutos();
				gerarPdfDebito();
				JOptionPane.showMessageDialog(null, "Compra realizada com sucesso");
				setVisible(false);
			}
		});

		btnPagarParcelado.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (cmbParcelas.getSelectedIndex() != -1) {
					salvarCompra();
					pegaIdVenda();
					salvarProdutos();
					gerarPdfCredito();
					JOptionPane.showMessageDialog(null, "Compra realizada com sucesso");
					setVisible(false);
				} else {
					JOptionPane.showMessageDialog(null, "Informe o nÃºmero de parcelas");
				}

			}
		});

		btnPagarParcelado.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0),
				"evento");
		btnPagarParcelado.getActionMap().put("evento", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (cmbParcelas.getSelectedIndex() != -1) {
					salvarCompra();
					pegaIdVenda();
					salvarProdutos();
					gerarPdfCredito();
					JOptionPane.showMessageDialog(null, "Compra realizada com sucesso");
					setVisible(false);
				} else {
					JOptionPane.showMessageDialog(null, "Informe o nÃºmero de parcelas");
				}
			}
		});

		cmbParcelas.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				valorTotal = converteValor(valorTotal);
				
				String quantParcela = String.valueOf(cmbParcelas.getSelectedItem());
				quantParcela = quantParcela.replace("x", "");
				parcela = Double.parseDouble(valorTotal) / Double.parseDouble(quantParcela);
				txtParcela.setText(String.valueOf(converte(parcela)));
			}
		});
	}

	public void gerarPdfDebito() {
		
		valorTotal = converteValor(valorTotal);
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
					"TOTAL                      R$                                                       "
							+ converte(Double.parseDouble(valorTotal)),
					fontePadrao));
			cupom.add(new Paragraph(
					"DÉBITO                    R$                                                       "
							+ converte(Double.parseDouble(valorTotal)),
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

	public void gerarPdfCredito() {
		
		valorTotal = converteValor(valorTotal);
		String parcela = txtParcela.getText();
		parcela = converteValor(parcela);
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
			NumberFormat nf = NumberFormat.getNumberInstance(new Locale("pt", "BR")); 
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
					"TOTAL                      R$                                                       "
							+ converte(Double.parseDouble(valorTotal)),
					fontePadrao));
			cupom.add(new Paragraph("CRÉDITO                 R$                                                   "
					+ cmbParcelas.getSelectedItem().toString() + " " + converte(Double.parseDouble(parcela)),
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

		SimpleDateFormat formata = new SimpleDateFormat(hora);
		hora1 = formata.format(agora);
		hora2 = hora1;
		return hora1;
	}

	public String pegaData() {
		String data = "dd/MM/yyyy";
		String data1;
		java.util.Date agora = new java.util.Date();

		SimpleDateFormat formata = new SimpleDateFormat(data);
		data1 = formata.format(agora);

		return data1;
	}
	
	public String pegaDataParaBancoDeDados() {
		String data = "yyyy-MM-dd";
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
	
	public void salvarCompra() {
		try {
			pegaIdFuncionario();
			pegaHora();
			// Procura por uma classe no projeto
			Class.forName("com.mysql.jdbc.Driver");

			// Cria uma variavel de conex�o (local do banco, usuario, senha)
			// Connection tem q ser a do .sql
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/supermercado", "root", "");

			// Cria a string de inser��o no banco
			String query = "INSERT INTO vendas (id_funcionario, data, hora, metodo_pagamento, valor_total) VALUES(?,?,?,?,?)";

			// Cria o comando
			// PreparedStatement tem q ser a do .sql
			PreparedStatement stmt = con.prepareStatement(query);

			// Seta os valores na string de inser��o
			stmt.setInt(1, idFuncionario);
			stmt.setString(2, pegaDataParaBancoDeDados());
			stmt.setString(3, hora2);
			stmt.setString(4, "Dinheiro");
			stmt.setString(5, compra[0][5]);
			

			// Executa o comando no banco de dados
			stmt.executeUpdate();

			// Fecha comando e conex�o
			stmt.close();
			con.close();

		} catch (Exception e) {
			System.out.println("Erro: " + e);
		}
	}

	public void pegaIdFuncionario() {
		try {

			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/supermercado", "root", "");

			String query = "SELECT * FROM funcionarios";

			PreparedStatement stmt = con.prepareStatement(query);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				if (rs.getString("nome").equals(compra[0][6])) {
					idFuncionario = rs.getInt("id");
				}

			}

			con.close();
			stmt.close();
			rs.close();

		} catch (Exception e) {
			System.out.println("Erro: " + e);
		}
	}

	public void salvarProdutos() {
		try {
			pegaIdFuncionario();
			
			for (int i = 0; i < compra.length; i++) {
				// Procura por uma classe no projeto
				Class.forName("com.mysql.jdbc.Driver");

				// Cria uma variavel de conex�o (local do banco, usuario, senha)
				// Connection tem q ser a do .sql
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost/supermercado", "root", "");

				// Cria a string de inser��o no banco
				String query = "INSERT INTO produtos_vendas (id_produto, id_venda, quantidade) VALUES(?,?,?)";

				// Cria o comando
				// PreparedStatement tem q ser a do .sql
				PreparedStatement stmt = con.prepareStatement(query);

				// Seta os valores na string de inser��o
				stmt.setInt(1, Integer.parseInt(compra[i][1]));
				stmt.setInt(2, idVenda);
				stmt.setInt(3, Integer.parseInt(compra[i][3]));

				// Executa o comando no banco de dados
				stmt.executeUpdate();

				// Fecha comando e conex�o
				stmt.close();
				con.close();
			}
			

		} catch (Exception e) {
			System.out.println("Erro: " + e);
		}
	}

	public void pegaIdVenda() {
		try {
			
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/supermercado", "root", "");
			
			String query = "SELECT * FROM vendas";
			PreparedStatement stmt = con.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				if (rs.getDate("data").toString().equals(pegaDataParaBancoDeDados()) && rs.getTime("hora").toString().equals(hora2)) {
					idVenda = rs.getInt("id");		
				}	
			}
			
			con.close();
			stmt.close();
			rs.close();

		} catch (Exception e) {
			System.out.println("Erro: " + e);
		}
	}
}
