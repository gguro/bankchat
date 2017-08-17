package message;

import java.io.Serializable;

//채팅,입장 내용을 담는 클래스 서버와 클라이언트간의 통신에 사용된다.
public class Message implements Serializable {
	protected String order; // 명령부류	ex) chat -> 채팅, create ->계좌생성 etc...
	protected String value; // 명령 값	ex) 채팅내용, 입금액, 출금액 etc...
	protected String userId; // 유저아이디, 주체자
	public Message() {

	}

	public Message(String order, String value, String userId) {
		this.order = order;
		this.value = value;
		this.userId = userId;
	}

	public void setMessage(String order, String value, String userId) {
		this.order = order;
		this.value = value;
		this.userId = userId;
	}
	
	public void setOrder(String order) {
		this.order = order;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrder() {
		return order;
	}

	public String getValue() {
		return value;
	}

	public String getUserId() {
		return userId;
	}
	//Message 구성내용을 보기 쉽게 toString 메소드 재정의
	public String toString() {
		String str = "";
		if (value != null)
			str = "[" + userId + "] : '" + value + "'을 " + order;
		else
			str = "[" + userId + "] : " + order;

		return str;
	}
}
