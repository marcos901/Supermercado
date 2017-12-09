package cliente;

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
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.RowFilter;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import com.itextpdf.text.log.SysoCounter;

import Janelas.Inicial;
import Janelas.Login;

import javax.swing.JScrollPane;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;

public class ConsultaCliente extends JFrame {

	private JPanel contentPane;
	private JTextField txtNome;
	private JTable tableCliente;
	private JScrollPane scrollPane;
	private JButton btnExlcuir, btnAlterar, btnVoltar, btnCadastrar;
	private TableRowSorter trs;
	private DefaultTableModel modelo;
	private Login login;

	public ConsultaCliente(Login login1) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 675, 376);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		login = login1;
		Componentes();
		CriaAcoes();
		this.mostrarBanco();

	}

	public void Componentes() {
		JLabel lblConsultaCliente = new JLabel("CONSULTA CLIENTE");
		lblConsultaCliente.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblConsultaCliente.setBounds(213, 11, 175, 22);
		contentPane.add(lblConsultaCliente);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNome.setBounds(10, 52, 46, 14);
		contentPane.add(lblNome);

		txtNome = new JTextField();
		txtNome.setBounds(53, 51, 286, 20);
		contentPane.add(txtNome);
		txtNome.setColumns(10);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 86, 640, 210);
		scrollPane.getViewport().setBackground(Color.WHITE);
		contentPane.add(scrollPane);

		tableCliente = new JTable();
		tableCliente.setBackground(Color.WHITE);
		scrollPane.setViewportView(tableCliente);
		tableCliente.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableCliente.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Nome", "Endere\u00E7o",
				"N\u00BA", "Bairro", "Ponto de Refer\u00EAncia ", "Telefone", "CEP", "CPF" }) {
			boolean[] canEdit = new boolean[] { false, false, false, false, false, false, false, false, false };

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});

		tableCliente.getColumnModel().getColumn(0).setPreferredWidth(50);
		tableCliente.getColumnModel().getColumn(1).setPreferredWidth(234);
		tableCliente.getColumnModel().getColumn(2).setPreferredWidth(185);
		tableCliente.getColumnModel().getColumn(3).setPreferredWidth(45);
		tableCliente.getColumnModel().getColumn(4).setPreferredWidth(150);
		tableCliente.getColumnModel().getColumn(5).setPreferredWidth(200);
		tableCliente.getColumnModel().getColumn(6).setPreferredWidth(100);
		tableCliente.getColumnModel().getColumn(7).setPreferredWidth(100);
		tableCliente.getColumnModel().getColumn(8).setPreferredWidth(100);
		tableCliente.setColumnSelectionAllowed(false);

		btnVoltar = new JButton("Voltar | ESC |");
		btnVoltar.setBackground(Color.WHITE);
		btnVoltar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnVoltar.setBounds(10, 307, 150, 23);
		contentPane.add(btnVoltar);

		btnCadastrar = new JButton("Cadastrar | F1 |");
		btnCadastrar.setBackground(Color.WHITE);
		btnCadastrar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnCadastrar.setBounds(170, 307, 150, 23);
		contentPane.add(btnCadastrar);

		btnAlterar = new JButton("ALTERAR | F3 |");
		btnAlterar.setBackground(Color.WHITE);
		btnAlterar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnAlterar.setBounds(330, 307, 150, 23);
		contentPane.add(btnAlterar);

		btnExlcuir = new JButton("EXLCUIR | F12 |");
		btnExlcuir.setBackground(Color.WHITE);
		btnExlcuir.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnExlcuir.setBounds(500, 307, 150, 23);
		contentPane.add(btnExlcuir);

		contentPane.add(btnAlterar);
	}

	public void CriaAcoes() {
		btnVoltar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Inicial inicio = new Inicial(login);
				inicio.setVisible(true);
				setVisible(false);
			}
		});

		btnCadastrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CadastrarCliente cadastro = new CadastrarCliente(login);
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

		btnCadastrar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0),
				"evento");
		btnCadastrar.getActionMap().put("evento", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CadastrarCliente cadastro = new CadastrarCliente(login);
				cadastro.setVisible(true);
				setVisible(false);
			}
		});

		btnExlcuir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				excluirBanco();
			}
		});

		btnExlcuir.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0),
				"evento");
		btnExlcuir.getActionMap().put("evento", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				excluirBanco();
			}
		});

		btnAlterar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				alterarCliente();
			}
		});

		btnAlterar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0),
				"evento");
		btnAlterar.getActionMap().put("evento", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				alterarCliente();
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

	}

	public void pesquisarBanco() {
		try {

			while (modelo.getRowCount() > 0) {
				modelo.removeRow(0);
			}

			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/supermercado", "root", "");

			String query = "SELECT * FROM clientes WHERE nome = ?";

			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, txtNome.getText());

			// Pega o resultado la no banco
			ResultSet rs = stmt.executeQuery();

			modelo = (DefaultTableModel) tableCliente.getModel();
			String[] linha;

			while (rs.next()) {
				// System.out.println("Nome: " + rs.getString("nome"));
				linha = new String[9];
				linha[0] = rs.getString("id");
				linha[1] = rs.getString("nome");
				linha[2] = rs.getString("endereco");
				linha[3] = rs.getString("numero");
				linha[4] = rs.getString("bairro");
				linha[5] = rs.getString("ponto_referencia");
				linha[6] = rs.getString("telefone");
				linha[7] = rs.getString("cep");
				linha[8] = rs.getString("cpf");
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

			String query = "SELECT * FROM clientes";

			PreparedStatement stmt = con.prepareStatement(query);

			// Pega o resultado la no banco
			ResultSet rs = stmt.executeQuery();

			modelo = (DefaultTableModel) tableCliente.getModel();
			String[] linha;

			while (rs.next()) {
				// System.out.println("Nome: " + rs.getString("nome"));
				linha = new String[9];
				linha[0] = rs.getString("id");
				linha[1] = rs.getString("nome");
				linha[2] = rs.getString("endereco");
				linha[3] = rs.getString("numero");
				linha[4] = rs.getString("bairro");
				linha[5] = rs.getString("ponto_referencia");
				linha[6] = rs.getString("telefone");
				linha[7] = rs.getString("cep");
				linha[8] = rs.getString("cpf");
				modelo.addRow(linha);
			}

			con.close();
			rs.close();

		} catch (Exception e) {
			System.out.println("Erro: " + e);
		}
	}

	public void alterarCliente() {
		if (tableCliente.getSelectedRow() != -1) {
			String id = (String) tableCliente.getModel().getValueAt(tableCliente.getSelectedRow(), 0);
			String nome = (String) tableCliente.getModel().getValueAt(tableCliente.getSelectedRow(), 1);
			String endereco = (String) tableCliente.getModel().getValueAt(tableCliente.getSelectedRow(), 2);
			String numero = (String) tableCliente.getModel().getValueAt(tableCliente.getSelectedRow(), 3);
			String bairro = (String) tableCliente.getModel().getValueAt(tableCliente.getSelectedRow(), 4);
			String ponto = (String) tableCliente.getModel().getValueAt(tableCliente.getSelectedRow(), 5);
			String telefone = (String) tableCliente.getModel().getValueAt(tableCliente.getSelectedRow(), 6);
			String cep = (String) tableCliente.getModel().getValueAt(tableCliente.getSelectedRow(), 7);
			String cpf = (String) tableCliente.getModel().getValueAt(tableCliente.getSelectedRow(), 8);
			boolean atualizar = true;

			CadastrarCliente cadastro = new CadastrarCliente(nome, telefone, cpf, cep, endereco, numero, bairro, ponto,
					id, atualizar);
			cadastro.setVisible(true);
			setVisible(false);
		} else {
			JOptionPane.showMessageDialog(null, "Selecione o cliente a ser alterado ");
		}
	}

	public void excluirBanco() {
		try {
			if (tableCliente.getSelectedRow() != -1) {
				Class.forName("com.mysql.jdbc.Driver");

				Connection con = DriverManager.getConnection("jdbc:mysql://localhost/supermercado", "root", "");

				String query = "DELETE FROM clientes WHERE id = ?";

				PreparedStatement stmt = con.prepareStatement(query);

				String index = (String) tableCliente.getModel().getValueAt(tableCliente.getSelectedRow(), 0);
				stmt.setString(1, index);

				stmt.executeUpdate();

				modelo.removeRow(tableCliente.getSelectedRow());
			} else {
				JOptionPane.showMessageDialog(null, "Selecione o cliente para excluir");
			}

		} catch (Exception e) {
			System.out.println("Erro: " + e);
		}
	}

}
