package software;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Start extends JPanel{
	public static final int SCREEN_WIDTH = 1280; 
	public static final int SCREEN_HEIGHT = 720;
    private Image screenImage;
    private Graphics screenGraphic;
    private ImageIcon nextImage = new ImageIcon(this.getClass().getResource("images/next.png"));
    public JButton btnnext = new JButton(nextImage);
   public Image screen;
   private Image a= new ImageIcon(this.getClass().getResource("images/1.png")).getImage();
   private Image b= new ImageIcon(this.getClass().getResource("images/2.png")).getImage();
   private Image c= new ImageIcon(this.getClass().getResource("images/3.png")).getImage();
   public Image d= new ImageIcon(this.getClass().getResource("images/4.png")).getImage();
   
	public Start() {
		 setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
	      setVisible(true);
	      setBackground(new Color(0, 0, 0, 0));
	      setLayout(null);
	      screen = a;
	      
	      btnnext.setBounds(939, 420, 282, 171);
	      btnnext.setBorderPainted(false);
	      btnnext.setContentAreaFilled(false);
	      btnnext.setFocusPainted(false);
	      btnnext.addActionListener(new ActionListener() {
	          public void actionPerformed(ActionEvent e) {
	        	  if(screen==a)
	        		 screen=b;
	        	  else if(screen==b)
	        		  screen=c;
	        	  else if(screen==c)
	        		  screen=d;
	        	 
	          }
	      });
	      
	      add(btnnext);
 
	      
	     
	}
	 public void paint(Graphics g) { // 약속된 메소드
	      screenImage = createImage(SCREEN_WIDTH, SCREEN_HEIGHT);// 1280X700에 이미지 생성
	      screenGraphic = screenImage.getGraphics();// 스크린 이미지를 이용하여 그래픽 개체 불러옴
	      screenDraw(screenGraphic);// 그래픽에 그림을 그려줌
	      g.drawImage(screenImage, 0, 0, null);// (0,0)위치에 screenImage생성
	 }
	private void screenDraw(Graphics g) {
		  g.drawImage(screen, 0, 0, null);// 그리는 방법1
	      paintComponents(g);// 그리는 방법2:항상 고정되는 것, 버튼이나 메뉴바
	      this.repaint();
		
		
	}
}
