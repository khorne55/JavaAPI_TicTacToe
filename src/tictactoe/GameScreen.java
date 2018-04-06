package tictactoe;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameScreen extends JFrame implements ActionListener {

    private JLabel picPanel;
    private JButton next;
    private BufferedImage current;
    private int counter;
    
    public GameScreen() {
        counter = 8;
        setTitle("Hangman Main Panel Test");
        setBounds(20,20,500,600);
        picPanel = new JLabel();
        try {
            String path = "pics/" + counter + ".png";
            ClassLoader classLoader = getClass().getClassLoader();
            File picFile = new File(classLoader.getResource(path).getFile());
            current = ImageIO.read(picFile);
        } catch(IOException io) {
            System.out.println(io.getMessage());
        }
        picPanel = new JLabel(new ImageIcon(current));
        setLayout(new GridLayout(2,1));
        add(picPanel);
        
        next = new JButton("next");
        next.addActionListener(this);
        add(next);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    
    public static void main(String[] args) {
        GameScreen mytest = new GameScreen();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        if(source == next) {
            counter--;
            if(counter>=0) {
                try {
                    String path = "pictures/" + counter + ".png";
                    System.out.println(path);
                    ClassLoader classLoader = getClass().getClassLoader();
                    File picFile = new File(classLoader.getResource(path).getFile());
                    current = ImageIO.read(picFile);
                } catch(IOException io) {
                    System.out.println(io.getMessage());
                }
                picPanel.setIcon(new ImageIcon(current));
                picPanel.repaint();
            }
        }
    }
    
}