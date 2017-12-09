package venda;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import Janelas.Inicial;

import java.awt.Color;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class SelecaoPagamentos extends JFrame {

	private JPanel contentPane;
	private JButton btnDinheiro, btnCartao;
	private String valorTotal;
	private String[][] compra;
	private int item;

	public SelecaoPagamentos(String pcompra[][], String pvalorTotal, int pItem) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 289);
		this.setTitle("Seleção de Pagamentos");
		this.setResizable(false);
		this.criaComponentes();
		this.criaAcoes();
		this.setVisible(true);
		this.valorTotal = pvalorTotal;
		this.compra = pcompra;
		this.item = pItem;

	}

	public void criaComponentes() {
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnDinheiro = new JButton("Dinheiro | D |");
		btnDinheiro.setBackground(Color.WHITE);
		btnDinheiro.setBounds(150, 70, 150, 46);
		btnDinheiro.setFocusable(false);
		contentPane.add(btnDinheiro);

		btnCartao = new JButton("Cart\u00E3o | C |");
		btnCartao.setBackground(Color.WHITE);
		btnCartao.setBounds(150, 142, 150, 46);
		btnCartao.setFocusable(false);
		contentPane.add(btnCartao);

	}

	public void criaAcoes() {

		btnDinheiro.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				Dinheiro pagarEmDinheiro = new Dinheiro(compra, valorTotal, item);

			}
		});

		btnDinheiro.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0),
				"evento");
		btnDinheiro.getActionMap().put("evento", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				Dinheiro pagarEmDinheiro = new Dinheiro(compra, valorTotal, item);
			}
		});
		
		btnCartao.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0),
				"evento");
		btnCartao.getActionMap().put("evento", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				Cartao pagarEmCartao = new Cartao(compra, valorTotal, item);
			}
		});

		btnCartao.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				Cartao pagarEmCartao = new Cartao(compra, valorTotal, item);
			}
		});

	}

}
