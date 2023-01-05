package software;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.*;
import javax.swing.*;

public class End extends JPanel{
	public static final int SCREEN_WIDTH = 1280; 
	public static final int SCREEN_HEIGHT = 720;
    private Image screenImage;
   private Graphics screenGraphic;
   private ImageIcon nextImage = new ImageIcon(this.getClass().getResource("images/next.png"));
   public JButton btnnext = new JButton(nextImage);
  
   private ImageIcon endImage = new ImageIcon(this.getClass().getResource("images/endbt.png"));
   public JButton btnend = new JButton(endImage);
   private Image e1= new ImageIcon(this.getClass().getResource("images/end.png")).getImage();
   private Image e2=new ImageIcon(this.getClass().getResource("images/end3.png")).getImage();
   private Image e3=new ImageIcon(this.getClass().getResource("images/end2.png")).getImage();
   private Image screen;
   
 
	public End() {
		 setSize(SCREEN_WIDTH, SCREEN_HEIGHT);// 사이즈
	      setVisible(true);// 게임 출력
	      setBackground(new Color(0, 0, 0, 0));// 색이 하얀색으로 표시
	      setLayout(null);// 지정한 위치 그대로 설정
	     screen=e1;
	      btnnext.setBounds(824, 534, 272, 162);
	      btnnext.setBorderPainted(false);
	      btnnext.setContentAreaFilled(false);
	      btnnext.setFocusPainted(false);
	      btnnext.addActionListener(new ActionListener() {
	          public void actionPerformed(ActionEvent e) {
	        	
	        	  if(screen == e1){
	        		  screen=e2;
	        	  }
	        	  else if(screen == e2){
	        		  screen = e3;
	        		  btnnext.setVisible(false);
	        		  btnend.setBorderPainted(false);
	        		  btnend.setContentAreaFilled(false);
	        		  btnend.setFocusPainted(false);
	        		  btnend.setBounds(447, 290, 418, 245);
	        		  add(btnend);
	        	  }
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
