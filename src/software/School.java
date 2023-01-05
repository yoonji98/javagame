package software;
import static software.House.exp;
import static software.House.day;
import static software.House.tired;
import static software.House.userText;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.*;
import javax.swing.*;

public class School extends JPanel implements Runnable {
	   Image dbImage;
	   Graphics dbg;
	   Image back;
	   Image I;
	   Image em;

	   private ArrayList msList = null;
	   private ArrayList enList = null;
	   private boolean left = false, right = false, up = false, down = false, fire = false;
	   private boolean start = false, end = false;
	   private int w = 1280, h = 720, x = 550, y = 648, xw = 50, xh = 50;
	   private final Font font = new Font("맑은 고딕", Font.BOLD | Font.ITALIC, 30);

	   private ImageIcon backImage = new ImageIcon(this.getClass().getResource("images/back.png"));
	   public JButton btnback = new JButton(backImage);

	   private ImageIcon IImage = new ImageIcon(this.getClass().getResource("images/I.png"));
	   private ImageIcon emImage = new ImageIcon(this.getClass().getResource("images/em.png"));

	   private String r_id;
	   private int r_score = 0;
	   private Connection conn;
	   private Statement stmt;
	   private ResultSet rs;
	   private String sql;


	   public School() {
	      DB();
	      back = backImage.getImage();
	      I = IImage.getImage();
	      em = emImage.getImage();
	      msList = new ArrayList();
	      enList = new ArrayList();
	      KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
	      manager.addKeyEventDispatcher(new AL());
	      setSize(w, h);
	      setVisible(true);
	      setLayout(null);

	      btnback.setBounds(1245, 0, 30, 30);
	      btnback.setBorderPainted(false);
	      btnback.setContentAreaFilled(false);
	      btnback.setFocusPainted(false);
	      add(btnback);  
	   }

	   public void run() {
	      try {
	         int msCnt = 0;
	         int enCnt = 0;
	         while(true) {
	            Thread.sleep(10);

	            if(start) {
	               if(enCnt > 2000) {
	                  enCreate();
	                  enCnt = 0;
	               }
	               if(msCnt >= 100) {
	                  fireMs();
	                  msCnt = 0;
	               }
	               msCnt += 10;
	               enCnt += 10;
	               keyControl();
	               crashChk();
	            }
	         }
	      } catch(Exception e) {
	         e.printStackTrace();
	      }
	   }


	   public void fireMs() {
	      if(fire) {
	         if(msList.size() < 100) {
	            Ms m = new Ms(this.x, this.y);
	            msList.add(m);
	         }
	      }
	   }
	   public void enCreate() {
	      for(int i = 0; i < 9; i++) {
	         double rx = Math.random() * (w - xw);
	         double ry = Math.random() * 50;
	         Enemy en = new Enemy((int)rx, (int)ry);
	         enList.add(en);
	      }
	   }
	   public void crashChk() {
	      dbg = this.getGraphics();
	      Polygon p = null;
	      for(int i = 0; i < msList.size(); i++) {
	         Ms m = (Ms)msList.get(i);
	         for(int j = 0; j < enList.size(); j++) {
	            Enemy e = (Enemy)enList.get(j);
	            int[] xpoints = {m.x, (m.x + m.w), (m.x + m.w), m.x};
	            int[] ypoints = {m.y, m.y, (m.y + m.h), (m.y + m.h)};
	            p = new Polygon(xpoints, ypoints, 4);
	            if(p.intersects((double)e.x, (double)e.y, (double)e.w, (double)e.h)) {
	               msList.remove(i);
	               enList.remove(j);
	               r_score++;
	            }
	         }
	      }
	      for(int i = 0; i < enList.size(); i++) {
	         Enemy e = (Enemy)enList.get(i);
	         int[] xpoints = {x, (x + xw), (x + xw), x};
	         int[] ypoints = {y, y, (y + xh), (y + xh)};
	         p = new Polygon(xpoints, ypoints, 4);
	         if(p.intersects((double)e.x, (double)e.y, (double)e.w, (double)e.h)) {
	            enList.remove(i);
	            end = true;
	            start = false;
	         }
	      }
	   }
	   public void paint(Graphics gs) {
	      dbImage = createImage(w, h);
	      dbg = dbImage.getGraphics();
	      paintComponent(dbg);
	      gs.drawImage(dbImage, 0, 0, this);
	   }

