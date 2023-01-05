package software;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class map extends JPanel{
	public static final int SCREEN_WIDTH = 1280; 
	public static final int SCREEN_HEIGHT = 720;
   private Image screenImage;
   private Graphics screenGraphic;

   private Image screen = new ImageIcon(this.getClass().getResource("images/map.png")).getImage();
   private JLabel menuBar = new JLabel(new ImageIcon(this.getClass().getResource("images/menuBar.png")));
   
   private ImageIcon backImage = new ImageIcon(this.getClass().getResource("images/back.png"));
   public JButton btnback = new JButton(backImage);
   private ImageIcon shoppingImage = new ImageIcon(this.getClass().getResource("images/shopping_image.png"));
   private ImageIcon parttimeImage = new ImageIcon(this.getClass().getResource("images/parttime_image.png"));
   private ImageIcon homeImage = new ImageIcon(this.getClass().getResource("images/home_image.png"));
   private ImageIcon schoolImage = new ImageIcon(this.getClass().getResource("images/school_image.png"));
   private int mouseX, mouseY;
   public JButton btnHOME = new JButton(homeImage);
   public JButton btnSHOP = new JButton(shoppingImage);
   public JButton btnPTJ = new JButton(parttimeImage);
   public JButton btnSchool = new JButton(schoolImage);
   
   public map() {
      setSize(SCREEN_WIDTH, SCREEN_HEIGHT);// 사이즈
      setVisible(true);// 게임 출력
      setBackground(new Color(0, 0, 0, 0));// 색이 하얀색으로 표시
      setLayout(null);// 지정한 위치 그대로 설정
      
      screen = new ImageIcon(this.getClass().getResource("images/map.png")).getImage();

      btnback.setBounds(1245, 0, 30, 30);
      btnback.setBorderPainted(false);
      btnback.setContentAreaFilled(false);
      btnback.setFocusPainted(false);
      add(btnback);
      
      menuBar.setBounds(0, 0, 1280, 30);// 메뉴바 크기 설정
      menuBar.addMouseListener(new MouseAdapter() {//메뉴바 좌표
         @Override
         public void mousePressed(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
      
         }
      });
      menuBar.addMouseMotionListener(new MouseMotionAdapter() {//메뉴바 드래그 가능
         @Override
         public void mouseDragged(MouseEvent e) {
            int x = e.getXOnScreen();
            int y = e.getYOnScreen();
            setLocation(x - mouseX, y - mouseY);
         }
      });
      add(menuBar);   
     
      ////////이동버튼 ///////////////
    
      
      btnHOME.setBounds(54, 447, 120, 120);
      btnHOME.setBorderPainted(false);
      btnHOME.setContentAreaFilled(false);
      btnHOME.setFocusPainted(false);
      add(btnHOME);
     
      btnSHOP.setBounds(1000, 500, 120, 120);
      btnSHOP.setBorderPainted(false);
      btnSHOP.setContentAreaFilled(false);
      btnSHOP.setFocusPainted(false);
      add(btnSHOP);
   
      btnPTJ.setBounds(950, 80, 100, 112);
      btnPTJ.setBorderPainted(false);
      btnPTJ.setContentAreaFilled(false);
      btnPTJ.setFocusPainted(false);
      add(btnPTJ);
     
      btnSchool.setBounds(100, 100, 100, 135);
      btnSchool.setBorderPainted(false);
      btnSchool.setContentAreaFilled(false);
      btnSchool.setFocusPainted(false);
      add(btnSchool);

   }


public void paint(Graphics g) { // 약속된 메소드
      screenImage = createImage(SCREEN_WIDTH, SCREEN_HEIGHT);// 1280X700에 이미지 생성
      screenGraphic = screenImage.getGraphics();// 스크린 이미지를 이용하여 그래픽 개체 불러옴
      screenDraw(screenGraphic);// 그래픽에 그림을 그려줌
      g.drawImage(screenImage, 0, 0, null);// (0,0)위치에 screenImage생성
   }

   public void screenDraw(Graphics g) {
      g.drawImage(screen, 0, 0, null);// 그리는 방법1
      paintComponents(g);// 그리는 방법2:항상 고정되는 것, 버튼이나 메뉴바
      this.repaint();
   }
}