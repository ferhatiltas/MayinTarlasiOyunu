package code;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;



class Mains implements ActionListener, MouseListener {

	
       private JFrame screen = null;
       private boolean dead;
       private JButton smiley = new JButton("");
       private JPanel composite = new JPanel();
       private JPanel topPanel = new JPanel();
       private JLabel time=new JLabel("0");
       
       Date startDate=new Date(0);
       			//iconlara önce null deðerini atayýp sonra iconlarýný tanýmlayacaðýz
          ImageIcon smileyImageIcon = null;
          ImageIcon tImageIcon = null;
          ImageIcon pitImageIcon = null;
          ImageIcon lossImageIcon = null;
          ImageIcon cryImageIcon = null;
          ImageIcon oneImageIcon = null;
          ImageIcon twoImageIcon = null;
          ImageIcon threeImageIcon = null;
          ImageIcon fourImageIcon = null;
          ImageIcon fiveImageIcon = null;

          private int btnwdth = 20; //oyun alanýnndaki ýzgaradaki geniþliði
    
          private int btnhgt = 20;  //oyun alanýnndaki ýzgaradaki yüksekliði
    
          private int mines = 16; // oyun baþlar baþlamaz oyundaki mayýn sayýsý
    
          int mineArray[][]; 
    
          JButton button[][];
          JPanel minespan = null;
   
         
       public Mains() { //oyun alanýnda üst orta kýsmýna MineSwepper yazýyoruz
              screen = new JFrame("                                                                     MineSweeper");
              screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
              screen.setVisible(true);
              screen.setResizable(false);
              screen.setJMenuBar(createMenuBar());
              loadMinesweeperImages();
              time.setLayout(new  BorderLayout());
              time.add(topPanel, BorderLayout.SOUTH);
              
              composite.setLayout(new BorderLayout());
              smiley.setPreferredSize(new Dimension(45, 45));
              smiley.setIcon(smileyImageIcon); // oyunda alt-ortaya bir buton tanýmlayýp 
                                                //o buttonun içine smiley iconunu yerleþtiriyoruz
              topPanel.add(smiley); 
              composite.add(topPanel, BorderLayout.SOUTH);

              smiley.addActionListener(this);
              smiley.addMouseListener(this);
              arrangeButtons();
              screen.add(composite);
              screen.pack();
     
       }
   
    
       public void loadMinesweeperImages() {
    	   
    	   //Bütün iconlarý src/gfx adlý paketin içine yerleþtirip iconlarý tanýmlýyoruz
    	   smileyImageIcon=getScaledImage("src/gfx/smiley.png");
           tImageIcon = getScaledImage("src/gfx/t.png");
           pitImageIcon = getScaledImage("src/gfx/pit.png");
           lossImageIcon = getScaledImage("src/gfx/loss.png");
           cryImageIcon = getScaledImage("src/gfx/cry.png");
           oneImageIcon = getScaledImage("src/gfx/1.png");
           twoImageIcon = getScaledImage("src/gfx/2.png");
           threeImageIcon = getScaledImage("src/gfx/3.png");
           fourImageIcon = getScaledImage("src/gfx/4.png");
           fiveImageIcon = getScaledImage("src/gfx/5.png");

       }

