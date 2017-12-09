package fornecedor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import Janelas.Inicial;
import Janelas.Login;
import cliente.CadastrarCliente;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;

public class ConsultaFornecedor extends JFrame {
	JPanel contentPane;
	private JTextField textField;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton btnRemover, btnAlterar, btnVoltar, btnCadastrar;
	private DefaultTableModel model;
	String nome, cnpj, telefone, email, endereco, representante, tel_representante;
	boolean atualizar;
	private Login login;

	public ConsultaFornecedor(Login login1) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 675, 385);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		login = login1;
		contentPane.setLayout(null);

		criaComponentes();
		criaAcoes();
		mostrarBanco();
	}

	public void criaComponentes() {
		JLabel lblEditarFornecedor = new JLabel("EDITAR FORNECEDOR");
		lblEditarFornecedor.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEditarFornecedor.setBounds(210, 11, 158, 14);
		contentPane.add(lblEditarFornecedor);

		JLabel lblFornecedor = new JLabel("Fornecedor");
		lblFornecedor.setBounds(10, 43, 78, 14);
		contentPane.add(lblFornecedor);

		textField = new JTextField();
		textField.setBounds(78, 40, 259, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 68, 649, 243);
		scrollPane.getViewport().setBackground(Color.WHITE);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setBackground(Color.WHITE);
		scrollPane.setViewportView(table);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Nome", "CNPJ", "Telefone",
				"Email", "Endere\u00E7o", "Representante", "Tel. Representante" }) {
			boolean[] canEdit = new boolean[] { false, false, false, false, false, false, false, false };

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});

		table.getColumnModel().getColumn(0).setPreferredWidth(45);
		table.getColumnModel().getColumn(1).setPreferredWidth(250);
		table.getColumnModel().getColumn(2).setPreferredWidth(150);
		table.getColumnModel().getColumn(3).setPreferredWidth(150);
		table.getColumnModel().getColumn(4).setPreferredWidth(200);
		table.getColumnModel().getColumn(5).setPreferredWidth(250);
		table.getColumnModel().getColumn(6).setPreferredWidth(250);
		table.getColumnModel().getColumn(7).setPreferredWidth(150);
		table.setColumnSelectionAllowed(false);

		btnRemover = new JButton("REMOVER | F12 |");
		btnRemover.setBackground(Color.WHITE);
		btnRemover.setBounds(490, 322, 150, 23);
		contentPane.add(btnRemover);

		btnAlterar = new JButton("ALTERAR | F3 |");
		btnAlterar.setBackground(Color.WHITE);
		btnAlterar.setBounds(330, 322, 150, 23);
		contentPane.add(btnAlterar);

		btnVoltar = new JButton("VOLTAR | ESC |");
		btnVoltar.setBackground(Color.WHITE);
		btnVoltar.setBounds(10, 322, 150, 23);
		contentPane.add(btnVoltar);

		btnCadastrar = new JButton("Cadastrar | F1 |");
		btnCadastrar.setBackground(Color.WHITE);
		btnCadastrar.setBounds(170, 322, 150, 23);
		contentPane.add(btnCadastrar);
	}

	public void criaAcoes() {

		btnCadastrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CadastroFornecedor cadastro = new CadastroFornecedor(login);
				cadastro.setVisible(true);
				setVisible(false);

			}
		});

		btnCadastrar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0),
				"evento");
		btnCadastrar.getActionMap().put("evento", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CadastroFornecedor cadastro = new CadastroFornecedor(login);
				cadastro.setVisible(true);
				setVisible(false);
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

		btnVoltar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Inicial inicio = new Inicial(login);
				inicio.setVisible(true);
				setVisible(false);

			}
		});

		btnRemover.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				excluirBanco();
			}
		});
		
		btnRemover.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0),
				"evento");
		btnRemover.getActionMap().put("evento", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				excluirBanco();
			}
		});

		btnAlterar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				alterarFornecedor();
			}
		});
		
		btnAlterar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0),
				"evento");
		btnAlterar.getActionMap().put("evento", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				alterarFornecedor();
			}
		});

		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(java.awt.event.KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					if (textField.getText().equals("")) {
						while (model.getRowCount() > 0) {
							model.removeRow(0);
						}
						mostrarBanco();
					} else {
						pesquisarBanco();
					}

				}
			}
		});
	}

	public void pesquisarBanco() {
		try {

			while (model.getRowCount() > 0) {
				model.removeRow(0);
			}

			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/supermercado", "root", "");

			String query = "SELECT * FROM fornecedores WHERE nome = ?";

			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, textField.getText());

			// Pega o resultado la no banco
			ResultSet rs = stmt.executeQuery();

			model = (DefaultTableModel) table.getModel();
			String[] linha;

			while (rs.next()) {
				// System.out.println("Nome: " + rs.getString("nome"));
				linha = new String[8];
				linha[0] = rs.getString("id");
				linha[1] = rs.getString("nome");
				linha[2] = rs.getString("cnpj");
				linha[3] = rs.getString("telefone");
				linha[4] = rs.getString("email");
				linha[5] = rs.getString("endereco");
				linha[6] = rs.getString("representante");
				linha[7] = rs.getString("telefone_representante");
				model.addRow(linha);
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

			String query = "SELECT * FROM fornecedores";

			PreparedStatement stmt = con.prepareStatement(query);

			// Pega o resultado la no banco
			ResultSet rs = stmt.executeQuery();

			model = (DefaultTableModel) table.getModel();
			String[] linha;

			while (rs.next()) {
				// System.out.println("Nome: " + rs.getString("nome"));
				linha = new String[8];
				linha[0] = rs.getString("id");
				linha[1] = rs.getString("nome");
				linha[2] = rs.getString("cnpj");
				linha[3] = rs.getString("telefone");
				linha[4] = rs.getString("email");
				linha[5] = rs.getString("endereco");
				linha[6] = rs.getString("representante");
				linha[7] = rs.getString("telefone_representante");
				model.addRow(linha);
			}

			con.close();
			rs.close();

		} catch (Exception e) {
			System.out.println("Erro: " + e);
		}
	}
	
	public void alterarFornecedor() {
		if(table.getSelectedRow() != -1) {
			String id = (String) table.getModel().getValueAt(table.getSelectedRow(), 0);
			String nome = (String) table.getModel().getValueAt(table.getSelectedRow(), 1);
			String cnpj = (String) table.getModel().getValueAt(table.getSelectedRow(), 2);
			String telefone = (String) table.getModel().getValueAt(table.getSelectedRow(), 3);
			String email = (String) table.getModel().getValueAt(table.getSelectedRow(), 4);
			String endereco = (String) table.getModel().getValueAt(table.getSelectedRow(), 5);
			String representante = (String) table.getModel().getValueAt(table.getSelectedRow(), 6);
			String telefone_representante = (String) table.getModel().getValueAt(table.getSelectedRow(), 7);
			boolean atualizar = true;

			CadastroFornecedor cadastro = new CadastroFornecedor(id, nome, cnpj, telefone, email, endereco,
					representante, telefone_representante, atualizar);
			cadastro.setVisible(true);
			setVisible(false);
		}else {
			JOptionPane.showMessageDialog(null, "Selecione o fornecedor a ser alterado");
		}
	}

	public void excluirBanco() {
		if(table.getSelectedRow() != -1) {
			try {
				Class.forName("com.mysql.jdbc.Driver");

				Connection con = DriverManager.getConnection("jdbc:mysql://localhost/supermercado", "root", "");

				String query = "DELETE FROM fornecedores WHERE id = ?";

				PreparedStatement stmt = con.prepareStatement(query);

				String index = (String) table.getModel().getValueAt(table.getSelectedRow(), 0);
				stmt.setString(1, index);

				stmt.executeUpdate();

				model.removeRow(table.getSelectedRow());

			} catch (Exception e) {
				System.out.println("Erro: " + e);
			}
		}else {
			JOptionPane.showMessageDialog(null, "Selecione um fornecedor para excluir");
		}
	}

}
