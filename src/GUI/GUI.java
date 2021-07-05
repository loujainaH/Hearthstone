package GUI;


import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;



public class GUI extends JFrame{
    private JFrame hero;
    private JTextArea text;
    private JTextArea text2;
    private ImageIcon x;
    private JLabel y;
    
public GUI(){
    super();
    hero =new JFrame();
    int width=Toolkit.getDefaultToolkit().getScreenSize().width;
    int height=Toolkit.getDefaultToolkit().getScreenSize().height;
    hero.setSize(width,(int) (height-height*0.05));
    this.setTitle("Hearthstone");
    this.setBounds(0,0,width,(int) (height-height*0.04));
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
     
    x = new ImageIcon("images/background.png");
    y = new JLabel(x);
    y.setVisible(true);
    try {
        final Image background = javax.imageio.ImageIO.read(new File("images/background.png"));
        setContentPane(new JPanel(new BorderLayout()) {
        public void paintComponent(Graphics y) {
                y.drawImage(background, 0, 0, null);
            }
        });
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
    
    hero.add(y);
    this.setResizable(false);
    this.revalidate();
    this.repaint();
    this.setVisible(true);

}
public static void main(String[]args) {
    new GUI();
} 
}
