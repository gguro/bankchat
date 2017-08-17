package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import message.AccountMessage;
import message.Message;
//Client ���α׷��� GUI�� ���
//Client ���α׷��� ���θ޼ҵ带 �����Ѵ�.
public class ClientFrame extends JFrame {
	JLabel topLabel1;
	JLabel topLabel2;
	JLabel midLabel;
	JLabel botLabel;

	JButton[] btn1;
	JButton[] btn2;

	JPanel topPanel;
	JPanel midPanel;
	JPanel botPanel;
	JPanel topBtnPanel1;
	JPanel topBtnPanel2;
	JPanel topSubPanel1;
	JPanel topSubPanel2;

	JTextArea chat;
	JTextField input;
	JScrollPane jsp;
	JScrollBar jsb;
	JProgressBar jpb;
	String[] buttonName1 = { "��������", "�������ۿ�û", "���·α���" };
	String[] buttonName2 = { "�����»���", "�Ա�", "���", "������ü", "����������", "��������" };
	private Client client;
	ClientFrame cf;

	public ClientFrame(String title) {
		cf = this;
		init();
		start();
		setBounds(100, 100, 800, 600);
		setTitle(title);
		setVisible(true);
	}

	public void init() {
		topLabel1 = new JLabel("��Ʈ��ũ���");
		topLabel2 = new JLabel("��ŷ����");
		midLabel = new JLabel("����ä��");
		botLabel = new JLabel("��ȭ�Է�");

		topPanel = new JPanel(new BorderLayout());
		midPanel = new JPanel();
		botPanel = new JPanel();
		topSubPanel1 = new JPanel();
		topSubPanel2 = new JPanel();
		topBtnPanel1 = new JPanel();
		topBtnPanel2 = new JPanel();

		jpb = new JProgressBar();
		chat = new JTextArea();
		input = new JTextField();
		input.addKeyListener(new MessageSendListener());
		jsp = new JScrollPane(chat);
		chat.setEditable(false);
		jsb = jsp.getVerticalScrollBar(); // ������ũ�ѹ�

		topSubPanel1.setLayout(new BorderLayout());
		topSubPanel1.add(topLabel1, BorderLayout.NORTH);
		topSubPanel2.setLayout(new BorderLayout());
		topSubPanel2.add(topLabel2, BorderLayout.NORTH);
		btn1 = new JButton[buttonName1.length];
		for (int i = 0; i < buttonName1.length; i++) {
			btn1[i] = new JButton(buttonName1[i]);
			topBtnPanel1.add(btn1[i]);
			if (i != 0)
				btn1[i].setEnabled(false);
			btn1[i].addActionListener(new buttonHanlder());
		}
		topSubPanel1.add(topBtnPanel1, BorderLayout.CENTER);

		btn2 = new JButton[buttonName2.length];
		for (int i = 0; i < buttonName2.length; i++) {
			btn2[i] = new JButton(buttonName2[i]);
			topBtnPanel2.add(btn2[i]);
			btn2[i].setEnabled(false);
			btn2[i].addActionListener(new buttonHanlder());
		}
		topSubPanel2.add(topBtnPanel2, BorderLayout.CENTER);

		topPanel.add(topSubPanel1, BorderLayout.NORTH);
		topPanel.add(topSubPanel2, BorderLayout.CENTER);

		midPanel.setLayout(new BorderLayout());
		midPanel.add(midLabel, BorderLayout.NORTH);
		midPanel.add(jsp, BorderLayout.CENTER);
		midPanel.add(jpb, BorderLayout.SOUTH);

		botPanel.setLayout(new BorderLayout());
		botPanel.add(botLabel, BorderLayout.NORTH);
		botPanel.add(input, BorderLayout.CENTER);

		add(topPanel, BorderLayout.NORTH);
		add(midPanel, BorderLayout.CENTER);
		add(botPanel, BorderLayout.SOUTH);
	}

