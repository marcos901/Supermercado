package cliente;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.itextpdf.text.log.SysoCounter;

import Janelas.Inicial;
import Janelas.Login;
import cliente.WebServiceCep;

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

public class CadastrarCliente extends JFrame {

	private JPanel contentPane;
	private JTextField txtNome;
	private JTextField txtEndereco;
	private JTextField txtNum;
	private JTextField txtBairro;
	private JTextField txtPonto;
	private JTextField txtTelefone;
	private JTextField txtCep;
	private JButton btnVoltar, btnCadastrar;
	private JLabel lblCpf;
	private JTextField txtCpf;
	protected Inicial inicio;
	private String nome, telefone, cpf, cep, endereco, num, bairro, pontoReferencia, id;
	private boolean atualizar = false;
	private Login login;

	public CadastrarCliente(Login login1) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 415, 300);
		contentPane = new JPanel();
		setResizable(false);
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		login = login1;
		Componetes();
		CriaAcoes();

	}

	public CadastrarCliente(String pnome, String ptelefone, String pcpf, String pcep, String pendereco, String pnumero,
			String pbairro, String pponto, String pid, boolean patualizar) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 415, 300);
		contentPane = new JPanel();
		setResizable(false);
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		Componetes();
		CriaAcoes();
		this.txtNome.setText(pnome);
		this.txtTelefone.setText(ptelefone);
		this.txtCpf.setText(pcpf);
		this.txtCep.setText(pcep);
		this.txtEndereco.setText(pendereco);
		this.txtNum.setText(pnumero);
		this.txtBairro.setText(pbairro);
		this.txtPonto.setText(pponto);
		this.id = pid;
		this.atualizar = patualizar;
	}

	public void Componetes() {

		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblCadastrarCliente = new JLabel("Cadastrar Cliente");
		lblCadastrarCliente.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCadastrarCliente.setBounds(139, 11, 119, 14);
		contentPane.add(lblCadastrarCliente);

		JLabel lblNome = new JLabel("Nome: ");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNome.setBounds(10, 36, 46, 14);
		contentPane.add(lblNome);

		txtNome = new JTextField();
		txtNome.setBounds(10, 52, 379, 20);
		contentPane.add(txtNome);
		txtNome.setColumns(10);

		JLabel lblEndereo = new JLabel("Endere\u00E7o:");
		lblEndereo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblEndereo.setBounds(10, 122, 64, 14);
		contentPane.add(lblEndereo);

		txtEndereco = new JTextField();
		txtEndereco.setBounds(10, 140, 298, 20);
		contentPane.add(txtEndereco);
		txtEndereco.setColumns(10);

		JLabel lblN = new JLabel("N\u00BA:");
		lblN.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblN.setBounds(318, 122, 37, 14);
		contentPane.add(lblN);

		txtNum = new JTextField();
		txtNum.setBounds(318, 140, 68, 20);
		contentPane.add(txtNum);
		txtNum.setColumns(10);

		JLabel lblBairro = new JLabel("Bairro:");
		lblBairro.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblBairro.setBounds(10, 171, 46, 14);
		contentPane.add(lblBairro);

		txtBairro = new JTextField();
		txtBairro.setBounds(10, 187, 154, 20);
		contentPane.add(txtBairro);
		txtBairro.setColumns(10);

		JLabel lblPontoDeReferncia = new JLabel("Ponto de Refer\u00EAncia:");
		lblPontoDeReferncia.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPontoDeReferncia.setBounds(174, 171, 132, 14);
		contentPane.add(lblPontoDeReferncia);

		txtPonto = new JTextField();
		txtPonto.setBounds(174, 187, 215, 20);
		contentPane.add(txtPonto);
		txtPonto.setColumns(10);

		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTelefone.setBounds(10, 74, 64, 14);
		contentPane.add(lblTelefone);

		txtTelefone = new JTextField();
		txtTelefone.setBounds(10, 91, 106, 20);
		contentPane.add(txtTelefone);
		txtTelefone.setColumns(10);

		btnCadastrar = new JButton("CADASTRAR | F1 |");
		btnCadastrar.setBackground(Color.WHITE);
		btnCadastrar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnCadastrar.setBounds(250, 215, 150, 23);
		contentPane.add(btnCadastrar);

		btnVoltar = new JButton("VOLTAR | ESC |");
		btnVoltar.setBackground(Color.WHITE);
		btnVoltar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnVoltar.setBounds(90, 215, 150, 23);
		contentPane.add(btnVoltar);

		JLabel lblCep = new JLabel("CEP:");
		lblCep.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCep.setBounds(234, 74, 46, 14);
		contentPane.add(lblCep);

		txtCep = new JTextField();
		txtCep.setBounds(234, 91, 98, 20);
		contentPane.add(txtCep);
		txtCep.setColumns(10);

		lblCpf = new JLabel("CPF:");
		lblCpf.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCpf.setBounds(131, 75, 46, 14);
		contentPane.add(lblCpf);

		txtCpf = new JTextField();
		txtCpf.setBounds(126, 91, 98, 20);
		contentPane.add(txtCpf);
		txtCpf.setColumns(10);

	}

	public void CriaAcoes() {
		btnVoltar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ConsultaCliente cliente = new ConsultaCliente(login);
				cliente.setVisible(true);
				setVisible(false);

			}
		});

		btnVoltar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				"evento");
		btnVoltar.getActionMap().put("evento", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ConsultaCliente cliente = new ConsultaCliente(login);
				cliente.setVisible(true);
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
				Cadastrar();

			}
		});

		btnCadastrar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0),
				"evento");
		btnCadastrar.getActionMap().put("evento", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Cadastrar();
			}
		});
	}

	public void Cadastrar() {
		if (!txtNome.getText().equals("") && !txtTelefone.getText().equals("") && !txtCpf.getText().equals("")
				&& !txtCep.getText().equals("") && !txtEndereco.getText().equals("") && !txtNum.getText().equals("")
				&& !txtBairro.getText().equals("") && !txtPonto.getText().equals("")) {
			nome = txtNome.getText();
			telefone = txtTelefone.getText();
			cpf = txtCpf.getText();
			cep = txtCep.getText();
			endereco = txtEndereco.getText();
			num = txtNum.getText();
			bairro = txtBairro.getText();
			pontoReferencia = txtPonto.getText();

			cadastrarBanco();

			JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");
			ConsultaCliente cons = new ConsultaCliente(login);
			cons.setVisible(true);
			setVisible(false);
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
				String query = "UPDATE clientes SET nome = ?, telefone = ?, cpf = ?, cep = ?, endereco = ?, numero = ?, bairro = ?, ponto_referencia = ? WHERE id = ?";

				PreparedStatement stmt = con.prepareStatement(query);
				stmt.setString(1, nome);
				stmt.setString(2, telefone);
				stmt.setString(3, cpf);
				stmt.setString(4, cep);
				stmt.setString(5, endereco);
				stmt.setString(6, num);
				stmt.setString(7, bairro);
				stmt.setString(8, pontoReferencia);
				stmt.setString(9, id);

				// Executa o comando no banco de dados
				stmt.executeUpdate();

				// Fecha comando e conexão
				stmt.close();
				con.close();
			} else {

				// Cria a string de inserção no banco
				String query = "INSERT INTO clientes (nome, telefone, cpf, cep, endereco, numero, bairro, ponto_referencia) VALUES(?,?,?,?,?,?,?,?)";

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
				stmt.setString(8, pontoReferencia);

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
