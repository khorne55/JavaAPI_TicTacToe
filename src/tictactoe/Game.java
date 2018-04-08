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
                                String squareCheck="";
                                String xCheck=list.getText();
                                TicTacToe game = new TicTacToe();
                                TTTWebService myLink = game.getProxy();
                                if(!(xCheck.equals("X")) && !(xCheck.equals("O"))) {
                                    squareCheck=myLink.checkSquare(Integer.valueOf(text[0]), Integer.valueOf(text[1]), Integer.valueOf(ListGames.getGame()));
                                }
                                //Check if the square is not taken and if both players are connected to the game.
                                if(squareCheck.equals("0") && Integer.valueOf(myLink.getGameState(Integer.valueOf(ListGames.getGame())))==0) {
                                    myLink.takeSquare(Integer.valueOf(text[0]), Integer.valueOf(text[1]), Integer.valueOf(ListGames.getGame()), LoginWindow.getUser());
                                    if(ListGames.getXO()==1) {
                                        list.setText("X");  
                                    } else if (ListGames.getXO()==2) {
                                        list.setText("O");  
                                    }
                                    list.repaint();
                                    System.out.println("Win:"+myLink.checkWin(Integer.valueOf(ListGames.getGame())));
                                    String win=myLink.checkWin(Integer.valueOf(ListGames.getGame()));
                                    if(win.equals("1")) {
                                        System.out.println("Player 1 wins");
                                    } else if(win.equals("2")) {
                                        System.out.println("Player 2 wins");
                                    } else if(win.equals("3")) {
                                        System.out.println("Players drew");
                                    } 
                                } else {
                                    System.out.println("Square Taken");
                                }

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