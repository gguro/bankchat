package core.message;

//������ ������ ��� Ŭ���� ������ Ŭ���̾�Ʈ���� ��ſ� ���ȴ�.
//�Ϲ� �޼���(ä��,����)�� ��� Ŭ������ ��ӹ���
public class AccountMessage extends Message {
	private String to;		//������ ���¹�ȣ
	private int type;		//���� Ÿ�� 1. �Ϲ� 2. ����
	private String id;		//���¹�ȣ
	private String password;//��й�ȣ
	private String name;	//������ �̸�
	private int rate;		//����
	
	//Message ���������� ���� ���� toString �޼ҵ� ������
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
			str += "([" + to + "]����)";

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
