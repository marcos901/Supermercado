package empresa;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import cliente.WebServiceCep;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import Janelas.Inicial;
import Janelas.Login;

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
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;

public class CadastrarEmpresa extends JFrame {

	private JPanel contentPane;
	private JTextField txtRazaoSocial;
	private JTextField txtNomeFantasia;
	private JTextField txtCnpj;
	private JTextField txtInsEstadual;
	private JTextField txtTelefone;
	private JTextField txtEmail;
	private JTextField txtCep;
	private JTextField txtEndereco;
	private JTextField txtNum;
	private JTextField txtBairro;
	private JTextField txtCidade;
	private JTextField txtUf;
	private JButton btnCadastrar;
	private JButton btnVoltarEsc;
	private int id;
	private String nomeFantasia, razaoSocial, cnpj, inscEstadual, telefone, email, cep, endereco, num, bairro, cidade,
			uf;
	private boolean atualizar = false, cadastro;
	private Login login;

	public CadastrarEmpresa(Login login1) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 492, 345);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		login = login;
		criaComponente();
		criaAcao();
	}

	public CadastrarEmpresa(int pid, String pnome, String prazao, String pcnpj, String pinsc_estadual, String ptelefone,
			String pemail, String pcep, String pendereco, String pnumero, String pbairro, String pcidade, String puf) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 492, 345);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		criaComponente();
		criaAcao();
		txtNomeFantasia.setText(pnome);
		txtRazaoSocial.setText(prazao);
		txtCnpj.setText(pcnpj);
		txtInsEstadual.setText(pinsc_estadual);
		txtTelefone.setText(ptelefone);
		txtEmail.setText(pemail);
		txtCep.setText(pcep);
		txtEndereco.setText(pendereco);
		txtNum.setText(pnumero);
		txtBairro.setText(pbairro);
		txtCidade.setText(pcidade);
		txtUf.setText(puf);
		this.id = pid;
		this.atualizar = true;
	}

	public void criaComponente() {
		JLabel lblCadastrarEmpresa = new JLabel("CADASTRAR EMPRESA");
		lblCadastrarEmpresa.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCadastrarEmpresa.setBounds(151, 11, 173, 14);
		contentPane.add(lblCadastrarEmpresa);

		JLabel lblRazoSocial = new JLabel("Raz\u00E3o Social:");
		lblRazoSocial.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblRazoSocial.setBounds(10, 75, 87, 14);
		contentPane.add(lblRazoSocial);

		txtRazaoSocial = new JTextField();
		txtRazaoSocial.setBounds(10, 90, 170, 20);
		contentPane.add(txtRazaoSocial);
		txtRazaoSocial.setColumns(10);

		JLabel lblNomeFantasia = new JLabel("Nome Fantasia:");
		lblNomeFantasia.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNomeFantasia.setBounds(10, 34, 103, 14);
		contentPane.add(lblNomeFantasia);

		txtNomeFantasia = new JTextField();
		txtNomeFantasia.setBounds(11, 50, 455, 20);
		contentPane.add(txtNomeFantasia);
		txtNomeFantasia.setColumns(10);

		JLabel lblCnpj = new JLabel("CNPJ:");
		lblCnpj.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCnpj.setBounds(190, 75, 46, 14);
		contentPane.add(lblCnpj);

		txtCnpj = new JTextField();
		txtCnpj.setBounds(190, 90, 114, 20);
		contentPane.add(txtCnpj);
		txtCnpj.setColumns(10);

		JLabel lblInscEstadual = new JLabel("Insc Estadual:");
		lblInscEstadual.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblInscEstadual.setBounds(314, 75, 96, 14);
		contentPane.add(lblInscEstadual);

		txtInsEstadual = new JTextField();
		txtInsEstadual.setBounds(314, 90, 152, 20);
		contentPane.add(txtInsEstadual);
		txtInsEstadual.setColumns(10);

		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTelefone.setBounds(10, 121, 68, 14);
		contentPane.add(lblTelefone);

		txtTelefone = new JTextField();
		txtTelefone.setBounds(10, 136, 114, 20);
		contentPane.add(txtTelefone);
		txtTelefone.setColumns(10);

		JLabel lblEmail = new JLabel("E-mail:");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblEmail.setBounds(134, 122, 46, 14);
		contentPane.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setBounds(134, 136, 200, 20);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);

		JLabel lblCep = new JLabel("CEP:");
		lblCep.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCep.setBounds(341, 121, 46, 14);
		contentPane.add(lblCep);

		txtCep = new JTextField();
		txtCep.setBounds(341, 136, 125, 20);
		contentPane.add(txtCep);
		txtCep.setColumns(10);

		JLabel lblEndereo = new JLabel("Endere\u00E7o:");
		lblEndereo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblEndereo.setBounds(10, 167, 68, 14);
		contentPane.add(lblEndereo);

		txtEndereco = new JTextField();
		txtEndereco.setBounds(10, 185, 386, 20);
		contentPane.add(txtEndereco);
		txtEndereco.setColumns(10);

		JLabel lblN = new JLabel("N\u00BA:");
		lblN.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblN.setBounds(407, 167, 46, 14);
		contentPane.add(lblN);

		txtNum = new JTextField();
		txtNum.setBounds(407, 185, 59, 20);
		contentPane.add(txtNum);
		txtNum.setColumns(10);

		JLabel lblBairro = new JLabel("Bairro:");
		lblBairro.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblBairro.setBounds(10, 216, 46, 14);
		contentPane.add(lblBairro);

		txtBairro = new JTextField();
		txtBairro.setBounds(10, 232, 142, 20);
		contentPane.add(txtBairro);
		txtBairro.setColumns(10);

		JLabel lblCidade = new JLabel("Cidade:");
		lblCidade.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCidade.setBounds(162, 216, 96, 14);
		contentPane.add(lblCidade);

		txtCidade = new JTextField();
		txtCidade.setBounds(162, 232, 248, 20);
		contentPane.add(txtCidade);
		txtCidade.setColumns(10);

		JLabel lblUf = new JLabel("UF:");
		lblUf.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblUf.setBounds(420, 216, 46, 14);
		contentPane.add(lblUf);

		txtUf = new JTextField();
		txtUf.setBounds(420, 232, 46, 20);
		contentPane.add(txtUf);
		txtUf.setColumns(10);

		btnCadastrar = new JButton("CADASTRAR | F1 |");
		btnCadastrar.setBackground(Color.WHITE);
		btnCadastrar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnCadastrar.setBounds(293, 272, 160, 23);
		contentPane.add(btnCadastrar);

		btnVoltarEsc = new JButton("VOLTAR | ESC |");
		btnVoltarEsc.setBackground(Color.WHITE);
		btnVoltarEsc.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnVoltarEsc.setBounds(141, 272, 142, 23);
		contentPane.add(btnVoltarEsc);

	}

	public void criaAcao() {
		btnCadastrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				cadastrarEmpresa();
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
						txtCidade.setText(cep.getCidade());
						txtUf.setText(cep.getUf());
					} else {
						JOptionPane.showMessageDialog(null, "CEP incorreto");
					}
				}
			}
		});

		// Atalhos

		btnCadastrar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0),
				"evento");
		btnCadastrar.getActionMap().put("evento", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				cadastrarEmpresa();

			}
		});

		btnVoltarEsc.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Inicial menu = new Inicial(login);
				menu.setVisible(true);
				setVisible(false);

			}
		});

		btnVoltarEsc.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				"evento");
		btnVoltarEsc.getActionMap().put("evento", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Inicial menu = new Inicial(login);
				menu.setVisible(true);
				setVisible(false);
			}
		});

	}

	public void cadastrarEmpresa() {
		if (!txtNomeFantasia.getText().equals("") && !txtRazaoSocial.getText().equals("")
				&& !txtCnpj.getText().equals("") && !txtInsEstadual.getText().equals("")
				&& !txtTelefone.getText().equals("") && !txtEmail.getText().equals("") && !txtCep.getText().equals("")
				&& !txtEndereco.getText().equals("") && !txtNum.getText().equals("") && !txtBairro.getText().equals("")
				&& !txtCidade.getText().equals("") && !txtUf.getText().equals("")) {

			nomeFantasia = txtNomeFantasia.getText();
			razaoSocial = txtRazaoSocial.getText();
			cnpj = txtCnpj.getText();
			inscEstadual = txtInsEstadual.getText();
			telefone = txtTelefone.getText();
			email = txtEmail.getText();
			cep = txtCep.getText();
			endereco = txtEndereco.getText();
			num = txtNum.getText();
			bairro = txtBairro.getText();
			cidade = txtCidade.getText();
			uf = txtUf.getText();

			cadastrarBanco();

			JOptionPane.showMessageDialog(null, "Empresa cadastrada com sucesso!");

			Inicial menu = new Inicial(login);
			menu.setVisible(true);
			setVisible(false);
		} else {
			JOptionPane.showMessageDialog(null, "Informe todos os campos para continuar!");
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
				String query = "UPDATE empresas SET nome = ?, razao_social = ?, cnpj = ?, insc_estadual = ?, telefone = ?, email = ?, cep = ?, endereco = ?, numero = ?, bairro = ?, cidade = ?, uf = ? WHERE id = ?";

				PreparedStatement stmt = con.prepareStatement(query);
				// Seta os valores na string de inserção
				stmt.setString(1, nomeFantasia);
				stmt.setString(2, razaoSocial);
				stmt.setString(3, cnpj);
				stmt.setString(4, inscEstadual);
				stmt.setString(5, telefone);
				stmt.setString(6, email);
				stmt.setString(7, cep);
				stmt.setString(8, endereco);
				stmt.setString(9, num);
				stmt.setString(10, bairro);
				stmt.setString(11, cidade);
				stmt.setString(12, uf);
				stmt.setInt(13, id);

				// Executa o comando no banco de dados
				stmt.executeUpdate();

				// Fecha comando e conexão
				stmt.close();
				con.close();
			} else {

				// Cria a string de inserção no banco
				String query = "INSERT INTO empresas (nome, razao_social, cnpj, insc_estadual, telefone, email, cep, endereco, numero, bairro, cidade, uf) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

				// Cria o comando
				// PreparedStatement tem q ser a do .sql
				PreparedStatement stmt = con.prepareStatement(query);

				// Seta os valores na string de inserção
				stmt.setString(1, nomeFantasia);
				stmt.setString(2, razaoSocial);
				stmt.setString(3, cnpj);
				stmt.setString(4, inscEstadual);
				stmt.setString(5, telefone);
				stmt.setString(6, email);
				stmt.setString(7, cep);
				stmt.setString(8, endereco);
				stmt.setString(9, num);
				stmt.setString(10, bairro);
				stmt.setString(11, cidade);
				stmt.setString(12, uf);

				// Executa o comando no banco de dados
				stmt.executeUpdate();

				// Fecha comando e conexão
				stmt.close();
				con.close();
			}

		} catch (Exception e) {
			System.out.println("Erro: " + e);
		}

	}

}
