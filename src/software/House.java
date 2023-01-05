package software;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;


public class House extends JFrame{
   public static final int SCREEN_WIDTH = 1280; //상수는 전부 대문자
   public static final int SCREEN_HEIGHT = 720;
   private Image screenImage;
   private Graphics screenGraphic;
   private Image screen = new ImageIcon(this.getClass().getResource("images/screen.png")).getImage().getScaledInstance(SCREEN_WIDTH, SCREEN_HEIGHT, Image.SCALE_SMOOTH );
   private JLabel menuBar = new JLabel(new ImageIcon(this.getClass().getResource("images/menuBar.png"))); 
   private ImageIcon exitButtonEnteredImage = new ImageIcon(this.getClass().getResource("images/exitButtonEntered.png"));
   private ImageIcon exitButtonImage = new ImageIcon(this.getClass().getResource("images/exitButton.png")); 
   private JButton exitButton = new JButton(exitButtonImage);
   private int mouseX, mouseY;
   private ImageIcon startImage = new ImageIcon(this.getClass().getResource("images/start.PNG"));
   private ImageIcon startbImage = new ImageIcon(this.getClass().getResource("images/startb.png"));
   private JButton startbutton = new JButton(startImage);
   private ImageIcon doorImage = new ImageIcon(this.getClass().getResource("images/door.png"));
   private final JButton btnDoor = new JButton(doorImage);
   private ImageIcon charImage = new ImageIcon(this.getClass().getResource("images/charactor.png"));
   private JButton btnchar = new JButton(charImage);
   private ImageIcon mainImage = new ImageIcon(this.getClass().getResource("images/main.png"));
   private JButton btnmain = new JButton(mainImage);
   private ImageIcon bedImage = new ImageIcon(this.getClass().getResource("images/bed.png"));
   private JButton btnbed = new JButton(bedImage);
   private ImageIcon setImage = new ImageIcon(this.getClass().getResource("images/setting.png"));
   private JButton btnset = new JButton(setImage);
   private ImageIcon joinImage = new ImageIcon(this.getClass().getResource("images/join.png"));
   private JButton btnjoin = new JButton(joinImage);
   private JPasswordField passText;
   public static JTextField userText;
   public static ArrayList uid = new ArrayList();
   public static ArrayList upw = new ArrayList();
   public static ArrayList id = new ArrayList();
   public static double tired=0;
   public static int coin = 0;
   public static int day = 100;
   public static int lv = 0;
   public static int exp = 0;
   private boolean Iscoin = false;
   private boolean Islv = false;
   private final Font font = new Font("맑은 고딕", Font.BOLD | Font.ITALIC, 30);
   private final Font font1 = new Font("맑은 고딕", Font.BOLD | Font.ITALIC, 24);
   public Boolean finish_add=false;
   private map m = new map();
   private Shopping s = new Shopping();
   private School mini1 = new School();
   private Parttime mini2 = new Parttime();
   private End end = new End();
   private Start st = new Start();
  
