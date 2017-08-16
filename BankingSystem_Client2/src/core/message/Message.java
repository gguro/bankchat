package core.message;

import java.io.Serializable;

//ä��,���� ������ ��� Ŭ���� ������ Ŭ���̾�Ʈ���� ��ſ� ���ȴ�.
public class Message implements Serializable {
	protected String order; // ��ɺη�	ex) chat -> ä��, create ->���»��� etc...
	protected String value; // ��� ��	ex) ä�ó���, �Աݾ�, ��ݾ� etc...
	protected String userId; // �������̵�, ��ü��
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
	//Message ���������� ���� ���� toString �޼ҵ� ������
	public String toString() {
		String str = "";
		if (value != null)
			str = "[" + userId + "] : '" + value + "'�� " + order;
		else
			str = "[" + userId + "] : " + order;

		return str;
	}
}