	public void start() {
		setDefaultCloseOperation(this.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new ClientFrame("��ŷ����");
	}

	class MessageSendListener extends KeyAdapter {
		// Ű������ Ư�� Ű ������ ��� ȣ��
		@Override
		public void keyPressed(KeyEvent e) {

			// ���� Ű�� ENTER�̰� ��޼��� �ƴϸ�
			if (e.getKeyCode() == KeyEvent.VK_ENTER && !(input.getText()).equals("")) {
				// '/msg'�� ���� -> �ӼӸ� �Ҷ�
				if (input.getText().startsWith("/msg")) {
					// �޴� �ؽ�Ʈ
					String text = input.getText().split("/msg")[1].trim();
					// Ŭ���̾�Ʈ (�ڱ��ڽ�)�� ���̵�� �ؽ�Ʈ�� �ۼ��� �������� �޽��� ����
					Message msg = new Message("whisper", text, client.getClientId());
					input.setText("");
					client.sendMSG(msg);
				} else if (input.getText().startsWith("transaction")) {
					String text = "transaction";
					Message msg = new Message("transaction", text, client.getClientId());
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
			// ���������Ϸ��� �Ҷ� client ���Ӱ� ���ÿ� ��ư ��Ȱ��ȭ
			if (e.getActionCommand().equals(buttonName1[0])) {
				client = new Client(cf);
				chat.append("<<��ڿ��� �ӼӸ� �ϴ¹�>>\t/msg ä�ó���\n");
				btn1[0].setEnabled(false);
				btn1[2].setEnabled(true);
				btn2[0].setEnabled(true);
			}
			// ���� ���ۿ�û �޽���
			// ������ Ŭ���̾�Ʈ�� ������ �˾Ƴ��� ���� Ŭ���̾�Ʈ�� ���̵� ������
			else if (e.getActionCommand().equals(buttonName1[1])) {
				String order = "filetrans";
				String id = client.getClientId();

				AccountMessage accMsg = new AccountMessage();
				accMsg.setOrder(order);
				accMsg.setId(id);
				client.sendMSG(accMsg);
			}

			// ���¹�ȣ�� ��й�ȣ�� Ŭ���̾�Ʈ�� ���� �Է¹޾�
			// ���� �α��� �޽��� �۽�
			else if (e.getActionCommand().equals(buttonName1[2])) {
				String order = "login";
				String id = JOptionPane.showInputDialog(cf, "���¹�ȣ", JOptionPane.QUESTION_MESSAGE);
				String password = JOptionPane.showInputDialog(cf, "��й�ȣ", JOptionPane.QUESTION_MESSAGE);

				AccountMessage accMsg = new AccountMessage();
				accMsg.setOrder(order);
				accMsg.setId(id);
				accMsg.setPassword(password);
				client.sendMSG(accMsg);
			}
			// �� ���� ���� �޽��� �۽�
			else if (e.getActionCommand().equals(buttonName2[0])) {
				String order = "create";
				String type = JOptionPane.showInputDialog(cf, "1. �Ϲ� ���� , 2. ���� ����", JOptionPane.QUESTION_MESSAGE);
				if (!type.equals("1") && !type.equals("2")) {
					JOptionPane.showMessageDialog(null, "(1 ~ 2)�� ����");
				} else {
					String rate = "";
					if (type.equals("2")) {
						rate = JOptionPane.showInputDialog(cf, "����", JOptionPane.QUESTION_MESSAGE);
					}
					String id = JOptionPane.showInputDialog(cf, "���¹�ȣ", JOptionPane.QUESTION_MESSAGE);
					String name = JOptionPane.showInputDialog(cf, "������", JOptionPane.QUESTION_MESSAGE);
					String password = JOptionPane.showInputDialog(cf, "��й�ȣ", JOptionPane.QUESTION_MESSAGE);

					AccountMessage accMsg = new AccountMessage();
					accMsg.setOrder(order);
					accMsg.setId(id);
					accMsg.setName(name);
					accMsg.setPassword(password);
					accMsg.setType(Integer.parseInt(type));
					if (type.equals("2")) {
						accMsg.setRate(Integer.parseInt(rate));
					}
					client.sendMSG(accMsg);
				}

			}
			// �������� �Աݿ�û �޽��� �۽�
			else if (e.getActionCommand().equals(buttonName2[1])) {
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
			else if (e.getActionCommand().equals(buttonName2[2])) {
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
			else if (e.getActionCommand().equals(buttonName2[3])) {
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
			else if (e.getActionCommand().equals(buttonName2[4])) {
				String order = "info";
				String userId = client.getClientId();

				AccountMessage accMsg = new AccountMessage();
				accMsg.setOrder(order);
				accMsg.setUserId(userId);
				client.sendMSG(accMsg);
			}
			// �������� ����������û �۽�
			else if (e.getActionCommand().equals(buttonName2[5])) {
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