   public House() {
      setUndecorated(true);// 메뉴바가 평소에는 보이지 않음
      setTitle("Graduate");// 생성자이름
      setSize(SCREEN_WIDTH, SCREEN_HEIGHT);// 사이즈
      setResizable(false);// 수정불가능
      setLocationRelativeTo(null);// 게임 창이 전축에 생성
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 창 종료시 프로그램 종료
      setVisible(true);// 게임 출력
      setBackground(new Color(0, 0, 0, 0));// 색이 하얀색으로 표시
      getContentPane().setLayout(null);
      
      m.btnHOME.setLocation(55, 555);
      m.btnSchool.setLocation(151, 83);
      m.btnPTJ.setLocation(1093, 555);
      m.btnSHOP.setLocation(954, 85);
      
      getContentPane().add(st);
      getContentPane().add(end);
      getContentPane().add(m);
      getContentPane().add(s);
      getContentPane().add(mini1);
      getContentPane().add(mini2);
      
      st.setVisible(true);
      end.setVisible(false);
      m.setVisible(false);
      s.setVisible(false);
      mini1.setVisible(false);
      mini2.setVisible(false);
      
      //////////  로그인  ///////////////////
      userText = new JTextField(20);
      userText.setBounds(740, 542, 201, 30);
      getContentPane().add(userText);
      userText.setVisible(true);
      
      passText = new JPasswordField(20);
      passText.setBounds(740, 584, 201, 30);
      getContentPane().add(passText);
      passText.setVisible(true);
      
      JLabel userLabel = new JLabel("User");
      userLabel.setBounds(664, 548, 62, 18);
      getContentPane().add(userLabel);
      userLabel.setVisible(true);
      
      JLabel passLabel = new JLabel("Pass");
      passLabel.setBounds(664, 590, 62, 18);
      getContentPane().add(passLabel);
      passLabel.setVisible(true);
      
      
      //////////////////////////////////////////////////////////////
      exitButton.setBounds(1245, 0, 30, 30);
      exitButton.setBorderPainted(false);
      exitButton.setContentAreaFilled(false);
      exitButton.setFocusPainted(false);
      
      exitButton.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseEntered(MouseEvent e) {//마우스 위치o 이미지 변화
            exitButton.setIcon(exitButtonEnteredImage);
            exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
         }
         @Override
         public void mouseExited(MouseEvent e) {//마우스 위치x 이미지 변화
            exitButton.setIcon(exitButtonImage);
            exitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
         }
         @Override
         public void mousePressed(MouseEvent e) {//프로그램 종료
            try { 
               Thread.sleep(1000);
            }catch (InterruptedException ex) {
               ex.printStackTrace();
            }
            System.exit(0);
         }
      });
      getContentPane().add(exitButton);
      
      
      btnset.setBounds(1200, 0, 30, 30);
      btnset.setBorderPainted(false);
      btnset.setContentAreaFilled(false);
      btnset.setFocusPainted(false);
      
      
      JPopupMenu setmenu = new JPopupMenu("Setting");
      JMenuItem join = new JMenuItem("새로 하기");
      join.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
             btnmain.setVisible(false);
             btnDoor.setVisible(false);
              btnbed.setVisible(false);
              btnchar.setVisible(false);
              btnjoin.setVisible(true);
              startbutton.setVisible(true);
              userText.setVisible(true);
              passText.setVisible(true);
              userLabel.setVisible(true);
              passLabel.setVisible(true);
              Iscoin=false;
          }
      });
      
      JMenuItem restart = new JMenuItem("이어하기");
      restart.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
             btnmain.setVisible(false);
             btnDoor.setVisible(false);
              btnbed.setVisible(false);
              btnchar.setVisible(false);
              btnjoin.setVisible(false);
              startbutton.setVisible(true);
              userText.setVisible(true);
              passText.setVisible(true);
              userLabel.setVisible(true);
              passLabel.setVisible(true);
              Iscoin=false;
          }
      });
     setmenu.add(restart);
     setmenu.add(join);
     
      btnset.addActionListener(new ActionListener() {    
         public void actionPerformed(ActionEvent e) {
             setmenu.show(btnset,0,30);
          }
       });
      getContentPane().add(btnset);
      btnset.setVisible(true);
      
      
      
      menuBar.setBounds(0, 0, 1280, 30);
      menuBar.addMouseMotionListener(new MouseMotionAdapter() {
         @Override
         public void mouseDragged(MouseEvent e) {
            int x = e.getXOnScreen();
            int y = e.getYOnScreen();
            setLocation(x - mouseX, y - mouseY);
         }
      });
      getContentPane().add(menuBar);

      startbutton.setBounds(1000, 500, 200, 200);
      startbutton.setBorderPainted(false);
      startbutton.setContentAreaFilled(false);
      startbutton.setFocusPainted(false);
      startbutton.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseEntered(MouseEvent e) {
            startbutton.setIcon(startbImage);
            startbutton.setCursor(new Cursor(Cursor.HAND_CURSOR));
         }

         @Override
         public void mouseExited(MouseEvent e) {
            startbutton.setIcon(startImage);
            startbutton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
         }

         public void mousePressed(MouseEvent e) {
           login();
           for(int i=0;i<uid.size();i++) {
              if(userText.getText().equals(uid.get(i)) && new String(passText.getPassword()).equals(upw.get(i))){
                 btnchar.setVisible(true);
                 btnDoor.setVisible(true);
                 btnmain.setVisible(true);
                 btnjoin.setVisible(false);
                 btnbed.setVisible(true);
                 startbutton.setVisible(false);
                 userText.setVisible(false);
                  passText.setVisible(false);
                  userLabel.setVisible(false);
                  passLabel.setVisible(false);
                 Iscoin = true;
                 GetId();
                 
              }
              
           }
         }
      });
      getContentPane().add(startbutton);
     
      btnjoin.setBorderPainted(false);
      btnjoin.setContentAreaFilled(false);
      btnjoin.setFocusPainted(false);
      btnjoin.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent arg0) {
              login();
              for(int i=0;i<uid.size();i++) {
                  if(userText.getText().equals(uid.get(i))){
                     JOptionPane rewrite = new JOptionPane(); 
                     JOptionPane.showMessageDialog(null, "이미 존재하는 id 입니다.");
                     getContentPane().add(rewrite);
                     
                  }
                  else{
                     finish_add=true;
                     btnjoin.setVisible(false);
                  }
              }
           }
        });
        btnjoin.setBounds(816, 636, 122, 38);
        getContentPane().add(btnjoin);
        
      
      btnDoor.setBounds(0, 155, 350, 500);
       btnDoor.setBorderPainted(false);
       btnDoor.setContentAreaFilled(false);
       btnDoor.setFocusPainted(false);
      getContentPane().add(btnDoor);
      
      btnDoor.addMouseListener(new MouseAdapter() {
          @Override
          public void mousePressed(MouseEvent e) {
            btnmain.setVisible(false);
             btnDoor.setVisible(false);
             btnbed.setVisible(false);
             btnchar.setVisible(false);
             m.setVisible(true);
             exitButton.setVisible(false);
             Iscoin = false;
          }
       });
      
      btnchar.setBorderPainted(false);
       btnchar.setBounds(800, 400, 130, 290);
       btnchar.setContentAreaFilled(false);
       btnchar.setFocusPainted(false);
      getContentPane().add(btnchar);
      btnchar.addMouseListener(new MouseAdapter() {
         public void mousePressed(MouseEvent e) {
            Iscoin = false;
              Islv = true;
         }
          @Override
          public void mouseReleased(MouseEvent e) {
             Iscoin = true;
              Islv = false;
          }
       });
      
      btnbed.setBorderPainted(false);
      btnbed.setBounds(700, 250, 570, 350);
      btnbed.setContentAreaFilled(false);
      btnbed.setFocusPainted(false);
     getContentPane().add(btnbed);
     
     btnbed.addMouseListener(new MouseAdapter() {
         @Override
         public void mousePressed(MouseEvent e) {
            if(tired>45){
               int result = JOptionPane.showConfirmDialog(null, "다음날로 이동하시겠습니까?", "잠자기", JOptionPane.YES_NO_OPTION);
               if(result == JOptionPane.YES_OPTION){
                  day--;
                  tired=0;
                  CoinDay_DB();
                  if(lv>=42 && day<=0){
                        end.setVisible(true);
                        btnmain.setVisible(false);
                           btnDoor.setVisible(false);
                           btnbed.setVisible(false);
                           btnchar.setVisible(false);
                           exitButton.setVisible(false);
                           Iscoin = false;
                  }
               }
               else
                  return;
            }
            else{
               JOptionPane no_sleep = new JOptionPane(); 
              JOptionPane.showMessageDialog(null, "피로도가 45이하여서 잠을 잘 수 없습니다");
              getContentPane().add(no_sleep);
            }
         }
      });
     
     
      btnmain.setBounds(0, 0, 1280, 720);
      btnmain.setBorderPainted(false);
      btnmain.setContentAreaFilled(false);
      btnmain.setFocusPainted(false);
      getContentPane().add(btnmain);
     
     btnmain.addMouseListener(new MouseAdapter() {
         @Override
         public void mousePressed(MouseEvent e) {
            if(Iscoin){
               coin +=1;
               tired +=0.5;
               CoinDay_DB();
               if(tired>=50){
                  JOptionPane sleep = new JOptionPane(); 
                  JOptionPane.showMessageDialog(null, "피로도가 최대치에 도달해 잠을 잡니다");
                 getContentPane().add(sleep);
                     day--;
                     tired=0;
                     CoinDay_DB();
                    if(lv>=42 && day<=0){
                       end.setVisible(true);
                    btnmain.setVisible(false);
                       btnDoor.setVisible(false);
                       btnbed.setVisible(false);
                       btnchar.setVisible(false);
                       exitButton.setVisible(false);
                       Iscoin = false;
                  }
               }
               
            }
         }
      });
      
      mini1.btnback.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            mini1.setVisible(false);
            exitButton.setVisible(true);
            m.setVisible(true);
         }
      });
      
      mini2.btnback.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
              mini2.setVisible(false);
              exitButton.setVisible(true);
              m.setVisible(true);
           }
        });
      
     
    btnDoor.setVisible(false);
    btnchar.setVisible(false);
    btnbed.setVisible(false);
    btnmain.setVisible(false);
    
    s.btnback.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
          s.setVisible(false);
            m.setVisible(true);
         }
      });
    
    m.btnback.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
          btnchar.setVisible(true);
          btnDoor.setVisible(true);
          btnmain.setVisible(true);
          btnbed.setVisible(true);
          exitButton.setVisible(true);
          m.setVisible(false);
          Iscoin = true;
       }
    });
    
    st.btnnext.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
           if(st.screen==st.d)
              st.setVisible(false);
         }
      });
    
    m.btnSHOP.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            exitButton.setVisible(false);
            s.setVisible(true);
             m.setVisible(false);
             
           }
        });
    
    m.btnHOME.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
          btnchar.setVisible(true);
          btnDoor.setVisible(true);
          btnbed.setVisible(true);
          btnmain.setVisible(true);
          exitButton.setVisible(true);
          m.setVisible(false);
          Iscoin = true;
         }
      });
    
    m.btnSchool.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
            exitButton.setVisible(false);
              mini1.setVisible(true);
              Thread t1 = new Thread(mini1);
              t1.start();
              m.setVisible(false);

       }
    });
          
    m.btnPTJ.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
           exitButton.setVisible(false);
            mini2.setVisible(true);
            Thread t2 = new Thread(mini2);
            t2.start();
            m.setVisible(false);

       }
    });
    

    end.btnend.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
           END_DB();
           System.exit(0);
        }
     });
   }
   
   
   public void paint(Graphics g) { // 약속된 메소드
      screenImage = createImage(SCREEN_WIDTH, SCREEN_HEIGHT);// 1280X700에 이미지 생성
      screenGraphic = screenImage.getGraphics();// 스크린 이미지를 이용하여 그래픽 개체 불러옴
      screenDraw(screenGraphic);// 그래픽에 그림을 그려줌
      g.drawImage(screenImage, 0, 0, null);// (0,0)위치에 screenImage생성
      if(Iscoin) {
         g.setFont(font);
         g.setColor(Color.black);
         g.drawString("D - " + day, 100, 60);
         g.drawString("coin : " + coin, 100, 100);
         g.drawString("피로도", 100, 140);
         g.drawRect(200,120,102,22);
         g.setColor(Color.gray);
         g.fillRect(201,121,(int)(100-tired*2), 20);
      }
      if(Islv){
         g.setFont(font1);
          g.setColor(Color.black);
          if(lv>=42)
         g.drawString("졸업 조건 완성", 100, 60);
          
          g.drawString("학점수 : " + lv, 100, 100);
          g.drawString("학점exp", 100, 140);
          g.drawRect(200,120,102,22);
          g.setColor(Color.gray);
          g.fillRect(201,121,(int)(100-exp*10), 20);
      }
    
      
   }

   public void screenDraw(Graphics g) {
      g.drawImage(screen, 0, 0, null);// 그리는 방법1
      paintComponents(g);// 그리는 방법2:항상 고정되는 것, 버튼이나 메뉴바
      this.repaint();
   }
  
   public void login() {
      Connection conn; 
       Statement stmt; 
       ResultSet rs; 
       try {
          Class.forName("com.mysql.jdbc.Driver");  // JDBC driver를 메모리에 로드
          conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/software", "root","apmsetup");
          stmt = (Statement) conn.createStatement();
          rs=stmt.executeQuery("SELECT * FROM user");
          
          while(rs.next()) {
                uid.add(rs.getString("u_id"));
                upw.add(rs.getString("u_pw"));
                
          }
          if( finish_add==true){
             stmt.executeUpdate("insert into user values('"+userText.getText()+"','"+new String(passText.getPassword()) +"')");
             finish_add=false;
          }
          rs.close();
          stmt.close(); 
          conn.close();
       } catch (ClassNotFoundException e) {
         System.out.println("JDBC 드라이버 로드 에러");
      } catch (SQLException e) {
         System.out.println("SQL 실행 에러");
      }
   }

   public void GetId(){ // 코인 데이 관련 함수
      int j;
      Statement stmt1;
       Connection conn; 
      ResultSet rs; 
     try {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/software", "root","apmsetup");
        stmt1 = (Statement) conn.createStatement();
       rs=stmt1.executeQuery("SELECT * FROM progress");
        
       while(rs.next()){
          id.add(rs.getString("u_id"));
       }
        for(j=0;j<id.size();j++) {
          if(userText.getText().equals(id.get(j)))
             break;
       }
       if(j==id.size()) {//이름 타임 코인
         stmt1.executeUpdate("insert into progress values('"+userText.getText()+"',100,0,0,0,0)");
       }
       else {
         ResultSet rs1 = stmt1.executeQuery("SELECT * FROM progress where u_id = '"+userText.getText()+"'");
         rs1.next();
         coin = rs1.getInt("p_coin");
         day = rs1.getInt("p_time");
         tired = rs1.getDouble("p_tired");
         lv=rs1.getInt("p_lv");
         exp = rs1.getInt("p_exp");
    
       }
       rs.close();
       stmt1.close();
       conn.close();
     } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
     }
   }
     
     public void CoinDay_DB(){ // 코인 데이 관련 함수
        Statement stmt1;
         Connection conn; 
       try {
          conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/software", "root","apmsetup");
         
          stmt1 = (Statement) conn.createStatement();
          stmt1.executeUpdate("update progress set p_coin='"+coin+"',p_time='"+day+"',p_tired='"+tired+"' where u_id='"+userText.getText()+"'");
          stmt1.close();
        
         conn.close();
       } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
       }  
   }
     public void END_DB(){
         Statement stmt1;
          Connection conn; 
        try {
           conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/software", "root","apmsetup");
          
           stmt1 = (Statement) conn.createStatement();
           stmt1.executeUpdate("update progress set p_coin=0,p_time=100,p_tired=0,p_lv=0,p_exp=0 where u_id='"+userText.getText()+"'");
           stmt1.close();
         
          conn.close();
        } catch (SQLException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
        }  
      }

   public static void main(String[] args) {         
         new House();

      }
}