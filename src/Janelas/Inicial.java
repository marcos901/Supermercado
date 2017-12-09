package Janelas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import cliente.CadastrarCliente;
import cliente.ConsultaCliente;
import empresa.CadastrarEmpresa;
import fornecedor.ConsultaFornecedor;
import funcionario.ConsultaFuncionario;
import produto.ConsultarProduto;
import venda.Venda;

import javax.security.auth.kerberos.KerberosKey;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class Inicial extends JFrame {

	private JPanel contentPane;
	JButton btnVender, btnCadastroDeCliente, btnCadastroDeFornercedor, btnCadastroFuncionario, btnCadastroDeProdutos,
			btnVoltarLogin, btnEmpresa, btnAlterarLogin;
	CadastrarEmpresa empresa;
	private String nomeFantasia, razaoSocial, cnpj, inscEstadual, telefone, email, cep, endereco, num, bairro, cidade,
			uf, nomeFunc;
	private int clique = 0, id;
	private Login login;

	public Inicial(Login login1) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 440, 194);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		login = login1;
		contentPane.setLayout(null);
		setResizable(false);
		Compomentes();
		CriaAcoes();
		
		

	}

	public void Compomentes() {
		// System.out.println(nomeFunc);
		btnCadastroDeCliente = new JButton("Consulta de Cliente  | F1 |");
		btnCadastroDeCliente.setBackground(Color.WHITE);
		btnCadastroDeCliente.setFocusable(false);
		btnCadastroDeCliente.setBounds(10, 10, 200, 25);
		contentPane.add(btnCadastroDeCliente);

		btnCadastroDeFornercedor = new JButton("Consulta Fornercedor | F2 |");
		btnCadastroDeFornercedor.setBackground(Color.WHITE);
		btnCadastroDeFornercedor.setFocusable(false);
		btnCadastroDeFornercedor.setBounds(10, 46, 200, 25);
		contentPane.add(btnCadastroDeFornercedor);

		btnCadastroFuncionario = new JButton("Consulta Funcionario | F3|");
		btnCadastroFuncionario.setBackground(Color.WHITE);
		btnCadastroFuncionario.setFocusable(false);
		btnCadastroFuncionario.setBounds(10, 82, 200, 25);
		contentPane.add(btnCadastroFuncionario);

		btnCadastroDeProdutos = new JButton("Consulta Produtos | F4 |");
		btnCadastroDeProdutos.setBackground(Color.WHITE);
		btnCadastroDeProdutos.setFocusable(false);
		btnCadastroDeProdutos.setBounds(10, 117, 200, 25);
		contentPane.add(btnCadastroDeProdutos);

		btnVender = new JButton("Vender | F5 |");
		btnVender.setBackground(Color.WHITE);
		btnVender.setFocusable(false);
		btnVender.setBounds(220, 10, 200, 25);
		contentPane.add(btnVender);

		btnVoltarLogin = new JButton("Voltar Login  | ESC |");
		btnVoltarLogin.setBackground(Color.WHITE);
		btnVoltarLogin.setFocusable(false);
		btnVoltarLogin.setBounds(220, 117, 200, 25);
		contentPane.add(btnVoltarLogin);

		btnEmpresa = new JButton("Empresa |  F6  | ");
		btnEmpresa.setBackground(Color.WHITE);
		btnEmpresa.setBounds(220, 47, 200, 23);
		btnEmpresa.setFocusable(false);
		contentPane.add(btnEmpresa);

		// btnAlterarLogin = new JButton("Alterar Login | F7 |");
		// btnAlterarLogin.setBackground(Color.WHITE);
		// btnAlterarLogin.setBounds(220, 82, 200, 23);
		// btnAlterarLogin.setFocusable(false);
		// contentPane.add(btnAlterarLogin);
	}

	public void CriaAcoes() {

		btnCadastroDeCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConsultaCliente cliente = new ConsultaCliente(login);
				cliente.setVisible(true);
				setVisible(false);
			}
		});

		btnCadastroDeProdutos.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ConsultarProduto produto = new ConsultarProduto(login);
				produto.setVisible(true);
				setVisible(false);

			}
		});

		btnCadastroFuncionario.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ConsultaFuncionario funcionario = new ConsultaFuncionario(login);
				funcionario.setVisible(true);
				setVisible(false);

			}
		});

		btnCadastroDeFornercedor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ConsultaFornecedor fornercedor = new ConsultaFornecedor(login);
				fornercedor.setVisible(true);
				setVisible(false);

			}
		});

		btnVender.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Venda venda = new Venda(login,nomeFunc);
				venda.setVisible(true);
				setVisible(false);

			}
		});
		btnVoltarLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Login lo = new Login();
				lo.setVisible(true);
				setVisible(false);
			}
		});

		btnEmpresa.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pegarDadosEmpresa();
				System.out.println(clique);
				if (clique == 1) {
					empresa = new CadastrarEmpresa(id, nomeFantasia, razaoSocial, cnpj, inscEstadual, telefone, email,
							cep, endereco, num, bairro, cidade, uf);
					empresa.setVisible(true);
					setVisible(false);
				} else {
					empresa = new CadastrarEmpresa(login);
					empresa.setVisible(true);
					setVisible(false);

				}

			}
		});

		// btnAlterarLogin.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent e) {
		// AlterarLogin alterar = new AlterarLogin();
		// alterar.setVisible(true);
		// setVisible(false);
		//
		// }
		// });

		/////////////////////////////////////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////////////////
		/// ATALHOS

		btnCadastroDeCliente.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, InputEvent.BUTTON1_MASK), "evento");
		btnCadastroDeCliente.getActionMap().put("evento", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ConsultaCliente cliente = new ConsultaCliente(login);
				cliente.setVisible(true);
				setVisible(false);
			}
		});

		btnCadastroDeFornercedor.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "evento");
		btnCadastroDeFornercedor.getActionMap().put("evento", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ConsultaFornecedor fornercedor = new ConsultaFornecedor(login);
				fornercedor.setVisible(true);
				setVisible(false);
			}
		});

		btnCadastroFuncionario.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0), "evento");
		btnCadastroFuncionario.getActionMap().put("evento", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ConsultaFuncionario funcionario = new ConsultaFuncionario(login);
				funcionario.setVisible(true);
				setVisible(false);
			}
		});

		btnCadastroDeProdutos.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0), "evento");
		btnCadastroDeProdutos.getActionMap().put("evento", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ConsultarProduto produto = new ConsultarProduto(login);
				produto.setVisible(true);
				setVisible(false);
			}
		});

		btnVender.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0),
				"evento");
		btnVender.getActionMap().put("evento", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Venda venda = new Venda(login,nomeFunc);
				venda.setVisible(true);
				setVisible(false);
			}
		});

		btnEmpresa.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0),
				"evento");
		btnEmpresa.getActionMap().put("evento", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CadastrarEmpresa empresa = new CadastrarEmpresa(login);
				empresa.setVisible(true);
				setVisible(false);
			}
		});

		btnVoltarLogin.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				"evento");
		btnVoltarLogin.getActionMap().put("evento", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Login login = new Login();
				login.setVisible(true);
				setVisible(false);
			}
		});

	}

	public void pegarDadosEmpresa() {
		try {

			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/supermercado", "root", "");

			String query = "SELECT * FROM empresas";

			PreparedStatement stmt = con.prepareStatement(query);

			// Pega o resultado la no banco
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				id = rs.getInt("id");
				nomeFantasia = rs.getString("nome");
				razaoSocial = rs.getString("razao_social");
				cnpj = rs.getString("cnpj");
				inscEstadual = rs.getString("insc_estadual");
				telefone = rs.getString("telefone");
				email = rs.getString("email");
				cep = rs.getString("cep");
				endereco = rs.getString("endereco");
				num = rs.getString("numero");
				bairro = rs.getString("bairro");
				cidade = rs.getString("cidade");
				uf = rs.getString("uf");
				clique = 1;
			}

			con.close();
			rs.close();

		} catch (Exception e) {
			System.out.println("Erro: " + e);
		}
	}

}
