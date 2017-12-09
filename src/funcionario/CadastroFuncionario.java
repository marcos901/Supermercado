package funcionario;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Janelas.Login;
import cliente.WebServiceCep;
import cliente.ConsultaCliente;

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
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPasswordField;

public class CadastroFuncionario extends JFrame {

	private JPanel ctFuncao;
	private JTextField txtNome;
	private JTextField txtEndereco;
	private JTextField txtNum;
	private JTextField txtBairro;
	private JTextField txtCtps;
	private JTextField txtCnh;
	private JTextField txtTelefone;
	private JTextField txtCep;
	private JTextField txtCpf;
	private JComboBox cbFuncao;
	private JButton btnCadastrar, btnVoltar;
	private String nome, telefone, cpf, cep, endereco, num, bairro, ctps, cnh, usuario, senha, funcao;
	private int id;
	private JLabel lblUsuario;
	private JTextField textUsuario;
	private JLabel lblSenha;
	private JPasswordField textSenha;
	private boolean atualizar = false;
	private Login login;

	public CadastroFuncionario(Login login1) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 370, 403);
		ctFuncao = new JPanel();
		ctFuncao.setBackground(Color.WHITE);
		ctFuncao.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(ctFuncao);
		ctFuncao.setLayout(null);
		Componetes();
		CriaAcoes();

	}

	public CadastroFuncionario(int pid, String pnome, String ptelefone, String pcpf, String pcep, String pendereco,
			String pnumero, String pbairro, String pfuncao, String pctps, String pcnh, String pusuario, String psenha,
			boolean patualizar) {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 370, 403);
		ctFuncao = new JPanel();
		ctFuncao.setBackground(Color.WHITE);
		ctFuncao.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(ctFuncao);
		ctFuncao.setLayout(null);
		Componetes();
		CriaAcoes();

		this.txtNome.setText(pnome);
		this.txtTelefone.setText(ptelefone);
		this.txtCpf.setText(pcpf);
		this.txtCep.setText(pcep);
		this.txtEndereco.setText(pendereco);
		this.txtNum.setText(pnumero);
		this.txtBairro.setText(pbairro);
		this.cbFuncao.setSelectedItem(pfuncao);
		this.txtCtps.setText(pctps);
		this.txtCnh.setText(pcnh);
		this.textUsuario.setText(pusuario);
		this.textSenha.setText(psenha);
		this.id = pid;
		this.atualizar = patualizar;
	}

	public void Componetes() {
		JLabel lblCadastrarFuncionrio = new JLabel("Cadastrar Funcion\u00E1rio");
		lblCadastrarFuncionrio.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCadastrarFuncionrio.setBounds(95, 11, 150, 14);
		ctFuncao.add(lblCadastrarFuncionrio);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNome.setBounds(10, 39, 46, 14);
		ctFuncao.add(lblNome);

		txtNome = new JTextField();
		txtNome.setBounds(10, 57, 332, 20);
		ctFuncao.add(txtNome);
		txtNome.setColumns(10);

		JLabel lblEndereo = new JLabel("Endere\u00E7o:");
		lblEndereo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblEndereo.setBounds(10, 133, 64, 14);
		ctFuncao.add(lblEndereo);

		txtEndereco = new JTextField();
		txtEndereco.setBounds(10, 151, 260, 20);
		ctFuncao.add(txtEndereco);
		txtEndereco.setColumns(10);

		JLabel lblN = new JLabel("N\u00BA:");
		lblN.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblN.setBounds(280, 133, 29, 14);
		ctFuncao.add(lblN);

		txtNum = new JTextField();
		txtNum.setBounds(280, 151, 62, 20);
		ctFuncao.add(txtNum);
		txtNum.setColumns(10);

		JLabel lblBairro = new JLabel("Bairro:");
		lblBairro.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblBairro.setBounds(10, 182, 46, 14);
		ctFuncao.add(lblBairro);

		txtBairro = new JTextField();
		txtBairro.setBounds(10, 199, 127, 20);
		ctFuncao.add(txtBairro);
		txtBairro.setColumns(10);

		JLabel lblCtps = new JLabel("CTPS:");
		lblCtps.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCtps.setBounds(10, 230, 46, 14);
		ctFuncao.add(lblCtps);

		txtCtps = new JTextField();
		txtCtps.setBounds(10, 246, 107, 20);
		ctFuncao.add(txtCtps);
		txtCtps.setColumns(10);

		JLabel lblCnh = new JLabel("CNH:");
		lblCnh.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCnh.setBounds(128, 230, 46, 14);
		ctFuncao.add(lblCnh);

		txtCnh = new JTextField();
		txtCnh.setBounds(128, 246, 117, 20);
		ctFuncao.add(txtCnh);
		txtCnh.setColumns(10);

		JLabel lblFuno = new JLabel("Fun\u00E7\u00E3o:");
		lblFuno.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFuno.setBounds(147, 182, 46, 20);
		ctFuncao.add(lblFuno);

		cbFuncao = new JComboBox();
		cbFuncao.setBackground(new Color(255, 255, 255));
		cbFuncao.setBounds(147, 199, 195, 20);
		cbFuncao.addItem("Gerente");
		cbFuncao.addItem("Funcionario");
		cbFuncao.addItem("Entregador");
		ctFuncao.add(cbFuncao);

		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTelefone.setBounds(10, 88, 64, 14);
		ctFuncao.add(lblTelefone);

		txtTelefone = new JTextField();
		txtTelefone.setBounds(10, 105, 107, 20);
		ctFuncao.add(txtTelefone);
		txtTelefone.setColumns(10);

		JLabel lblCep = new JLabel("CEP:");
		lblCep.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCep.setBounds(244, 88, 46, 14);
		ctFuncao.add(lblCep);

		txtCep = new JTextField();
		txtCep.setBounds(246, 105, 96, 20);
		ctFuncao.add(txtCep);
		txtCep.setColumns(10);

		JLabel lblCpf = new JLabel("CPF:");
		lblCpf.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCpf.setBounds(128, 88, 96, 14);
		ctFuncao.add(lblCpf);

		txtCpf = new JTextField();
		txtCpf.setBounds(128, 105, 96, 20);
		ctFuncao.add(txtCpf);
		txtCpf.setColumns(10);

		btnCadastrar = new JButton("CADASTRAR | F1 |");
		btnCadastrar.setBackground(Color.WHITE);
		btnCadastrar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnCadastrar.setBounds(184, 327, 158, 23);
		ctFuncao.add(btnCadastrar);

		btnVoltar = new JButton("VOLTAR | ESC |");
		btnVoltar.setBackground(Color.WHITE);
		btnVoltar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnVoltar.setBounds(10, 327, 164, 23);
		ctFuncao.add(btnVoltar);

		lblUsuario = new JLabel("USUARIO :");
		lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblUsuario.setBounds(10, 277, 70, 14);
		ctFuncao.add(lblUsuario);

		textUsuario = new JTextField();
		textUsuario.setBounds(90, 275, 252, 20);
		ctFuncao.add(textUsuario);
		textUsuario.setColumns(10);

		lblSenha = new JLabel("SENHA :");
		lblSenha.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSenha.setBounds(10, 302, 64, 14);
		ctFuncao.add(lblSenha);

		textSenha = new JPasswordField();
		textSenha.setBounds(90, 300, 252, 20);
		ctFuncao.add(textSenha);

	}

	public void CriaAcoes() {
		btnVoltar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ConsultaFuncionario func = new ConsultaFuncionario(login);
				func.setVisible(true);
				setVisible(false);
			}
		});

		txtCep.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(java.awt.event.KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					WebServiceCep cep = WebServiceCep.searchCep(txtCep.getText());
					if (cep.wasSuccessful()) {
						txtEndereco.setText(cep.getLogradouro());
						txtBairro.setText(cep.getBairro());
					} else {
						JOptionPane.showMessageDialog(null, "CEP incorreto");
					}

				}

			}
		});

		btnCadastrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cadastrarFuncionario();
			}
		});

		// Atalhos

		btnCadastrar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0),
				"evento");
		btnCadastrar.getActionMap().put("evento", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				cadastrarFuncionario();
			}
		});

		btnVoltar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				"evento");
		btnVoltar.getActionMap().put("evento", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ConsultaFuncionario func = new ConsultaFuncionario(login);
				func.setVisible(true);
				setVisible(false);
			}
		});
	}

	public void cadastrarFuncionario() {
		cbFuncao.addItem("Caixa");
		if (cbFuncao.getSelectedIndex() != -1 && !txtNome.getText().equals("") && !txtTelefone.getText().equals("")
				&& !txtCpf.getText().equals("") && !txtCep.getText().equals("") && !txtEndereco.getText().equals("")
				&& !txtNum.getText().equals("") && !txtBairro.getText().equals("") && !txtCtps.getText().equals("")
				&& !txtCnh.getText().equals("") && !textUsuario.getText().equals("")
				&& !textSenha.getPassword().equals("")) {
			nome = txtNome.getText();
			telefone = txtTelefone.getText();
			cpf = txtCpf.getText();
			cep = txtCep.getText();
			endereco = txtEndereco.getText();
			num = txtNum.getText();
			bairro = txtBairro.getText();
			funcao = (String) cbFuncao.getSelectedItem();
			ctps = txtCtps.getText();
			cnh = txtCnh.getText();
			usuario = textUsuario.getText();
			senha = new String(textSenha.getPassword());

			cadastrarBanco();

			JOptionPane.showMessageDialog(null, "FuncionÃ¡rio cadastrado com sucesso!");

			ConsultaFuncionario cons = new ConsultaFuncionario(login);
			cons.setVisible(true);
			dispose();

		} else {
			JOptionPane.showMessageDialog(null, "Preencha todos os dados para continuar!");
		}
	}

	public void cadastrarBanco() {
		try {

			// Procura por uma classe no projeto
			Class.forName("com.mysql.jdbc.Driver");

			// Cria uma variavel de conexão (local do banco, usuario, senha)
			// Connection tem q ser a do .sql
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/supermercado", "root", "");
			if (atualizar) {
				String query = "UPDATE funcionarios SET nome = ?, telefone = ?, cpf = ?, cep = ?, endereco = ?, numero = ?, bairro = ?, funcao = ?, ctps = ?, cnh = ?, usuario = ?, senha = ? WHERE id = ?";

				PreparedStatement stmt = con.prepareStatement(query);
				stmt.setString(1, nome);
				stmt.setString(2, telefone);
				stmt.setString(3, cpf);
				stmt.setString(4, cep);
				stmt.setString(5, endereco);
				stmt.setString(6, num);
				stmt.setString(7, bairro);
				stmt.setString(8, funcao);
				stmt.setString(9, ctps);
				stmt.setString(10, cnh);
				stmt.setString(11, usuario);
				stmt.setString(12, senha);
				stmt.setInt(13, id);

				// Executa o comando no banco de dados
				stmt.executeUpdate();

				// Fecha comando e conexão
				stmt.close();
				con.close();
			} else {

				// Cria a string de inserção no banco
				String query = "INSERT INTO funcionarios (nome, telefone, cpf, cep, endereco, numero, bairro, funcao, ctps, cnh, usuario, senha) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

				// Cria o comando
				// PreparedStatement tem q ser a do .sql
				PreparedStatement stmt = con.prepareStatement(query);

				// Seta os valores na string de inserção
				stmt.setString(1, nome);
				stmt.setString(2, telefone);
				stmt.setString(3, cpf);
				stmt.setString(4, cep);
				stmt.setString(5, endereco);
				stmt.setString(6, num);
				stmt.setString(7, bairro);
				stmt.setString(8, funcao);
				stmt.setString(9, ctps);
				stmt.setString(10, cnh);
				stmt.setString(11, usuario);
				stmt.setString(12, senha);

				// Executa o comando no banco de dados
				stmt.executeUpdate();

				// Fecha comando e conexão
				stmt.close();
				con.close();
			}

			atualizar = false;

		} catch (Exception e) {
			System.out.println("Erro: " + e);
		}

	}

}
