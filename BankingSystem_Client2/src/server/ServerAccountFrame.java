package server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import common.Account;
import manager.ServerMgr;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class ServerAccountFrame extends JFrame {

	private JPanel contentPane;
	private JTextField searchAcc;

	private DefaultTableModel model;
	private Vector<String> tableIndex = new Vector<String>();
	private JLabel lblNewLabel_1;
	private JButton searchButton;
 
	private JTextField accName;
	private JTextField accId;
	private JTextField balance;
	private JTextField accType;
	private JTextField rate;
 
	private JLabel accNamelbl;
	private JLabel accIdlbl;
	private JLabel balancelbl;
	private JLabel accTypelbl;
	private JLabel ratelbl;
	
	ServerMgr serverMgr;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ServerAccountFrame frame = new ServerAccountFrame();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public ServerAccountFrame(ServerMgr serverMgr) {
		this.serverMgr = serverMgr;
		setTitle("계좌검색");
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 636, 220);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\uACC4\uC88C\uBC88\uD638");
		lblNewLabel.setBounds(12, 26, 57, 15);
		contentPane.add(lblNewLabel);
		
		// 검색텍스트
		searchAcc = new JTextField();
		searchAcc.setBounds(81, 23, 130, 21);
		contentPane.add(searchAcc);
		searchAcc.setColumns(10);
		
		// 검색버튼
		searchButton = new JButton("\uAC80\uC0C9");
		searchButton.setBounds(250, 22, 97, 23);
		contentPane.add(searchButton);
		searchButton.addActionListener(new buttonHanlder());

		lblNewLabel_1 = new JLabel("\uACC4\uC88C\uC815\uBCF4");
		lblNewLabel_1.setBounds(12, 76, 57, 15);
		contentPane.add(lblNewLabel_1);

		accNamelbl = new JLabel("이름"); // user name
		accNamelbl.setBounds(29, 102, 61, 16);
		contentPane.add(accNamelbl);

		accName = new JTextField();
		accName.setBounds(82, 96, 130, 26);
		contentPane.add(accName);
		accName.setColumns(10);
		accName.setEditable(false);
		accName.setBackground(new Color(255, 255, 255));

		accIdlbl = new JLabel("계좌번호"); // account id
		accIdlbl.setBounds(224, 102, 61, 16);
		contentPane.add(accIdlbl);

		accId = new JTextField();
		accId.setBounds(280, 96, 130, 26);
		contentPane.add(accId);
		accId.setColumns(10);
		accId.setEditable(false);
		accId.setBackground(new Color(255, 255, 255));

		accTypelbl = new JLabel("계좌종류"); // account type
		accTypelbl.setBounds(29, 131, 61, 16);
		contentPane.add(accTypelbl);

		accType = new JTextField();
		accType.setBounds(82, 126, 130, 26);
		contentPane.add(accType);
		accType.setColumns(10);
		accType.setEditable(false);
		accType.setBackground(new Color(255, 255, 255));

		balancelbl = new JLabel("잔액"); // balance
		balancelbl.setBounds(422, 102, 61, 16);
		contentPane.add(balancelbl);

		balance = new JTextField();
		balance.setBounds(461, 97, 130, 26);
		contentPane.add(balance);
		balance.setColumns(10);
		balance.setEditable(false);
		balance.setBackground(new Color(255, 255, 255));

		ratelbl = new JLabel("이율"); // rate of interest
		ratelbl.setBounds(224, 130, 61, 16);
		contentPane.add(ratelbl);

		rate = new JTextField();
		rate.setBounds(280, 126, 130, 26);
		contentPane.add(rate);
		rate.setColumns(10);
		rate.setEditable(false);
		rate.setBackground(new Color(255, 255, 255));

	}

	// 버튼이 눌렸을때 동작 정의
	private class buttonHanlder implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals(searchButton.getText())) {
				String accid = searchAcc.getText();
				Account account = serverMgr.getAccount(accid);
				
				if (account == null) {
					JOptionPane.showMessageDialog(null, "계좌 정보가 없습니다.");
				}
				else {
					accName.setText(account.getName());
					accId.setText(account.getAccountNo());
	
					String type;
					if (account.getAccountType() == 1) {
						type = "일반";
					} else if (account.getAccountType() == 2) {
						type = "보통";
					} else {
						type = "";
					}
					
					accType.setText(type);
					
					balance.setText(((Integer)account.getBalance()).toString());
					rate.setText(((Integer)account.getInterestRate()).toString());
				}
			}
		}
	}
}