       public JMenuBar createMenuBar() {
 
    	      	//Oyunda sol üst köþeye açýlýr pencere þeklinde buttonlar yerleþtiriyoruz
              JMenuBar mBar = new JMenuBar();
              JMenu game = new JMenu("Game");
 
              final JMenuItem miNew = new JMenuItem("New"); // açýlýr pencerelere istediðimiz isimleri verebiliriz
              final JMenuItem miBeg = new JMenuItem("Beginner");
              final JMenuItem miInter = new JMenuItem("Intermediate");
              final JMenuItem miExp = new JMenuItem("Expert");
              final JMenuItem miExit = new JMenuItem("Exit");
 
              game.add(miNew); //tanýmladýðým isimlerin gözükmesi için JMenu içine ekliyoruz
              game.add(miBeg);
              game.add(miInter);
              game.add(miExp);
              game.add(miExit);      

              ActionListener MENULSTNR = new ActionListener() {
 
                     public void actionPerformed(ActionEvent ae) {
                           if (miNew == ae.getSource()) { // açýlýr pencerede new buttonuna týklandýðinda buttonlarýn
                        	   								// geniþliði, yüksekliði ve bomba sayýsýný tanýmlýyoruz 
                        	   btnwdth = 20;				
                                  btnhgt = 20;
                                  mines = 10;
                                  reset();
                           }
                           if (miBeg == ae.getSource()) { // açýlýr pencerede Beginner buttonuna týklandýðinda buttonlarýn
  								                           // geniþliði, yüksekliði ve bomba sayýsýný tanýmlýyoruz diðer iflerde de ayný iþlemi yapýyoruz
                        	    
                                  btnwdth = 9;
                                  btnhgt = 9;
                                  mines = 10;
                                  reset();
 
                           }
                           if (miInter == ae.getSource()) {
                                  btnwdth = 16;
                                  btnhgt = 16;
                                  mines = 40;
                                  reset();
 
                           }
                           if (miExp == ae.getSource()) {
                                  btnwdth = 16;
                                  btnhgt = 30;
                                  mines = 99;
                                  reset();
                           }
                           if (miExit == ae.getSource()) {
                                  if (screen != null) {
                                         screen.dispose();
                                  }
                                  System.exit(0);
                           }
                     }
 
              };
 
              miNew.addActionListener(MENULSTNR); //Açýlýr penceredeki butonlarý listeliyoruz
              miBeg.addActionListener(MENULSTNR);
              miInter.addActionListener(MENULSTNR);
              miExp.addActionListener(MENULSTNR);
              miExit.addActionListener(MENULSTNR);
              mBar.add(game);
              return mBar;
       }
 
 
       public void arrangeButtons() {
              mineArray = new int[btnwdth][btnhgt];
              button = new JButton[btnwdth][btnhgt];
              boolean starting = true;
              if (minespan != null) {
                     composite.remove(minespan);
                     minespan = null;
                     starting = false;
 
              }
              minespan = new JPanel();
              minespan.setLayout(new GridLayout(btnwdth, btnhgt));
 
              for (int i = 0; i < btnwdth; i++) {
                     for (int j = 0; j < btnhgt; j++) {
                           mineArray[i][j] = 0;
                           button[i][j] = new JButton("");
                           button[i][j].setBackground(Color.lightGray); //ýzgaradaki renk ve diðer ayarlamalar
                           button[i][j].setPreferredSize(new Dimension(20,20));
                           button[i][j].addActionListener(this);
                           button[i][j].addMouseListener(this);
                           minespan.add(button[i][j]);
                     }
              }
 
              minespan.setVisible(true);
              composite.add(minespan, BorderLayout.CENTER);
              if (starting) {
                     minesFormat(button);
              }
              screen.pack();
       }
 
       public void reset() {
              smiley.setIcon(smileyImageIcon);
              
              arrangeButtons();
              for (int i = 0; i < btnwdth; i++) {
                     for (int j = 0; j < btnhgt; j++) {
                           mineArray[i][j] = 0;
                           button[i][j].addActionListener(this);
                           button[i][j].addMouseListener(this);
                           button[i][j].setText("");
                           button[i][j].setBackground(Color.lightGray);
                           
                     }
              }
              minesFormat(button);
              System.out.println("");
              System.out.println("");
             
       }
 
