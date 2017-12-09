package produto;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.log.SysoCounter;

import Janelas.Login;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

public class CadastrarProduto extends JFrame {

	private JPanel contentPane;
	private JTextField txtNomeProduto;
	private JTextField txtCodigoDebarra;
	private JTextField txtValorCusto;
	private JTextField txtQuant;
	private JTextField txtValorVenda;
	private JButton btnCadastrar, btnVoltar;
	private String nome = null, fornecedor = null, id = null;
	private double valorVenda = 0, valorCusto = 0;
	private int quantidade = 0, id_fornecedor = 0, codigoDeBarra;
	private boolean atualizar = false;
	private JComboBox cmbFonecedores;
	private Login login;

	public CadastrarProduto(Login login1) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 446, 241);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setForeground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setResizable(false);
		setTitle("Cadastro de Produto");
		contentPane.setLayout(null);
		login = login1;
		Componentes();
		CriaAcoes();
		mostrarFornecedores();

	}

	public CadastrarProduto(String pnome, String pCodigoBarra, String pValorCusto, String pQnt, String pValorVenda,
			boolean patualizar, String pid) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 446, 241);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setForeground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setResizable(false);
		setTitle("Cadastro de Produto");
		contentPane.setLayout(null);
		Componentes();
		CriaAcoes();
		mostrarFornecedores();

		this.txtNomeProduto.setText(pnome);
		this.txtCodigoDebarra.setText(pCodigoBarra);
		this.txtValorCusto.setText(pValorCusto);
		this.txtValorVenda.setText(pValorVenda);
		this.txtQuant.setText(pQnt);
		this.atualizar = patualizar;
		this.id = pid;
	}

	public void Componentes() {
		JLabel lblNome = new JLabel("Nome :");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNome.setBounds(10, 10, 50, 14);
		contentPane.add(lblNome);

		txtNomeProduto = new JTextField();
		txtNomeProduto.setBounds(70, 8, 321, 20);
		contentPane.add(txtNomeProduto);
		txtNomeProduto.setColumns(10);

		JLabel lblCodigoDeBarra = new JLabel("Codigo de barra :");
		lblCodigoDeBarra.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCodigoDeBarra.setBounds(10, 45, 114, 14);
		contentPane.add(lblCodigoDeBarra);

		txtCodigoDebarra = new JTextField();
		txtCodigoDebarra.setBounds(130, 42, 261, 20);
		contentPane.add(txtCodigoDebarra);
		txtCodigoDebarra.setColumns(10);

		JLabel lblValorCustoUnitario = new JLabel("Valor Custo Unitario :");
		lblValorCustoUnitario.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblValorCustoUnitario.setBounds(10, 80, 129, 14);
		contentPane.add(lblValorCustoUnitario);

		txtValorCusto = new JTextField();
		txtValorCusto.setBounds(140, 78, 65, 20);
		contentPane.add(txtValorCusto);
		txtValorCusto.setColumns(10);

		JLabel lblQuantidade = new JLabel("Quantidade :");
		lblQuantidade.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblQuantidade.setBounds(246, 80, 80, 14);
		contentPane.add(lblQuantidade);

		txtQuant = new JTextField();
		txtQuant.setBounds(336, 78, 55, 20);
		contentPane.add(txtQuant);
		txtQuant.setColumns(10);

		JLabel lblValorDeVenda = new JLabel("Valor de Venda Unitario :");
		lblValorDeVenda.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblValorDeVenda.setBounds(10, 110, 156, 14);
		contentPane.add(lblValorDeVenda);

		txtValorVenda = new JTextField();
		txtValorVenda.setBounds(167, 107, 65, 20);
		contentPane.add(txtValorVenda);
		txtValorVenda.setColumns(10);

		btnCadastrar = new JButton("CADASTRAR | F1 |");
		btnCadastrar.setBackground(Color.WHITE);
		btnCadastrar.setBounds(10, 178, 200, 23);
		contentPane.add(btnCadastrar);

		btnVoltar = new JButton("VOLTAR | ESC |");
		btnVoltar.setBackground(Color.WHITE);
		btnVoltar.setBounds(220, 178, 200, 23);
		contentPane.add(btnVoltar);

		cmbFonecedores = new JComboBox();
		cmbFonecedores.setBackground(Color.WHITE);
		cmbFonecedores.setBounds(100, 147, 291, 20);
		contentPane.add(cmbFonecedores);

		JLabel lblFornecerdor = new JLabel("Fornecerdor :");
		lblFornecerdor.setHorizontalAlignment(SwingConstants.LEFT);
		lblFornecerdor.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFornecerdor.setBounds(10, 147, 93, 20);
		contentPane.add(lblFornecerdor);
	}

	public void CriaAcoes() {

		btnCadastrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cadastrarProduto();
				limpar();
			}
		});

		btnVoltar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ConsultarProduto consulta = new ConsultarProduto(login);
				consulta.setVisible(true);
				setVisible(false);

			}
		});

		// Atalhos

		btnCadastrar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0),
				"evento");
		btnCadastrar.getActionMap().put("evento", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				cadastrarProduto();
				limpar();
			}
		});

		btnVoltar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				"evento");
		btnVoltar.getActionMap().put("evento", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ConsultarProduto consulta = new ConsultarProduto(login);
				consulta.setVisible(true);
				setVisible(false);
			}
		});
	}

	public void cadastrarProduto() {
		if (!txtNomeProduto.getText().equals("") && !txtCodigoDebarra.getText().equals("")
				&& !txtValorCusto.getText().equals("") && !txtQuant.getText().equals("")
				&& !txtValorVenda.getText().equals("")) {

			nome = txtNomeProduto.getText();
			fornecedor = cmbFonecedores.getSelectedItem().toString();

			try {
				codigoDeBarra = Integer.parseInt(txtCodigoDebarra.getText());

				valorCusto = Double.parseDouble(txtValorCusto.getText());

				valorVenda = Double.parseDouble(txtValorVenda.getText());

				quantidade = Integer.parseInt(txtQuant.getText());

				Class.forName("com.mysql.jdbc.Driver");

				Connection con = DriverManager.getConnection("jdbc:mysql://localhost/supermercado", "root", "");

				String query = "SELECT * FROM fornecedores";

				PreparedStatement stmt = con.prepareStatement(query);

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {

					if (rs.getString("nome").equals(cmbFonecedores.getSelectedItem().toString().trim())) {
						id_fornecedor = rs.getInt("id");
					}

				}

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "ERRO", "!!FORMATO DE NUMERO ERRADO!!", JOptionPane.ERROR_MESSAGE);
			}

			JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
			cadastrarBanco();
		} else {
			JOptionPane.showMessageDialog(null, "Preencha todos os campos para continuar!");
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

				String query = "UPDATE produtos SET id_fornecedor = ?, nome = ?, codigo = ?, valor_custo = ?, quantidade = ?, valor_venda = ?  WHERE id = ?";

				PreparedStatement stmt = con.prepareStatement(query);
				stmt.setInt(1, id_fornecedor);
				stmt.setString(2, nome);
				stmt.setInt(3, codigoDeBarra);
				stmt.setDouble(4, valorCusto);
				stmt.setInt(5, quantidade);
				stmt.setDouble(6, valorVenda);
				stmt.setString(7, id);

				// Executa o comando no banco de dados
				stmt.executeUpdate();

				// Fecha comando e conexão
				stmt.close();
				con.close();
			} else {

				// Cria a string de inserção no banco
				String query = "INSERT INTO produtos (id_fornecedor,nome, codigo, valor_custo, quantidade,valor_venda) VALUES(?,?,?,?,?,?)";

				// Cria o comando
				// PreparedStatement tem q ser a do .sql
				PreparedStatement stmt = con.prepareStatement(query);

				// Seta os valores na string de inserção
				stmt.setInt(1, id_fornecedor);
				stmt.setString(2, nome);
				stmt.setInt(3, codigoDeBarra);
				stmt.setDouble(4, valorCusto);
				stmt.setInt(5, quantidade);
				stmt.setDouble(6, valorVenda);
				// stmt.setInt(7, id);

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

	private void limpar() {
		txtCodigoDebarra.setText(null);
		txtNomeProduto.setText(null);
		txtQuant.setText(null);
		txtValorCusto.setText(null);
		txtValorVenda.setText(null);
		cmbFonecedores.setSelectedIndex(-1);
	}

	public void mostrarFornecedores() {
		try {

			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/supermercado", "root", "");

			String query = "SELECT * FROM fornecedores";

			PreparedStatement stmt = con.prepareStatement(query);

			// Pega o resultado la no banco
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				cmbFonecedores.addItem(rs.getString("nome"));
			}

			con.close();
			rs.close();

		} catch (Exception e) {
			System.out.println("Erro: " + e);
		}
	}

}
