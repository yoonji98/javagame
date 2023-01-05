package software;
import static software.House.coin;
import static software.House.day;
import static software.House.userText;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import java.util.Timer;

import javax.swing.*;


public class Parttime extends JPanel implements Runnable {
  
    int x, y, xDirection, yDirection, rectX, rectY;
    int time = 60;
    Image dbImage;
    Graphics dbg;
    Image face, coins, back;    
    boolean mouseOnScreen;
    
    boolean won = false;
    private JTable table;
    private Vector s = new Vector<>();
    private Timer timer = new Timer();
    // 기본적인 설정
    private final int WIDTH = 1280;
    private final int HEIGHT = 720;
    private final int circleSize = 35;
    private final int rectSize = 35;
    private final Font font = new Font("맑은 고딕", Font.BOLD | Font.ITALIC, 30);
    
    
    private ImageIcon backImage = new ImageIcon(this.getClass().getResource("images/back.png"));
    public JButton btnback = new JButton(backImage);

    
    private String r_id;
    private int r_score = 0;
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private String sql;
    // 쓰레드 실행 함수
    public void run() {
       
        try {
        	time=60;
            while(true) {
                move();
                Thread.sleep(5);
            }
        }
        catch(Exception e) {
            System.out.println("오류가 발생했습니다.");
        }
    }
    
    // 실제로 캐릭터가 이동하는 함수
    public void move() {
        x += xDirection;
        y += yDirection;
        if(x <= 0)
            x = 0;
        if(x >= WIDTH - circleSize)
            x = WIDTH - circleSize;
        if(y <= circleSize)
            y = circleSize;
        if(y >= WIDTH - circleSize)
            y = WIDTH - circleSize;
    }
    
    // 방향 지정
    public void setXDirection(int xdir) {
        xDirection = xdir;
    }
    
    public void setYDirection(int ydir) {
        yDirection = ydir;
    }

    // 키보드 이벤트 처리
    public class AL implements KeyEventDispatcher {
        public boolean dispatchKeyEvent(KeyEvent e) {
            if(e.getID() == KeyEvent.KEY_PRESSED) {
                int keyCode = e.getKeyCode();
                if(keyCode == e.VK_LEFT)
                {
                    setXDirection(-1);
                }
                if(keyCode == e.VK_RIGHT)
                {
                    setXDirection(+1);
                }            
                if(keyCode == e.VK_UP)
                {
                    setYDirection(-1);
                }    
                if(keyCode == e.VK_DOWN)
                {
                    setYDirection(+1);
                }                
            }
            if(e.getID() == KeyEvent.KEY_RELEASED) {
                int keyCode = e.getKeyCode();
                if(keyCode == e.VK_LEFT)
                {
                    setXDirection(0);
                }
                if(keyCode == e.VK_RIGHT)
                {
                    setXDirection(0);
                }            
                if(keyCode == e.VK_UP)
                {
                    setYDirection(0);
                }    
                if(keyCode == e.VK_DOWN)
                {
                    setYDirection(0);
                }   
            }
            return false;
        }
    }
    
    public Parttime() {
    	DB();
    	time = 60;
   
        ImageIcon faceIcon = new ImageIcon(getClass().getResource("images/face.png"));
        face = faceIcon.getImage();
        ImageIcon coinIcon = new ImageIcon(getClass().getResource("images/coin.png"));
        coins = coinIcon.getImage();
        back = backImage.getImage();
    
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new AL());
        setSize(WIDTH, HEIGHT);
        setVisible(true);
        setLayout(null);
        
        btnback.setBounds(1245, 0, 30, 30);
        btnback.setBorderPainted(false);
        btnback.setContentAreaFilled(false);
        btnback.setFocusPainted(false);
        
        add(btnback);
     
         
        // 캐릭터 및 장애물 초기 설정
        x = WIDTH / 2;
        y = HEIGHT / 2;
        rectX = new Random().nextInt(WIDTH - rectSize);
        rectY = new Random().nextInt(HEIGHT - rectSize * 2) + rectSize;  
                     
    }
    
   
    public void paint(Graphics g) {
        dbImage = createImage(WIDTH, HEIGHT);
        dbg = dbImage.getGraphics();
        paintComponent(dbg);
        g.drawImage(dbImage, 0, 0, this);
    }
    public void Mytimer() {

  	  TimerTask task = new TimerTask() {
			@Override
			public void run() {
					time--;
		    		try {
		    			Thread.sleep(1000);
		    			} catch(InterruptedException ex) {}
		    			
			}
    	};
    	timer.schedule(task, 1000);
  }
  
  public void stop() {
      timer.cancel();
  }
  public void restart() {
  	time = 61;
  	r_score = 0;
  	  TimerTask task = new TimerTask() {
  			@Override
  			public void run() {
  		    		try {
  		    			Thread.sleep(1000);
  		    			} catch(InterruptedException ex) {}
  		    		time--;
  			}
      	};
      	
      timer = new Timer();
      timer.schedule(task, 1000);
  }
  
  
  public void paintComponent(Graphics g){
      // 캐릭터와 위치 정보 나타내기 그리고 코인 나타내기
      g.setFont(font);
      g.setColor(Color.WHITE);
      Mytimer();
      	
      // 승리 판정
      if(time == 0)
      {
    	  won = true;
    	  stop();
      }
      else {
      	g.drawString(" 점수 : " + r_score, 2, 62);
      	g.drawString(" 시간 : " + time, 2, 90);
      }
      
      g.drawImage(face, x, y, this);
      g.drawImage(coins, rectX, rectY, this);
      g.drawImage(back,1245, 0,this);
      // 코인과 캐릭터 충돌 감지
      if(rectX - circleSize < x && x < rectX + rectSize && rectY - circleSize < y && y < rectY + rectSize)
      {
          r_score++;
          rectX = new Random().nextInt(WIDTH - rectSize);
          rectY = new Random().nextInt(HEIGHT - rectSize * 2) + rectSize;     
      }
      
      if(won == true)
      {
    	stop();
    	getcoin();
    	tired();
      	won = false;
      	r_id = JOptionPane.showInputDialog("이름을 입력하세요");
      	insert();
      	String[] buttons = {"보고 다시하기", "그냥 다시하기"};
      	int ok = JOptionPane.showOptionDialog(null, "랭킹을 보시겠습니까?", "랭킹 확인", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, "다시하기");
          if(ok == JOptionPane.OK_OPTION) {
          	new Rank2();
          	restart();
          }
          else {
        	  restart();
          }
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
			sql = "select * from mini2rank";
			rs = stmt.executeQuery(sql);  // 원하는 쿼리문 실행
		}catch (Exception e) {
			System.out.println("mini2rank 테이블 불러오기 실패" + e);
		}
	}

  private void insert() {
  	try {
  		sql = "insert into mini2rank values('" + r_id + "', " + r_score + ");";
  	int insert = stmt.executeUpdate(sql);
		System.out.println("데이터 입력 성공 유무(insert) =>" + insert);
		init();
  	} catch (Exception e) {
			System.out.println("insert() 메서드 오류 발생 :" + e);
		}
  }
  private void getcoin() {
	  if(r_score >= 10)
		  coin += 500;
	  else if(r_score >=15)
		  coin +=1000;
	  else if(r_score >=20)
		  coin +=2000;
}
  private void tired() {
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