	   public void minesFormat(JButton button[][]) {
              int mine[] = getRndmNos(btnwdth, btnhgt, mines);
              int count = 1;
              for (int i = 0; i < btnwdth; i++) {
                     for (int j = 0; j < btnhgt; j++)
 
                     {
 
                           for (int k = 0; k < mine.length && mine[k] != 0; k++) {
                                  if (count == mine[k]) {
                                         mineArray[i][j] = 9;
                                  }
                           }
                           count++;
                     }
              }
 
              int boxcount = 0;
              for (int i = 0; i < btnwdth; i++) {
                     for (int j = 0; j < btnhgt; j++) {
                           boxcount = 0;
 
                           if (mineArray[i][j] != 9) {
                                  if (i > 0 && j > 0) {
                                         if (mineArray[i - 1][j - 1] == 9)
                                                boxcount++;
                                  }
 
                                  if (i > 0) {
                                         if (mineArray[i - 1][j] == 9)
                                                boxcount++;
                                  }
 
                                  if (i > 0 && j < btnhgt - 1) {
                                         if (mineArray[i - 1][j + 1] == 9)
                                                boxcount++;
                                  }
 
                                  if (i < btnwdth - 1 && j > 0) {
                                         if (mineArray[i + 1][j - 1] == 9)
                                                boxcount++;
                                  }
                                  if (i < btnwdth - 1) {
                                         if (mineArray[i + 1][j] == 9)
                                                boxcount++;
                                  }
 
                                  if (i < btnwdth - 1 && j < btnhgt - 1) {
                                         if (mineArray[i + 1][j + 1] == 9)
                                                boxcount++;
                                  }
 
                                  if (j > 0) {
                                         if (mineArray[i][j - 1] == 9)
                                                boxcount++;
                                  }
                                  if (j < btnhgt - 1) {
                                         if (mineArray[i][j + 1] == 9)
                                                boxcount++;
                                  }
                                  mineArray[i][j] = boxcount;
                           }
                     }
              }
 
              for (int i = 0; i < btnwdth; i++) {
                     for (int j = 0; j < btnhgt; j++) {
                           System.out.print(" " + mineArray[i][j]);
                     }
                     System.out.println("");
              }
 
       }
 
       public int[] getRndmNos(int btnwdth, int btnhgt, int mines) {
              Random rand = new Random();
              int rndmines[] = new int[btnwdth * btnhgt];
              boolean in = false;
              int count = 0;
              while (count < mines) {
                     int rndno = (int) ((btnwdth * btnhgt) * (rand.nextDouble())) + 1;
                     in = false;
                     for (int i = 0; i < count; i++) {
                           if (rndmines[i] == rndno) {
                                  in = true;
                                  break;
                           }
                     }
                     if (!in) {
                           rndmines[count++] = rndno;
                     }
              }
              return rndmines;
       }
 
       public void actionPerformed(ActionEvent ae) {
 
              if (ae.getSource() == smiley) {
                     reset();
              } else {
                     for (int i = 0; i < btnwdth; i++)
                           for (int j = 0; j < btnhgt; j++) {
                                  if (button[i][j] == ae.getSource()) {
                                         if (mineArray[i][j] == 9) {
                                                for (int k = 0; k < btnwdth; k++) {
                                                       for (int l = 0; l < btnhgt; l++) {
 
                                                              if (mineArray[k][l] == 9) {
                                                                     button[k][l].setIcon(pitImageIcon);
                                                              }
 
                                                              button[k][l].removeActionListener(this);
                                                              button[k][l].removeMouseListener(this);
 
                                                       }
                                                }
 
                                         }
                                         if (mineArray[i][j] == 1) {
                                                button[i][j].setIcon((Icon) oneImageIcon);
                                         }
                                         if (mineArray[i][j] == 2) {
                                                button[i][j].setIcon(twoImageIcon);
                                         }
                                         if (mineArray[i][j] == 3) {
                                                button[i][j].setIcon(threeImageIcon);
                                         }
                                         if (mineArray[i][j] == 4) {
                                                button[i][j].setIcon(fourImageIcon);
                                         }
                                         if (mineArray[i][j] == 5) {
                                                button[i][j].setIcon(fiveImageIcon);
                                         }
                                         if (mineArray[i][j] == 0) {
                                                findAllEmpty(i, j);
                                         }
                                  }
                           }
              }
       }
 
