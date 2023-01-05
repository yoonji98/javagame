package software;
import java.awt.*;
import java.awt.event.*;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import static software.House.userText;
import static software.House.tired;
import static software.House.day;
import static software.House.coin;
import static software.House.lv;
public class Shopping extends JPanel{
   public static final int SCREEN_WIDTH = 1280; //상수는 전부 대문자
   public static final int SCREEN_HEIGHT = 720;   
   private Image screenImage;
   private Graphics screenGraphic;
   private Image screen;
   private ImageIcon backImage = new ImageIcon(this.getClass().getResource("images/back.png"));
   public JButton btnback = new JButton(backImage);
   private ImageIcon buyImage = new ImageIcon(this.getClass().getResource("images/buy.png"));
   private JButton btnpur = new JButton(buyImage);
   private JTable table;
   private String itemname;
   private String itemprice;
   DefaultTableModel dtm;
   private boolean show = true;
   private final Font font = new Font("맑은 고딕", Font.BOLD | Font.ITALIC, 24);

   private Vector s = new Vector<>();
   
   public Shopping() {
     DB();
     setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
     
      screen = new ImageIcon(this.getClass().getResource("images/shopping.png")).getImage().getScaledInstance(SCREEN_WIDTH, SCREEN_HEIGHT, Image.SCALE_SMOOTH );
      setLayout(null);
      
      btnback.setBounds(1245, 0, 30, 30);
      btnback.setBorderPainted(false);
      btnback.setContentAreaFilled(false);
      btnback.setFocusPainted(false);
      add(btnback);
      
      
      Vector label = new Vector<>();
      label.add("Item Name");
      label.add("Item Price");
      dtm = new DefaultTableModel(s,label);
      table = new JTable(dtm);
      table.setBounds(432, 400, 417, 89);
      add(table);
      
      btnpur.setBorderPainted(false);
      btnpur.setContentAreaFilled(false);
      btnpur.setFocusPainted(false);
      btnpur.setBounds(879, 400, 137, 68);
      btnpur.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
           
            tired();
         }
      });
      add(btnpur);
   
   
   }

public void paint(Graphics g) { // 약속된 메소드
      screenImage = createImage(SCREEN_WIDTH, SCREEN_HEIGHT);// 1280X720에 이미지 생성
      screenGraphic = screenImage.getGraphics();// 스크린 이미지를 이용하여 그래픽 개체 불러옴
      screenDraw(screenGraphic);// 그래픽에 그림을 그려줌
      g.drawImage(screenImage, 0, 0, null);// (0,0)위치에 screenImage생성
   }

   public void screenDraw(Graphics g) {
      g.drawImage(screen, 0, 0, null);// 그리는 방법1
      paintComponents(g);// 그리는 방법2:항상 고정되는 것, 버튼이나 메뉴바
      this.repaint();
      if(show){
          g.setFont(font);
          g.setColor(Color.black);
         g.drawString("D - " + day, 100, 60);
          g.drawString("coin : " + coin, 100, 100);
          g.drawString("학점수 : "+lv, 100, 180);
          g.drawString("피로도", 100, 140);
          g.drawRect(200,120,102,22);
          g.setColor(Color.gray);
          g.fillRect(201,121,(int)(100-tired*2), 20);
      }
   }
   public void DB(){
      Connection conn; 
       Statement stmt; 
       ResultSet rs; 
       
       try {
          Class.forName("com.mysql.jdbc.Driver");  // JDBC driver를 메모리에 로드
          conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/software", "root","apmsetup");
          stmt = (Statement) conn.createStatement();
          rs=stmt.executeQuery("SELECT * FROM shopping");  // 원하는 쿼리문 실행
          while(rs.next()) {
             Vector r = new Vector<>();
             itemname = new String(rs.getString("s_name").getBytes("ISO-8859-1"));
             itemprice = new String(rs.getString("s_price").getBytes("ISO-8859-1"));
             r.add(itemname);
             r.add(itemprice);
             s.add(r);
          }
          rs.close(); 
          stmt.close(); 
          conn.close();
       } catch (ClassNotFoundException e) {
         System.out.println("JDBC 드라이버 로드 에러");
      } catch (SQLException e) {
         System.out.println("SQL 실행 에러");
      } catch (UnsupportedEncodingException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
   // tired == 50 이면  day -- ; 
   public void tired() {
      Statement stmt1;
       Connection conn; 
       ResultSet rs; 
     try {
        int n = table.getSelectedRow();
        itemname = new String(dtm.getValueAt(n, 0).toString().getBytes(),"ISO-8859-1");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/software", "root","apmsetup");
        stmt1 = (Statement) conn.createStatement();
        if(n==0){
           if(coin>=1500){
              stmt1.executeUpdate("update progress set p_tired=p_tired+10 where u_id='"+userText.getText()+"' ");   
              coin-=1500;
           }
           else{
              JOptionPane nocoin = new JOptionPane(); 
                 JOptionPane.showMessageDialog(null, "돈이 부족합니다.");
                add(nocoin);
           }
        }
        
        if(n==1){
           if(coin>=1000){
              stmt1.executeUpdate("update progress set p_tired=p_tired+5 where u_id='"+userText.getText()+"' ");
              coin-=1000;
           }
           else{
              JOptionPane nocoin = new JOptionPane(); 
               JOptionPane.showMessageDialog(null, "돈이 부족합니다.");
                add(nocoin);
           }
        }
        if(n==2){
           if(coin>=5000){
              stmt1.executeUpdate("update progress set p_lv=p_lv+5 where u_id='"+userText.getText()+"' ");
              coin-=5000;
              lv+=5;
           }
           else{
              JOptionPane nocoin = new JOptionPane(); 
               JOptionPane.showMessageDialog(null, "돈이 부족합니다.");
                add(nocoin);
           }
        }
        
        if(n==3){
           if(coin>=10000){
              stmt1.executeUpdate("update progress set p_lv=p_lv+10 where u_id='"+userText.getText()+"' ");
              coin-=10000;
              lv+=10;
           }
           else{
              JOptionPane nocoin = new JOptionPane(); 
               JOptionPane.showMessageDialog(null, "돈이 부족합니다.");
                add(nocoin);
           }
        }
       rs = stmt1.executeQuery("SELECT * FROM progress where u_id = '"+userText.getText()+"'");
       rs.next();
          tired = rs.getDouble("p_tired");
          if(tired >= 50){
              JOptionPane sleep = new JOptionPane(); 
                  JOptionPane.showMessageDialog(null, "피로도가 최대치에 도달해 잠을 잡니다");
                 add(sleep);
                 stmt1.executeUpdate("update progress set p_tired=0, p_time=p_time-1 where u_id='"+userText.getText()+"'");
                 tired=0;   day-=1;
          }
       stmt1.close();
       rs.close();
       conn.close(); 
     } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
     } catch (UnsupportedEncodingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
   }
  
   }
   
}
