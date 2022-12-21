package sql_java_test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

// Class for Action 
class Action extends JFrame implements ActionListener{
	frame f; // ���� ������ ���� ��ü 
	test t; // DB ���� ��ü 
	JTextField search_field; // �˻� �ؽ�Ʈ �ʵ�
	JTextField name,author,date; // �߰� �ؽ�Ʈ �ʵ�� 
	
	// panel 1 (���� �߰� ����)���� ��ü ���� 
	public Action(frame f, test t,JTextField name, JTextField author, JTextField date) {
		this.f=f;
		this.t=t;
		this.name=name;
		this.author=author;
		this.date=date;
	}
	
	// panel 2 (���� ��ȸ ����)���� ��ü ���� 
	public Action(frame f, test t) {
		this.f=f;
		this.t=t;
	}
	
	// panel 3 (���� �˻� ����)���� ��ü ���� 
	public Action(frame f, test t,JTextField search_field) {
		this.f=f;
		this.t=t;
		this.search_field=search_field;
	}
	
	// (���� �߰� ����) �̺�Ʈ ó��
	public void func1() {
		// ���� ���� �� SQL�� ���� 
		try {
			String n=name.getText();
			String a=author.getText();
			String d=date.getText();
			
			// �Է� �� ����
			if (n.equals("")||a.equals("")||d.equals("")) {
				f.error_label.setText("�Է� ����!");
				f.error_label.setForeground(Color.RED);
			}
			// �Է� ����
			else {
				// SQL�� ����
				t.stmt.executeUpdate("insert into book values('"+n+"','"+a+"','"+d+"');");
				f.error_label.setText("���� �Ϸ�!!  (��ü ����� ������ Ȯ��!!)");
				f.error_label.setForeground(Color.BLUE);
				// �Է� �ʱ�ȭ 
				name.setText(null);
				author.setText(null);
				date.setText(null);
			}
		} 
		// DB Error 
		catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			f.error_label.setText("DB Error");
			f.error_label.setForeground(Color.RED);
		}	
	}
	
	// (���� ��ȸ ����) �̺�Ʈ ó��
	public String[][] func2() {
		// SQL�� ���� 
		try {
			ResultSet rs = t.stmt.executeQuery("select * from book;");
			// ������ Ȯ��
			int rows = 0;
			if (rs.last()) {
			    rows = rs.getRow();
			    rs.beforeFirst();
			}
			// ������ ����
			if(rows>0) {
				// �����͸� 2���� �迭�� ��Ƽ� ���� 
				String[][] contents=new String[rows][3];
				int idx=0;
				while(rs.next()) {
					contents[idx][0]=rs.getString("name");
					contents[idx][1]=rs.getString("author");
					contents[idx][2]=rs.getString("date");
					idx++;
				}	
				f.error_label.setText("��ȸ �Ϸ�!!");
				f.error_label.setForeground(Color.BLUE);
				return contents;
			}
			// ������ ���� X
			else {
				// �� 2���� �迭 ���� 
				f.error_label.setText("������ ����!");
				f.error_label.setForeground(Color.RED);
				String[][] contents=new String[1][3];
				return contents;
			}
		}
		// DB Error 
		catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			f.error_label.setText("DB Error");
			f.error_label.setForeground(Color.RED);
			return null;
		}
	}
	
	// (���� �˻� ����) �̺�Ʈ ó��
	public String[][] func3() {
		// ���� ���� �� SQL�� ���� 
		try {
			ResultSet rs;
			String search=search_field.getText();
			// �Է� ����
			if (!search.equals("")) {
				rs = t.stmt.executeQuery("select * from book where name like '%"+search+"%' or author like '%"+search+"%';");
				// �Է� �ʱ�ȭ 
				search_field.setText(null);
				// ������ Ȯ��
				int rows = 0;
				if (rs.last()) {
				    rows = rs.getRow();
				    rs.beforeFirst();
				}
				// ������ ����
				if(rows>0) {
					// �����͸� 2���� �迭�� ��Ƽ� ���� 
					String[][] contents=new String[rows][3];
					int idx=0;
					while(rs.next()) {
						contents[idx][0]=rs.getString("name");
						contents[idx][1]=rs.getString("author");
						contents[idx][2]=rs.getString("date");
						idx++;
					}	
					f.error_label.setText("("+search+") �˻� �Ϸ�!!");
					f.error_label.setForeground(Color.BLUE);
					return contents;
				}
				// ������ ���� X
				else {
					// �� 2���� �迭 ���� 
					f.error_label.setText("������ ����!");
					f.error_label.setForeground(Color.RED);
					String[][] contents=new String[1][3];
					return contents;
				}
			}
			// �Է� �� ����
			else {
				f.error_label.setText("�Է� ����!");
				f.error_label.setForeground(Color.RED);
				return null;
			}
		}
		// DB Error 
		catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			f.error_label.setText("DB Error");
			f.error_label.setForeground(Color.RED);
			return null;
		}
	}
	
	@Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
		JButton act=(JButton) e.getSource(); //Ȱ���ϴ� ��ư 
	    String actname=act.getText(); // Ȱ���ϴ� ��ư�� �ؽ�Ʈ
	    	
	    switch(actname) {
	    	case "�߰�":
	    		func1(); // ���� �߰� �̺�Ʈ ó�� �ٽ� -> ���̺� �Է��� ���� �߰� 
	    		break;
	    		
	    	case "��ü ���":
	    		// mini-window size, title, location
		    	getContentPane().removeAll(); // ���� ������ �����ǰ� ���ο� ������ �����ϴµ� ������ �� ������� ���� �� �־ ����
	    		setSize(400,200);
	    		setTitle("��ü ��� ��ȸ");
	    		setLocation(1190,300);
	    		// table setting
	    		String[] header= {"�̸�","����","������"};
	    		String[][] contents=func2(); // ���� ��ȸ �̺�Ʈ ó�� �ٽ� -> ��ü ���� ����� ��� 2���� �迭 ��ȯ 
	    		if(contents != null) {
		    		JTable table = new JTable(contents,header);
		    		JScrollPane scrollpane=new JScrollPane(table);
		    		add(scrollpane);
		    		setVisible(true);
	    		}
	    		break;
	    	
	    	case "�˻�":
	    		// mini-window size, title, location
		    	getContentPane().removeAll(); // ���� ������ �����ǰ� ���ο� ������ �����ϴµ� ������ �� ������� ���� �� �־ ����
	    		setSize(400,200);
	    		setTitle("���� �˻�");
	    		setLocation(1190,500);
	    		// table setting
	    		String[] header2= {"�̸�","����","������"};
	    		String[][] contents2=func3(); // ���� �˻� �̺�Ʈ ó�� �ٽ� -> ���� �˻��� ����� ��� 2���� �迭 ��ȯ
	    		if(contents2 != null) {
		    		JTable table2 = new JTable(contents2,header2);
		    		JScrollPane scrollpane2=new JScrollPane(table2);
		    		add(scrollpane2);
		    		setVisible(true);
	    		}
	    		break;
	    }

    }
}

