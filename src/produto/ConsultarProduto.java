package produto;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Janelas.Inicial;
import Janelas.Login;
import cliente.CadastrarCliente;

import javax.swing.JScrollPane;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;

public class ConsultarProduto extends JFrame {

	private JPanel contentPane;
	private JTextField txtNome;
	private JButton btnExlcuir, btnVoltar, btnAlterar, btnCadastrar;
	private JTable tableProduto;
	private JScrollPane scrollPane;
	private DefaultTableModel modelo;
	private Login login;

	public ConsultarProduto(Login login1) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 675, 376);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setResizable(false);
		contentPane.setLayout(null);
		login = login1;
		Componetes();
		CriaAcoes();
		this.mostrarBanco();
	}

	public void Componetes() {
		JLabel lblConsultaCliente = new JLabel("CONSULTA PRODUTO");
		lblConsultaCliente.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblConsultaCliente.setBounds(213, 10, 200, 30);
		contentPane.add(lblConsultaCliente);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNome.setBounds(10, 52, 46, 14);
		contentPane.add(lblNome);

		txtNome = new JTextField();
		txtNome.setBounds(53, 51, 286, 20);
		contentPane.add(txtNome);
		txtNome.setColumns(10);

		btnVoltar = new JButton("Voltar| ESC |");
		btnVoltar.setBackground(Color.WHITE);
		btnVoltar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnVoltar.setBounds(10, 307, 150, 23);
		contentPane.add(btnVoltar);

		btnCadastrar = new JButton("Cadastrar | F1 |");
		btnCadastrar.setBackground(Color.WHITE);
		btnCadastrar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnCadastrar.setBounds(170, 307, 150, 23);
		contentPane.add(btnCadastrar);

		btnExlcuir = new JButton("EXLCUIR | F4 |");
		btnExlcuir.setBackground(Color.WHITE);
		btnExlcuir.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnExlcuir.setBounds(499, 307, 150, 23);
		contentPane.add(btnExlcuir);

		btnAlterar = new JButton("ALTERAR | F3 |");
		btnAlterar.setBackground(Color.WHITE);
		btnAlterar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnAlterar.setBounds(330, 307, 150, 23);
		contentPane.add(btnAlterar);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 86, 606, 210);
		scrollPane.getViewport().setBackground(Color.WHITE);
		contentPane.add(scrollPane);

		tableProduto = new JTable();
		scrollPane.setViewportView(tableProduto);
		tableProduto.setBackground(Color.WHITE);
		tableProduto.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableProduto.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "ID", "ID-FORNERCEDOR", "Nome",
				"Codigo de Barra", "Valor de Custo Uni", "Quantidade", "Valor de Venda Uni" }) {

			boolean[] canEdit = new boolean[] { false, false, false, false, false, false, false };

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});

		tableProduto.getColumnModel().getColumn(1).setPreferredWidth(116);
		tableProduto.getColumnModel().getColumn(2).setPreferredWidth(102);
		tableProduto.getColumnModel().getColumn(3).setPreferredWidth(102);
		tableProduto.getColumnModel().getColumn(4).setPreferredWidth(150);
		tableProduto.getColumnModel().getColumn(5).setPreferredWidth(150);
		tableProduto.getColumnModel().getColumn(6).setPreferredWidth(102);
		tableProduto.setColumnSelectionAllowed(false);

	}

	public void CriaAcoes() {
		btnCadastrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CadastrarProduto cadastrar = new CadastrarProduto(login);
				cadastrar.setVisible(true);
				setVisible(false);

			}
		});

		btnCadastrar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0),
				"evento");
		btnCadastrar.getActionMap().put("evento", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CadastrarProduto cadastrar = new CadastrarProduto(login);
				cadastrar.setVisible(true);
				setVisible(false);
			}
		});

		btnVoltar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Inicial inicio = new Inicial(login);
				inicio.setVisible(true);
				setVisible(false);

			}
		});

		btnAlterar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				PeencherAlterar();
			}
		});

		btnVoltar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				"evento");
		btnVoltar.getActionMap().put("evento", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Inicial inicio = new Inicial(login);
				inicio.setVisible(true);
				setVisible(false);
			}
		});

		btnAlterar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0),
				"evento");
		btnAlterar.getActionMap().put("evento", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {

				PeencherAlterar();

			}
		});

		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(java.awt.event.KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					if (txtNome.getText().equals("")) {
						while (modelo.getRowCount() > 0) {
							modelo.removeRow(0);
						}
						mostrarBanco();
					} else {
						pesquisarBanco();
					}

				}
			}
		});

		btnExlcuir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				excluirBanco();
			}
		});

		btnExlcuir.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0),
				"evento");
		btnExlcuir.getActionMap().put("evento", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				excluirBanco();

			}
		});

	}

	public void pesquisarBanco() {
		try {

			while (modelo.getRowCount() > 0) {
				modelo.removeRow(0);
			}

			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/supermercado", "root", "");

			String query = "SELECT * FROM produtos WHERE nome = ?";

			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, txtNome.getText());

			// Pega o resultado la no banco
			ResultSet rs = stmt.executeQuery();

			modelo = (DefaultTableModel) tableProduto.getModel();
			String[] linha;

			while (rs.next()) {
				// System.out.println("Nome: " + rs.getString("nome"));
				linha = new String[7];
				linha[0] = rs.getString("id");
				linha[1] = rs.getString("id_fornecedor");
				linha[2] = rs.getString("nome");
				linha[3] = rs.getString("codigo");
				linha[4] = rs.getString("valor_custo");
				linha[5] = rs.getString("quantidade");
				linha[6] = rs.getString("valor_venda");
				modelo.addRow(linha);
			}

			con.close();
			rs.close();

		} catch (Exception e) {
			System.out.println("Erro: " + e);
		}
	}

	public void mostrarBanco() {
		try {

			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/supermercado", "root", "");

			String query = "SELECT * FROM produtos";

			PreparedStatement stmt = con.prepareStatement(query);

			// Pega o resultado la no banco
			ResultSet rs = stmt.executeQuery();

			modelo = (DefaultTableModel) tableProduto.getModel();
			String[] linha;

			while (rs.next()) {
				// System.out.println("Nome: " + rs.getString("nome"));
				linha = new String[7];
				linha[0] = rs.getString("id");
				linha[1] = rs.getString("id_fornecedor");
				linha[2] = rs.getString("nome");
				linha[3] = rs.getString("codigo");
				linha[4] = rs.getString("valor_custo");
				linha[5] = rs.getString("quantidade");
				linha[6] = rs.getString("valor_venda");

				modelo.addRow(linha);
			}

			con.close();
			rs.close();

		} catch (Exception e) {
			System.out.println("Erro: " + e);
		}
	}

	public void excluirBanco() {
		try {
			if (tableProduto.getSelectedRow() != -1) {
				Class.forName("com.mysql.jdbc.Driver");

				Connection con = DriverManager.getConnection("jdbc:mysql://localhost/supermercado", "root", "");

				String query = "DELETE FROM produtos WHERE id = ?";

				PreparedStatement stmt = con.prepareStatement(query);

				String index = (String) tableProduto.getModel().getValueAt(tableProduto.getSelectedRow(), 0);
				stmt.setString(1, index);

				stmt.executeUpdate();

				ConsultarProduto produto = new ConsultarProduto(login);
				produto.setVisible(true);
				setVisible(false);
			} else {
				JOptionPane.showMessageDialog(null, "Selecione o produto a ser excluido", "ATENÇÃO",
						JOptionPane.INFORMATION_MESSAGE);
			}

		} catch (Exception e) {
			System.out.println("Erro: " + e);
		}
	}

	private void PeencherAlterar() {
		if (tableProduto.getSelectedRow() != -1) {
			String id = (String) tableProduto.getModel().getValueAt(tableProduto.getSelectedRow(), 0);
			String nome = (String) tableProduto.getModel().getValueAt(tableProduto.getSelectedRow(), 2);
			String codigo = (String) tableProduto.getModel().getValueAt(tableProduto.getSelectedRow(), 3);
			String valorCusto = (String) tableProduto.getModel().getValueAt(tableProduto.getSelectedRow(), 4);
			String valorVenda = (String) tableProduto.getModel().getValueAt(tableProduto.getSelectedRow(), 5);
			String quantidade = (String) tableProduto.getModel().getValueAt(tableProduto.getSelectedRow(), 6);

			boolean atualizar = true;

			CadastrarProduto cadastro = new CadastrarProduto(nome, codigo, valorCusto, valorVenda, quantidade,
					atualizar, id);
			cadastro.setVisible(true);
			setVisible(false);
		} else {
			JOptionPane.showMessageDialog(null, "Selecione o produto a ser alterado", "ATENÇÃO",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
