package Janelas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.JPasswordField;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsuario;
	private JPasswordField txtSenha;
	private String nomeFunc, cargo;
	protected String entraga[][];
	protected String aux[];
	protected int ganbiara;
	boolean confirmado = false;

	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 230, 170);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		setResizable(false);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblLogin = new JLabel("Login");
		lblLogin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblLogin.setBounds(85, 11, 46, 19);
		contentPane.add(lblLogin);

		JLabel lblUsurio = new JLabel("Usu\u00E1rio:");
		lblUsurio.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblUsurio.setBounds(10, 41, 58, 14);
		contentPane.add(lblUsurio);

		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSenha.setBounds(10, 71, 46, 14);
		contentPane.add(lblSenha);

		txtUsuario = new JTextField();
		txtUsuario.setBounds(61, 41, 113, 20);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);
		txtUsuario.setText("admin");
		txtSenha = new JPasswordField();
		txtSenha.setBounds(61, 69, 113, 19);
		txtSenha.setText("admin");
		contentPane.add(txtSenha);

		JButton btnEntrar = new JButton("ENTRAR");
		btnEntrar.setBackground(Color.WHITE);
		btnEntrar.setBounds(102, 97, 83, 23);
		btnEntrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				VerificarSenha();
				if (confirmado == true && cargo.equals("Gerente")) {
					Inicial inicio = new Inicial(Login.this);

					inicio.setVisible(true);
					setVisible(false);
				} else if (confirmado == true && cargo.equals("Funcionario")) {
					InicialFuncionario inicio1 = new InicialFuncionario(Login.this);
					inicio1.setVisible(true);
					setVisible(false);
					

				} else {
					JOptionPane.showMessageDialog(null, "SENHA OU USUARIO INCORRETO", "ERRO",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		btnEntrar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
				"evento");
		btnEntrar.getActionMap().put("evento", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				VerificarSenha();
				if (confirmado == true && cargo.equals("Gerente")) {
					Inicial inicio = new Inicial(Login.this);

					inicio.setVisible(true);
					setVisible(false);
				} else if (confirmado == true && cargo.equals("Funcionario")) {
					InicialFuncionario inicio1 = new InicialFuncionario(Login.this);
					inicio1.setVisible(true);
					setVisible(false);

				} else {
					JOptionPane.showMessageDialog(null, "SENHA OU USUARIO INCORRETO", "ERRO",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		contentPane.add(btnEntrar);
	}

	public void VerificarSenha() {
		try {

			String senha = new String(txtSenha.getPassword());

			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/supermercado", "root", "");

			String query = "SELECT * FROM funcionarios";

			PreparedStatement stmt = con.prepareStatement(query);

			// Pega o resultado la no banco
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				if (rs.getString("usuario").equals(txtUsuario.getText()) && (rs.getString("senha").equals(senha))) {
					confirmado = true;
					nomeFunc = rs.getString("nome");
					cargo = rs.getString("funcao").trim();
					
				}
			}

			con.close();
			rs.close();

		} catch (Exception e) {
			System.out.println("Erro: " + e);
		}
	}

	public String getNomeFunc() {
		return nomeFunc;
	}

	public void setNomeFunc(String nomeFunc) {
		this.nomeFunc = nomeFunc;
	}

	public String[][] getEntraga() {
		return entraga;
	}

	public void setEntraga(String[][] entraga) {
		this.entraga = entraga;
	}

	public int getGanbiara() {
		return ganbiara;
	}

	public void setGanbiara(int ganbiara) {
		this.ganbiara = ganbiara;
	}

	public String[] getAux() {
		return aux;
	}

	public void setAux(String[] aux) {
		this.aux = aux;
	}
	
	
	
	

}