// Class for Window
class frame extends JFrame{
	JLabel error_label;
	
	public frame(test t) {		
		// ������ â ���� 
		setSize(500,200);
		setLocation(700,300);
		setTitle("������");
		setLayout(new GridLayout(5,1));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	    // panel ��ü �迭 ���� 
	    JPanel pns[]=new JPanel[5];
	    for(int i=0; i<4; i++) {
	    	pns[i]=new JPanel();
	    	pns[i].setLayout(new GridLayout(0,5));
	    }
    	pns[4]=new JPanel();
    	pns[4].setLayout(new GridLayout(1,2));
	    
	    // panel 0 (�Ұ� ����)
	    JLabel[] lbs=new JLabel[4]; // Labels
	    String[] strs= {"[���� ���� ��]","[�̸�]","[����]","[������]"};
	    for(int i=0; i<4; i++) {
	    	lbs[i]=new JLabel(strs[i]);
	    	pns[0].add(lbs[i]);
	    }
	    
	    // panel 1 (���� �߰� ����) 
	    JLabel lb1=new JLabel("���� �߰�"); // Label
	    pns[1].add(lb1);
	    JTextField[] fields=new JTextField[3]; // TextFields
	    for(int i=0; i<3; i++) {
	    	fields[i]=new JTextField();
	    	pns[1].add(fields[i]);
	    }
	    JButton bt1=new JButton("�߰�"); // Button
	    bt1.addActionListener(new Action(this,t,fields[0],fields[1],fields[2]));
	    pns[1].add(bt1);
	    
	    // panel 2 (���� ��ȸ ����) 
	    JLabel lb2=new JLabel("���� ��� ��ȸ"); // Label
	    JButton bt2=new JButton("��ü ���"); // Button
	    Cursor cursor = bt2.getCursor();
	    bt2.addActionListener(new Action(this,t));
	    pns[2].add(lb2);
	    pns[2].add(bt2);
	    
	    // panel 3 (���� �˻� ����) 
	    JLabel lb3=new JLabel("���� ã��"); // Label
	    JTextField search_field=new JTextField(); // TextField
	    JButton bt3=new JButton("�˻�"); // Button
	    bt3.addActionListener(new Action(this,t,search_field));
	    pns[3].add(lb3);
	    pns[3].add(search_field);
	    pns[3].add(bt3);
	    
	    // panel 4 (���� ����)
	    JLabel lb4=new JLabel("�޽���:"); // Label
	    pns[4].add(lb4);
	    error_label=new JLabel("DB ���� �Ϸ�!"); // Label
	    pns[4].add(error_label);

	    
	    // ������Ʈ ��ġ �� Ȱ��ȭ  
	    for (int i=0; i<5; i++) {
	    	add(pns[i]);
	    }
		setVisible(true);
	} 
}

// class for DB Connection 
public class test {
	Connection conn;
	Statement stmt;
	ResultSet rs;
	
	public test() {
		// DB Connection 
		try {
			// Connection�� ���� �ʿ��� DB Driver �ε�
			Class.forName("com.mysql.jdbc.Driver"); 
			System.out.println("DB Driver Loading OK!");
			
			// DB Connection 
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbjava","javaman","0000");
			System.out.println("DB Connection OK!");
			
			// Statement ��ü ���� (Java -> DB�� SQL�� �����ϴ� ����, DB -> JAVA ó�� ��� ������ �� �޴� ����)
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);		
			
			// ������ ���� 
			frame f=new frame(this); 
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("DB Driver Error!");
		}
		catch(SQLException se) {
			se.printStackTrace();
			System.out.println("DB Error!");
		}
	}

	public static void main(String[] args) {
		test t=new test(); // DB ���� �� ������ ���� 
	}
}
