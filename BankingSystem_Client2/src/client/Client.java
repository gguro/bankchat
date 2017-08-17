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

//서버와의 연결을 담당하는 클라이언트, 소켓과 스트림을 갖는다.
public class Client {
	private Socket clientSocket; // 클라이언트
	private ObjectInputStream ois; // object를 받는 변수
	private ObjectOutputStream oos; // object를 보내는 변수
	private ClientFrame2 frame; // 클라이언트 프레임을 받음
	private String id; // 각 클라이언트 ID

	public Client(ClientFrame2 frame) {
		this.frame = frame;
		try {
			// 통신소켓(전화기) 입출력 문자스트림(전화선) 생성
			clientSocket = new Socket("127.0.0.1", 7777);
			// Message 클래스로 통신을 주고 받기 때문에 Object스트림 생성
			oos = new ObjectOutputStream(clientSocket.getOutputStream());
			ois = new ObjectInputStream(clientSocket.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 통신스레드 시작(클라이언트 프로그램이 동작하는 동안 새로운 스레드가 통신을 담당함)
		new ToServer().start();
	}

	// 서버로 보내는 메시지
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

	// Server와 통신을 하는 스레드
	private class ToServer extends Thread {
		private int max;

		public void run() {
			// 접속을 알리는 메시지 생성
			Message msg;
			msg = new Message("login", "guest", "");
			// 서버에 접속을 알림
			sendMSG(msg);
			// 서버에서 처리 후 다시 각 클라이언트에게 보낸 것을 받음
			try {
				System.out.println("run");
				// 서버에서 온메시지를 열어 ID에 저장
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
					// 서버의 메시지를 계속 받는다.
					while ((msg = (Message) ois.readObject()) != null) {
						// 서버에서 보낸 메시지가 채팅 또는 입장일때
						// 클라이언트 화면에 뿌려준다
						if (msg.getOrder().equals("chat") || msg.getOrder().equals("join")) {
							if (msg.getOrder().equals("chat"))
								frame.chat.append("[" + msg.getUserId() + "] : " + msg.getValue() + "\n");
							else
								frame.chat.append(msg.getUserId() + " " + msg.getValue() + "\n");
						} else if (msg.getOrder().equals("whisper")) {
							
							if (msg.getOrder().equals("whisper")) {
								frame.chat.append("[귓속말(" + msg.getUserId() +")] : "+ msg.getValue() + "\n");

							
							}
						}
						// 서버에서 보낸 메시지가 에러일때 화면에 뿌려줌
						else if (msg.getOrder().equals("error")) {
							JOptionPane.showMessageDialog(null, msg.getValue());
							// 정보일때
						} else if (msg.getOrder().equals("info")) {
							JOptionPane.showMessageDialog(null, msg.getValue());
							// 강퇴일때 프로그램 종료
						} else if (msg.getOrder().equals("ban")) {
							JOptionPane.showMessageDialog(null, "강퇴되었습니다. 프로그램을 종료합니다.");
							System.exit(0);
							// 파일전송일때 새로운 쓰레드 생성
						} else if (msg.getOrder().equals("filetrans")) {
							
							Thread fileRe = new Thread() {
								@Override
								public void run() {
									// 새로운 소켓을 열어 서버메시지와 구분하게 만듬
									// 각 클라이언트마다 통신소켓이 겹치지 않게 자신의 아이디값을 더한 포트번호
									// 생성
									try {
										Socket sock = new Socket("127.0.0.1", 7777 + Integer.parseInt(id));
										DataInputStream in = new DataInputStream(sock.getInputStream());// 개방
										DataOutputStream out = new DataOutputStream(sock.getOutputStream());
										File file = new File(getClientId() + " 로그파일.txt");
										FileOutputStream fot = new FileOutputStream(file);
										try {
											int fdata = -1;
											//받으려는 파일의 크기를 받아옴
											int max = in.readInt();
											
											
										
											// 파일 끝까지 읽어오기
//											while ((fdata = in.read()) != -1) {
//												fot.write(fdata);
//												fot.flush();
//												//다운로드 상태바에 현재 상태 표시
//												frame.jpb.setValue((int) (file.length() * 100 / max));
//											}
//											// 파일이 완료 됐다면 화면에 출력
//											JOptionPane.showMessageDialog(frame, "파일받아오기 성공");
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
							// 채팅, 파일전송, 강퇴등이 아닌 다른 상황일때
							// 즉 계좌에 관련된 메시지
						} else {
							// 로그인일때, 버튼 활성화 및 비활성화
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
								frame.connect.setEnabled(false);	// 계좌해지일때, 버튼 활성화 및 비활성화
								
								frame.accName.setText(accMsg.getName());
								frame.accId.setText(accMsg.getId());
								String type = "";
								if(accMsg.getType()==1) {
									type="일반계좌";								}
								else if(accMsg.getType()==2) {
									type="보통계좌";
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
							
							
							JOptionPane.showMessageDialog(frame, msg.getOrder() + "성공");
						}

					}
				} catch (Exception e) { // 통신예외 스레드 종료
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
