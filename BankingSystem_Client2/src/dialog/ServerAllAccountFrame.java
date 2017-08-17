package dialog;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import common.Account;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;

public class ServerAllAccountFrame extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel model;
	private Vector<String> tableIndex = new Vector<String>();

	/**
	 * Launch the application.
	 */
	// public static void main(String[] args) {
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// ServerAllAccountFrame frame = new ServerAllAccountFrame();
	// frame.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// }

	/**
	 * Create the frame.
	 */
	public ServerAllAccountFrame(List<Account> acc) {
		setTitle("��ü��������");
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 718, 233);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(48, 46, 593, 102);
		contentPane.add(scrollPane);

		tableIndex.addElement("���¹�ȣ");
		tableIndex.addElement("������");
		tableIndex.addElement("�ܾ�");
		tableIndex.addElement("��������");
		tableIndex.addElement("����");

		model = new DefaultTableModel(tableIndex, 0) {
			public boolean isCellEditable(int i, int c) {
				return false;
			}
		};

		table = new JTable(model);
		scrollPane.setViewportView(table);

		JLabel lblNewLabel = new JLabel("\uC804\uCCB4\uACC4\uC88C\uC815\uBCF4");
		lblNewLabel.setBounds(48, 21, 137, 15);
		contentPane.add(lblNewLabel);

		setAccountList(acc);
	}

	// ���Ӹ���Ʈ
	private void setAccountList(List<Account> acc) {
		Vector<String> tableRow = null;
		for (Account account : acc) {
			tableRow = new Vector<String>();
			tableRow.addElement(account.getAccountNo());
			tableRow.addElement(account.getName());
			tableRow.addElement(((Integer) account.getBalance()).toString());

			String type;
			if (account.getAccountType() == 1) {
				type = "�Ϲ�";
			} else if (account.getAccountType() == 2) {
				type = "����";
			} else {
				type = "";
			}
			tableRow.addElement(type);
			tableRow.addElement(((Integer) account.getInterestRate()).toString());

			model.addRow(tableRow);
		}

	}
}
