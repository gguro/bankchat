package dialog;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import common.Account;
import message.Message;
import server.ServerMgr;

public class ServerFrame2 extends JFrame {

	private JPanel contentPane;
	private JTable table;
	DefaultTableModel model;
	Vector<String> tableIndex = new Vector<String>();

	private JTextField accName;
	private JTextField accId;
	private JTextField balance;
	private JTextField accType;
	private JTextField rate;

	JLabel accNamelbl;
	JLabel accIdlbl;
	JLabel balancelbl;
	JLabel accTypelbl;
	JLabel ratelbl;

	JTextField input;
	JTextArea chat;
	JScrollPane jsp;
	JScrollBar jsb;

	JButton btnNewButton;

	//// private ArrayList<Log> logs;
	//// private AccountMgr mgr;
	ServerFrame2 serverFrame2;
	private JTable transactionTable;
	ServerMgr serverMgr;

	public ServerFrame2(String title) {
		serverFrame2 = this;

		init();
		start();
		setBounds(100, 100, 811, 619);
		setTitle(title);
		setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public void init() {

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("���Ӹ���Ʈ"); // user list
		lblNewLabel.setBounds(17, 74, 92, 16);
		contentPane.add(lblNewLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(17, 96, 190, 213);
		contentPane.add(scrollPane);

		tableIndex.addElement("ID");
		tableIndex.addElement("Status");
		model = new DefaultTableModel(tableIndex, 0) {
			public boolean isCellEditable(int i, int c) {
				return false;
			}
		};

		table = new JTable(model);
		scrollPane.setViewportView(table);

		table.addMouseListener(new MouseEventListener());

		JLabel label = new JLabel("ȸ������"); // user info
		label.setBounds(229, 74, 61, 16);
		contentPane.add(label);

		accNamelbl = new JLabel("�̸�"); // user name
		accNamelbl.setBounds(229, 102, 61, 16);
		contentPane.add(accNamelbl);

		accName = new JTextField();
		accName.setBounds(282, 96, 130, 26);
		contentPane.add(accName);
		accName.setColumns(10);
		accName.setEditable(false);
		accName.setBackground(new Color(255, 255, 255));

		accIdlbl = new JLabel("���¹�ȣ"); // account id
		accIdlbl.setBounds(424, 102, 61, 16);
		contentPane.add(accIdlbl);

		accId = new JTextField();
		accId.setBounds(480, 96, 130, 26);
		contentPane.add(accId);
		accId.setColumns(10);
		accId.setEditable(false);
		accId.setBackground(new Color(255, 255, 255));

		accTypelbl = new JLabel("��������"); // account type
		accTypelbl.setBounds(229, 131, 61, 16);
		contentPane.add(accTypelbl);

		accType = new JTextField();
		accType.setBounds(282, 126, 130, 26);
		contentPane.add(accType);
		accType.setColumns(10);
		accType.setEditable(false);
		accType.setBackground(new Color(255, 255, 255));

		balancelbl = new JLabel("�ܾ�"); // balance
		balancelbl.setBounds(622, 102, 61, 16);
		contentPane.add(balancelbl);

		balance = new JTextField();
		balance.setBounds(661, 97, 130, 26);
		contentPane.add(balance);
		balance.setColumns(10);
		balance.setEditable(false);
		balance.setBackground(new Color(255, 255, 255));

		ratelbl = new JLabel("����"); // rate of interest
		ratelbl.setBounds(424, 130, 61, 16);
		contentPane.add(ratelbl);

		rate = new JTextField();
		rate.setBounds(480, 126, 130, 26);
		contentPane.add(rate);
		rate.setColumns(10);
		rate.setEditable(false);
		rate.setBackground(new Color(255, 255, 255));

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(229, 190, 562, 119);
		contentPane.add(scrollPane_1);

		// �ŷ�����
		transactionTable = new JTable();
		scrollPane_1.setViewportView(transactionTable);

		chat = new JTextArea();
		input = new JTextField();
		input.addKeyListener(new MessageSendListener());
		jsp = new JScrollPane();
		chat.setEditable(false);
		jsb = jsp.getVerticalScrollBar();
		jsp.setViewportView(chat);

		jsp.setBounds(17, 336, 774, 178);
		contentPane.add(jsp);

		JLabel label_6 = new JLabel("ä��"); // chat
		label_6.setBounds(17, 318, 61, 16);
		contentPane.add(label_6);

		input.setBounds(17, 549, 774, 26);
		contentPane.add(input);
		input.setColumns(10);

		JLabel label_7 = new JLabel("�޽��� �Է�"); // chat input text
		label_7.setBounds(17, 534, 116, 16);
		contentPane.add(label_7);

		JLabel label_8 = new JLabel("�ŷ�����"); // transaction list
		label_8.setBounds(229, 162, 61, 16);
		contentPane.add(label_8);

		JButton button = new JButton("�����ѱ�"); // server start
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				serverMgr = new ServerMgr(serverFrame2);
				// mgr = new AccountMgr(serverFrame2);
				// mgr.accountsLoad();
				addText("��������\n");
				chat.append("<<Ŭ���̾�Ʈ���� �ӼӸ� �ϴ� ���>>\t/msg ���̵� ä�ó���\n");

				serverMgr.startServer();
				btnNewButton.setEnabled(true);
				button.setEnabled(false);
			}
		});
		button.setBounds(16, 21, 117, 29);
		contentPane.add(button);

