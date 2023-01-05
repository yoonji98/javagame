package software;
import java.awt.Color;
import static software.House.userText;
import static software.House.tired;
import static software.House.day;
import static software.House.coin;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Rank2 extends JFrame{
	
	String [][] datas = new String[0][5];
    String r_id;
    int r_score;
    int rank;
    
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private String sql;
	

	private JTable table2;
	private JScrollPane pane2 = new JScrollPane(table2);
	private Vector mini2 = new Vector<>();
	
        public Rank2(){
          	DB();
        	init2();
        	
        	setTitle("랭킹");
            setBounds(500,200,900,400);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            
            Vector label = new Vector<>();
            label.add("이름");
            label.add("점수");
            label.add("순위");
            DefaultTableModel model2 = new DefaultTableModel(mini2, label);
            
            table2 = new JTable(model2);

            pane2 = new JScrollPane(table2);
            add(pane2);
            
            setVisible(true);
        }
        
        private void DB(){
            try {
               Class.forName("com.mysql.jdbc.Driver");  // JDBC driver를 메모리에 로드
           
               try {
               	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/software", "root","apmsetup");
               	stmt = (Statement) conn.createStatement();
               }catch (Exception e) {
   				System.out.println("접속 con" + conn);
   			}
   		} catch (ClassNotFoundException e) {
   			System.out.println("DB연결 테스트 실패 : " + e);
   		}
       }
		
   	private void init2() {
   		try {
   			sql = "SELECT m2_id, m2_score,"
   					+ "CASE "
   					+ "WHEN @prec_value = m2_score "
   					+ "THEN @vRank "
   					+ "WHEN @prev_value := m2_score "
   					+ "THEN @vRank := @vRank +1 "
   					+ "END AS rank "
   					+ "FROM mini2rank AS p, "
   					+ "(SELECT @vRank := 0, @prev_value := NULL) AS r "
   					+ "ORDER BY m2_score DESC;";
   			rs = stmt.executeQuery(sql);// 원하는 쿼리문 실행
   			
   			while(rs.next()){            // 각각 값을 가져와서 테이블값들을 추가
   				Vector r = new Vector<>();
   				r_id = rs.getString("m2_id");
   		   		r_score = rs.getInt("m2_score");
   		   		rank = rs.getInt("rank");
   	       		r.add(r_id);
   	       		r.add(r_score);
   	       		r.add(rank);
   	       		mini2.add(r);	
   				
   			}
   		}catch (Exception e) {
   			System.out.println("mini2rank 테이블 불러오기 실패" + e);
   		}finally{
        try {
        	rs.close();
        	stmt.close();
        	conn.close();   // 객체 생성한 반대 순으로 사용한 객체는 닫아준다.
        	} catch (Exception e2) {}
        }
   	}   		

}
