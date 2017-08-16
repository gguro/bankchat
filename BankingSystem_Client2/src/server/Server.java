package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;

import core.common.Account;
import core.common.Logger;
import core.message.AccountMessage;
import core.message.Message;
import exception.BMSException;

public class Server extends Thread {
	private Vector<ToClient> clientList = null;
	private ServerSocket serverSocket;
	final private int portNumber = 7777;
	private ServerMgr serverMgr;
	private int userCount = 0;
	Logger logger;
	
	public Server(ServerMgr mgr) {
		serverMgr = mgr;
		logger = Logger.getInstance();
		clientList = new Vector<ToClient>();
		initServerSocket();
	}
	
	public Vector<ToClient> getClientList() {
		return clientList;
	}
	
	public void run() {
		startServer();
	}
	
	private void initServerSocket() {
		try {
			serverSocket = new ServerSocket(portNumber);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}
	
	public void startServer() {
		Socket newClient = null;
		while(true) {
			try {
				newClient = serverSocket.accept();
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
			
			ToClient toClient = new ToClient(newClient);
			toClient.start();
			clientList.add(toClient);
		}
	}
	
	public void broadcastToAllUser(Message msg) {
		
		Iterator<ToClient> iterator = clientList.iterator();
		while (iterator.hasNext()) {
			ToClient temp = iterator.next();
			try {
				temp.oos.writeObject(msg);
				temp.oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		logger.log(msg.toString());
		
//		for(ToClient client : clientList) {
//			try {
//				client.oos.writeObject(msg);
//				client.oos.flush();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
		
		// ÀÔÀåÀÏ¶§ --´ÔÀÌ ÀÔÀåÇÏ¼Ì½À´Ï´Ù.
		if(msg.getOrder().equals("join")) {
			serverMgr.getFrame().addText(msg.getUserId() + msg.getValue() + "\n");
		} else {
			serverMgr.getFrame().addText("[" + msg.getUserId() + "] : " + msg.getValue() + "\n");
		}
	}
	
	public void sendTragetUser(String targetUserId, Message msg) {
		Iterator<ToClient> iterator = clientList.iterator();
		while (iterator.hasNext()) {
			ToClient temp = iterator.next();
			if(temp.userId.equals(targetUserId)) {
				try {
					temp.oos.writeObject(msg);
					temp.oos.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}
	}
	
	public class ToClient extends Thread{
		private Socket client;
		private String userId;
		ObjectOutputStream oos;
		ObjectInputStream ois;
		
		public ToClient(Socket s) {
			client = s;
		}
		
		public String getUserId() {
			return userId;
		}
		
		public void sendPrivate(Message msg) {
			try {
				oos.writeObject(msg);
				oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void sendFailMsg(Message msg, String message) {
			try {
				oos.writeObject(msg);
				oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			logger.log("ERR : " + msg.toString() + ", " + message);
		}
		
		
		public void sendSuccessMsg(Message msg) {
			String order = msg.getOrder();
			
			try {
				oos.writeObject(msg);
				oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if("chat".equals(order) || "join".equals(order) || "whisper".equals(order)) {
				logger.log(msg.toString() + " ¼º°ø");
			} else {
				logger.log(((AccountMessage)msg).toString() + " ¼º°ø");
			}
		}
		
		
		public void run() {
			Message msg = new Message();
			try {
				oos = new ObjectOutputStream(client.getOutputStream());
				ois = new ObjectInputStream(client.getInputStream());
				
				while((msg = (Message) ois.readObject()) != null ) {
					String order = msg.getOrder();
					
					//if(order.equals("join")) {
					//	processJoinMessage(msg);
					//}
					if(order.equals("login")) {
						processLoginMessage(msg);
					}
					else if (order.equals("chat")) {
						processChatMessage(msg);
					}
					else if (order.equals("whisper")) {
						processWhisperMessage(msg);
					}
					else if (order.equals("create")) {
						processCreateMessage(msg);
					}
					else if (order.equals("deposit")) {
						processDepositMessage(msg);
					}
					else if (order.equals("withdraw")) {
						processWithdrawMessage(msg);
					}
					else if (order.equals("send")) {
						processTransferMessage(msg);
					}
					else if (order.equals("remove")) {
						processRemoveMessage(msg);
					}
					else {
						System.out.println("WRN : Invalid order. msg = " + msg);
					}
 				}
					
			} catch (Exception e) {
				clientList.remove(this);
				msg = new Message("join", "´Ô ÅðÀå", userId);
				broadcastToAllUser(msg);
						
			} finally {
				try {
					ois.close();
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		private void processJoinMessage(Message msg) {
			userId = msg.getValue();
			if(userId.equals("")) {
				userCount++;
				userId = "¼Õ´Ô" + userCount;
			}
			
			msg.setMessage("join", "´Ô ÀÔÀå", userId);
			
			sendSuccessMsg(msg);
			broadcastToAllUser(msg);
			
		}

		private void processLoginMessage(Message msg) {
			userId = msg.getValue();
			if("guest".equals(userId)) {
				userCount++;
				userId = "¼Õ´Ô" + userCount;
			
				msg.setMessage("join", "´Ô ÀÔÀå", userId);
				
				broadcastToAllUser(msg);
				sendSuccessMsg(msg);
			} else {
				AccountMessage accMsg = (AccountMessage) msg;
				boolean result = false;
				
				try {
					result = serverMgr.login(accMsg.getId(), accMsg.getPassword());
					if(result == true) {
						sendSuccessMsg(msg);
					} else {
						sendFailMsg(msg, "·Î±×ÀÎ ½ÇÆÐ");
					}
				} catch (BMSException e) {
					// TODO Auto-generated catch block
					sendFailMsg(msg, e.getMessage());
				}
			}
		}
		
		private void processChatMessage(Message msg) {
			msg.setMessage("chat", msg.getValue(), msg.getUserId());
			broadcastToAllUser(msg);
			sendSuccessMsg(msg);
		}
		private void processWhisperMessage(Message msg) {
			msg.setMessage("whisper", msg.getValue(), msg.getUserId());
			sendTragetUser(msg.getUserId(), msg);
			System.out.println("±Ó¼Ó¸» Ã³¸® Ãß°¡");
		}
		private void processCreateMessage(Message msg) {
			AccountMessage accMsg = (AccountMessage) msg;
			String accountNo = accMsg.getId();
			String password = accMsg.getPassword();
			String name = accMsg.getName();
			int accountType = accMsg.getType();
			int interestRate = accMsg.getRate();
			
			Account account = new Account(accountNo, password, name, accountType, interestRate);
			boolean result;
			try {
				result = serverMgr.create(account);
				if(result == true) {
					sendSuccessMsg(accMsg);
				} else {
					sendFailMsg(accMsg, "°èÁÂ »ý¼º ½ÇÆÐ");
				}
					
			} catch (BMSException e1) {
				// TODO Auto-generated catch block
				sendFailMsg(accMsg, e1.getMessage());
			} 
		}
		private void processDepositMessage(Message msg) {
			AccountMessage accMsg = (AccountMessage) msg;
			int amount = Integer.parseInt(msg.getValue());
			boolean result;
			try {
				result = serverMgr.deposit(accMsg.getId(), amount);
				if(result == true) {
					sendSuccessMsg(accMsg);
				} else {
					sendFailMsg(accMsg, "ÀÔ±Ý ½ÇÆÐ");
				}
			} catch (BMSException e) {
				// TODO Auto-generated catch block
				sendFailMsg(accMsg, e.getMessage());
			}
			
		}
		private void processWithdrawMessage(Message msg) {
			AccountMessage accMsg = (AccountMessage) msg;
			int amount = Integer.parseInt(msg.getValue());
			boolean result;
			
			try {
				result = serverMgr.withdraw(accMsg.getId(), amount);
				if(result == true) {
					sendSuccessMsg(accMsg);
				} else {
					sendFailMsg(accMsg, "Ãâ±Ý ½ÇÆÐ");
				}
			} catch (BMSException e) {
				sendFailMsg(accMsg, e.getMessage());
			}
			
		}
		private void processTransferMessage(Message msg) {
			AccountMessage accMsg = (AccountMessage) msg;
			String to = accMsg.getTo();
			int amount = Integer.parseInt(accMsg.getValue());
			boolean result;
			try {
				result = serverMgr.transfer(accMsg.getId(), to, amount);
				if(result == true) {
					sendSuccessMsg(accMsg);
				} else {
					sendFailMsg(accMsg, "ÀÌÃ¼ ½ÇÆÐ");
				}
			} catch (BMSException e) {
				// TODO Auto-generated catch block
				sendFailMsg(accMsg, e.getMessage());
			}
			
		}
		private void processRemoveMessage(Message msg) {
			AccountMessage accMsg = (AccountMessage) msg;
			String to = accMsg.getTo();
			int amount = Integer.parseInt(accMsg.getValue());
			boolean result;
			try {
				result = serverMgr.transfer(accMsg.getId(), to, amount);
				if(result == true) {
					sendSuccessMsg(accMsg);
				} else {
					sendFailMsg(accMsg, "°èÁ¤ »èÁ¦ ½ÇÆÐ");
				}
			} catch (BMSException e) {
				// TODO Auto-generated catch block
				sendFailMsg(accMsg, e.getMessage());
			}
		}
		
	}
}
