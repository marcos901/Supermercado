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
import venda.VendaFuncionario;

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

public class InicialFuncionario extends JFrame {

	private JPanel contentPane;
	JButton btnVender, btnVoltarLogin;
	CadastrarEmpresa empresa;
	private String nomeFantasia, razaoSocial, cnpj, inscEstadual, telefone, email, cep, endereco, num, bairro, cidade,
			uf, nomeFunc;
	private int clique = 0, id;
	private Login login;

	public InicialFuncionario(Login login1) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 440, 89);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		login = login1;
		setResizable(false);
		Compomentes();
		CriaAcoes();

	}


	public void Compomentes() {

		btnVender = new JButton("Vender | F1 |");
		btnVender.setBackground(Color.WHITE);
		btnVender.setFocusable(false);
		btnVender.setBounds(10, 11, 200, 25);
		contentPane.add(btnVender);

		btnVoltarLogin = new JButton("Voltar Login  | ESC |");
		btnVoltarLogin.setBackground(Color.WHITE);
		btnVoltarLogin.setFocusable(false);
		btnVoltarLogin.setBounds(220, 11, 200, 25);
		contentPane.add(btnVoltarLogin);

	}

	public void CriaAcoes() {

		btnVender.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				VendaFuncionario venda = new VendaFuncionario(login);
				venda.setVisible(true);
				setVisible(false);

			}
		});
		
		btnVoltarLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Login lo=new Login();
				lo.setVisible(true);
				setVisible(false);
			}
		});

		btnVender.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0),
				"evento");
		btnVender.getActionMap().put("evento", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {

				VendaFuncionario venda = new VendaFuncionario(login);
				venda.setVisible(true);
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

}
