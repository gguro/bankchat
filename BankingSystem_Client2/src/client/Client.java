package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import javax.swing.JOptionPane;

import common.Transaction;
import message.AccountMessage;
import message.Message;
import message.TransactionMessage;
//�������� ������ ����ϴ� Ŭ���̾�Ʈ, ���ϰ� ��Ʈ���� ���´�.
public class Client {
	private Socket clientSocket; // Ŭ���̾�Ʈ
	private ObjectInputStream ois; // object�� �޴� ����
	private ObjectOutputStream oos; // object�� ������ ����
	private ClientFrame frame; // Ŭ���̾�Ʈ �������� ����
	private String id; // �� Ŭ���̾�Ʈ ID

	public Client(ClientFrame frame) {
		this.frame = frame;
		try {
			// ��ż���(��ȭ��) ����� ���ڽ�Ʈ��(��ȭ��) ����
			clientSocket = new Socket("127.0.0.1", 7777);
			// Message Ŭ������ ����� �ְ� �ޱ� ������ Object��Ʈ�� ����
			oos = new ObjectOutputStream(clientSocket.getOutputStream());
			ois = new ObjectInputStream(clientSocket.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ��Ž����� ����(Ŭ���̾�Ʈ ���α׷��� �����ϴ� ���� ���ο� �����尡 ����� �����)
		new ToServer().start();
	}

	// ������ ������ �޽���
	public void sendMSG(Message msg) {
		try {
			oos.writeObject(msg);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getClientId() {
		return id;
	}

	// Server�� ����� �ϴ� ������
	private class ToServer extends Thread {
		private int max;

		public void run() {
			// ������ �˸��� �޽��� ����
			Message msg;
			msg = new Message("login", "guest", "");
			// ������ ������ �˸�
			sendMSG(msg);
			// �������� ó�� �� �ٽ� �� Ŭ���̾�Ʈ���� ���� ���� ����
			try {
				// �������� �¸޽����� ���� ID�� ����
				msg = (Message) ois.readObject();
				id = msg.getUserId();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			while (true) {
				try {
					// ������ �޽����� ��� �޴´�.
					while ((msg = (Message) ois.readObject()) != null) {
						// �������� ���� �޽����� ä�� �Ǵ� �����϶�
						// Ŭ���̾�Ʈ ȭ�鿡 �ѷ��ش�
						if (msg.getOrder().equals("chat") || msg.getOrder().equals("join")) {
							if (msg.getOrder().equals("chat"))
								frame.chat.append("[" + msg.getUserId() + "] : " + msg.getValue() + "\n");
							else
								frame.chat.append(msg.getUserId() + " " + msg.getValue() + "\n");
						} else if (msg.getOrder().equals("whisper")) {
							String str = JOptionPane.showInputDialog(null, msg.getValue(), msg.getUserId(),
									JOptionPane.INFORMATION_MESSAGE);
							if (str != null && str.length() > 0) {
								msg = new Message("whisper", str, getClientId());
								sendMSG(msg);
							}
						}
						else if (msg.getOrder().equals("transaction")) {
							JOptionPane.showMessageDialog(null, "�ŷ�����");
							TransactionMessage tMsg = (TransactionMessage) msg;
							
							int totalCount = tMsg.getTotalCount();
							List<Transaction> list = tMsg.getList();
							
							System.out.println("Count = " + totalCount);
							System.out.println("list.size() = " + list.size());
							/////////////////////
						}
						// �������� ���� �޽����� �����϶� ȭ�鿡 �ѷ���
						else if (msg.getOrder().equals("error")) {
							JOptionPane.showMessageDialog(null, msg.getValue());
							// �����϶�
						} else if (msg.getOrder().equals("info")) {
							JOptionPane.showMessageDialog(null, msg.getValue());
							// �����϶� ���α׷� ����
						} else if (msg.getOrder().equals("ban")) {
							JOptionPane.showMessageDialog(null, "����Ǿ����ϴ�. ���α׷��� �����մϴ�.");
							System.exit(0);
							// ���������϶� ���ο� ������ ����
						} else if (msg.getOrder().equals("filetrans")) {
							
							Thread fileRe = new Thread() {
								@Override
								public void run() {
									// ���ο� ������ ���� �����޽����� �����ϰ� ����
									// �� Ŭ���̾�Ʈ���� ��ż����� ��ġ�� �ʰ� �ڽ��� ���̵��� ���� ��Ʈ��ȣ
									// ����
									try {
										Socket sock = new Socket("127.0.0.1", 7777 + Integer.parseInt(id));
										DataInputStream in = new DataInputStream(sock.getInputStream());// ����
										DataOutputStream out = new DataOutputStream(sock.getOutputStream());
										File file = new File(getClientId() + " �α�����.txt");
										FileOutputStream fot = new FileOutputStream(file);
										try {
											int fdata = -1;
											//�������� ������ ũ�⸦ �޾ƿ�
											int max = in.readInt();
											// ���� ������ �о����
											while ((fdata = in.read()) != -1) {
												fot.write(fdata);
												fot.flush();
												//�ٿ�ε� ���¹ٿ� ���� ���� ǥ��
												frame.jpb.setValue((int) (file.length() * 100 / max));
											}
											// ������ �Ϸ� �ƴٸ� ȭ�鿡 ���
											JOptionPane.showMessageDialog(frame, "���Ϲ޾ƿ��� ����");
											frame.jpb.setValue(0);
										} catch (Exception e) {
											e.printStackTrace();
										} finally {
											try {
												sock.close();
												fot.close();
											} catch (Exception e) {
												e.printStackTrace();
											}
										}

									} catch (Exception e) {
										e.printStackTrace();
									}

								}
							};
							fileRe.start();
							// ä��, ��������, ������� �ƴ� �ٸ� ��Ȳ�϶�
							// �� ���¿� ���õ� �޽���
						} else {
							// �α����϶�, ��ư Ȱ��ȭ �� ��Ȱ��ȭ
							if (msg.getOrder().equals("login")) {
								AccountMessage accMsg = (AccountMessage) msg;
								id = accMsg.getId();
								for (int i = 0; i < frame.btn2.length; i++) {
									frame.btn2[i].setEnabled(true);
								}
								frame.btn1[1].setEnabled(true);
								frame.btn1[2].setEnabled(false);
								// ���������϶�, ��ư Ȱ��ȭ �� ��Ȱ��ȭ
							} else if (msg.getOrder().equals("close")) {
								for (int i = 0; i < frame.btn2.length; i++) {
									if (i != 0)
										frame.btn2[i].setEnabled(false);
								}
								frame.btn1[1].setEnabled(false);
								frame.btn1[2].setEnabled(true);
							}
							JOptionPane.showMessageDialog(frame, msg.getOrder() + "����");
						}

					}
				} catch (Exception e) { // ��ſ��� ������ ����
					e.printStackTrace();
					break;
				} finally {
					try {
						ois.close();
						oos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
