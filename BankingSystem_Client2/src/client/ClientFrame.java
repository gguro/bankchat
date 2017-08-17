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
//Client 프로그램의 GUI를 담당
//Client 프로그램의 메인메소드를 포함한다.
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
	String[] buttonName1 = { "서버접속", "파일전송요청", "계좌로그인" };
	String[] buttonName2 = { "새계좌생성", "입금", "출금", "계좌이체", "내계좌정보", "계좌해지" };
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
		topLabel1 = new JLabel("네트워크기능");
		topLabel2 = new JLabel("뱅킹서비스");
		midLabel = new JLabel("공개채팅");
		botLabel = new JLabel("대화입력");

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
		jsb = jsp.getVerticalScrollBar(); // 수직스크롤바

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
		new ClientFrame("뱅킹서비스");
	}

	class MessageSendListener extends KeyAdapter {
		// 키보드의 특정 키 누르는 경우 호출
		@Override
		public void keyPressed(KeyEvent e) {

			// 누른 키가 ENTER이고 빈메세지 아니면
			if (e.getKeyCode() == KeyEvent.VK_ENTER && !(input.getText()).equals("")) {
				// '/msg'로 시작 -> 귓속말 할때
				if (input.getText().startsWith("/msg")) {
					// 받는 텍스트
					String text = input.getText().split("/msg")[1].trim();
					// 클라이언트 (자기자신)의 아이디와 텍스트를 작성해 서버에게 메시지 전송
					Message msg = new Message("whisper", text, client.getClientId());
					input.setText("");
					client.sendMSG(msg);
				} else if (input.getText().startsWith("transaction")) {
					String text = "transaction";
					Message msg = new Message("transaction", text, client.getClientId());
					client.sendMSG(msg);
					input.setText("");
				} else {
					// 클라이언트 입력 메세지를 서버 전송
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
			// 서버접속하려고 할때 client 접속과 동시에 버튼 비활성화
			if (e.getActionCommand().equals(buttonName1[0])) {
				client = new Client(cf);
				chat.append("<<운영자에게 귓속말 하는법>>\t/msg 채팅내용\n");
				btn1[0].setEnabled(false);
				btn1[2].setEnabled(true);
				btn2[0].setEnabled(true);
			}
			// 파일 전송요청 메시지
			// 서버가 클라이언트가 누군지 알아내기 쉽게 클라이언트의 아이디를 보내줌
			else if (e.getActionCommand().equals(buttonName1[1])) {
				String order = "filetrans";
				String id = client.getClientId();

				AccountMessage accMsg = new AccountMessage();
				accMsg.setOrder(order);
				accMsg.setId(id);
				client.sendMSG(accMsg);
			}

			// 계좌번호와 비밀번호를 클라이언트로 부터 입력받아
			// 계좌 로그인 메시지 송신
			else if (e.getActionCommand().equals(buttonName1[2])) {
				String order = "login";
				String id = JOptionPane.showInputDialog(cf, "계좌번호", JOptionPane.QUESTION_MESSAGE);
				String password = JOptionPane.showInputDialog(cf, "비밀번호", JOptionPane.QUESTION_MESSAGE);

				AccountMessage accMsg = new AccountMessage();
				accMsg.setOrder(order);
				accMsg.setId(id);
				accMsg.setPassword(password);
				client.sendMSG(accMsg);
			}
			// 새 계좌 생성 메시지 송신
			else if (e.getActionCommand().equals(buttonName2[0])) {
				String order = "create";
				String type = JOptionPane.showInputDialog(cf, "1. 일반 계좌 , 2. 보통 계좌", JOptionPane.QUESTION_MESSAGE);
				if (!type.equals("1") && !type.equals("2")) {
					JOptionPane.showMessageDialog(null, "(1 ~ 2)중 선택");
				} else {
					String rate = "";
					if (type.equals("2")) {
						rate = JOptionPane.showInputDialog(cf, "이율", JOptionPane.QUESTION_MESSAGE);
					}
					String id = JOptionPane.showInputDialog(cf, "계좌번호", JOptionPane.QUESTION_MESSAGE);
					String name = JOptionPane.showInputDialog(cf, "예금주", JOptionPane.QUESTION_MESSAGE);
					String password = JOptionPane.showInputDialog(cf, "비밀번호", JOptionPane.QUESTION_MESSAGE);

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
			// 서버에게 입금요청 메시지 송신
			else if (e.getActionCommand().equals(buttonName2[1])) {
				String order = "deposit";
				String userId = client.getClientId();
				String value = JOptionPane.showInputDialog(cf, "금액", JOptionPane.QUESTION_MESSAGE);

				AccountMessage accMsg = new AccountMessage();
				accMsg.setOrder(order);
				accMsg.setUserId(userId);
				accMsg.setValue(value);
				client.sendMSG(accMsg);
			}
			// 서버에게 출금요청 메시지 송신
			else if (e.getActionCommand().equals(buttonName2[2])) {
				String order = "withdraw";
				String userId = client.getClientId();
				String value = JOptionPane.showInputDialog(cf, "금액", JOptionPane.QUESTION_MESSAGE);

				AccountMessage accMsg = new AccountMessage();
				accMsg.setOrder(order);
				accMsg.setUserId(userId);
				accMsg.setValue(value);
				client.sendMSG(accMsg);
			}
			// 서버에게 계좌이체 요청 메시지 송신
			else if (e.getActionCommand().equals(buttonName2[3])) {
				String order = "send";
				String userId = client.getClientId();
				String to = JOptionPane.showInputDialog(cf, "보낼 계좌번호", JOptionPane.QUESTION_MESSAGE);
				String value = JOptionPane.showInputDialog(cf, "금액", JOptionPane.QUESTION_MESSAGE);

				AccountMessage accMsg = new AccountMessage();
				accMsg.setOrder(order);
				accMsg.setTo(to);
				accMsg.setUserId(userId);
				accMsg.setValue(value);
				client.sendMSG(accMsg);
			}
			// 서버에게 내계좌정보요청 송신
			else if (e.getActionCommand().equals(buttonName2[4])) {
				String order = "info";
				String userId = client.getClientId();

				AccountMessage accMsg = new AccountMessage();
				accMsg.setOrder(order);
				accMsg.setUserId(userId);
				client.sendMSG(accMsg);
			}
			// 서버에게 계좌해지요청 송신
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