	   public void paintComponent(Graphics gs){
	      gs.setFont(font);

	      gs.setColor(Color.white);
	      gs.fillRect(0, 0, w, h);


	      gs.drawImage(back, 1245, 0, 30, 30, null);
	      gs.drawImage(I, x, y, xw, xh, this);

	      gs.setColor(Color.black);
	      if(start==false)
	         gs.drawString("게임시작 : Enter", 2, 62);
	      gs.drawString(" 점수 : " + r_score, 2, 90);
	      if(end) {
	         getexp();
	         end = false;
	         r_id = JOptionPane.showInputDialog("이름을 입력하세요");
	         insert();
	         String[] buttons = {"보고 다시하기", "그냥 다시하기"};
	         int ok = JOptionPane.showOptionDialog(null, "랭킹을 보시겠습니까?", "랭킹 확인", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, "다시하기");
	         if(ok == JOptionPane.OK_OPTION) {
	            new Rank1();
	         }

	      }


	      //gs.drawImage(I, x, y, xw, xh, null);
	      for(int i = 0; i < msList.size(); i++) {
	         Ms m = (Ms)msList.get(i);
	         gs.setColor(Color.red);
	         gs.drawOval(m.x, m.y, m.w, m.h);
	         if(m.y < 0) {msList.remove(i);}
	         m.moveMs();
	      }
	      gs.setColor(Color.black);
	      for(int i = 0; i < enList.size(); i++) {
	         Enemy e = (Enemy)enList.get(i);
	         gs.drawImage(em, e.x, e.y, e.w, e.h, this);
	         if(e.y > h) {enList.remove(i); }
	         e.moveEn();
	      }

	   }

	   public void keyControl() {
	      if(0 < x) {
	         if(left) x -= 3;
	      }
	      if(w > x + xw) {
	         if(right) x += 3;
	      }
	      if(25 < y) {
	         if(up) y -= 3;
	      }
	      if(h > y + xh) {
	         if(down) y += 3;
	      }
	   }
	   public class AL implements KeyEventDispatcher {
	      public boolean dispatchKeyEvent(KeyEvent ke) {
	         if(ke.getID() == KeyEvent.KEY_PRESSED) {
	            switch(ke.getKeyCode()) {
	            case KeyEvent.VK_LEFT:
	               left = true;
	               break;
	            case KeyEvent.VK_RIGHT:
	               right = true;
	               break;
	            case KeyEvent.VK_UP:
	               up = true;
	               break;
	            case KeyEvent.VK_DOWN:
	               down = true;
	               break;
	            case KeyEvent.VK_A:
	               fire = true;
	               break;
	            case KeyEvent.VK_ENTER:
	               start = true;
	               end = false;
	               r_score = 0;
	               break;
	            }
	         }
	         if(ke.getID() == KeyEvent.KEY_RELEASED) {
	            switch(ke.getKeyCode()) {
	            case KeyEvent.VK_LEFT:
	               left = false;
	               break;
	            case KeyEvent.VK_RIGHT:
	               right = false;
	               break;
	            case KeyEvent.VK_UP:
	               up = false;
	               break;
	            case KeyEvent.VK_DOWN:
	               down = false;
	               break;
	            case KeyEvent.VK_A:
	               fire = false;
	               break;
	            }
	         }
	         return false;
	      }
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

	   private void init() {
	      try {
	         sql = "select * from mini1rank";
	         rs = stmt.executeQuery(sql);  // 원하는 쿼리문 실행
	      }catch (Exception e) {
	         System.out.println("mini1rank 테이블 불러오기 실패" + e);
	      }
	   }

	   private void insert() {
	      try {
	         sql = "insert into mini1rank values('" + r_id + "', " + r_score + ");";
	         int insert = stmt.executeUpdate(sql);
	         System.out.println("데이터 입력 성공 유무(insert) =>" + insert);
	         init();
	      } catch (Exception e) {
	         System.out.println("insert() 메서드 오류 발생 :" + e);
	      }
	   }
	   private void getexp() {
	      if(r_score >= 10)
	         exp += 1;
	      else if(r_score >=20)
	         exp += 5;
	      else if(r_score >=30)
	         exp += 10;
	      else if(r_score >=40)
	         exp += 20;
	   }
	   public void tired() {
	      try {
	         sql = "update progress set p_tired=p_tired+20 where u_id='"+userText.getText()+"';";
	         double tired = stmt.executeUpdate(sql);
	         rs = stmt.executeQuery("SELECT * FROM progress where u_id = '"+userText.getText()+"'");
	         rs.next();
	         tired = rs.getDouble("p_tired");
	         if(tired >= 50){
	            JOptionPane sleep = new JOptionPane(); 
	            JOptionPane.showMessageDialog(null, "피로도가 최대치에 도달해 잠을 잡니다");
	            add(sleep);
	            stmt.executeUpdate("update progress set p_tired=0, p_time=p_time-1 where u_id='"+userText.getText()+"'");
	            tired=0;   day-=1;
	         }
	         System.out.println("데이터 입력 성공 유무(tired) =>" + tired);
	         init();
	      } catch (Exception e) {
	         System.out.println("tired() 메서드 오류 발생 :" + e);
	      }
	   }

	}

	class Ms {
	   int x;
	   int y;
	   int w = 5;
	   int h = 5;
	   public Ms(int x, int y) {
	      this.x = x;
	      this.y = y;
	   }
	   public void moveMs() {
	      y--;
	   }
	}

	class Enemy {
	   int x;
	   int y;
	   int w = 15;
	   int h = 15;
	   public Enemy(int x, int y) {
	      this.x = x;
	      this.y = y;
	   }
	   public void moveEn() {
	      y++;
	   } 
	}

