package funcionario;

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

import javax.swing.JScrollPane;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;

public class ConsultaFuncionario extends JFrame {

	private JPanel contentPane;
	private JTextField txtNome;
	private JTable tableFuncionario;
	private JScrollPane scrollPane;
	private JButton btnExcluir, btnAlterar, btnVoltar, btnCadastrar;
	private DefaultTableModel modelo;
	private Login login;

	public ConsultaFuncionario(Login login1) {
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
		JLabel lblConsultaCliente = new JLabel("CONSULTA FUNCIONARIO");
		lblConsultaCliente.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblConsultaCliente.setBounds(196, 11, 253, 22);
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

		tableFuncionario = new JTable();
		tableFuncionario.setBackground(Color.WHITE);
		scrollPane.setViewportView(tableFuncionario);
		tableFuncionario.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableFuncionario.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Nome", "Telefone",
				"CPF", "CEP", "EndereÃ§o", "NÃºmero", "Bairro", "FunÃ§Ã£o", "CTPS", "CNH", "Usuario", "Senha" }) {
			boolean[] canEdit = new boolean[] { false, false, false, false, false, false, false, false, false, false,
					false, false, false };

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		tableFuncionario.setColumnSelectionAllowed(false);

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

		btnExcluir = new JButton("EXLCUIR | F12 |");
		btnExcluir.setBackground(Color.WHITE);
		btnExcluir.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnExcluir.setBounds(500, 307, 150, 23);
		contentPane.add(btnExcluir);

		contentPane.add(btnAlterar);
		tableFuncionario.getColumnModel().getColumn(0).setPreferredWidth(45);
		tableFuncionario.getColumnModel().getColumn(1).setPreferredWidth(234);
		tableFuncionario.getColumnModel().getColumn(2).setPreferredWidth(86);
		tableFuncionario.getColumnModel().getColumn(3).setPreferredWidth(86);
		tableFuncionario.getColumnModel().getColumn(4).setPreferredWidth(86);
		tableFuncionario.getColumnModel().getColumn(5).setPreferredWidth(234);
		tableFuncionario.getColumnModel().getColumn(6).setPreferredWidth(60);
		tableFuncionario.getColumnModel().getColumn(7).setPreferredWidth(190);
		tableFuncionario.getColumnModel().getColumn(8).setPreferredWidth(100);
		tableFuncionario.getColumnModel().getColumn(9).setPreferredWidth(100);
		tableFuncionario.getColumnModel().getColumn(10).setPreferredWidth(100);
		tableFuncionario.getColumnModel().getColumn(11).setPreferredWidth(100);
		tableFuncionario.getColumnModel().getColumn(12).setPreferredWidth(100);
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
				CadastroFuncionario cadastro = new CadastroFuncionario(login);
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
				CadastroFuncionario cadastro = new CadastroFuncionario(login);
				cadastro.setVisible(true);
				setVisible(false);
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
		
		btnAlterar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0),
				"evento");
		btnAlterar.getActionMap().put("evento", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				alterarFuncionario();
			}
		});

		btnAlterar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				alterarFuncionario();

			}
		});

		btnExcluir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				excluirBanco();

			}
		});
		

		btnExcluir.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0),
				"evento");

		btnExcluir.getActionMap().put("evento", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				excluirBanco();
			}
		});
	}

	public void mostrarBanco() {
		try {

			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/supermercado", "root", "");

			String query = "SELECT * FROM funcionarios";

			PreparedStatement stmt = con.prepareStatement(query);

			// Pega o resultado la no banco
			ResultSet rs = stmt.executeQuery();

			modelo = (DefaultTableModel) tableFuncionario.getModel();
			String[] linha;

			while (rs.next()) {
				// System.out.println("Nome: " + rs.getString("nome"));
				linha = new String[13];
				linha[0] = rs.getString("id");
				linha[1] = rs.getString("nome");
				linha[2] = rs.getString("telefone");
				linha[3] = rs.getString("cpf");
				linha[4] = rs.getString("cep");
				linha[5] = rs.getString("endereco");
				linha[6] = rs.getString("numero");
				linha[7] = rs.getString("bairro");
				linha[8] = rs.getString("funcao");
				linha[9] = rs.getString("ctps");
				linha[10] = rs.getString("cnh");
				linha[11] = rs.getString("usuario");
				linha[12] = rs.getString("senha");
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
			if(tableFuncionario.getSelectedRow() != -1) {
				Class.forName("com.mysql.jdbc.Driver");

				Connection con = DriverManager.getConnection("jdbc:mysql://localhost/supermercado", "root", "");

				String query = "DELETE FROM funcionarios WHERE id = ?";

				PreparedStatement stmt = con.prepareStatement(query);

				String index = (String) tableFuncionario.getModel().getValueAt(tableFuncionario.getSelectedRow(), 0);
				stmt.setString(1, index);

				stmt.executeUpdate();

				modelo.removeRow(tableFuncionario.getSelectedRow());
			}else {
				JOptionPane.showMessageDialog(null, "Selecione o funcionario a ser excluido", "ATENÇÃO",
						JOptionPane.INFORMATION_MESSAGE);
			}
			

		} catch (Exception e) {
			System.out.println("Erro: " + e);
		}
	}

	public void pesquisarBanco() {
		try {

			while (modelo.getRowCount() > 0) {
				modelo.removeRow(0);
			}

			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/supermercado", "root", "");

			String query = "SELECT * FROM funcionarios WHERE nome = ?";

			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, txtNome.getText());

			// Pega o resultado la no banco
			ResultSet rs = stmt.executeQuery();

			modelo = (DefaultTableModel) tableFuncionario.getModel();
			String[] linha;

			while (rs.next()) {
				// System.out.println("Nome: " + rs.getString("nome"));
				linha = new String[13];
				linha[0] = rs.getString("id");
				linha[1] = rs.getString("nome");
				linha[2] = rs.getString("telefone");
				linha[3] = rs.getString("cpf");
				linha[4] = rs.getString("cep");
				linha[5] = rs.getString("endereco");
				linha[6] = rs.getString("numero");
				linha[7] = rs.getString("bairro");
				linha[8] = rs.getString("funcao");
				linha[9] = rs.getString("ctps");
				linha[10] = rs.getString("cnh");
				linha[11] = rs.getString("usuario");
				linha[12] = rs.getString("senha");
				modelo.addRow(linha);
			}

			con.close();
			rs.close();

		} catch (Exception e) {
			System.out.println("Erro: " + e);
		}
	}

	private void alterarFuncionario() {
		if (tableFuncionario.getSelectedRow() != -1) {
			int id = Integer.parseInt(
					String.valueOf(tableFuncionario.getModel().getValueAt(tableFuncionario.getSelectedRow(), 0)));
			String nome = (String) tableFuncionario.getModel().getValueAt(tableFuncionario.getSelectedRow(), 1);
			String telefone = (String) tableFuncionario.getModel().getValueAt(tableFuncionario.getSelectedRow(), 2);
			String cpf = (String) tableFuncionario.getModel().getValueAt(tableFuncionario.getSelectedRow(), 3);
			String cep = (String) tableFuncionario.getModel().getValueAt(tableFuncionario.getSelectedRow(), 4);
			String endereco = (String) tableFuncionario.getModel().getValueAt(tableFuncionario.getSelectedRow(), 5);
			String num = (String) tableFuncionario.getModel().getValueAt(tableFuncionario.getSelectedRow(), 6);
			String bairro = (String) tableFuncionario.getModel().getValueAt(tableFuncionario.getSelectedRow(), 7);
			String funcao = (String) tableFuncionario.getModel().getValueAt(tableFuncionario.getSelectedRow(), 8);
			String ctps = (String) tableFuncionario.getModel().getValueAt(tableFuncionario.getSelectedRow(), 9);
			String cnh = (String) tableFuncionario.getModel().getValueAt(tableFuncionario.getSelectedRow(), 10);
			String usuario = (String) tableFuncionario.getModel().getValueAt(tableFuncionario.getSelectedRow(), 11);
			String senha = (String) tableFuncionario.getModel().getValueAt(tableFuncionario.getSelectedRow(), 12);
			boolean atualizar = true;
			CadastroFuncionario funcionario = new CadastroFuncionario(id, nome, telefone, cpf, cep, endereco, num,
					bairro, funcao, ctps, cnh, usuario, senha, atualizar);
			funcionario.setVisible(true);
			setVisible(false);
		} else {
			JOptionPane.showMessageDialog(null, "Selecione o funcionario a ser alterado", "ATENÇÃO",
					JOptionPane.INFORMATION_MESSAGE);
		}

	}
}
