package tictactoe;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Game {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Game window = new Game();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Game() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(3, 3, 0, 0));
		
		List<JButton> buttonList = new ArrayList<JButton>();
		int[] x=new int[3];
		int[] y=new int[3];
		
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
		JButton list = new JButton();
		x[i]=i;
		y[j]=j;
		buttonList.add(list);
		frame.getContentPane().add(list);
		list.setText(String.valueOf(i)+" "+String.valueOf(j));
		list.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					String[] text=list.getText().split(" ");
                                        TicTacToe game = new TicTacToe();
                                        TTTWebService myLink = game.getProxy();
					myLink.checkSquare(Integer.valueOf(text[0]), Integer.valueOf(text[1]), Integer.valueOf(ListGames.getGame()));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		}}
	}
	public JFrame getFrame() {
		return frame;
	}
}