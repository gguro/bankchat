package message;

//계좌의 내용을 담는 클래스 서버와 클라이언트간의 통신에 사용된다.
//일반 메세지(채팅,입장)를 담는 클래스를 상속받음
public class AccountMessage extends Message {
	private String to;		//보내는 계좌번호
	private int type;		//계좌 타입 1. 일반 2. 보통
	private String id;		//계좌번호
	private String password;//비밀번호
	private String name;	//예금주 이름
	private int rate;		//이자
	
	//Message 구성내용을 보기 쉽게 toString 메소드 재정의
	public String toString() {
		String str = "";
		String val = "";
		if (value != null)
			val = value;
		if (id != null)
			str = "[" + id + "] : '"+ val+"' " + order;
		else
			str = "[" + userId + "] : '" + val+"' " + order;
		if (to != null)
			str += "([" + to + "]에게)";

		return str;
	}

	public AccountMessage() {
		super();
	}

	public AccountMessage(String order, String value, String userId) {
		super(order, value, userId);
		// TODO Auto-generated constructor stub
	}

	public AccountMessage(String order, String value, String userId, String id, String password) {
		super(order, value, userId);
		this.id = id;
		this.password = password;
	}

	public AccountMessage(String order, String value, String userId, String to, int type, String id, String password) {
		super(order, value, userId);
		this.to = to;
		this.type = type;
		this.id = id;
		this.password = password;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

}