		btnNewButton = new JButton("��ü��������"); // all account info
		btnNewButton.setBounds(147, 21, 117, 29);
		contentPane.add(btnNewButton);

		// ��������
		// JButton getOutBtn = new JButton("\uAC15\uC81C\uD1F4\uC7A5");
		// getOutBtn.setBounds(694, 129, 97, 23);
		// contentPane.add(getOutBtn);
		// getOutBtn.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent e) {
		// System.out.println("����");
		//// mgr.getServer().getClientSocket().r
		// }
		// });
	}

	public void start() {
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				serverMgr.shutDown();
				System.exit(0);// cierra aplicacion
			}
		});

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

	//
	// public AccountMgr getMgr() {
	// return mgr;
	// }
	//
	// public ArrayList<Log> getLogs() {
	// return logs;
	// }

	public ServerFrame2 getServerFrame() {
		return serverFrame2;
	}

	public void addText(String str) {
		chat.append(str);
		jsb.setValue(jsb.getMaximum());
	}

	public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		System.out.println("Terminate program!!!");

		System.exit(0);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new ServerFrame2("����");
	}

	class MessageSendListener extends KeyAdapter {
		// Ű������ Ư�� Ű ������ ��� ȣ��
		@Override
		public void keyPressed(KeyEvent e) {

			// ���� Ű�� ENTER�̰� ��޼��� �ƴϸ�
			if (e.getKeyCode() == KeyEvent.VK_ENTER && !(input.getText()).equals("")) {
				// '/msg'�� ���� -> �ӼӸ� �Ҷ�
				if (input.getText().startsWith("/msg")) {
					
					
					
					// //�޴»��
					// String toId = input.getText().split("/msg")[1].trim();
					// //���� �ؽ�Ʈ
					// String text = "";
					// int index = 0;
					// for (int i = 0; i < toId.length(); i++) {
					// if (toId.charAt(i) == ' ') {
					// index = i;
					// break;
					// }
					// }
					// // �޴»���� �ؽ�Ʈ�� �������� Ŭ���̾�Ʈ���� �޽��� ����
					// text = toId.substring(index, toId.length());
					// toId = toId.substring(0, index);
					// Message msg = new Message("whisper", text, "���");
					// input.setText("");
					// //�޴� ����� ���̵� ��� Ŭ���̾�Ʈ�� ���� ã�´�
					// for (int i = 0; i <
					// mgr.getServer().getClientSocket().size(); i++) {
					// //ã�� Ŭ���̾�Ʈ���� �ٽ� ����
					// if(toId.equals(mgr.getServer().getClientSocket().get(i).getUserId())){
					// mgr.getServer().getClientSocket().get(i).sendPrivate(msg);
					// }
					// }
					
					// /msg 1111 �ȳ�.
					
					Message msg = new Message("whisper", input.getText(), "���");
					String userId = "1111";
					serverMgr.getServer().sendTragetUser("1111" , msg);
					input.setText("");
				} else {
					// Ŭ���̾�Ʈ �Է� �޼����� ������ ����
					Message msg = new Message("chat", input.getText(), "���");
					serverMgr.getServer().broadcastToAllUser(msg);
					input.setText("");
				}
			}
		}

	}

	// table click event listener
	private class MouseEventListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == 1) {
				int row = table.getSelectedRow();
				System.out.println("Table Click : " + row);
				System.out.println(model.getDataVector().get(row));
				// list value
				String id = model.getValueAt(row, 0).toString();
				String stat = model.getValueAt(row, 1).toString();

				Account acc = serverMgr.getAccount(id);// mgr.findbyId(id);
				accName.setText(acc.getName());
				accId.setText(acc.getAccountNo());
				balance.setText(((Integer) acc.getBalance()).toString());

			}
		}
	}

}
