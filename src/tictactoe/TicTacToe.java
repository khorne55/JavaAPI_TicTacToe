/*
Tic Tac Toe Game

17/04/2018
Ricky Kearney - 14141647
Piotr Kurzynoga - 14143097

This is the main class for the Tic Tac Toe game
It holds the webservice proxy and allows other class's access the proxy
The login window is opened to allow the user to login.

 */
package tictactoe;

public class TicTacToe {

    private final TTTWebService proxy;
    private final TTTWebService_Service hm;

    // set hm and proxy variables
    public TicTacToe() {
        hm = new TTTWebService_Service();
        proxy = hm.getTTTWebServicePort();
    }

    // get the webservice proxy
    public TTTWebService getProxy() {
        return proxy;
    }

    public static void main(String[] args) {

        TicTacToe game = new TicTacToe();
        TTTWebService myLink = game.getProxy();

        LoginWindow login = new LoginWindow(); // open the login window
        login.show();
    }

}