       public void findAllEmpty(int boxX, int boxY) {
              int arrX[] = new int[(btnwdth) * (btnhgt)];
              int arrY[] = new int[(btnwdth) * (btnhgt)];
              int cntEmpty = 0;
              for (int i = 0; i < ((btnwdth) * (btnhgt)); i++) {
                     arrX[i] = -1;
                     arrY[i] = -1;
              }
              arrX[cntEmpty] = boxX;
              arrY[cntEmpty] = boxY;
              cntEmpty++;
 
              for (int i = 0; i < cntEmpty; i++) {
                     if (arrX[i] > 0) {
                           int xxX = arrX[i] - 1;
                           int yyY = arrY[i];
                           if (mineArray[xxX][yyY] == 0) {
                                  if (!findIn(arrX, arrY, cntEmpty, xxX, yyY)) {
                                         arrX[cntEmpty] = xxX;
                                         arrY[cntEmpty] = yyY;
                                         cntEmpty++;
                                  }
                           }
                     }
 
                     if (arrX[i] < (btnwdth - 1)) {
                           int xxX = arrX[i] + 1;
                           int yyY = arrY[i];
                           if (mineArray[xxX][yyY] == 0) {
                                  if (!findIn(arrX, arrY, cntEmpty, xxX, yyY)) {
                                         arrX[cntEmpty] = xxX;
                                         arrY[cntEmpty] = yyY;
                                         cntEmpty++;
                                  }
                           }
                     }
 
                     if (arrY[i] > 0) {
                           int xxX = arrX[i];
                           int yyY = arrY[i] - 1;
                           if (mineArray[xxX][yyY] == 0) {
                                  if (!findIn(arrX, arrY, cntEmpty, xxX, yyY)) {
                                         arrX[cntEmpty] = xxX;
                                         arrY[cntEmpty] = yyY;
                                         cntEmpty++;
                                  }
                           }
                     }
 
                     if (arrY[i] < (btnhgt - 1)) {
                           int xxX = arrX[i];
                           int yyY = arrY[i] + 1;
                           if (mineArray[xxX][yyY] == 0) {
                                  if (!findIn(arrX, arrY, cntEmpty, xxX, yyY)) {
                                         arrX[cntEmpty] = xxX;
                                         arrY[cntEmpty] = yyY;
                                         cntEmpty++;
                                  }
                           }
                     }
              }
 
              for (int k = 0; k < cntEmpty; k++) {
                     button[arrX[k]][arrY[k]].setBackground(new Color(200, 200, 250));
              }
 
       }
 
       public boolean findIn(int[] arrX, int[] arrY, int cntEmpty, int xxX, int yyY) {
              int j = 0;
              for (j = 0; j < cntEmpty; j++) {
                     if ((arrX[j] == (xxX)) && (arrY[j] == (yyY))) {
                           return true;
                     }
              }
              return false;
       }
 
       public void mouseClicked(MouseEvent arg0) {
              
       }
 
       public void mousePressed(MouseEvent me) {
              for (int i = 0; i < btnwdth; i++)
                     for (int j = 0; j < btnhgt; j++) {
                           if (button[i][j] == me.getSource()) {
                                  smiley.setIcon(smileyImageIcon);  
                           }
                     }
 
              if (me.getSource() == smiley) {
                     smiley.setIcon(smileyImageIcon);
              }
       }
 
       public void mouseReleased(MouseEvent me) {

              if (me.getSource() == smiley) {
                     smiley.setIcon(smileyImageIcon);
              }
              for (int i = 0; i < btnwdth; i++)
                     for (int j = 0; j < btnhgt; j++) {
                           if (button[i][j] == me.getSource()) {
                                  if (mineArray[i][j] == 9) {
                                         smiley.setIcon(lossImageIcon);
                                  } else {
                                         smiley.setIcon(smileyImageIcon);
                                  }
                           }
 
                     }
                    
       }
       
       public ImageIcon getScaledImage(String imageString) {
          ImageIcon imageIcon = new ImageIcon(imageString);
          Image image = imageIcon.getImage(); 
          Image newimg = image.getScaledInstance(30,30,  java.awt.Image.SCALE_SMOOTH);
          imageIcon = new ImageIcon(newimg);
          return imageIcon;
       }
 
       public void mouseEntered(MouseEvent arg0) {
 
       }
 
       public void mouseExited(MouseEvent arg0) {
 
       }
}
