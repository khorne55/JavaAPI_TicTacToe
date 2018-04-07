package tictactoe;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.List;

import javax.swing.SwingConstants;

public class ListGames {

	public static String gameCheck="0";
	
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListGames window = new ListGames();
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
	public ListGames() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton createButton = new JButton("Create Game");
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Create Game
				TicTacToe game = new TicTacToe();
                                TTTWebService myLink = game.getProxy();
				System.out.println(LoginWindow.getUser());

		        try {
		        	gameCheck=myLink.newGame(LoginWindow.getUser());
		        } catch (Exception ex) {
		            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
		        }
		        
		        frame.setVisible(false);
				Game game1=new Game();
				game1.getFrame().setVisible(true);
			}
		});
		frame.getContentPane().add(createButton);
		
		JButton joinButton = new JButton("Join Game");
		joinButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<String> userList = new ArrayList<String>();
				int counter=0;
				//Join Game
				frame.getContentPane().remove(createButton);
				frame.getContentPane().remove(joinButton);
				
                                TicTacToe game = new TicTacToe();
                                TTTWebService myLink = game.getProxy();
                                
				try {
					userList=Arrays.asList(myLink.showOpenGames().split(",|\\\n"));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				while(counter<userList.size()) {
					JButton list = new JButton("<html>Game Created by: "+userList.get(counter+1)+"<br>Game ID: "+userList.get(counter)+"<br>Creation Time: "+userList.get(counter+2)+" </html>");
					//gameCheck=Integer.valueOf(userList.get(counter-1));
					frame.getContentPane().add(list);
					counter+=3;
					list.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						frame.setVisible(false);
						String[] text=list.getText().split(" ");
						gameCheck=text[5];
						try {
							myLink.joinGame(LoginWindow.getUser(),Integer.valueOf(gameCheck));
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						Game game=new Game();
						game.getFrame().setVisible(true);
					}					
					});
				}
				frame.getContentPane().revalidate();
				frame.getContentPane().repaint();
			}
		});
		frame.getContentPane().add(joinButton);
	}
	
    public static String getGame() {
    	return gameCheck;
    }

	public JFrame getFrame() {
		return frame;
	}
}
