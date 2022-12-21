package sql_java_test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

// Class for Action 
class Action extends JFrame implements ActionListener{
	frame f; // 메인 윈도우 관련 객체 
	test t; // DB 관련 객체 
	JTextField search_field,search_field4; // 검색 텍스트 필드
	JTextField name,author,date,book1; // 추가 텍스트 필드들 
	
	// panel 1 (도서 추가 관련)에서 객체 생성 
	public Action(frame f, test t,JTextField name, JTextField author, JTextField date) {
		this.f=f;
		this.t=t;
		this.name=name;
		this.author=author;
		this.date=date;

	}
	
	// panel 2 (도서 조회 관련)에서 객체 생성 
	public Action(frame f, test t) { 
		this.f=f;
		this.t=t;
	}
	
	// panel 3 (도서 검색 관련)에서 객체 생성 
	public Action(frame f, test t,JTextField search_field) {
		this.f=f;
		this.t=t;
		this.search_field=search_field;
	}

	// (도서 추가 관련) 이벤트 처리
	public void func1() {
		// 조건 만족 시 SQL문 실행 
		try {
			String n=name.getText();
			String a=author.getText();
			String d=date.getText();

			
			// 입력 안 들어옴
			if (n.equals("")||a.equals("")||d.equals("")) {
				f.error_label.setText("입력 없음!");
				f.error_label.setForeground(Color.RED);
			}
			// 입력 들어옴
			else {
				// SQL문 실행
				t.stmt.executeUpdate("insert into book values('"+n+"','"+a+"','"+d+"','"+"대출가능"+"');");
				f.error_label.setText("저장 완료!!  (전체 목록을 눌러서 확인!!)");
				f.error_label.setForeground(Color.BLUE);
				// 입력 초기화 
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
	
	// (도서 조회 관련) 이벤트 처리
	public String[][] func2() {
		// SQL문 실행 
		try {
			ResultSet rs = t.stmt.executeQuery("select * from book;");
			// 데이터 확인
			int rows = 0;
			if (rs.last()) {
			    rows = rs.getRow();
			    rs.beforeFirst();
			}
			// 데이터 존재
			if(rows>0) {
				// 데이터를 2차원 배열에 담아서 리턴 
				String[][] contents=new String[rows][4];
				int idx=0;
				while(rs.next()) {
					contents[idx][0]=rs.getString("name");
					contents[idx][1]=rs.getString("author");
					contents[idx][2]=rs.getString("date");
					contents[idx][3]=rs.getString("book1");
					idx++;
				}	
				f.error_label.setText("조회 완료!!");
				f.error_label.setForeground(Color.BLUE);
				return contents;
			}
			// 데이터 존재 X
			else {
				// 빈 2차원 배열 리턴 
				f.error_label.setText("데이터 없음!");
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
	
	// (도서 검색 관련) 이벤트 처리
	public String[][] func3() {
		// 조건 만족 시 SQL문 실행 
		try {
			ResultSet rs;
			String search=search_field.getText();
			// 입력 들어옴
			if (!search.equals("")) {
				rs = t.stmt.executeQuery("select * from book where name like '%"+search+"%' or author like '%"+search+"%';");
				// 입력 초기화 
				search_field.setText(null);
				// 데이터 확인
				int rows = 0;
				if (rs.last()) {
				    rows = rs.getRow();
				    rs.beforeFirst();
				}
				// 데이터 존재
				if(rows>0) {
					// 데이터를 2차원 배열에 담아서 리턴 
					String[][] contents=new String[rows][4];
					int idx=0;
					while(rs.next()) {
						contents[idx][0]=rs.getString("name");
						contents[idx][1]=rs.getString("author");
						contents[idx][2]=rs.getString("date");
						contents[idx][3]=rs.getString("book1");
						idx++;
					}	
					f.error_label.setText("("+search+") 검색 완료!!");
					f.error_label.setForeground(Color.BLUE);
					return contents;
				}
				// 데이터 존재 X
				else {
					// 빈 2차원 배열 리턴 
					f.error_label.setText("데이터 없음!");
					f.error_label.setForeground(Color.RED);
					String[][] contents=new String[1][3];
					return contents;
				}
			}
			// 입력 안 들어옴
			else {
				f.error_label.setText("입력 없음!");
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
	public String[][] func4() {
		// 조건 만족 시 SQL문 실행 
		try {
			ResultSet rs;
			String search=search_field.getText();
			// 입력 들어옴
			if (!search.equals("")) {
				rs = t.stmt.executeQuery("select * from book where name = '"+search+"';");
				// 입력 초기화 
				search_field.setText(null);
				// 데이터 확인
				int rows = 0;
				if (rs.last()) {
				    rows = rs.getRow();
				    rs.beforeFirst();
				}
				// 데이터 존재
				if(rows>0) {
					// 데이터를 2차원 배열에 담아서 리턴 
					String[][] contents4=new String[rows][4];
					int idx=0;
					while(rs.next()) {
						contents4[idx][0]=rs.getString("name");
						contents4[idx][1]=rs.getString("author");
						contents4[idx][2]=rs.getString("date");
						contents4[idx][3]=rs.getString("book1");
						idx++;
					}
					
					int rnt = 0;
					if("대출가능".equals(contents4[0][3])) {
						t.stmt.executeUpdate("update book set book1 = '대출중' where name ='"+search+"';");
						f.error_label.setText("("+search+") 대출 완료!!");
						f.error_label.setForeground(Color.BLUE);
						contents4[0][3] = "대출중";
					}
					else {
						t.stmt.executeUpdate("update book set book1 = '대출가능' where name ='"+search+"';");
						f.error_label.setText("("+search+") 반납 완료!!");
						f.error_label.setForeground(Color.BLUE);
						contents4[0][3] = "대출가능";
					}

				
					return contents4;
				}
				// 데이터 존재 X
				else {
					// 빈 2차원 배열 리턴 
					f.error_label.setText("책이름을 입력해주세요!");
					f.error_label.setForeground(Color.RED);
					String[][] contents4=new String[1][3];
					return contents4;
				}
			}
			// 입력 안 들어옴
			else {
				f.error_label.setText("입력 없음!");
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
		JButton act=(JButton) e.getSource(); //활동하는 버튼 
	    String actname=act.getText(); // 활동하는 버튼의 텍스트
	    	
	    switch(actname) {
	    	case "추가":
	    		func1(); // 도서 추가 이벤트 처리 핵심 -> 테이블에 입력한 도서 추가 
	    		break;
	    		
	    	case "전체 목록":
	    		// mini-window size, title, location
		    	getContentPane().removeAll(); // 기존 윈도우 삭제되고 새로운 윈도우 생성하는데 기존에 안 사라진게 있을 수 있어서 지움
	    		setSize(400,200);
	    		setTitle("전체 목록 조회");
	    		setLocation(1190,300);
	    		// table setting
	    		String[] header= {"이름","저자","출판일","대출"};
	    		String[][] contents=func2(); // 도서 조회 이벤트 처리 핵심 -> 전체 도서 목록이 담긴 2차원 배열 반환 
	    		if(contents != null) {
		    		JTable table = new JTable(contents,header);
		    		JScrollPane scrollpane=new JScrollPane(table);
		    		add(scrollpane);
		    		setVisible(true);
	    		}
	    		break;
	    	
	    	case "검색":
	    		// mini-window size, title, location
		    	getContentPane().removeAll(); // 기존 윈도우 삭제되고 새로운 윈도우 생성하는데 기존에 안 사라진게 있을 수 있어서 지움
	    		setSize(400,200);
	    		setTitle("도서 검색");
	    		setLocation(1190,500);
	    		// table setting
	    		String[] header2= {"이름","저자","출판일","대출"};
	    		String[][] contents2=func3(); // 도서 검색 이벤트 처리 핵심 -> 도서 검색한 목록이 담긴 2차원 배열 반환
	    		if(contents2 != null) {
		    		JTable table2 = new JTable(contents2,header2);
		    		JScrollPane scrollpane2=new JScrollPane(table2);
		    		add(scrollpane2);
		    		setVisible(true);
	    		}
	    		break;
	    		
	    	case "대출 반납":
	    		// mini-window size, title, location
		    	getContentPane().removeAll(); // 기존 윈도우 삭제되고 새로운 윈도우 생성하는데 기존에 안 사라진게 있을 수 있어서 지움
	    		setSize(400,200);
	    		setTitle("대출 반납");
	    		setLocation(1190,300);
	    		// table setting
	    		String[] header4= {"이름","저자","출판일","대출"};
	    		String[][] contents4=func4(); // 도서 조회 이벤트 처리 핵심 -> 전체 도서 목록이 담긴 2차원 배열 반환 
	    		if(contents4 != null) {
		    		JTable table = new JTable(contents4,header4);
		    		JScrollPane scrollpane=new JScrollPane(table);
		    		add(scrollpane);
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
		// 윈도우 창 설정 
		setSize(500,200);
		setLocation(700,300);
		setTitle("도서관");
		setLayout(new GridLayout(6,1));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	    // panel 객체 배열 생성 
	    JPanel pns[]=new JPanel[6];
	    for(int i=0; i<6; i++) {
	    	pns[i]=new JPanel();
	    	pns[i].setLayout(new GridLayout(0,5));
	    }
    	pns[5]=new JPanel();
    	pns[5].setLayout(new GridLayout(1,2));
	    
	    // panel 0 (소개 관련)
	    JLabel[] lbs=new JLabel[4]; // Labels
	    String[] strs= {"[도서 관리 앱]","[이름]","[저자]","[출판일]"};
	    for(int i=0; i<4; i++) {
	    	lbs[i]=new JLabel(strs[i]);
	    	pns[0].add(lbs[i]);
	    }
	    
	    // panel 1 (도서 추가 관련) 
	    JLabel lb1=new JLabel("도서 추가"); // Label
	    pns[1].add(lb1);
	    JTextField[] fields=new JTextField[3]; // TextFields
	    for(int i=0; i<3; i++) {
	    	fields[i]=new JTextField();
	    	pns[1].add(fields[i]);
	    }
	    JButton bt1=new JButton("추가"); // Button
	    bt1.addActionListener(new Action(this,t,fields[0],fields[1],fields[2]));
	    pns[1].add(bt1);
	    
	    // panel 2 (도서 조회 관련) 
	    JLabel lb2=new JLabel("도서 목록 조회"); // Label
	    JButton bt2=new JButton("전체 목록"); // Button
	    Cursor cursor = bt2.getCursor();
	    bt2.addActionListener(new Action(this,t));
	    pns[2].add(lb2);
	    pns[2].add(bt2);
	    
	    // panel 3 (도서 검색 관련) 
	    JLabel lb3=new JLabel("도서 찾기"); // Label
	    JTextField search_field=new JTextField(); // TextField
	    JButton bt3=new JButton("검색"); // Button
	    bt3.addActionListener(new Action(this,t,search_field));
	    pns[3].add(lb3);
	    pns[3].add(search_field);
	    pns[3].add(bt3);
	    
	    // panel 4 (에러 관련)
	    JLabel lb5=new JLabel("메시지:"); // Label
	    pns[5].add(lb5);
	    error_label=new JLabel("DB 연결 완료!"); // Label
	    pns[5].add(error_label);

	    // 대출반납기능 추가
	    JLabel lb4=new JLabel("대출 반납");
	    pns[4].add(lb4);
	    JTextField search_field4=new JTextField();
	    pns[4].add(search_field4);
	    JButton bt4=new JButton("대출 반납");
	    bt4.addActionListener(new Action(this,t,search_field4));
	    pns[4].add(bt4);

	    
	    // 컴포넌트 배치 및 활성화  
	    for (int i=0; i<6; i++) {
	    	add(pns[i]);
	    }
		setVisible(true);
	} 
}
	    // panel 4 (도서 조회 관련) 
//	    JLabel lb4=new JLabel("대출 반납"); // Label
//	    JButton bt4=new JButton("대출"); // Button
//	    Cursor cursor4 = bt4.getCursor();
//	    bt4.addActionListener(new Action(this,t));
//	    pns[4].add(lb4);
//	    pns[4].add(bt4);


// class for DB Connection 
public class test {
	Connection conn;
	Statement stmt;
	ResultSet rs;
	
	public test() {
		// DB Connection 
		try {
			// Connection을 위해 필요한 DB Driver 로드
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			System.out.println("DB Driver Loading OK!");
			
			// DB Connection 
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbjava","javaman","0000");
			System.out.println("DB Connection OK!");
			
			// Statement 객체 생성 (Java -> DB로 SQL문 전송하는 역할, DB -> JAVA 처리 결과 전송할 때 받는 역할)
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);		
			
			// 윈도우 생성 
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
		test t=new test(); // DB 연결 및 윈도우 생성 
	}
}
