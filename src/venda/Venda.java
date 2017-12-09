package venda;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Desktop;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;

import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.JTextField;
import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.plaf.synth.SynthStyle;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.log.SysoCounter;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import Janelas.Inicial;
import Janelas.Login;
import cliente.CadastrarCliente;
import cliente.CadastroClienteEntrega;
import cliente.ConsultaCliente;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.AttributedCharacterIterator;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Venda extends JFrame {

	private JPanel contentPaneVenda;
	private JTextField txtCodigoBarras;
	private DefaultListModel modeloProduto;
	private JTable tableVenda;
	private DefaultTableModel modelo;
	private int cont = 0;
	private JLabel lblCdigoDeBarras;
	private JLabel lblDescrio;
	private JTextField txtDescricao;
	private JLabel lblQuant;
	private JTextField txtQuant;
	private JLabel lblValorUn;
	private JTextField txtValor;
	private JLabel lblValorTotal;
	private JTextField txtValorTotal;
	private JLabel lblOperador;
	private JLabel lblOp;
	private JLabel lblHora;
	private JLabel lblData;
	private Double valorTotal = 0.0, valorTotalCompra = 0.0;
	private JButton btnFinalizarVenda, btnCancelarProduto, btnVoltar, btnEntrega;
	private String nomeFunc, nomeProd, preco, cod;
	private boolean confirmado, conEntrega = false, enTrega = false;
	private Login login1;
	private String[][] compra;
	private String uu1;

	public Venda(Login login,String um) {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 766, 396);
		login1 = login;
		criaComponente();
		Acoes();
		this.uu1 = um;

		new Thread() {
			@Override
			public void run() {
				for (;;) {
					dataEHora();
					try {
						sleep(1000);
					} catch (InterruptedException ie) {
						ie.printStackTrace();
					}
				}
			}
		}.start();

	}

	public void criaComponente() {
		contentPaneVenda = new JPanel();
		contentPaneVenda.setBackground(Color.WHITE);
		contentPaneVenda.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPaneVenda);
		contentPaneVenda.setLayout(null);

		txtCodigoBarras = new JTextField();
		txtCodigoBarras.setFont(new Font("Tahoma", Font.PLAIN, 13));

		txtCodigoBarras.setBounds(10, 107, 236, 20);
		contentPaneVenda.add(txtCodigoBarras);
		txtCodigoBarras.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(256, 81, 484, 267);
		scrollPane.getViewport().setBackground(Color.WHITE);

		contentPaneVenda.add(scrollPane);

		tableVenda = new JTable();
		tableVenda.setBackground(Color.WHITE);
		scrollPane.setViewportView(tableVenda);
		tableVenda.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "ITEM", "C\u00D3DIGO", "DESCRI\u00C7\u00C3O", "QTD", "VL. UN", "TOTAL" }) {
			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableVenda.setColumnSelectionAllowed(false);
		tableVenda.getColumnModel().getColumn(0).setPreferredWidth(42);
		tableVenda.getColumnModel().getColumn(1).setPreferredWidth(83);
		tableVenda.getColumnModel().getColumn(2).setPreferredWidth(235);
		tableVenda.getColumnModel().getColumn(5).setResizable(false);
		tableVenda.getColumnModel().getColumn(5).setPreferredWidth(80);

		lblCdigoDeBarras = new JLabel("C\u00F3digo de Barras");
		lblCdigoDeBarras.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCdigoDeBarras.setBounds(10, 83, 140, 21);
		contentPaneVenda.add(lblCdigoDeBarras);

		lblDescrio = new JLabel("Descri\u00E7\u00E3o ");
		lblDescrio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDescrio.setBounds(10, 11, 75, 20);
		contentPaneVenda.add(lblDescrio);

		txtDescricao = new JTextField();
		txtDescricao.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtDescricao.setEditable(false);
		txtDescricao.setBackground(Color.WHITE);
		txtDescricao.setBounds(10, 34, 248, 30);
		contentPaneVenda.add(txtDescricao);

		// txtDescriÃ§Ã£o.setText("Laranja");

		lblQuant = new JLabel("Quant.");
		lblQuant.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblQuant.setBounds(10, 138, 46, 20);
		contentPaneVenda.add(lblQuant);

		txtQuant = new JTextField();
		txtQuant.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtQuant.setBounds(10, 159, 56, 20);
		contentPaneVenda.add(txtQuant);
		txtQuant.setColumns(10);

		lblValorUn = new JLabel("Valor Unit\u00E1rio");
		lblValorUn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblValorUn.setBounds(78, 138, 99, 20);
		contentPaneVenda.add(lblValorUn);

		txtValor = new JTextField();
		txtValor.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtValor.setBackground(Color.WHITE);
		txtValor.setEditable(false);
		txtValor.setBounds(76, 159, 101, 20);
		contentPaneVenda.add(txtValor);
		txtValor.setColumns(10);

		lblValorTotal = new JLabel("Valor Total");
		lblValorTotal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblValorTotal.setBounds(10, 198, 75, 20);
		contentPaneVenda.add(lblValorTotal);

		txtValorTotal = new JTextField();
		txtValorTotal.setBackground(Color.WHITE);
		txtValorTotal.setEditable(false);
		txtValorTotal.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtValorTotal.setBounds(10, 221, 86, 20);
		contentPaneVenda.add(txtValorTotal);
		txtValorTotal.setColumns(10);

		lblOperador = new JLabel("Operador:");
		lblOperador.setBounds(311, 11, 68, 14);
		contentPaneVenda.add(lblOperador);

		System.out.println(login1.getNomeFunc());
		System.out.println(uu1);
		lblOp = new JLabel(login1.getNomeFunc());
		lblOp.setBounds(374, 11, 400, 14);
		contentPaneVenda.add(lblOp);

		lblHora = new JLabel("");
		lblHora.setBounds(311, 34, 112, 14);
		contentPaneVenda.add(lblHora);

		lblData = new JLabel("");
		lblData.setBounds(311, 56, 112, 14);
		contentPaneVenda.add(lblData);

		btnCancelarProduto = new JButton("REMOVER PRODUTO | F3 |");
		btnCancelarProduto.setBackground(Color.WHITE);
		btnCancelarProduto.setBounds(545, 47, 195, 23);
		contentPaneVenda.add(btnCancelarProduto);

		btnFinalizarVenda = new JButton("FINALIZAR VENDA | F1 |");
		btnFinalizarVenda.setBackground(Color.WHITE);
		btnFinalizarVenda.setBounds(22, 279, 190, 23);
		contentPaneVenda.add(btnFinalizarVenda);

		btnVoltar = new JButton("VOLTAR | ESC |");
		btnVoltar.setBackground(Color.WHITE);
		btnVoltar.setBounds(22, 310, 190, 23);
		contentPaneVenda.add(btnVoltar);

		btnEntrega = new JButton("Entrega | F5 |");
		btnEntrega.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnEntrega.setBackground(Color.WHITE);
		btnEntrega.setBounds(20, 250, 195, 23);
		contentPaneVenda.add(btnEntrega);

		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				txtCodigoBarras.grabFocus();
				txtCodigoBarras.requestFocus();// or inWindow
			}
		});
	}

	public void dataEHora() {
		Calendar cal = new GregorianCalendar();
		int dia = cal.get(Calendar.DAY_OF_MONTH);
		int mes = cal.get(Calendar.MONTH);
		int ano = cal.get(Calendar.YEAR);
		int hora = cal.get(Calendar.HOUR);
		int minuto = cal.get(Calendar.MINUTE);
		int segundo = cal.get(Calendar.SECOND);
		String horaString;// nova string horas
		String minString;// nova string minutos
		String segundoString;// nova string segundos
		if (hora < 10) {// se hora for menor que 10 precisa colocar um 0 Ã  esquerda
			horaString = "0" + hora;
		} else {
			horaString = "" + hora;
		}
		if (minuto < 10) {// se minuto for menor que 10 precisa colocar um 0 Ã  esquerda
			minString = "0" + minuto;
		} else {
			minString = "" + minuto;
		}
		if (segundo < 10) {// se segundo for menor que 10 precisa colocar um 0 Ã  esquerda
			segundoString = "0" + segundo;
		} else {
			segundoString = "" + segundo;
		}
		lblData.setText("Data: " + dia + "/" + (mes + 1) + "/" + ano);
		lblHora.setText("Hora: " + horaString + ":" + minString + ":" + segundoString);
	}

	public void Acoes() {

		txtCodigoBarras.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(java.awt.event.KeyEvent evt) {
				NumberFormat doubleformat = NumberFormat.getInstance();
				doubleformat.setMinimumFractionDigits(2);
				doubleformat.setMaximumFractionDigits(2);
				if (evt.getKeyCode() == KeyEvent.VK_ENTER && !txtCodigoBarras.getText().trim().equals("")
						&& !txtQuant.getText().equals("")) {

					modelo = (DefaultTableModel) tableVenda.getModel();

					VerificarProduto();
					if (confirmado == true) {
						cont++;
						txtDescricao.setText(nomeProd);
						txtValor.setText(doubleformat.format(Double.parseDouble(preco)));
						valorTotal = Double.parseDouble(txtQuant.getText()) * Double.parseDouble(preco);
						valorTotalCompra += valorTotal;
						txtValorTotal.setText(String.valueOf(doubleformat.format(valorTotalCompra)));
						modelo.addRow(new String[] { String.valueOf(cont), cod, nomeProd, txtQuant.getText(),
								doubleformat.format(Double.parseDouble(preco)),
								String.valueOf(doubleformat.format(valorTotal)) });
						confirmado = false;
					} else {
						JOptionPane.showMessageDialog(null, "Código Incorreto");
					}

					txtCodigoBarras.setText("");
					txtQuant.setText("");
				}

			}
		});

		btnFinalizarVenda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FinalizarCompra();
			}
		});

		btnFinalizarVenda.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0),
				"evento");
		btnFinalizarVenda.getActionMap().put("evento", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FinalizarCompra();
			}
		});

		btnEntrega.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Entrega();
				if (enTrega == true && conEntrega == true) {
					Entrega11 entr = new Entrega11(compra, cont, login1);
					enTrega = false;
				} else {
					JOptionPane.showMessageDialog(null, "FINALIZE A VENDA", "AREN��O", JOptionPane.INFORMATION_MESSAGE);
				}

			}
		});

		btnEntrega.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0),
				"evento");
		btnEntrega.getActionMap().put("evento", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Entrega();
				if (enTrega == true && conEntrega == true) {
					Entrega11 entr = new Entrega11(compra, cont, login1);
					enTrega = false;
				} else {
					JOptionPane.showMessageDialog(null, "FINALIZE A VENDA", "AREN��O", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		txtQuant.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(java.awt.event.KeyEvent evt) {
				NumberFormat doubleformat = NumberFormat.getInstance();
				doubleformat.setMinimumFractionDigits(2);
				doubleformat.setMaximumFractionDigits(2);
				if (evt.getKeyCode() == KeyEvent.VK_ENTER && !txtCodigoBarras.getText().trim().equals("")
						&& !txtQuant.getText().equals("")) {

					modelo = (DefaultTableModel) tableVenda.getModel();

					VerificarProduto();
					if (confirmado == true) {
						cont++;
						txtDescricao.setText(nomeProd);
						txtValor.setText(doubleformat.format(Double.parseDouble(preco)));
						valorTotal = Double.parseDouble(txtQuant.getText()) * Double.parseDouble(preco);
						valorTotalCompra += valorTotal;
						txtValorTotal.setText(String.valueOf(doubleformat.format(valorTotalCompra)));
						modelo.addRow(new String[] { String.valueOf(cont), cod, nomeProd, txtQuant.getText(),
								doubleformat.format(Double.parseDouble(preco)),
								String.valueOf(doubleformat.format(valorTotal)) });
						confirmado = false;
					} else {
						JOptionPane.showMessageDialog(null, "Código Incorreto");
					}

					txtCodigoBarras.setText("");
					txtQuant.setText("");
					EventQueue.invokeLater(new Runnable() {

						@Override
						public void run() {
							txtCodigoBarras.grabFocus();
							txtCodigoBarras.requestFocus();// or inWindow
						}
					});
				}

			}
			
			
		});

		btnCancelarProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tableVenda.getSelectedColumn() != -1) {
					String valor = String.valueOf(tableVenda.getValueAt(tableVenda.getSelectedRow(), 5));
					// cont--;
					valorTotalCompra -= Double.parseDouble(converteValor(valor));
					((DefaultTableModel) tableVenda.getModel()).removeRow(tableVenda.getSelectedRow());
					txtValorTotal.setText(String.valueOf(converte(valorTotalCompra)));
					txtDescricao.setText("");
					txtValor.setText("");
				} else {
					JOptionPane.showMessageDialog(null, "Informe o produto a ser cancelado");
				}
			}
		});

		btnCancelarProduto.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0),
				"evento");
		btnCancelarProduto.getActionMap().put("evento", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (tableVenda.getSelectedColumn() != -1) {
					String valor = String.valueOf(tableVenda.getValueAt(tableVenda.getSelectedRow(), 5));
					// cont--;
					valorTotalCompra -= Double.parseDouble(converteValor(valor));
					((DefaultTableModel) tableVenda.getModel()).removeRow(tableVenda.getSelectedRow());
					txtValorTotal.setText(String.valueOf(converte(valorTotalCompra)));
					txtDescricao.setText("");
					txtValor.setText("");

				} else {
					JOptionPane.showMessageDialog(null, "Informe o produto a ser cancelado");
				}
			}
		});

		btnVoltar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Inicial inicio = new Inicial(login1);
				inicio.setVisible(true);
				setVisible(false);

			}
		});

		btnVoltar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				"evento");
		btnVoltar.getActionMap().put("evento", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Inicial inicio = new Inicial(login1);
				inicio.setVisible(true);
				setVisible(false);
			}
		});
	}

	public void FinalizarCompra() {

		if (tableVenda.getRowCount() != 0) {
			enTrega = true;
			compra = new String[cont][7];
			login1.setGanbiara(tableVenda.getRowCount());

			for (int i = 0; i < tableVenda.getRowCount(); i++) {
				String valorUn = String.valueOf(tableVenda.getValueAt(i, 4));
				String valorTot = String.valueOf(tableVenda.getValueAt(i, 5));

				valorUn = valorUn.replace(",", ".");
				valorTot = valorTot.replace(",", ".");

				// Item
				compra[i][0] = String.valueOf(tableVenda.getValueAt(i, 0));

				// Codigo
				compra[i][1] = String.valueOf(tableVenda.getValueAt(i, 1));

				// System.out.println(String.valueOf(tableVenda.getValueAt(i, 1)));

				// Descricao
				compra[i][2] = String.valueOf(tableVenda.getValueAt(i, 2));

				// System.out.println( String.valueOf(tableVenda.getValueAt(i, 2)));

				// Quantidade
				compra[i][3] = String.valueOf(tableVenda.getValueAt(i, 3));

				// System.out.println( String.valueOf(tableVenda.getValueAt(i, 3)));

				// Valor Un
				compra[i][4] = valorUn;

				// System.out.println( String.valueOf(tableVenda.getValueAt(i, 4)));

				// Valor Total
				compra[i][5] = valorTot;

				// System.out.println( String.valueOf(tableVenda.getValueAt(i, 5)));

				// Operador
				compra[i][6] = lblOp.getText();

				login1.setEntraga(compra);

			}

			SelecaoPagamentos pagamento = new SelecaoPagamentos(compra, txtValorTotal.getText(), cont);
			DefaultTableModel tblRemove = (DefaultTableModel) tableVenda.getModel();

			while (tblRemove.getRowCount() > 0) {
				tblRemove.removeRow(0);
			}
			txtDescricao.setText("");
			txtValor.setText("");
			txtValorTotal.setText("");
			cont = 0;
		} else {
			JOptionPane.showMessageDialog(null, "Insira algum produto 123");
		}
	}

	public void VerificarProduto() {
		try {

			cod = txtCodigoBarras.getText();

			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/supermercado", "root", "");

			String query = "SELECT * FROM produtos";

			PreparedStatement stmt = con.prepareStatement(query);

			// Pega o resultado la no banco
			ResultSet rs = stmt.executeQuery();
			int cont = 0;
			while (rs.next()) {
				cont++;

				if (rs.getString("codigo").equals(txtCodigoBarras.getText().trim())) {

					confirmado = true;
					nomeProd = rs.getString("nome");
					preco = rs.getString("valor_venda");
					cod = rs.getString("codigo");
				}
			}

			con.close();
			rs.close();

		} catch (Exception e) {
			System.out.println("Erro: " + e);
		}
	}

	public String converteValor(String valor) {
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

	public String converte(double valor) {
		NumberFormat doubleformat = NumberFormat.getInstance();
		doubleformat.setMinimumFractionDigits(2);
		doubleformat.setMaximumFractionDigits(2);
		String valorConv = doubleformat.format(valor);

		return valorConv;
	}

	public void Entrega() {
		try {

			String cpf = JOptionPane.showInputDialog("Digite o CPF do CLIENTE");

			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/supermercado", "root", "");

			String query = "SELECT * FROM clientes";

			PreparedStatement stmt = con.prepareStatement(query);

			// Pega o resultado la no banco
			ResultSet rs = stmt.executeQuery();
			int cont = 0;
			String aux[] = new String[7];
			while (rs.next()) {
				cont++;

				if (rs.getString("cpf").equals(cpf)) {
					conEntrega = true;
					aux[0] = rs.getString("nome");
					aux[1] = rs.getString("cpf");
					aux[2] = rs.getString("cep");
					aux[3] = rs.getString("endereco");
					aux[4] = rs.getString("numero");
					aux[5] = rs.getString("bairro");
					aux[6] = rs.getString("ponto_referencia");

				}
			}

			login1.setAux(aux);

			if (conEntrega == false) {
				int coNT = JOptionPane.showConfirmDialog(null, "Deseja Cadastrar o CLIENTE");
				if (coNT == 0) {
					CadastroClienteEntrega cada = new CadastroClienteEntrega();
					cada.setVisible(true);
				}
			}

			con.close();
			rs.close();

		} catch (Exception e) {
			System.out.println("Erro: " + e);
		}

	}

}
