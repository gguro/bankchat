package client;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.TitledBorder;

import message.AccountMessage;
import message.Message;

public class ClientFrame2 extends JFrame {
	JTextArea chat;
	JButton connect;
	JButton deposit;
	JButton widthraw;
	JButton create;
	JButton send;
	JButton close;
	JButton craete;
	JButton login;
	JButton print;
	
	JTextField accName;
    JTextField accId;
    JTextField balance;
    JTextField accType;
    JTextField rate;
	
	JLabel accNamelbl;
	JLabel accIdlbl;
	JLabel balancelbl;
	JLabel accTypelbl;
	JLabel ratelbl;

	JScrollPane jsp;

	private Client client;
	ClientFrame2 cf;
	private JTextField input;

	String id;
	private JLabel label_2;
	


	public ClientFrame2(String title) {
		System.out.println("ClientFrame2");
		cf = this;
		init();
		start();
		setBounds(100, 100, 600, 650);
		setTitle(title);
		setVisible(true);
	}

	public void start() {
		setDefaultCloseOperation(this.EXIT_ON_CLOSE);
	}

	private void init() {
		System.out.println("init");
		getContentPane().setLayout(null);

		deposit = new JButton("\uC785  \uAE08");
		deposit.setEnabled(false);
		deposit.setBounds(30, 225, 100, 40);
		getContentPane().add(deposit);
		deposit.addActionListener(new buttonHanlder());

		widthraw = new JButton("\uCD9C  \uAE08");
		widthraw.setEnabled(false);
		widthraw.setBounds(170, 225, 100, 40);
		getContentPane().add(widthraw);
		widthraw.addActionListener(new buttonHanlder());
		
		send = new JButton("\uACC4\uC88C\uC774\uCCB4");
		send.setEnabled(false);
		send.setBounds(310, 225, 100, 40);
		getContentPane().add(send);
		send.addActionListener(new buttonHanlder());
		
		
	    print = new JButton("\uAC70\uB798\uC870\uD68C");
	    print.setEnabled(false);
	    print.setBounds(450, 225, 100, 40);
		getContentPane().add(print);
		print.addActionListener(new buttonHanlder());

		chat = new JTextArea();
		jsp = new JScrollPane(chat);
		jsp.setBounds(20, 300, 550, 250);
		getContentPane().add(jsp);
		chat.setEditable(false);
		jsp.setViewportView(chat);
		
		input = new JTextField();
		input.addKeyListener(new MessageSendListener());
		input.setBounds(20, 575, 550, 25);
		getContentPane().add(input);
		input.setColumns(10);
		
		

		connect = new JButton("��������");
		connect.setBounds(40, 5, 100, 30);
		getContentPane().add(connect);
		
		JLabel label = new JLabel("\uC785\uB825\uCC3D");
		label.setBounds(10, 560, 57, 15);
		getContentPane().add(label);
		
		JLabel label_1 = new JLabel("\uCC44\uD305\uCC3D");
		label_1.setBounds(10, 280, 57, 15);
		getContentPane().add(label_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(20, 60, 550, 150);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		

		accNamelbl = new JLabel("�̸�"); // user name
		accNamelbl.setBounds(30, 30, 61, 20);
		 panel_1.add(accNamelbl);
		 
		
		accName = new JTextField();
		accName.setBounds(100, 30, 100, 20);
		 panel_1.add(accName);
		accName.setColumns(10);
		accName.setEditable(false);
		accName.setBackground(new Color(255,255,255));
		
		accIdlbl = new JLabel("���¹�ȣ"); // account id
		accIdlbl.setBounds(30, 75, 61, 20);
		 panel_1.add(accIdlbl);
		
		accId = new JTextField();
		accId.setBounds(100, 75, 100, 20);
		 panel_1.add(accId);
		accId.setColumns(10);
		accId.setEditable(false);
		accId.setBackground(new Color(255,255,255));
		
		accTypelbl = new JLabel("��������"); // account type
		accTypelbl.setBounds(30, 120, 61, 20);
		 panel_1.add(accTypelbl);
		
		accType = new JTextField();
		accType.setBounds(100, 120, 100, 20);
		 panel_1.add(accType);
		accType.setColumns(10);
		accType.setEditable(false);
		accType.setBackground(new Color(255,255,255));
		
		balancelbl = new JLabel("�ܾ�"); //balance
		balancelbl.setBounds(280, 30, 61, 16);
		 panel_1.add(balancelbl);
		
		balance = new JTextField();
		balance.setBounds(360, 30, 100, 20);
		 panel_1.add(balance);
		balance.setColumns(10);
		balance.setEditable(false);
		balance.setBackground(new Color(255,255,255));
		
		ratelbl = new JLabel("����"); // rate of interest
		ratelbl.setBounds(280, 75, 61, 16);
		 panel_1.add(ratelbl);
		
		rate = new JTextField();
		rate.setBounds(358, 75, 100, 20);
		 panel_1.add(rate);
		rate.setColumns(10);
		rate.setEditable(false);
		rate.setBackground(new Color(255,255,255));
		
		label_2 = new JLabel("\uACC4\uC88C\uC815\uBCF4");
		label_2.setFont(new Font("����", Font.PLAIN, 14));
		label_2.setBounds(10, 5, 57, 15);
		panel_1.add(label_2);
		
				login = new JButton("\uB85C\uADF8\uC778");
				login.setEnabled(false);
				login.setBounds(310, 5, 100, 30);
				getContentPane().add(login);
				login.setFont(new Font("����", Font.PLAIN, 12));
				
						create = new JButton("\uD68C\uC6D0\uAC00\uC785");
						create.setEnabled(false);
						create.setBounds(175, 5, 100, 30);
						getContentPane().add(create);
						
						close = new JButton("\uACC4\uC88C\uD574\uC9C0");
						close.setEnabled(false);
						close.setBounds(447, 6, 97, 29);
						getContentPane().add(close);
						close.addActionListener(new buttonHanlder());
						create.addActionListener(new buttonHanlder());
				login.addActionListener(new buttonHanlder());
		connect.addActionListener(new buttonHanlder());

		
	}
	

	public static void main(String[] args) {
		new ClientFrame2("��ŷ����");

	}

	class MessageSendListener extends KeyAdapter {
		// Ű������ Ư�� Ű ������ ��� ȣ��
		@Override
		public void keyPressed(KeyEvent e) {

			// ���� Ű�� ENTER�̰� ��޼��� �ƴϸ�
			if (e.getKeyCode() == KeyEvent.VK_ENTER && !(input.getText()).equals("")) {
				// '/msg'�� ���� -> �ӼӸ� �Ҷ�
				if (input.getText().startsWith("/msg")) {
					String text = input.getText().split("/msg ")[1].trim();
					Message msg = new Message("whisper", text, client.getClientId()); 

					chat.append("��� ���� ������ �޽���>> "+ text + "\n");
					client.sendMSG(msg);
					input.setText("");
					
				} else {
					// Ŭ���̾�Ʈ �Է� �޼����� ���� ����
					Message msg = new Message("chat", input.getText(), client.getClientId());
					client.sendMSG(msg);
					input.setText("");
				}
			}
		}
	}
	private class buttonHanlder implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("action");
			// ���������Ϸ��� �Ҷ� client ���Ӱ� ���ÿ� ��ư ��Ȱ��ȭ
			if (e.getActionCommand().equals(connect.getText())) {
				System.out.println("connect");
				client = new Client(cf);
				chat.append("<<��ڿ��� �ӼӸ� �ϴ¹�>>\t/msg ä�ó���\n");
				connect.setEnabled(false);

			}

//
//			���¹�ȣ�� ��й�ȣ�� Ŭ���̾�Ʈ�� ���� �Է¹޾�
//			���� �α��� �޽��� �۽�
			else if (e.getActionCommand().equals(login.getText())) {
				String order = "login";
				String id = JOptionPane.showInputDialog(cf, "���¹�ȣ",
				JOptionPane.QUESTION_MESSAGE);
				String password = JOptionPane.showInputDialog(cf, "��й�ȣ",
				JOptionPane.QUESTION_MESSAGE);
				//
				AccountMessage accMsg = new AccountMessage();
				accMsg.setOrder(order);
				accMsg.setId(id);
				accMsg.setPassword(password);
				client.sendMSG(accMsg);
			}
			// �� ���� ���� �޽��� �۽�
			else if (e.getActionCommand().equals(create.getText())) {
				System.out.println("create");
				String order = "create";
//				String type = JOptionPane.showInputDialog(cf, "1. �Ϲ� ���� , 2. ���� ����", JOptionPane.QUESTION_MESSAGE);
//				if (!type.equals("1") && !type.equals("2")) {
//					JOptionPane.showMessageDialog(null, "(1 ~ 2)�� ����");
//				} else {
//					String rate = "";
//					if (type.equals("2")) {
//						rate = JOptionPane.showInputDialog(cf, "����", JOptionPane.QUESTION_MESSAGE);
//					}
//					String id = JOptionPane.showInputDialog(cf, "���¹�ȣ", JOptionPane.QUESTION_MESSAGE);
//					String name = JOptionPane.showInputDialog(cf, "������", JOptionPane.QUESTION_MESSAGE);
//					String password = JOptionPane.showInputDialog(cf, "��й�ȣ", JOptionPane.QUESTION_MESSAGE);
				    new AccountFrame(client);
//				    System.out.println("test");
//				    System.out.println(id);
//					AccountMessage accMsg = new AccountMessage();
//					accMsg.setOrder(order);
//					accMsg.setId(id);
//					accMsg.setName(name);
//					accMsg.setPassword(password);
//					accMsg.setType(Integer.parseInt(type));
//					if (type.equals("2")) {
//						accMsg.setRate(Integer.parseInt(rate));
//					}

//					client.sendMSG(accMsg);
				}

//			}
			// �������� �Աݿ�û �޽��� �۽�
			else if (e.getActionCommand().equals(deposit.getText())) {
				String order = "deposit";
				String userId = client.getClientId();
				String value = JOptionPane.showInputDialog(cf, "�ݾ�", JOptionPane.QUESTION_MESSAGE);

				AccountMessage accMsg = new AccountMessage();
				accMsg.setOrder(order);
				accMsg.setUserId(userId);
				accMsg.setValue(value);
				client.sendMSG(accMsg);
			}
			// �������� ��ݿ�û �޽��� �۽�
			else if (e.getActionCommand().equals(widthraw.getText())) {
				String order = "withdraw";
				String userId = client.getClientId();
				String value = JOptionPane.showInputDialog(cf, "�ݾ�", JOptionPane.QUESTION_MESSAGE);

				AccountMessage accMsg = new AccountMessage();
				accMsg.setOrder(order);
				accMsg.setUserId(userId);
				accMsg.setValue(value);
				client.sendMSG(accMsg);
			}
			// �������� ������ü ��û �޽��� �۽�
			else if (e.getActionCommand().equals(send.getText())) {
				String order = "send";
				String userId = client.getClientId();
				String to = JOptionPane.showInputDialog(cf, "���� ���¹�ȣ", JOptionPane.QUESTION_MESSAGE);
				String value = JOptionPane.showInputDialog(cf, "�ݾ�", JOptionPane.QUESTION_MESSAGE);

				AccountMessage accMsg = new AccountMessage();
				accMsg.setOrder(order);
				accMsg.setTo(to);
				accMsg.setUserId(userId);
				accMsg.setValue(value);
				client.sendMSG(accMsg);
			}
			// �������� ������������û �۽�
			// else if (e.getActionCommand().equals(buttonName2[4])) {
			// String order = "info";
			// String userId = client.getClientId();
			//
			// AccountMessage accMsg = new AccountMessage();
			// accMsg.setOrder(order);
			// accMsg.setUserId(userId);
			// client.sendMSG(accMsg);
			// }
			// �������� ����������û �۽�
			else if (e.getActionCommand().equals(close.getText())) {
				String order = "remove";
				String userId = client.getClientId();

				AccountMessage accMsg = new AccountMessage();
				accMsg.setOrder(order);
				accMsg.setUserId(userId);
				client.sendMSG(accMsg);
			}

		}
	}
}
