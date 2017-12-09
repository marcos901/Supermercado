package fornecedor;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PersistenceDelegate;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Janelas.Inicial;
import Janelas.Login;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import java.awt.Color;

public class CadastroFornecedor extends JFrame {

	private JPanel contentPane;
	private JTextField txtNome;
	private JTextField txtCNPJ;
	private JTextField txtTelefone;
	private JTextField txtEmail;
	private JTextField txtEndereco;
	private JTextField txtRepresentante;
	private JButton btnCadastrar, btnVoltar;
	private JLabel lblTelRepresentante;
	private JTextField txtTelRep;
	private String nome, cnpj, telefone, email, endereco, representante, tel_representante, id;
	private boolean atualizar = false;
	private Login login;

	public CadastroFornecedor(Login login1) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 370);
		setResizable(false);
		setTitle("Cadastro de fornecedor");
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		criaComponentes();
		criaAcoes();

	}

	public CadastroFornecedor(String pid, String pnome, String pcnpj, String ptel, String pemail, String pend,
			String prepre, String ptelrepre, boolean patualizar) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 370);
		setResizable(false);
		setTitle("Cadastro de fornecedor");
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		criaComponentes();
		criaAcoes();

		this.txtNome.setText(pnome);
		this.txtCNPJ.setText(pcnpj);
		this.txtTelefone.setText(ptel);
		this.txtEmail.setText(pemail);
		this.txtEndereco.setText(pend);
		this.txtRepresentante.setText(prepre);
		this.txtTelRep.setText(ptelrepre);
		this.id = pid;
		this.atualizar = patualizar;

	}

	public void criaComponentes() {
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(12, 12, 50, 15);
		contentPane.add(lblNome);

		JLabel lblCnpj = new JLabel("CNPJ:");
		lblCnpj.setBounds(12, 61, 50, 15);
		contentPane.add(lblCnpj);

		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setBounds(248, 61, 70, 15);
		contentPane.add(lblTelefone);

		JLabel lblEndereo = new JLabel("Endere\u00E7o:");
		lblEndereo.setBounds(12, 161, 70, 15);
		contentPane.add(lblEndereo);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(12, 111, 50, 15);
		contentPane.add(lblEmail);

		JLabel lblRepresentante = new JLabel("Representante:");
		lblRepresentante.setBounds(12, 211, 107, 15);
		contentPane.add(lblRepresentante);

		txtNome = new JTextField();
		txtNome.setBounds(12, 30, 426, 19);
		contentPane.add(txtNome);
		txtNome.setColumns(10);

		txtCNPJ = new JTextField();
		txtCNPJ.setBounds(12, 80, 183, 19);
		contentPane.add(txtCNPJ);
		txtCNPJ.setColumns(10);

		txtTelefone = new JTextField();
		txtTelefone.setBounds(248, 80, 190, 19);
		contentPane.add(txtTelefone);
		txtTelefone.setColumns(10);

		txtEmail = new JTextField();
		txtEmail.setBounds(12, 130, 426, 19);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);

		txtEndereco = new JTextField();
		txtEndereco.setBounds(12, 180, 426, 19);
		contentPane.add(txtEndereco);
		txtEndereco.setColumns(10);

		txtRepresentante = new JTextField();
		txtRepresentante.setBounds(12, 230, 426, 19);
		contentPane.add(txtRepresentante);
		txtRepresentante.setColumns(10);

		btnCadastrar = new JButton("Cadastrar | F1 |");
		btnCadastrar.setBackground(Color.WHITE);
		btnCadastrar.setBounds(280, 305, 150, 25);
		contentPane.add(btnCadastrar);

		lblTelRepresentante = new JLabel("Tel. Representante: ");
		lblTelRepresentante.setBounds(12, 261, 137, 15);
		contentPane.add(lblTelRepresentante);

		txtTelRep = new JTextField();
		txtTelRep.setBounds(12, 280, 183, 19);
		contentPane.add(txtTelRep);
		txtTelRep.setColumns(10);

		btnVoltar = new JButton("VOLTAR | ESC |");
		btnVoltar.setBackground(Color.WHITE);
		btnVoltar.setBounds(100, 305, 150, 25);
		contentPane.add(btnVoltar);
	}

	public void criaAcoes() {
		btnCadastrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Cadastrar();
				ConsultaFornecedor cons = new ConsultaFornecedor(login);
				cons.setVisible(true);
				setVisible(false);
			}
		});

		btnVoltar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ConsultaFornecedor cons = new ConsultaFornecedor(login);
				cons.setVisible(true);
				setVisible(false);
			}
		});

		// Atalhos

		btnCadastrar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0),
				"evento");
		btnCadastrar.getActionMap().put("evento", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Cadastrar();
				ConsultaFornecedor cons = new ConsultaFornecedor(login);
				cons.setVisible(true);
				setVisible(false);
			}
		});

		btnVoltar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				"evento");
		btnVoltar.getActionMap().put("evento", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ConsultaFornecedor cons = new ConsultaFornecedor(login);
				cons.setVisible(true);
				dispose();
			}
		});
	}

	private void Cadastrar() {
		if (txtNome.getText().equals("") || txtCNPJ.getText().equals("") || txtTelefone.getText().equals("")
				|| txtEndereco.getText().equals("") || txtRepresentante.getText().equals("")
				|| txtTelRep.getText().equals("")) {

			JOptionPane.showMessageDialog(null, "Preencha todos os campos para continuar!");

		} else {
			nome = txtNome.getText().trim();
			cnpj = txtCNPJ.getText().trim();
			telefone = txtTelefone.getText().trim();
			email = txtEmail.getText().trim();
			endereco = txtEndereco.getText().trim();
			representante = txtRepresentante.getText().trim();
			tel_representante = txtTelRep.getText().trim();

			conectarBanco();
			limpar();
			JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");
		}
	}

	private void conectarBanco() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/supermercado", "root", "");

			if (atualizar) {
				String query = "UPDATE fornecedores SET nome = ?, cnpj = ?, telefone = ?, email = ?, endereco = ?, representante = ?, telefone_representante = ? WHERE id = ?";

				PreparedStatement stmt = con.prepareStatement(query);
				stmt.setString(1, nome);
				stmt.setString(2, cnpj);
				stmt.setString(3, telefone);
				stmt.setString(4, email);
				stmt.setString(5, endereco);
				stmt.setString(6, representante);
				stmt.setString(7, tel_representante);
				stmt.setString(8, id);

				// Executa o comando no banco de dados
				stmt.executeUpdate();

				// Fecha comando e conexão
				stmt.close();
				con.close();
			} else {

				// Cria a string de inserção no banco
				String query = "INSERT INTO fornecedores (nome, cnpj, telefone, email, endereco, representante, telefone_representante) VALUES(?,?,?,?,?,?,?)";

				// Cria o comando
				// PreparedStatement tem q ser a do .sql
				PreparedStatement stmt = con.prepareStatement(query);

				// Seta os valores na string de inserção
				stmt.setString(1, nome);
				stmt.setString(2, cnpj);
				stmt.setString(3, telefone);
				stmt.setString(4, email);
				stmt.setString(5, endereco);
				stmt.setString(6, representante);
				stmt.setString(7, tel_representante);

				// Executa o comando no banco de dados
				stmt.executeUpdate();

				// Fecha comando e conexão
				stmt.close();
				con.close();
			}

			atualizar = false;
		} catch (Exception e) {

			System.out.println("Erro: " + e);

			e.printStackTrace();
		}

	}

	public void limpar() {
		txtNome.setText("");
		txtCNPJ.setText("");
		txtTelefone.setText("");
		txtEmail.setText("");
		txtRepresentante.setText("");
		txtTelRep.setText("");
		txtEndereco.setText("");
	}

}
