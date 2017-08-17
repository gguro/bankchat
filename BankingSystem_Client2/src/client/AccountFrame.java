package client;


import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import message.AccountMessage;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;

public class AccountFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JPasswordField passwordField;
	int type = 1;
	
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		new AccountFrame("회원가입");
//	}

	/**
	 * Create the frame.
	 */
	public AccountFrame(Client c) {
		setTitle("회원가입");
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 430);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("\uACC4\uC88C\uBC88\uD638");
		label.setBounds(12, 25, 60, 20);
		contentPane.add(label);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(10, 49, 170, 20);
		contentPane.add(textField_1);

		
		JButton btnNewButton = new JButton("\uC800   \uC7A5");

	
		btnNewButton.setBounds(60, 350, 100, 20);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				   String order = "create";
                   String id =textField_1.getText();
				   String password =passwordField.getText();
				   String name =textField_2.getText();
				   String rate = textField_3.getText();
				   
					AccountMessage accMsg = new AccountMessage();
					accMsg.setOrder(order);
					accMsg.setId(id);
				    accMsg.setName(name);
					accMsg.setPassword(password);
 		            accMsg.setType(type);
					if (type == 2) {
						accMsg.setRate(Integer.parseInt(rate));
					}
 		            
 		            c.sendMSG(accMsg);
					dispose();
			}
			
		});
		
		JButton button = new JButton("\uCDE8  \uC18C ");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dispose();
			}
		});
		button.setBounds(220, 350, 100, 20);
		contentPane.add(button);
		
		JLabel label_1 = new JLabel("\uBE44\uBC00\uBC88\uD638");
		label_1.setBounds(12, 79, 57, 20);
		contentPane.add(label_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(12, 169, 168, 20);
		contentPane.add(textField_2);
		
		JLabel label_2 = new JLabel("\uC608\uAE08\uC8FC");
		label_2.setBounds(15, 139, 57, 20);
		contentPane.add(label_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(12, 289, 168, 20);
		contentPane.add(textField_3);
		textField_3.setVisible(false);
		
		JLabel label_3 = new JLabel("\uACC4\uC88C\uC885\uB958");
		label_3.setBounds(12, 199, 186, 20);
		contentPane.add(label_3);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(12, 109, 168, 20);
		contentPane.add(passwordField);
		
		JLabel label_4 = new JLabel(" \uC774\uC790\uC728");
		label_4.setBounds(12, 259, 57, 20);
		contentPane.add(label_4);
		label_4.setVisible(false);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(12, 229, 168, 21);
		comboBox.addItem("일반계좌");
		comboBox.addItem("보통계좌");
		
		contentPane.add(comboBox);
		comboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String str = (String)comboBox.getSelectedItem();
				
				if (str.equals("보통계좌")) {
					label_4.setVisible(true);
					textField_3.setVisible(true);
					type=2;
				} else {
					textField_3.setVisible(false);
					label_4.setVisible(false);
					type=1;
				}
			}
		});
		
	}
}
