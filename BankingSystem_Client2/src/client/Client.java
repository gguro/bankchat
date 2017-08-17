package client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import message.AccountMessage;
import message.Message;

//�������� ������ ����ϴ� Ŭ���̾�Ʈ, ���ϰ� ��Ʈ���� ���´�.
public class Client {
	private Socket clientSocket; // Ŭ���̾�Ʈ
	private ObjectInputStream ois; // object�� �޴� ����
	private ObjectOutputStream oos; // object�� ������ ����
	private ClientFrame2 frame; // Ŭ���̾�Ʈ �������� ����
	private String id; // �� Ŭ���̾�Ʈ ID

	public Client(ClientFrame2 frame) {
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
				System.out.println("run");
				// �������� �¸޽����� ���� ID�� ����
				msg = (Message) ois.readObject();
				System.out.println(msg);
				id = msg.getUserId();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			frame.create.setEnabled(true);
			frame.login.setEnabled(true);
			frame.close.setEnabled(false);
			frame.deposit.setEnabled(false);
			frame.widthraw.setEnabled(false);
			frame.send.setEnabled(false);
			frame.print.setEnabled(false);
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
							
							if (msg.getOrder().equals("whisper")) {
								frame.chat.append("[�ӼӸ�(" + msg.getUserId() +")] : "+ msg.getValue() + "\n");

							
							}
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
//											while ((fdata = in.read()) != -1) {
//												fot.write(fdata);
//												fot.flush();
//												//�ٿ�ε� ���¹ٿ� ���� ���� ǥ��
//												frame.jpb.setValue((int) (file.length() * 100 / max));
//											}
//											// ������ �Ϸ� �ƴٸ� ȭ�鿡 ���
//											JOptionPane.showMessageDialog(frame, "���Ϲ޾ƿ��� ����");
//											frame.jpb.setValue(0);
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
								
					            frame.create.setEnabled(true);
								frame.login.setEnabled(false);
								frame.close.setEnabled(true);
								frame.deposit.setEnabled(true);
								frame.widthraw.setEnabled(true);
								frame.send.setEnabled(true);
								frame.print.setEnabled(true);
								frame.connect.setEnabled(false);	// ���������϶�, ��ư Ȱ��ȭ �� ��Ȱ��ȭ
								
								frame.accName.setText(accMsg.getName());
								frame.accId.setText(accMsg.getId());
								String type = "";
								if(accMsg.getType()==1) {
									type="�Ϲݰ���";								}
								else if(accMsg.getType()==2) {
									type="�������";
								}
								frame.accType.setText(type);
								
								frame.balance.setText(((Integer)accMsg.getBalance()).toString());
								frame.rate.setText(((Integer)accMsg.getRate()).toString());
								
								
							} else if (msg.getOrder().equals("remove")) {
								
								 frame.create.setEnabled(true);
								 frame.login.setEnabled(true);
								 frame.close.setEnabled(false);
								 frame.deposit.setEnabled(false);
								 frame.widthraw.setEnabled(false);
								 frame.send.setEnabled(false);
								 frame.print.setEnabled(false);
							
							}
							else if (msg.getOrder().equals("deposit") 
									||(msg.getOrder().equals("withdraw"))
									||(msg.getOrder().equals("send"))) {
								AccountMessage accMsg = (AccountMessage) msg;
								frame.balance.setText(""+accMsg.getBalance());
							
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
