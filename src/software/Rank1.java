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

public class Rank1 extends JFrame{
	
	String [][] datas = new String[0][5];
    String r_id;
    int r_score;
    int rank;
    
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private String sql;
	

	private JTable table1;
	private JScrollPane pane1 = new JScrollPane(table1);
	private Vector mini1 = new Vector<>();
	
        public Rank1(){
          	DB();
          	init1();
        	
        	setTitle("랭킹");
            setBounds(500,200,900,400);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            
            Vector label = new Vector<>();
            label.add("이름");
            label.add("점수");
            label.add("순위");
            DefaultTableModel model1 = new DefaultTableModel(mini1, label);
            
            table1 = new JTable(model1);
            
            pane1 = new JScrollPane(table1);
            add(pane1);
            setVisible(true);
        }
        
        private void DB(){
            try {
               Class.forName("com.mysql.jdbc.Driver");  // JDBC driver를 메모리에 로드
               System.out.println("드라이버 적재 성공");
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
        
    private void init1() {
    	try {
   			sql = "SELECT m1_id, m1_score,"
   					+ "CASE "
   					+ "WHEN @prec_value = m1_score "
   					+ "THEN @vRank "
   					+ "WHEN @prev_value := m1_score "
   					+ "THEN @vRank := @vRank +1 "
   					+ "END AS rank "
   					+ "FROM mini1rank AS p, "
   					+ "(SELECT @vRank := 0, @prev_value := NULL) AS r "
   					+ "ORDER BY m1_score DESC;";
   			rs = stmt.executeQuery(sql);// 원하는 쿼리문 실행
   			
   			while(rs.next()){            // 각각 값을 가져와서 테이블값들을 추가
   				Vector r = new Vector<>();
   				r_id = rs.getString("m1_id");
   		   		r_score = rs.getInt("m1_score");
   		   		rank = rs.getInt("rank");
   	       		r.add(r_id);
   	       		r.add(r_score);
   	       		r.add(rank);
   	       		mini1.add(r);	
   			}
    	}catch (Exception e) {
   	   			System.out.println("mini1rank 테이블 불러오기 실패" + e);
   	   		}
   	   	}   		
 
}
